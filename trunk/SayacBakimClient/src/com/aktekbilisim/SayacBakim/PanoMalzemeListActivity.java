package com.aktekbilisim.SayacBakim;

import java.util.ArrayList;
import java.util.List;

import Objects.Malzeme;
import ServiceLocater.GlobalVariables;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PanoMalzemeListActivity extends ListActivity 
{
	private ProgressDialog m_ProgressDialog = null;
	public static TextView MapX ;
	public static TextView MapY ;
	private Runnable viewOrders;
	private OrderAdapter m_adapter;
	private static List<Malzeme> m_orders = null;
	Activity activity =PanoMalzemeListActivity.this;
	static ListView listViewItems;
	ImageButton btnNewPano;
	Button btnMalzemeHome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.malzemelist);
		Initiliaze();
		SyncLocation();
		m_orders = new ArrayList<Malzeme>();
		this.m_adapter = new OrderAdapter(this, R.layout.panorow, m_orders);
		setListAdapter(this.m_adapter);
		viewOrders = new Runnable()
		{
			public void run() {
				getOrdersEx();
			}
		};
		btnMalzemeHome.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent = new Intent(PanoMalzemeListActivity.this,MenuActivity.class);
			startActivity(intent);
			finish();
			}
		});
		Thread thread = new Thread(null, viewOrders, "MagentoBackground");
		thread.start();
		m_ProgressDialog = ProgressDialog.show(PanoMalzemeListActivity.this,
				"Lütfen Bekleyiniz...", "Bilgiler alýnýyor ...", true);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				GlobalVariables.getIntance().malzeme = (Malzeme) m_orders.get(position);
				GlobalVariables.getIntance().malzeme.Position=position;
				Intent intent = new Intent(PanoMalzemeListActivity.this,MalzemeEditActivity.class);
				GlobalVariables.getIntance().malzeme=m_orders.get(position);
				startActivity(intent);
			}
		});
	}
	
	private void Initiliaze() 
	{
		// TODO Auto-generated method stub
		MapX=(TextView) findViewById(R.id.lblMapX);
		MapY=(TextView) findViewById(R.id.lblMapY);
		btnMalzemeHome=(Button)findViewById(R.id.btnMalzemeHome);
		listViewItems= getListView();
	}
	public static void SyncLocation()
	{
		if(MapY!=null&&MapX!=null&&ServiceLocater.GlobalVariables.getIntance().MapX!=null&&ServiceLocater.GlobalVariables.getIntance().MapX!=null)
		{
			MapX.setText("X :" +ServiceLocater.GlobalVariables.getIntance().MapX);
			MapY.setText("Y :" +ServiceLocater.GlobalVariables.getIntance().MapY);
		}
	}
	public static void ChangeListItemIcon(int position,int Ricon,float value,String description,boolean isselected)
	{
		View v = listViewItems.getChildAt(position - 
	            listViewItems.getFirstVisiblePosition());
		ImageView image=(ImageView)v.findViewById(R.id.imageView1);
		image.setImageResource(Ricon);
		(m_orders.get(position)).Value=value;
		(m_orders.get(position)).Description=description;
		(m_orders.get(position)).IsSelected=isselected;
	}
	private void getOrdersEx() 
	{
		try 
		{
			// Web Servis baðlantýsýný burada kurmak gerekiyor.
			 m_orders = new ArrayList<Malzeme>();
			 m_orders=ServiceAydem.getIntance().LoadMalzeme(GlobalVariables.getIntance().pano.PanelId);
			 GlobalVariables.getIntance().malzemeList=m_orders;
			 Thread.sleep(5000);
		} catch (final Exception e) 
		{
			activity.runOnUiThread(new Runnable() 
			{
				public void run() 
				{
					// TODO Auto-generated method stub
					Intent intent = new Intent(PanoMalzemeListActivity.this, ErrorActivity.class);
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
	private class OrderAdapter extends ArrayAdapter<Malzeme> {

		private List<Malzeme> items;

		public OrderAdapter(Context context, int textViewResourceId,
				List<Malzeme> items) {
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
			Malzeme o = items.get(position);
			if (o != null) {
				TextView txtPanelId= (TextView)v.findViewById(R.id.txtPanelId);
				ImageView image= (ImageView)v.findViewById(R.id.imageView1);
				if(o.IsSelected)
					image.setImageResource(R.drawable.yesilnokta);
				else
					image.setImageResource(R.drawable.kirmizinokta);
					
				txtPanelId.setText(o.Name);
			}
			return v;
		}

	}
}
