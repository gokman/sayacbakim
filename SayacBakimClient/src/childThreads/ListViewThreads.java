package childThreads;

import android.os.Looper;

import com.aktekbilisim.SayacBakim.OrderListActivity;

public class ListViewThreads extends Thread {
	@Override
	public void run() 
	{
		Looper.prepare();

		OrderListActivity orderlist= new OrderListActivity();
		
	}
}
