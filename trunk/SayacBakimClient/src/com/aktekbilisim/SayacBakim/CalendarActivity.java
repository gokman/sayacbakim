package com.aktekbilisim.SayacBakim;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import ServiceLocater.GlobalVariables;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

public class CalendarActivity extends Activity 
{
	DatePicker datepicker;
	Format formatter ;
	Button btnOk;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		btnOk=(Button)findViewById(R.id.btnok);
		datepicker =(DatePicker)findViewById(R.id.datetimeEx);
		btnOk.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				String datetime=formatter.format(new Date(datepicker.getYear()-1900,datepicker.getMonth(),datepicker.getDayOfMonth()));
				GlobalVariables.getIntance().datetimeForFilter=datetime;
				FilterExActivity.setDate();
				finish();
			}
		});
	}

}
