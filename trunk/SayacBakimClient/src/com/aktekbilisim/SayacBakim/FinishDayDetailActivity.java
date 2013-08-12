package com.aktekbilisim.SayacBakim;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ServiceLocater.GlobalVariables;
import ServiceLocater.ServiceAydem;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class FinishDayDetailActivity extends Activity{
	
	private EditText editText;
	private Button okButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finishdatedetail);
		editText=(EditText)findViewById(R.id.finishDayDetailMulti_txt);
		okButton=(Button)findViewById(R.id.finishDayDetailButton);
		
		okButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(editText.getText().toString().isEmpty()){
					Toast.makeText(FinishDayDetailActivity.this, "Açýklamayý boþ býrakmayýnýz", Toast.LENGTH_LONG).show();;
					return;
				}
				int a=0;
				a=ServiceAydem.getIntance().GetPanelCount(Integer.parseInt(GlobalVariables.getIntance().user.UserId));
				
				try {
					ServiceAydem.getIntance().InsertGunOzur(Integer.parseInt(GlobalVariables.getIntance().user.UserId), editText.getText().toString(), a);
					finish();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}
		});
		
			

				
			}
		
	

}
