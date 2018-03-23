package threadlocal;

/**
 * @author Administrator
 *
 */
public class ThreadLocalTest
{
	private static final ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>()
	{
		@Override
		protected Integer initialValue()
		{
			return 0;
		}
	};

	public int getNextNum()
	{
		seqNum.set(seqNum.get() + 1);
		return seqNum.get();
	}

	public static void main(String[] args)
	{
		ThreadLocalTest sn = new ThreadLocalTest();
		
		TestClient t1 = new TestClient(sn);
		TestClient t2 = new TestClient(sn);
		TestClient t3 = new TestClient(sn);
		
		t1.start();
		t2.start();
		t3.start();
	}

	private static class TestClient extends Thread
	{
		private ThreadLocalTest sn;

		public TestClient(ThreadLocalTest sn)
		{
			super();
			this.sn = sn;
		}



		@Override
		public void run()
		{
			for (int i = 0; i < 5; i++)
			{
				// 每个进程打出5个序列值 
				System.out.println(Thread.currentThread().getName() + "  --> " + sn.getNextNum());
			}
		}


	}
}
