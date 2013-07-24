package com.aktekbilisim.SayacBakim;

import java.util.ArrayList;

import java.util.List;

import Objects.WorkOrder;
import ServiceLocater.GlobalVariables;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;

public class WorkOrderExList extends Activity {
	private List<WorkOrder> values;
	public static TextView MapX ;
	public static TextView MapY ;
	TextView nbryesil ;
	TextView nbrmavi;
	TextView nbrsari;
	TextView nbrturuncu;
	TextView nbrSiyah;
	Button btnHome;
	static int yesil;
	static int mavi;
	static int sari;
	static int turuncu;
	static int siyah;
	@Override
	public void onCreate(Bundle savedData) {

		super.onCreate(savedData);
		this.setContentView(R.layout.workorder_ex_listview);
		ListView list = (ListView)this.findViewById(R.id.list);
		Initialize();
		SyncLocation();
		list.setOnItemClickListener(new OnItemClickListener() 
		{
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				GlobalVariables.getIntance().workorder = (WorkOrder) values
						.get(position);
				Intent intent = new Intent(WorkOrderExList.this,
						EditOrderActivity.class);
				startActivity(intent);
			}
		});
		values = new ArrayList<WorkOrder>();
		values=ServiceLocater.GlobalVariables.getIntance().WorkOrderList;
		setWorkOrderCount();
		OrderAdapter adapter = new OrderAdapter(
			this,
			R.layout.workorder_ex_listview_child,
			values
		);
		list.setAdapter(
			new SlideExpandableListAdapter
			(
				adapter,
				R.id.expandable_toggle_button,
				R.id.expandable
			)
		);
		btnHome.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(WorkOrderExList.this,MenuActivity.class);
				startActivity(intent);
			}
		});
	}
	private void Initialize() {
		// TODO Auto-generated method stub
		btnHome=(Button)findViewById(R.id.btnHome);
		yesil=0;
		mavi =0;
		sari=0;
		turuncu=0;
		siyah=0;
		MapX=(TextView) findViewById(R.id.lblMapX);
		MapY=(TextView) findViewById(R.id.lblMapY);
		nbrmavi=(TextView) findViewById(R.id.nbrmavi);
		nbrsari=(TextView) findViewById(R.id.nbrsari);
		nbrSiyah=(TextView) findViewById(R.id.nbrSiyah);
		nbrturuncu=(TextView) findViewById(R.id.nbrturuncu);
		nbryesil=(TextView) findViewById(R.id.nbryesil);
	}
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
				v = vi.inflate(R.layout.workorder_ex_listview_child, null);
			}
			WorkOrder o = items.get(position);
			if (o != null) {
				TextView lblIsletmeKodu = (TextView) v.findViewById(R.id.lblIsletmeKodu);
				TextView lblAboneNo = (TextView) v.findViewById(R.id.lblAboneNo);
				TextView lblMeterModel = (TextView) v.findViewById(R.id.lblMeterModel);
				TextView lblSerialId = (TextView) v.findViewById(R.id.lblSerialId);
				TextView lblStatus = (TextView) v.findViewById(R.id.lblStatus);
				TextView lblElecCutIntrvl=(TextView) v.findViewById(R.id.lblElecCutIntrvl);
				TextView lblCityId =(TextView)v.findViewById(R.id.lblCity);
				TextView lblTown=(TextView)v.findViewById(R.id.lblTown);
				ImageView imgStatus = (ImageView) v	.findViewById(R.id.expandable_toggle_button);
				
				lblCityId.setText(o.CityName);
				lblTown.setText(o.TownName);
				lblAboneNo.setText(o.TesisatNo);
				lblMeterModel.setText(o.MeterType);
				lblSerialId.setText(o.SerialNo);
				lblStatus.setText(o.Statu.toString());
				lblElecCutIntrvl.setText(o.CutInterval);
				lblIsletmeKodu.setText(o.TesisatNo);
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
			}
			return v;
		}

	}

	
	private void setWorkOrderCount() 
	{
		
		// TODO Auto-generated method stub
		for (int i = 0; i < values.size(); i++)
		{
			if (values.get(i).getImageName() == "yesilNokta")
			{
				yesil++;
				nbryesil.setText(": "+Integer.toString(yesil));
			}
			if (values.get(i).getImageName() == "sariNokta")
			{
				sari++;
				nbrsari.setText(": "+Integer.toString(sari));
			}
			if (values.get(i).getImageName() == "siyahNokta")
			{
				siyah++;
				nbrSiyah.setText(": "+Integer.toString(siyah));
			}
			if (values.get(i).getImageName() == "kirmiziNokta")
			{
				
			}
			if (values.get(i).getImageName() == "maviNokta")
			{	
				mavi++;
				nbrmavi.setText(": "+Integer.toString(mavi));
			}
			if (values.get(i).getImageName() == "turuncuNokta")
			{	
				turuncu++;
				nbrturuncu.setText(": "+Integer.toString(turuncu));
			}
		}
	}
	public static void SyncLocation()
	{
		if(MapY!=null&&MapX!=null&&ServiceLocater.GlobalVariables.getIntance().MapX!=null&&ServiceLocater.GlobalVariables.getIntance().MapX!=null)
		{
			MapX.setText("X :" +ServiceLocater.GlobalVariables.getIntance().MapX);
			MapY.setText("Y :" +ServiceLocater.GlobalVariables.getIntance().MapY);
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
}
