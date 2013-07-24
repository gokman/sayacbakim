package com.aktekbilisim.SayacBakim;

import java.util.ArrayList;
import java.util.List;

import Objects.PanoMalzeme;
import ServiceLocater.GlobalVariables;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class PanoMalzemeActivity extends Activity
{
	Button btnOk;
	CheckBox cbm1,cbm2,cbm3,cbm4,cbm5,cbm6,cbm7,cbm8,cbm9,cbm10,cbm11,cbm12,cbm13,cbm14,cbm15,cbm16,cbm17,cbm18,cbm19,cbm20,cbm21,cbm22,cbm23,cbm24,cbm25,cbm26,
	cbm27,cbm28,cbm29,cbm30,cbm31,cbm32,cbm33,cbm34,cbm35,cbm36,cbm37,cbm38;
	EditText txtm1,txtm2,txtm3,txtm4,txtm5,txtm6,txtm7,txtm8,txtm9,txtm10,txtm11,txtm12,txtm13,txtm14,txtm15,txtm16,txtm17,txtm18,txtm19,txtm20,txtm21,txtm22,txtm23,txtm24,txtm25,
	txtm26,txtm27,txtm28,txtm29,txtm30,txtm31,txtm32,txtm33,txtm34,txtm35,txtm36,txtm37,txtm38;
	TextView nm1,nm2,nm3,nm4,nm5,nm6,nm7,nm8,nm9,nm10,nm11,nm12,nm13,nm14,nm15,nm16,nm17,nm18,nm19,nm20,nm21,nm22,nm23,nm24,nm25,nm26,nm27,nm28,nm29,nm30,nm31,nm32,nm33,nm34,
	nm35,nm36,nm37,nm38;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.panomalzeme);
		Initiliaze(GlobalVariables.getIntance().PanoMalzemeList);
		btnOk.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				try {
					
				
				List<PanoMalzeme> list = new ArrayList<PanoMalzeme>();
				
				if(cbm1.isChecked())
					list.add(new PanoMalzeme(true,nm1.getText().toString(),Double.parseDouble(txtm1.getText().toString())));
				if(cbm2.isChecked())
				list.add(new PanoMalzeme(true,nm2.getText().toString(),Double.parseDouble(txtm2.getText().toString())));
				if(cbm3.isChecked())
				list.add(new PanoMalzeme(true,nm3.getText().toString(),Double.parseDouble(txtm3.getText().toString())));
				if(cbm4.isChecked())
				list.add(new PanoMalzeme(true,nm4.getText().toString(),Double.parseDouble(txtm4.getText().toString())));
				if(cbm5.isChecked())
				list.add(new PanoMalzeme(true,nm5.getText().toString(),Double.parseDouble(txtm5.getText().toString())));
				if(cbm6.isChecked())
				list.add(new PanoMalzeme(true,nm6.getText().toString(),Double.parseDouble(txtm6.getText().toString())));
				if(cbm7.isChecked())
				list.add(new PanoMalzeme(true,nm7.getText().toString(),Double.parseDouble(txtm7.getText().toString())));
				if(cbm8.isChecked())
				list.add(new PanoMalzeme(true,nm8.getText().toString(),Double.parseDouble(txtm8.getText().toString())));
				if(cbm9.isChecked())
				list.add(new PanoMalzeme(true,nm9.getText().toString(),Double.parseDouble(txtm9.getText().toString())));
				if(cbm10.isChecked())
				list.add(new PanoMalzeme(true,nm10.getText().toString(),Double.parseDouble(txtm10.getText().toString())));
				if(cbm11.isChecked())
				list.add(new PanoMalzeme(true,nm11.getText().toString(),Double.parseDouble(txtm11.getText().toString())));
				if(cbm12.isChecked())
				list.add(new PanoMalzeme(true,nm12.getText().toString(),Double.parseDouble(txtm12.getText().toString())));
				if(cbm13.isChecked())
				list.add(new PanoMalzeme(true,nm13.getText().toString(),Double.parseDouble(txtm13.getText().toString())));
				if(cbm14.isChecked())
				list.add(new PanoMalzeme(true,nm14.getText().toString(),Double.parseDouble(txtm14.getText().toString())));
				if(cbm15.isChecked())
				list.add(new PanoMalzeme(true,nm15.getText().toString(),Double.parseDouble(txtm15.getText().toString())));
				if(cbm16.isChecked())
				list.add(new PanoMalzeme(true,nm16.getText().toString(),Double.parseDouble(txtm16.getText().toString())));
				if(cbm17.isChecked())
				list.add(new PanoMalzeme(true,nm17.getText().toString(),Double.parseDouble(txtm17.getText().toString())));
				if(cbm18.isChecked())
				list.add(new PanoMalzeme(true,nm18.getText().toString(),Double.parseDouble(txtm18.getText().toString())));
				if(cbm19.isChecked())
				list.add(new PanoMalzeme(true,nm19.getText().toString(),Double.parseDouble(txtm19.getText().toString())));
				if(cbm20.isChecked())
				list.add(new PanoMalzeme(true,nm20.getText().toString(),Double.parseDouble(txtm20.getText().toString())));
				if(cbm21.isChecked())
				list.add(new PanoMalzeme(true,nm21.getText().toString(),Double.parseDouble(txtm21.getText().toString())));
				if(cbm22.isChecked())
				list.add(new PanoMalzeme(true,nm22.getText().toString(),Double.parseDouble(txtm22.getText().toString())));
				if(cbm23.isChecked())
				list.add(new PanoMalzeme(true,nm23.getText().toString(),Double.parseDouble(txtm23.getText().toString())));
				if(cbm24.isChecked())
				list.add(new PanoMalzeme(true,nm24.getText().toString(),Double.parseDouble(txtm24.getText().toString())));
				if(cbm25.isChecked())
				list.add(new PanoMalzeme(true,nm25.getText().toString(),Double.parseDouble(txtm25.getText().toString())));
				if(cbm26.isChecked())
				list.add(new PanoMalzeme(true,nm26.getText().toString(),Double.parseDouble(txtm26.getText().toString())));
				if(cbm27.isChecked())
				list.add(new PanoMalzeme(true,nm27.getText().toString(),Double.parseDouble(txtm27.getText().toString())));
				if(cbm28.isChecked())
				list.add(new PanoMalzeme(true,nm28.getText().toString(),Double.parseDouble(txtm28.getText().toString())));
				if(cbm29.isChecked())
				list.add(new PanoMalzeme(true,nm29.getText().toString(),Double.parseDouble(txtm29.getText().toString())));
				if(cbm30.isChecked())
				list.add(new PanoMalzeme(true,nm30.getText().toString(),Double.parseDouble(txtm30.getText().toString())));
				if(cbm31.isChecked())
				list.add(new PanoMalzeme(true,nm31.getText().toString(),Double.parseDouble(txtm31.getText().toString())));
				if(cbm32.isChecked())
				list.add(new PanoMalzeme(true,nm32.getText().toString(),Double.parseDouble(txtm32.getText().toString())));
				if(cbm33.isChecked())
				list.add(new PanoMalzeme(true,nm33.getText().toString(),Double.parseDouble(txtm33.getText().toString())));
				if(cbm34.isChecked())
				list.add(new PanoMalzeme(true,nm34.getText().toString(),Double.parseDouble(txtm34.getText().toString())));
				if(cbm35.isChecked())
				list.add(new PanoMalzeme(true,nm35.getText().toString(),Double.parseDouble(txtm35.getText().toString())));
				if(cbm36.isChecked())
				list.add(new PanoMalzeme(true,nm36.getText().toString(),Double.parseDouble(txtm36.getText().toString())));
				if(cbm37.isChecked())
				list.add(new PanoMalzeme(true,nm37.getText().toString(),Double.parseDouble(txtm37.getText().toString())));
				if(cbm38.isChecked())
				list.add(new PanoMalzeme(true,nm38.getText().toString(),Double.parseDouble(txtm38.getText().toString())));
				

				GlobalVariables.getIntance().PanoMalzemeList=list;
				finish();
				
				} catch (Exception e) {
					// TODO: handle exception
					Intent intent = new Intent(PanoMalzemeActivity.this, ErrorActivity.class);
					intent.putExtra("Error_Message", e.toString());
					intent.putExtra("ErrorType", 0);
					startActivity(intent);
				}
			}
		});
	}

	private void Initiliaze(List<PanoMalzeme> list) {
		try
		{
		// TODO Auto-generated method stub
		btnOk=(Button) findViewById(R.id.btnMalzemeOk);
		cbm1=(CheckBox)findViewById(R.id.cbm1);
		cbm2=(CheckBox)findViewById(R.id.cbm2);
		cbm3=(CheckBox)findViewById(R.id.cbm3);
		cbm4=(CheckBox)findViewById(R.id.cbm4);
		cbm5=(CheckBox)findViewById(R.id.cbm5);
		cbm6=(CheckBox)findViewById(R.id.cbm6);
		cbm7=(CheckBox)findViewById(R.id.cbm7);
		cbm8=(CheckBox)findViewById(R.id.cbm8);
		cbm9=(CheckBox)findViewById(R.id.cbm9);
		cbm10=(CheckBox)findViewById(R.id.cbm10);
		cbm11=(CheckBox)findViewById(R.id.cbm11);
		cbm12=(CheckBox)findViewById(R.id.cbm12);
		cbm13=(CheckBox)findViewById(R.id.cbm13);
		cbm14=(CheckBox)findViewById(R.id.cbm14);
		cbm15=(CheckBox)findViewById(R.id.cbm15);
		cbm16=(CheckBox)findViewById(R.id.cbm16);
		cbm17=(CheckBox)findViewById(R.id.cbm17);
		cbm18=(CheckBox)findViewById(R.id.cbm18);
		cbm19=(CheckBox)findViewById(R.id.cbm19);
		cbm20=(CheckBox)findViewById(R.id.cbm20);
		cbm21=(CheckBox)findViewById(R.id.cbm21);
		cbm22=(CheckBox)findViewById(R.id.cbm22);
		cbm23=(CheckBox)findViewById(R.id.cbm23);
		cbm24=(CheckBox)findViewById(R.id.cbm24);
		cbm25=(CheckBox)findViewById(R.id.cbm25);
		cbm26=(CheckBox)findViewById(R.id.cbm26);
		cbm27=(CheckBox)findViewById(R.id.cbm27);
		cbm28=(CheckBox)findViewById(R.id.cbm28);
		cbm29=(CheckBox)findViewById(R.id.cbm29);
		cbm30=(CheckBox)findViewById(R.id.cbm30);
		cbm31=(CheckBox)findViewById(R.id.cbm31);
		cbm32=(CheckBox)findViewById(R.id.cbm32);
		cbm33=(CheckBox)findViewById(R.id.cbm33);
		cbm34=(CheckBox)findViewById(R.id.cbm34);
		cbm35=(CheckBox)findViewById(R.id.cbm35);
		cbm36=(CheckBox)findViewById(R.id.cbm36);
		cbm37=(CheckBox)findViewById(R.id.cbm37);
		cbm38=(CheckBox)findViewById(R.id.cbm38);
		
		txtm1=(EditText)findViewById(R.id.txtm1);
		txtm2=(EditText)findViewById(R.id.txtm2);
		txtm3=(EditText)findViewById(R.id.txtm3);
		txtm4=(EditText)findViewById(R.id.txtm4);
		txtm5=(EditText)findViewById(R.id.txtm5);
		txtm6=(EditText)findViewById(R.id.txtm6);
		txtm7=(EditText)findViewById(R.id.txtm7);
		txtm8=(EditText)findViewById(R.id.txtm8);
		txtm9=(EditText)findViewById(R.id.txtm9);
		txtm10=(EditText)findViewById(R.id.txtm10);
		txtm11=(EditText)findViewById(R.id.txtm11);
		txtm12=(EditText)findViewById(R.id.txtm12);
		txtm13=(EditText)findViewById(R.id.txtm13);
		txtm14=(EditText)findViewById(R.id.txtm14);
		txtm15=(EditText)findViewById(R.id.txtm15);
		txtm16=(EditText)findViewById(R.id.txtm16);
		txtm17=(EditText)findViewById(R.id.txtm17);
		txtm18=(EditText)findViewById(R.id.txtm18);
		txtm19=(EditText)findViewById(R.id.txtm19);
		txtm20=(EditText)findViewById(R.id.txtm20);
		txtm21=(EditText)findViewById(R.id.txtm21);
		txtm22=(EditText)findViewById(R.id.txtm22);
		txtm23=(EditText)findViewById(R.id.txtm23);
		txtm24=(EditText)findViewById(R.id.txtm24);
		txtm25=(EditText)findViewById(R.id.txtm25);
		txtm26=(EditText)findViewById(R.id.txtm26);
		txtm27=(EditText)findViewById(R.id.txtm27);
		txtm28=(EditText)findViewById(R.id.txtm28);
		txtm29=(EditText)findViewById(R.id.txtm29);
		txtm30=(EditText)findViewById(R.id.txtm30);
		txtm31=(EditText)findViewById(R.id.txtm31);
		txtm32=(EditText)findViewById(R.id.txtm32);
		txtm33=(EditText)findViewById(R.id.txtm33);
		txtm34=(EditText)findViewById(R.id.txtm34);
		txtm35=(EditText)findViewById(R.id.txtm35);
		txtm36=(EditText)findViewById(R.id.txtm36);
		txtm37=(EditText)findViewById(R.id.txtm37);
		txtm38=(EditText)findViewById(R.id.txtm38);

		nm1=(TextView)findViewById(R.id.nm1);
		nm2=(TextView)findViewById(R.id.nm2);
		nm3=(TextView)findViewById(R.id.nm3);
		nm4=(TextView)findViewById(R.id.nm4);
		nm5=(TextView)findViewById(R.id.nm5);
		nm6=(TextView)findViewById(R.id.nm6);
		nm7=(TextView)findViewById(R.id.nm7);
		nm8=(TextView)findViewById(R.id.nm8);
		nm9=(TextView)findViewById(R.id.nm9);
		nm10=(TextView)findViewById(R.id.nm10);
		nm11=(TextView)findViewById(R.id.nm11);
		nm12=(TextView)findViewById(R.id.nm12);
		nm13=(TextView)findViewById(R.id.nm13);
		nm14=(TextView)findViewById(R.id.nm14);
		nm15=(TextView)findViewById(R.id.nm15);
		nm16=(TextView)findViewById(R.id.nm16);
		nm17=(TextView)findViewById(R.id.nm17);
		nm18=(TextView)findViewById(R.id.nm18);
		nm19=(TextView)findViewById(R.id.nm19);
		nm20=(TextView)findViewById(R.id.nm20);
		nm21=(TextView)findViewById(R.id.nm21);
		nm22=(TextView)findViewById(R.id.nm22);
		nm23=(TextView)findViewById(R.id.nm23);
		nm24=(TextView)findViewById(R.id.nm24);
		nm25=(TextView)findViewById(R.id.nm25);
		nm26=(TextView)findViewById(R.id.nm26);
		nm27=(TextView)findViewById(R.id.nm27);
		nm28=(TextView)findViewById(R.id.nm28);
		nm29=(TextView)findViewById(R.id.nm29);
		nm30=(TextView)findViewById(R.id.nm30);
		nm31=(TextView)findViewById(R.id.nm31);
		nm32=(TextView)findViewById(R.id.nm32);
		nm33=(TextView)findViewById(R.id.nm33);
		nm34=(TextView)findViewById(R.id.nm34);
		nm35=(TextView)findViewById(R.id.nm35);
		nm36=(TextView)findViewById(R.id.nm36);
		nm37=(TextView)findViewById(R.id.nm37);
		nm38=(TextView)findViewById(R.id.nm38);
		
		if(list!=null)
		{
			txtm1.setText(Double.toString(list.get(0).Value));
			txtm2.setText(Double.toString(list.get(1).Value));
			txtm3.setText(Double.toString(list.get(2).Value));
			txtm4.setText(Double.toString(list.get(3).Value));
			txtm5.setText(Double.toString(list.get(4).Value));
			txtm6.setText(Double.toString(list.get(5).Value));
			txtm7.setText(Double.toString(list.get(6).Value));
			txtm8.setText(Double.toString(list.get(7).Value));
			txtm9.setText(Double.toString(list.get(8).Value));
			txtm10.setText(Double.toString(list.get(9).Value));
			txtm11.setText(Double.toString(list.get(10).Value));
			txtm12.setText(Double.toString(list.get(11).Value));
			txtm13.setText(Double.toString(list.get(12).Value));
			txtm14.setText(Double.toString(list.get(13).Value));
			txtm15.setText(Double.toString(list.get(14).Value));
			txtm16.setText(Double.toString(list.get(15).Value));
			txtm17.setText(Double.toString(list.get(16).Value));
			txtm18.setText(Double.toString(list.get(17).Value));
			txtm19.setText(Double.toString(list.get(18).Value));
			txtm20.setText(Double.toString(list.get(19).Value));
			txtm21.setText(Double.toString(list.get(20).Value));
			txtm22.setText(Double.toString(list.get(21).Value));
			txtm23.setText(Double.toString(list.get(22).Value));
			txtm24.setText(Double.toString(list.get(23).Value));
			txtm25.setText(Double.toString(list.get(24).Value));
			txtm26.setText(Double.toString(list.get(25).Value));
			txtm27.setText(Double.toString(list.get(26).Value));
			txtm28.setText(Double.toString(list.get(27).Value));
			txtm29.setText(Double.toString(list.get(28).Value));
			txtm30.setText(Double.toString(list.get(29).Value));
			txtm31.setText(Double.toString(list.get(30).Value));
			txtm32.setText(Double.toString(list.get(31).Value));
			txtm33.setText(Double.toString(list.get(32).Value));
			txtm34.setText(Double.toString(list.get(33).Value));
			txtm35.setText(Double.toString(list.get(34).Value));
			txtm36.setText(Double.toString(list.get(35).Value));
			txtm37.setText(Double.toString(list.get(36).Value));
			txtm38.setText(Double.toString(list.get(37).Value));
		}
	} catch (Exception e) 
	{
		// TODO: handle exception
		Intent intent = new Intent(PanoMalzemeActivity.this, ErrorActivity.class);
		intent.putExtra("Error_Message", e.toString());
		intent.putExtra("ErrorType", 0);
		startActivity(intent);
	}
	}
}
