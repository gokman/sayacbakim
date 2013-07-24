package com.aktekbilisim.SayacBakim;

import ServiceLocater.GlobalVariables;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class PanoProcessSelection extends Activity 
{
ImageButton newWork;
ImageButton listWork;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.panoprocessselection);
		Initialize();
		
		newWork.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PanoProcessSelection.this,PanoNew.class);
				intent.putExtra("processType", "Insert");
				GlobalVariables.getIntance().pano=null;
				startActivity(intent);
				finish();
			}
		});
		
		listWork.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(PanoProcessSelection.this,PanoListActivity.class);
				startActivity(intent);
			}
		});
	}

	private void Initialize() 
	{
		// TODO Auto-generated method stub
		listWork= (ImageButton)findViewById(R.id.btnListWork);
		newWork= (ImageButton)findViewById(R.id.btnNewWork);
		
	}
	

}
