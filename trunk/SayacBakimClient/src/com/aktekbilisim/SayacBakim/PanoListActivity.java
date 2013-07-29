package com.aktekbilisim.SayacBakim;

import java.util.ArrayList;
import java.util.List;

import Objects.Pano;
import ServiceLocater.GlobalVariables;
import ServiceLocater.Service;
import ServiceLocater.ServiceAydem;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PanoListActivity extends ListActivity 
{
	private ProgressDialog m_ProgressDialog = null;
	public static TextView MapX ;
	public static TextView MapY ;
	private Runnable viewOrders;
	private OrderAdapter m_adapter;
	private List<Pano> m_orders = null;
	Activity activity =PanoListActivity.this;
	ImageButton btnNewPano;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.panolist);
		Initiliaze();
		SyncLocation();
		m_orders = new ArrayList<Pano>();
		this.m_adapter = new OrderAdapter(this, R.layout.panorow, m_orders);
		setListAdapter(this.m_adapter);
		viewOrders = new Runnable()
		{
			public void run() {
				getOrdersEx();
			}
		};
		Thread thread = new Thread(null, viewOrders, "MagentoBackground");
		thread.start();
		m_ProgressDialog = ProgressDialog.show(PanoListActivity.this,
				"Lütfen Bekleyiniz...", "Bilgiler alýnýyor ...", true);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				GlobalVariables.getIntance().pano = (Pano) m_orders.get(position);
				Intent intent = new Intent(PanoListActivity.this,PanoNew.class);
				intent.putExtra("processType", "Update");
				GlobalVariables.getIntance().pano=m_orders.get(position);
				startActivity(intent);
				finish();
			}
		});
		btnNewPano.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
			Intent intent = new Intent(PanoListActivity.this,PanoNew.class);
			intent.putExtra("processType", "Insert");
			GlobalVariables.getIntance().pano=null;
			startActivity(intent);
			finish();
			}
		});
	}
	private void Initiliaze() 
	{
		// TODO Auto-generated method stub
		btnNewPano=(ImageButton) findViewById(R.id.btnNewPano);
		MapX=(TextView) findViewById(R.id.lblMapX);
		MapY=(TextView) findViewById(R.id.lblMapY);
	}
	public static void SyncLocation()
	{
		if(MapY!=null&&MapX!=null&&ServiceLocater.GlobalVariables.getIntance().MapX!=null&&ServiceLocater.GlobalVariables.getIntance().MapX!=null)
		{
			MapX.setText("X :" +ServiceLocater.GlobalVariables.getIntance().MapX);
			MapY.setText("Y :" +ServiceLocater.GlobalVariables.getIntance().MapY);
		}
	}
	private void getOrdersEx() {
		try {
			// Web Servis baðlantýsýný burada kurmak gerekiyor.
			 m_orders = new ArrayList<Pano>();
			 m_orders=ServiceAydem.getIntance().LoadPanel(GlobalVariables.getIntance().user.UserId);
			 Thread.sleep(5000);
		} catch (final Exception e) 
		{
			activity.runOnUiThread(new Runnable() 
			{
				public void run() 
				{
					// TODO Auto-generated method stub
					
					Intent intent = new Intent(PanoListActivity.this, ErrorActivity.class);
		    		intent.putExtra("Error_Message", e.toString());
		    		intent.putExtra("ErrorType", 0);
		    		startActivity(intent);
				}
			});
		}
		runOnUiThread(returnRes);
	}
	private Runnable returnRes = new Runnable() 
	{

		public void run() {
			if (m_orders != null && m_orders.size() > 0) {
				m_adapter.notifyDataSetChanged();
				for (int i = 0; i < m_orders.size(); i++)
				{
					m_adapter.add(m_orders.get(i));
				}
			}
			m_ProgressDialog.dismiss();
			m_adapter.notifyDataSetChanged();
		}
	};
	private class OrderAdapter extends ArrayAdapter<Pano> {

		private List<Pano> items;

		public OrderAdapter(Context context, int textViewResourceId,
				List<Pano> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.panorow, null);
			}
			Pano o = items.get(position);
			if (o != null) 
			{
				TextView txtPanelId= (TextView)v.findViewById(R.id.txtPanelId);
				ImageView image= (ImageView)v.findViewById(R.id.imageView1);
				
				txtPanelId.setText(o.PanelId);
				if(o.Statu!=3)
					image.setImageResource(R.drawable.kirmizinokta);
					
			}
			return v;
		}

	}
}
