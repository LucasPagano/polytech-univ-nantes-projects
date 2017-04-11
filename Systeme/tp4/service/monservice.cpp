#include <windows.h>
#include <time.h>
#include <direct.h>
#include <string>

//*********************************************************
// General management of the application

#define SERVICE_NAME "Test service"
#define FILE_LOG "test_svc.txt"

enum Order	// Order given in the command line
{
	SimpleApp,
	ServiceInstall,
	ServiceUninstall,
	ServiceStart,
	Other
};

bool Quiet=false;	// Display of message box style dialog to inform the user
char pModuleFile[MAX_PATH+1];	// Path of the current app

void LaunchApp(bool service);
void StopApp();
void SuspendApp();
void ResumeApp();

//*********************************************************
// Management of the win32 service

bool isService=false;	// Define if the current app run as a win32 service
SERVICE_STATUS serviceStatus;
SERVICE_STATUS_HANDLE hServiceStatusHandle;

void WINAPI NTServiceMain(DWORD dwArgc,LPTSTR *lpszArgv);
void WINAPI NTServiceHandler(DWORD fdwControl);
void Install(char *pName);
void Uninstall(char *pName);

SERVICE_TABLE_ENTRY DispatchTable[]=
{
	{SERVICE_NAME,NTServiceMain},
	{NULL,NULL}
};

//*********************************************************
// Main program

int APIENTRY WinMain(HINSTANCE hInstance,HINSTANCE hPrevInstance,LPSTR lpCmdLine,int nCmdShow)
{
	unsigned int sizeModuleFile;
	char c;
	Order theOrder;

	// Détermination des informations sur l'application en cours
	sizeModuleFile=GetModuleFileName(NULL,pModuleFile,MAX_PATH);
	pModuleFile[sizeModuleFile]='\0';
	while ((sizeModuleFile>0) && (pModuleFile[sizeModuleFile]!='\\')) sizeModuleFile--;
	c=pModuleFile[sizeModuleFile];
	pModuleFile[sizeModuleFile]='\0';
	_chdir(pModuleFile);
	pModuleFile[sizeModuleFile]=c;

	if (!strcmp(lpCmdLine,"i")) theOrder=ServiceInstall;
	else if (!strcmp(lpCmdLine,"u")) theOrder=ServiceUninstall;
	else if (!strcmp(lpCmdLine,"s")) theOrder=ServiceStart;
	else theOrder=SimpleApp;

	switch (theOrder)
	{
	case SimpleApp:
		LaunchApp(false);
		break;

	case ServiceInstall:
		if (Quiet) Install(SERVICE_NAME);
		else
		{
			if (MessageBox(NULL,"Voulez êtes sur le point d'installer le service",SERVICE_NAME,MB_OKCANCEL)==IDOK)
			{
				Install(SERVICE_NAME);
			}
		}
		break;

	case ServiceUninstall:
		if (Quiet) Uninstall(SERVICE_NAME);
		else
		{
			if (MessageBox(NULL,"Voulez êtes sur le point de désinstaller le service",SERVICE_NAME,MB_OKCANCEL)==IDOK)
			{
				Uninstall(SERVICE_NAME);
			}
		}
		break;

	case ServiceStart:
		isService=true;
		if (!StartServiceCtrlDispatcher(DispatchTable))
		{
			DWORD err=GetLastError();
			FILE *fp=fopen(FILE_LOG,"a");
			fprintf(fp,"StartServiceCtrlDispatcher failed, error code = %X\n",err);
			fclose(fp);
		}
	}
	return 0;
}

void LaunchApp(bool service)
{

}

void StopApp()
{
}

void SuspendApp()
{
}

void ResumeApp()
{
}

