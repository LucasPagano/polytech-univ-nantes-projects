cmake_minimum_required(VERSION 3.6)
project(untitled)

set(CMAKE_C_STANDARD 99)

set(SOURCE_FILES lecteurs-redacteurs-moniteur.c lecteurs-redacteurs-mutex.c macros-thread.h semaphore-moniteur.c semaphore-moniteur.h tprintf.c tprintf.h)
add_executable(untitled ${SOURCE_FILES})