package com.aktekbilisim.SayacBakim;

import Objects.Malzeme;
import ServiceLocater.GlobalVariables;
import ServiceLocater.ServiceAydem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MalzemeEditActivity extends Activity {
	Button btnMalzemeeditOk;
	EditText value;
	EditText description;
	Button btnMalzemeeditCancel;
	PanoMalzemeListActivity listActivity= new PanoMalzemeListActivity();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.malzemeedit);
		Initialize();
		btnMalzemeeditOk.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int position = GlobalVariables.getIntance().malzeme.Position;
				if (value.getText().toString().equals("")) 
				{
					
//					GlobalVariables.getIntance().malzemeList.get(position).Description = "";
//					GlobalVariables.getIntance().malzemeList.get(position).Value = 0;
//					GlobalVariables.getIntance().malzemeList.get(position).IsSelected = false;
//					finish();
					ServiceAydem.getIntance().UpdatePanoMalzeme(
							GlobalVariables.getIntance().pano.PanelId,
							GlobalVariables.getIntance().malzemeList.get(position).MalzemeId,
							"0",
							"",
							false
							);
					listActivity.ChangeListItemIcon(position, R.drawable.kirmizinokta);
				}
				else
				{
					ServiceAydem.getIntance().UpdatePanoMalzeme(
							GlobalVariables.getIntance().pano.PanelId,
							GlobalVariables.getIntance().malzemeList.get(position).MalzemeId,
							value.getText().toString(),
							description.getText().toString(),
							true);
					listActivity.ChangeListItemIcon(position, R.drawable.yesilnokta);
				}
				
				
				finish();
			}
		});
		btnMalzemeeditCancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void Initialize() {
		// TODO Auto-generated method stub
		btnMalzemeeditOk = (Button) findViewById(R.id.btnMalzemeeditOk);
		btnMalzemeeditCancel = (Button) findViewById(R.id.btnMalzemeeditCancel);
		value = (EditText) findViewById(R.id.txtValue);
		description = (EditText) findViewById(R.id.txtdescription);
		
		if(GlobalVariables.getIntance().malzeme.IsSelected){
			value.setText(Float.toString(GlobalVariables.getIntance().malzeme.Value));
			description.setText(GlobalVariables.getIntance().malzeme.Description);
		}
		
		

	}

}