void Install(char* pName)
{
	SC_HANDLE schSCManager;
	std::string StrTemp;
	std::string Path;

	Path="\"";
	Path+=pModuleFile;
	Path+="\" s";

	if ((schSCManager=OpenSCManager(NULL,NULL,SC_MANAGER_ALL_ACCESS))==NULL)
	{
		StrTemp="OpenSCManager failed, error code = ";
		StrTemp+=GetLastError();
		MessageBox(NULL,StrTemp.c_str(),"Installation du service",MB_OK);
	}
	else
	{
		SC_HANDLE schService=CreateService(
			schSCManager,												// SCManager database
			pName,														// name of service
			pName,														// service name to display
			SERVICE_ALL_ACCESS,											// desired access
			SERVICE_WIN32_OWN_PROCESS | SERVICE_INTERACTIVE_PROCESS,	// service type
			SERVICE_AUTO_START,											// start type
			SERVICE_ERROR_NORMAL,										// error control type
			Path.c_str(),											// service's binary
			NULL,														// no load ordering group
			NULL,														// no tag identifier
			NULL,														// no dependencies
			NULL,														// LocalSystem account
			NULL);														// no password
		if (schService==NULL)
		{
			StrTemp="Failed to create service";
			StrTemp+=pName;
			MessageBox(NULL,StrTemp.c_str(),"Installation du service",MB_OK);
		}
		else
		{
			SERVICE_DESCRIPTION description;

			description.lpDescription="Description du service test";
			ChangeServiceConfig2(schService,SERVICE_CONFIG_DESCRIPTION,&description);

			if (!Quiet)
			{
				StrTemp="Service ";
				StrTemp+=pName;
				StrTemp+=" installed";
				MessageBox(NULL,StrTemp.c_str(),"Installation du service",MB_OK);
			}

			if (!StartService(schService,0,NULL))
			{
				StrTemp="Failed to start service ";
				StrTemp+=pName;
				MessageBox(NULL,StrTemp.c_str(),"Installation du service",MB_OK);
			}
			else
			{
				if (!Quiet)
				{
					StrTemp="Service ";
					StrTemp+=pName;
					StrTemp+=" started";
					MessageBox(NULL,StrTemp.c_str(),"Installation du service",MB_OK);
				}
			}

			CloseServiceHandle(schService); 
		}
		CloseServiceHandle(schSCManager);
	}
}

void Uninstall(char* pName)
{
	SC_HANDLE schSCManager;
	std::string StrTemp;

	if ((schSCManager=OpenSCManager(NULL,NULL,SC_MANAGER_CREATE_SERVICE))==NULL)
	{
		StrTemp="OpenSCManager failed, error code = ";
		StrTemp+=GetLastError();
		MessageBox(NULL,StrTemp.c_str(),"Désinstallation du service",MB_OK);
	}
	else
	{
		SC_HANDLE schService;

		if ((schService=OpenService(schSCManager,pName,SERVICE_ALL_ACCESS))==NULL)
		{
			StrTemp="OpenService failed, error code = ";
			StrTemp+=GetLastError();
			MessageBox(NULL,StrTemp.c_str(),"Désinstallation du service",MB_OK);
		}
		else
		{
			SERVICE_STATUS aServiceStatus;

			if (!QueryServiceStatus(schService,&aServiceStatus))
			{
				StrTemp="Failed to query status service ";
				StrTemp+=pName;
				MessageBox(NULL,StrTemp.c_str(),"Désinstallation du service",MB_OK);
			}
			else
			{
				bool notErr=true;

				if (aServiceStatus.dwCurrentState!=SERVICE_STOPPED)
				{
					ControlService(schService,SERVICE_CONTROL_STOP,&aServiceStatus);
				}

				if (!DeleteService(schService))
				{
					StrTemp="Failed to delete service";
					StrTemp+=pName;
					MessageBox(NULL,StrTemp.c_str(),"Désinstallation du service",MB_OK);
				}
				else
				{
					if (!Quiet)
					{
						StrTemp="Service ";
						StrTemp+=pName;
						StrTemp+=" removed";
						MessageBox(NULL,StrTemp.c_str(),"Désinstallation du service",MB_OK);
					}
				}
			}
			CloseServiceHandle(schService);
		}
		CloseServiceHandle(schSCManager);
	}
}

