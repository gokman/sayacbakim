package com.aktekbilisim.SayacBakim;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import Objects.EnumWorkOrderStatus;
import Objects.WorkOrderDescription;
import ServiceLocater.Service;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DescriptionHistory extends ListActivity {
	private ProgressDialog m_ProgressDialog = null;
	private List<WorkOrderDescription> m_orders = null;
	private OrderAdapter m_adapter;
	private Runnable viewOrders;
	Format formatter;
	private String datetime;
	private int StatusCode;
	public static TextView MapX ;
	public static TextView MapY ;
	public String WorkOrderId;
	private Button close;
	Activity activity =DescriptionHistory.this;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		try
		{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deschislistview);
		m_orders = new ArrayList<WorkOrderDescription>();
		this.m_adapter = new OrderAdapter(this, R.layout.deschistoryrow, m_orders);
		setListAdapter(this.m_adapter);
		// get UserName
		MapX=(TextView) findViewById(R.id.lblMapX);
		MapY=(TextView) findViewById(R.id.lblMapY);
		SyncLocation();
		close= (Button)findViewById(R.id.btnClose);
		Bundle extras= getIntent().getExtras();
		WorkOrderId = extras.getString("WorkOrderId").toString();
		viewOrders = new Runnable()
		{
			public void run() 
			{
				getOrders();
			}
		};
		Thread thread = new Thread(null, viewOrders, "MagentoBackground");
		thread.start();
		m_ProgressDialog = ProgressDialog.show(DescriptionHistory.this,
				"Lütfen Bekleyiniz...", "Bilgiler alýnýyor ...", true);
		
		
		close.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
	} 
	catch (Exception e) 
    {
		Intent intent = new Intent(DescriptionHistory.this, ErrorActivity.class);
		intent.putExtra("Error_Message", e.toString());
		intent.putExtra("ErrorType", 0);
		startActivity(intent);
	}
	}

	private void getOrders() 
	{
		try {
			// Web Servis baðlantýsýný burada kurmak gerekiyor.
			m_orders = new ArrayList<WorkOrderDescription>();
			m_orders=ServiceLocater.Service.getIntance().getWorkOrderDescriptions(WorkOrderId);

			Thread.sleep(5000);
			Log.i("ARRAY", "" + m_orders.size());
		} catch (final Exception e) 
		{
			activity.runOnUiThread(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					Service.getIntance().ToastMessage(activity, "Data Aktarým hatasý Sistem Yöneticinize baþvurun!!! \n "+ e.toString());
				}
			});
			
		}
		runOnUiThread(returnRes);
	}

	private Runnable returnRes = new Runnable() {

		public void run() {
			if (m_orders != null && m_orders.size() > 0) {
				m_adapter.notifyDataSetChanged();
				for (int i = 0; i < m_orders.size(); i++)
					m_adapter.add(m_orders.get(i));
			}
			m_ProgressDialog.dismiss();
			m_adapter.notifyDataSetChanged();
		}
	};

	private class OrderAdapter extends ArrayAdapter<WorkOrderDescription> 
	{

		private List<WorkOrderDescription> items;

		public OrderAdapter(Context context, int textViewResourceId,
				List<WorkOrderDescription> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.deschistoryrow, null);
			}
			WorkOrderDescription o = items.get(position);
			if (o != null) {
				TextView lblworkorderStatu = (TextView) v.findViewById(R.id.lblworkorderStatu);
				TextView lblDecriptions = (TextView) v.findViewById(R.id.lblDecriptions);
				TextView lblupdateDate = (TextView) v.findViewById(R.id.lblupdateDate);
				lblworkorderStatu.setText(EnumWorkOrderStatus.getWorkOrderStatus(Integer.toString(o.WorkOrderStatu)));
				lblDecriptions.setText(o.Description);
				lblupdateDate.setText(o.UpdateDate);
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
			Intent intent2 = new Intent(getBaseContext(),FilterActivity.class);
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
