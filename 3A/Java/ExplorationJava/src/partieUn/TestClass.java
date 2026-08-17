package partieUn;


public class TestClass implements Cloneable{
	int val;
	TestClass previous;
	
	static TestClass last = new TestClass();
	
	public TestClass() {
		val = 10;
		previous = last;
		last = this;
	}
	
	public TestClass(TestClass p){
		val = 10;
		previous = p;
		last = this;
	}
	
	public Object myClone() throws CloneNotSupportedException{
		return this.clone();
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("RIP");
	}
	
}