package com.aktekbilisim.SayacBakim;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import Objects.EnumWorkOrderStatus;
import ServiceLocater.GlobalVariables;
import ServiceLocater.Service;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class FilterActivity extends Activity {
	EditText txtAboneNo ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			
		
		super.onCreate(savedInstanceState);
		final Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			setContentView(R.layout.filter);	
		
			// TODO: handle exception
			final Spinner spinner =(Spinner) findViewById(R.id.spnStatus);
			Button btnSearch = (Button) findViewById(R.id.btnSearch);
			//txtAboneNo = (EditText) findViewById(R.id.txtAboneNo);
			final DatePicker datepicker =(DatePicker)findViewById(R.id.datetime);
			String[] items = new String[] {"Ýþ Durumu Seçiniz", "Planlandý", "Beklemede", "Çalýþýlýyor","Montaj Tamamlandý"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, items);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			
			btnSearch.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int StatusCode;
					String datetime=formatter.format(new Date(datepicker.getYear()-1900,datepicker.getMonth(),datepicker.getDayOfMonth()));
			    if(spinner.getSelectedItem().toString().equalsIgnoreCase("Ýþ Durumu Seçiniz"))
			    {
			    	StatusCode=0;
			    }
			    else
			    {
			    	StatusCode=EnumWorkOrderStatus.getWorkOrderStatuCodeForName(spinner.getSelectedItem().toString());
			    }
			    Intent intent =new Intent(FilterActivity.this,OrderListActivity.class);
			    GlobalVariables.getIntance().StatusCodeForFilter= StatusCode;
			    GlobalVariables.getIntance().datetimeForFilter=datetime;
			    //GlobalVariables.getIntance().AboneNoForFilter=txtAboneNo.getText().toString();
			    startActivity(intent);
			    //finish();
				}
			});
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			Intent intent = new Intent(FilterActivity.this, ErrorActivity.class);
    		intent.putExtra("Error_Message", e.toString());
    		intent.putExtra("ErrorType", 0);
    		startActivity(intent);
		}
	}
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu)
	    {
	     menu.add(0,1,1,"Kapat").setIcon(R.drawable.kapat);
	     menu.add(0,2,2,"Foto Eþle").setIcon(R.drawable.camera_icon);
	     return true;
	    }
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	     
	     switch(item.getItemId())
	     {
	     case 1:
	    	 Intent intent = new Intent(Intent.ACTION_MAIN);
	    	 intent.addCategory(Intent.CATEGORY_HOME);
	    	 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	 startActivity(intent);

	           // finish();
	      return true;
	     case 2:
			 Intent intent2 = new Intent(FilterActivity.this,FotoSenkActivity.class);
	    	 startActivity(intent2);
			
			return  true;

	     }
	     return super.onOptionsItemSelected(item);

	    }
	
}
