package com.aktekbilisim.SayacBakim;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Objects.EnumWorkOrderStatus;
import Objects.WorkOrder;
import ServiceLocater.GlobalVariables;
import ServiceLocater.Service;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderListActivity extends ListActivity {
	private ProgressDialog m_ProgressDialog = null;
	private List<WorkOrder> m_orders = null;
	private OrderAdapter m_adapter;
	private Runnable viewOrders;
	Format formatter;
	private String datetime;
	private int StatusCode;
	public static TextView MapX ;
	public static TextView MapY ;
	TextView nbryesil ;
	TextView nbrmavi;
	TextView nbrsari;
	TextView nbrturuncu;
	TextView nbrSiyah;
	static int yesil;
	static int mavi;
	static int sari;
	static int turuncu;
	static int siyah;
	Activity activity =OrderListActivity.this;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		try
		{
		yesil=0;
		mavi =0;
		sari=0;
		turuncu=0;
		siyah=0;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		m_orders = new ArrayList<WorkOrder>();
		this.m_adapter = new OrderAdapter(this, R.layout.row, m_orders);
		setListAdapter(this.m_adapter);
		// get UserName
		Initiliaze();
		 
		SyncLocation();
		viewOrders = new Runnable()
		{
			public void run() {
				getOrdersEx();
			}
		};
		Thread thread = new Thread(null, viewOrders, "MagentoBackground");
		thread.start();
		m_ProgressDialog = ProgressDialog.show(OrderListActivity.this,
				"Lütfen Bekleyiniz...", "Bilgiler alýnýyor ...", true);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				GlobalVariables.getIntance().workorder = (WorkOrder) m_orders
						.get(position);
				Intent intent = new Intent(OrderListActivity.this,
						EditOrderActivity.class);
				startActivity(intent);
				
			}
		});
		
	
	} 
	catch (Exception e) 
    {
		
		Service.getIntance().ToastMessage(OrderListActivity.this, e.toString());
	}
	}

	private void setWorkOrderCount() 
	{
		
		// TODO Auto-generated method stub
		for (int i = 0; i < m_orders.size(); i++)
		{
			if (m_orders.get(i).getImageName() == "yesilNokta")
			{
				yesil++;
				nbryesil.setText(": "+Integer.toString(yesil));
			}
			if (m_orders.get(i).getImageName() == "sariNokta")
			{
				sari++;
				nbrsari.setText(": "+Integer.toString(sari));
			}
			if (m_orders.get(i).getImageName() == "siyahNokta")
			{
				siyah++;
				nbrSiyah.setText(": "+Integer.toString(siyah));
			}
			if (m_orders.get(i).getImageName() == "kirmiziNokta")
			{
				
			}
			if (m_orders.get(i).getImageName() == "maviNokta")
			{	
				mavi++;
				nbrmavi.setText(": "+Integer.toString(mavi));
			}
			if (m_orders.get(i).getImageName() == "turuncuNokta")
			{	
				turuncu++;
				nbrturuncu.setText(": "+Integer.toString(turuncu));
			}
		}
	}

	private void Initiliaze() {
		// TODO Auto-generated method stub
		MapX=(TextView) findViewById(R.id.lblMapX);
		MapY=(TextView) findViewById(R.id.lblMapY);
		nbrmavi=(TextView) findViewById(R.id.nbrmavi);
		nbrsari=(TextView) findViewById(R.id.nbrsari);
		nbrSiyah=(TextView) findViewById(R.id.nbrSiyah);
		nbrturuncu=(TextView) findViewById(R.id.nbrturuncu);
		nbryesil=(TextView) findViewById(R.id.nbryesil);
	}

	private void getOrders() {
		try {
			// Web Servis baðlantýsýný burada kurmak gerekiyor.
			 m_orders = new ArrayList<WorkOrder>();
			 m_orders=ServiceLocater.ServiceAydem.getIntance().LoadWorkOrdersOfUserEx(
					 GlobalVariables.getIntance().user.UserId,
					 GlobalVariables.getIntance().StatusCodeForFilter,
					 GlobalVariables.getIntance().datetimeForFilter,
					 GlobalVariables.getIntance().AboneNoForFilter);
			  
			 Thread.sleep(5000);
			Log.i("ARRAY", "" + m_orders.size());
		} catch (final Exception e) 
		{
			activity.runOnUiThread(new Runnable() 
			{
				
				public void run() {
					// TODO Auto-generated method stub
					Service.getIntance().ToastMessage(activity, "Data Aktarým hatasý Sistem Yöneticinize baþvurun!!! \n "+ e.toString());
				}
			});
			
		}
		ServiceLocater.GlobalVariables.WorkOrderList=(ArrayList<WorkOrder>) m_orders;
		Intent intent = new Intent(OrderListActivity.this,WorkOrderExList.class);
		startActivity(intent);
		finish();
		runOnUiThread(returnRes);
	}
	private void getOrdersEx() {
		try {
			// Web Servis baðlantýsýný burada kurmak gerekiyor.
			 m_orders = new ArrayList<WorkOrder>();
			 m_orders=ServiceLocater.ServiceAydem.getIntance().LoadWorkOrdersOfUserEx(
			 GlobalVariables.getIntance().user.UserId,
			 GlobalVariables.getIntance().StatusCodeForFilter,
			 GlobalVariables.getIntance().datetimeForFilter ,
			 GlobalVariables.getIntance().AboneNoForFilter);
			  
			 Thread.sleep(5000);
			Log.i("ARRAY", "" + m_orders.size());
		} catch (final Exception e) 
		{
			activity.runOnUiThread(new Runnable() 
			{
				
				public void run() {
					// TODO Auto-generated method stub
					Service.getIntance().ToastMessage(activity, "Data Aktarým hatasý Sistem Yöneticinize baþvurun!!! \n "+ e.toString());
				}
			});
			
		}
		ServiceLocater.GlobalVariables.WorkOrderList=(ArrayList<WorkOrder>) m_orders;
		Intent intent = new Intent(OrderListActivity.this,WorkOrderExList.class);
		startActivity(intent);
		finish();
	}
	private Runnable returnRes = new Runnable() {

		public void run() {
			if (m_orders != null && m_orders.size() > 0) {
				m_adapter.notifyDataSetChanged();
				for (int i = 0; i < m_orders.size(); i++)
				{
					m_adapter.add(m_orders.get(i));
				}
				setWorkOrderCount();
			}
			m_ProgressDialog.dismiss();
			m_adapter.notifyDataSetChanged();
		}
	};

	private class OrderAdapter extends ArrayAdapter<WorkOrder> {

		private List<WorkOrder> items;

		public OrderAdapter(Context context, int textViewResourceId,
				List<WorkOrder> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}
			WorkOrder o = items.get(position);
			if (o != null) {
				TextView lblCity = (TextView) v.findViewById(R.id.lblCity);
				TextView lblTown = (TextView) v.findViewById(R.id.lblTown);
				TextView lblMeterModel = (TextView) v.findViewById(R.id.lblMeterModel);
				TextView lblSerialId = (TextView) v.findViewById(R.id.lblSerialId);
				TextView lblStatus = (TextView) v.findViewById(R.id.lblStatus);
				TextView lblSubscriberNumber=(TextView) v.findViewById(R.id.lblSubscriberNumber);
				TextView lblElecCutIntrvl=(TextView) v.findViewById(R.id.lblElecCutIntrvl);
				ImageView imgStatus = (ImageView) v	.findViewById(R.id.imageView1);
				
				
				if (o.getImageName() == "yesilNokta")
				{
					imgStatus.setImageResource(R.drawable.yesilnokta);
				
				}
				if (o.getImageName() == "sariNokta")
				{
					imgStatus.setImageResource(R.drawable.sarinokta);
				
				}
				if (o.getImageName() == "siyahNokta")
				{
					imgStatus.setImageResource(R.drawable.siyahnokta);
			
				}
				if (o.getImageName() == "kirmiziNokta")
				{
					imgStatus.setImageResource(R.drawable.kirmizinokta);
				}
				if (o.getImageName() == "maviNokta")
				{
					imgStatus.setImageResource(R.drawable.mavinokta);
		
				}
				if (o.getImageName() == "turuncuNokta")
				{
					imgStatus.setImageResource(R.drawable.turuncunokta);
				}
				
				lblCity.setText(o.CityName);
				lblTown.setText(o.TownName);
				lblMeterModel.setText(o.MeterType);
				lblSerialId.setText(o.SerialNo);
				lblStatus.setText(o.Statu.toString());
				lblSubscriberNumber.setText(o.TesisatNo.toString());
				lblElecCutIntrvl.setText(o.CutInterval);
			}
			return v;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "Kapat").setIcon(R.drawable.kapat);
		menu.add(0, 2, 2, "Yeni Ýþ Emri Sorgula").setIcon(
				R.drawable.yeni_is_emri);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 1:
			 Intent intent = new Intent(Intent.ACTION_MAIN);
	    	 intent.addCategory(Intent.CATEGORY_HOME);
	    	 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	 startActivity(intent);

			//finish();
			return true;
		case 2:
			Intent intent2 = new Intent(getBaseContext(),FilterExActivity.class);
			startActivity(intent2);
			//finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public static void SyncLocation()
	{
		if(MapY!=null&&MapX!=null&&ServiceLocater.GlobalVariables.getIntance().MapX!=null&&ServiceLocater.GlobalVariables.getIntance().MapX!=null)
		{
			MapX.setText("X :" +ServiceLocater.GlobalVariables.getIntance().MapX);
			MapY.setText("Y :" +ServiceLocater.GlobalVariables.getIntance().MapY);
		}
	}
	
}