void WINAPI NTServiceMain(DWORD dwArgc,LPTSTR *lpszArgv)
{
	time_t ltime;
	FILE *fp;
	DWORD status=0;
	DWORD specificError=0xfffffff;

	serviceStatus.dwServiceType=SERVICE_WIN32 | SERVICE_INTERACTIVE_PROCESS;
	serviceStatus.dwCurrentState=SERVICE_START_PENDING;
	serviceStatus.dwControlsAccepted=SERVICE_ACCEPT_STOP | SERVICE_ACCEPT_PAUSE_CONTINUE;
	serviceStatus.dwWin32ExitCode=0;
	serviceStatus.dwServiceSpecificExitCode=0;
	serviceStatus.dwCheckPoint=0;
	serviceStatus.dwWaitHint=0;

	hServiceStatusHandle=RegisterServiceCtrlHandler(SERVICE_NAME,NTServiceHandler);
	if (hServiceStatusHandle==0)
	{
		FILE *fp=fopen(FILE_LOG,"a");
		fprintf(fp,"RegisterServiceCtrlHandler failed, error code = %X\n",GetLastError());
		fclose(fp);
		return;
	}

	// Initialization complete - report running status
	serviceStatus.dwCurrentState=SERVICE_RUNNING;
	serviceStatus.dwCheckPoint=0;
	serviceStatus.dwWaitHint=0;
	if(!SetServiceStatus(hServiceStatusHandle,&serviceStatus))
	{
		FILE *fp=fopen(FILE_LOG,"a");
		fprintf(fp,"SetServiceStatus failed, error code = %X\n",GetLastError());
		fclose(fp);
	}

	if (fp=fopen(FILE_LOG,"a"))
	{
		time(&ltime);
		fprintf(fp,"Service START (%s)",ctime(&ltime));
		fclose(fp);
	}

	LaunchApp(true);
}

void WINAPI NTServiceHandler(DWORD fdwControl)
{
	time_t ltime;
	FILE *fp;

	switch(fdwControl)
	{
	case SERVICE_CONTROL_STOP:
		serviceStatus.dwWin32ExitCode=0;
		serviceStatus.dwCurrentState=SERVICE_STOPPED;
		serviceStatus.dwCheckPoint=0;
		serviceStatus.dwWaitHint=0;

		StopApp();
		if (fp=fopen(FILE_LOG,"a"))
		{
			time(&ltime);
			fprintf(fp,"Service STOP (%s)\n",ctime(&ltime));
			fclose(fp);
		}
		break;

	case SERVICE_CONTROL_PAUSE:
		serviceStatus.dwCurrentState=SERVICE_PAUSED;

		if (fp=fopen(FILE_LOG,"a"))
		{
			time(&ltime);
			fprintf(fp,"Service PAUSE (%s)",ctime(&ltime));
			fclose(fp);
		}

		SuspendApp();
		break;

	case SERVICE_CONTROL_CONTINUE:
		serviceStatus.dwCurrentState=SERVICE_RUNNING;

		if (fp=fopen(FILE_LOG,"a"))
		{
			time(&ltime);
			fprintf(fp,"Service RESUME (%s)",ctime(&ltime));
			fclose(fp);
		}

		ResumeApp();
		break;

	case SERVICE_CONTROL_INTERROGATE:
		break;

	default:
		if ((fdwControl>=128) && (fdwControl<256))
		{
			int nIndex=fdwControl & 0x7F;
			//TODO: Implémenter messages optionnels envoyés au service
		}
		else
		{
			FILE *fp=fopen(FILE_LOG,"a");
			fprintf(fp,"Unrecognized opcode %X\n",fdwControl);
			fclose(fp);
		}
	}

	if (!SetServiceStatus(hServiceStatusHandle, &serviceStatus))
	{
		FILE *fp=fopen(FILE_LOG,"a");
		fprintf(fp,"SetServiceStatus failed, error code = %X\n",GetLastError());
		fclose(fp);
	}
}
