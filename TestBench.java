
public class TestBench implements Runnable {
	LRUCache lr=new LRUCache(5);
	void testcase1()
	{
		LRUCache lr1=new LRUCache(5);
		lr1.put(1, 1);
		lr1.put(2,2);
		lr1.put(3, 3);
		lr1.put(4, 4);
		lr1.put(5, 5);
		int val=lr1.get(1);
		System.out.println("key 1 value: "+val);
		val=lr1.get(2);
		System.out.println("key 2 value: "+val);
		val=lr1.get(3);
		System.out.println("key 3 value: "+val);
		val=lr1.get(4);
		System.out.println("key 4 value: "+val);
		lr1.put(6, 6);
		int val2=lr1.get(5);
		System.out.println("key 5 value after LRU replacement: "+val2);
	}
	public void run()
	{
		System.out.println("This is currently running on a separate thread, " +  
	            "the id is: " + Thread.currentThread().getId());
		if(Thread.currentThread().getId()==8)
		{
			lr.put(1, 1);
			lr.put(2, 1);
			try{Thread.sleep(300);}catch(Exception e){}
			int val=lr.get(2);
			int val2=lr.get(6);
			System.out.println("key 2 value in thread "+Thread.currentThread().getId()+": "+val);
			System.out.println("key 6 value in thread "+Thread.currentThread().getId()+": "+val2);
		}
		else
		{
			lr.put(1, 1);
			lr.put(2, 2);
			lr.put(3, 3);
			lr.put(4, 4);
			lr.put(5, 5);
			lr.put(6, 6);
			int val= lr.get(2);
			System.out.println("key 2 value in thread "+Thread.currentThread().getId()+": "+val);
			
		}
		
		
	}
	public static void main(String[] args){
		TestBench t= new TestBench();
		System.out.println("TEST CASE-1: Single thread, showing LRU replacement\n");
		t.testcase1();
		System.out.println("\nTEST CASE-2: 2 threads share same cache, update a key with different values\n");
		TestBench t1= new TestBench();
		Thread t11=new Thread(t1);
		Thread t12=new Thread(t1);
		t11.start();
		t12.start();	
		
	}
}
