package com.aktekbilisim.SayacBakim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FinishDayActivity extends Activity {
	
	private Button finishDayButton;
	private int a=3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finishday);
		finishDayButton=(Button)findViewById(R.id.buttonFinishDay);
		finishDayButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				//web servis metodu çaðýrýp sayý elde et
				//elde edilen sayý 4 ise
				if(a>=4){
				Toast.makeText(getApplicationContext(), "Günü baþarý ile tamamladýnýz", Toast.LENGTH_LONG);
				//finishDayButton.setClickable(false);
				}else{
				//elde edilen sayý 4 ten küçük ise 
				Intent intent=new Intent(FinishDayActivity.this,FinishDayDetailActivity.class);
				startActivity(intent);
				}
				
			}
		});
	}

}
