package com.aktekbilisim.SayacBakim;

import java.io.File;
import java.text.Format;
import java.util.ArrayList;

import Objects.EnumWorkOrderStatus;
import ServiceLocater.GlobalVariables;
import ServiceLocater.Service;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class FilterExActivity extends Activity 
{
	Button btnSearch;
	Spinner spinner;
	Button btndelete;
	Button btnHome;
	EditText txtAboneNo;
	static EditText lblDate;
	Format formatter ;
	ImageButton btnCalender;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filterex);
		Initiliaze();
		setClickListener();
	}

	private void setClickListener() {
		// TODO Auto-generated method stub
		btnSearch.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			int StatusCode;
			
		    if(spinner.getSelectedItem().toString().equalsIgnoreCase("Ýþ Durumu Seçiniz"))
		    {
		    	StatusCode=0;
		    }
		    else
		    {
		    	StatusCode=EnumWorkOrderStatus.getWorkOrderStatuCodeForName(spinner.getSelectedItem().toString());
		    }
		    if(StatusCode==0 && txtAboneNo.getText().toString().length()==0 && lblDate.getText().toString().length()==0)
		    {
		    	
		    	Intent intent = new Intent(FilterExActivity.this, ErrorActivity.class);
		    	intent.putExtra("Error_Message", "En az bir adet filtre kriteri girmelisiniz.");
		    	intent.putExtra("ErrorType", 0);
				startActivity(intent);
		    	return ;
		    }
		    
		    Intent intent =new Intent(FilterExActivity.this,OrderListActivity.class);
		    GlobalVariables.getIntance().StatusCodeForFilter= StatusCode;
		    GlobalVariables.getIntance().AboneNoForFilter=txtAboneNo.getText().toString();
		    startActivity(intent);
		    //finish();
			}
		});
		
		btnCalender.setOnClickListener(new OnClickListener() 
		{
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(FilterExActivity.this,CalendarActivity.class);
				startActivity(intent);
			}
		});
		btndelete.setOnClickListener(new OnClickListener() 
		{
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lblDate.setText("");
				GlobalVariables.getIntance().datetimeForFilter="";
			}
		});
		btnHome.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FilterExActivity.this,MenuActivity.class);
				startActivity(intent);
			}
		});
	}

	private void Initiliaze() 
	{
		// TODO Auto-generated method stub
		btnHome=(Button)findViewById(R.id.btnHome);
			btndelete=(Button) findViewById(R.id.btndelete);
			btnSearch = (Button) findViewById(R.id.btnSearchEx);
			spinner =(Spinner) findViewById(R.id.spnStatusEx);
			btnCalender=(ImageButton)findViewById(R.id.imageButton1);
			lblDate=(EditText)findViewById(R.id.lblDate);
			txtAboneNo = (EditText)findViewById(R.id.txtAboneNo);
			String[] items = new String[] {"Ýþ Durumu Seçiniz", "Planlandý", "Beklemede", "Çalýþýlýyor","Montaj Tamamlandý"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, items);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			GlobalVariables.getIntance().StatusCodeForFilter=0;
			GlobalVariables.getIntance().AboneNoForFilter="";
			GlobalVariables.getIntance().datetimeForFilter="";
			
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
			 Intent intent2 = new Intent(FilterExActivity.this,FotoSenkActivity.class);
	    	 startActivity(intent2);
			
			return  true;
	     case 3:
	    	 
	    	 ServiceLocater.Service.getIntance().LoadPhotoInfo(GetFiles("/sdcard/DCIM/Camera"));
	    	 return true;

	     }
	     return super.onOptionsItemSelected(item);

	    }
	 public ArrayList<String> GetFiles(String DirectoryPath) {
		    ArrayList<String> MyFiles = new ArrayList<String>();
		    File f = new File(DirectoryPath);

		    f.mkdirs();
		    File[] files = f.listFiles();
		    if (files.length == 0)
		        return null;
		    else {
		        for (int i=0; i<files.length; i++) 
		            MyFiles.add(files[i].getName());
		    }

		    return MyFiles;
		}
	 public static void setDate()
	 {
		 lblDate.setText(GlobalVariables.getIntance().datetimeForFilter);
	 }
	
}
