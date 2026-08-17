package partieUn;

public class Main {

	public static void main(String[] args) throws CloneNotSupportedException {
//		StringBuilder sBuild = new StringBuilder("Coucou");
//		String str = new String("Coucou");
//		System.out.println(sBuild.toString().equals(str));
//		
//		Object test2 = test.myClone();
//		System.out.println(test2.getClass());
		
		TestClass test = new TestClass();
		int i = 0;
		while(true){
			test = new TestClass();
			i++;
			if (i%100000 == 0){
				System.out.println(i);
			}
		}
	}

}
