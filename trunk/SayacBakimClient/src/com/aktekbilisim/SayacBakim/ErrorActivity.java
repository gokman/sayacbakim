package com.aktekbilisim.SayacBakim;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ErrorActivity extends Activity 
{
	Button btnTamam;
	Button btnUpdate;
	Button btnClose;
	TextView txtError;
	LinearLayout ClasicTypeError;
	LinearLayout UpdateTypeError;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.error);
		
		Initiliaze();
	btnTamam.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	btnUpdate.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
		Intent intent = new Intent(ErrorActivity.this,UpdateActivity.class);
		startActivity(intent);
		finish();
		}
	});
	btnClose.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ErrorActivity.this,LoginActivity.class);
			startActivity(intent);
			finish();
		}
	});
	}

	private void Initiliaze() 
	{
		// TODO Auto-generated method stub
		Bundle extras = getIntent().getExtras();
		btnTamam =(Button) findViewById(R.id.btnTamam);
		btnUpdate=(Button) findViewById(R.id.btnUpdate);
		btnClose=(Button) findViewById(R.id.btnClose);
		txtError=(TextView)findViewById(R.id.txtError);
		ClasicTypeError=(LinearLayout)findViewById(R.id.ClasicTypeError);
		UpdateTypeError=(LinearLayout)findViewById(R.id.UpdateTypeError);
		if (extras != null) 
		{
			txtError.setText(extras.getString("Error_Message"));
			switch (extras.getInt("ErrorType")) 
			{
			case 0:
				ClasicTypeError.setVisibility(View.VISIBLE);
				UpdateTypeError.setVisibility(View.GONE);
				break;
			case 1:
				ClasicTypeError.setVisibility(View.GONE);
				UpdateTypeError.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}
	}
}
