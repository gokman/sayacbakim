package com.aktekbilisim.SayacBakim;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ServiceLocater.GlobalVariables;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class UpdateActivity extends Activity {

	private Button btnUpdate ;
	private Runnable Update;
	private Thread updateThread;
	private ProgressDialog m_ProgressDialog = null;
	String Url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.updateapp);
		Initiliaze();
		
		btnUpdate.setOnClickListener(new OnClickListener() 
		{
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Url = "http://www.aktekapps.com/AndroidWebServices/update/SayacBakimClient.apk";
				m_ProgressDialog = ProgressDialog.show(UpdateActivity.this,"Lütfen Bekleyiniz...", "Güncelleme Gerçekleþtiriliyor...", false);
				Update= new Runnable() 
				{
					
					public void run() 
					{
						// TODO Auto-generated method stub
						Update(Url);
				
					}
				};
				updateThread = new Thread(null, Update, "MagentoBackground");
				updateThread.start();
			}
		});
	}
	
	private void Initiliaze() {
		// TODO Auto-generated method stub
		btnUpdate=(Button) findViewById(R.id.btnUpdateApp);
	}

	public void Update(String apkurl){
	      try 
	      {
	            URL url = new URL(apkurl);
	            HttpURLConnection c = (HttpURLConnection) url.openConnection();
	            c.setRequestMethod("GET");
	            c.setDoOutput(true);
	            c.connect();

	            String PATH = Environment.getExternalStorageDirectory() + "/download/";
	            File file = new File(PATH);
	            file.mkdirs();
	            File outputFile = new File(file, "SayacBakimClient.apk");
	            FileOutputStream fos = new FileOutputStream(outputFile);

	            InputStream is = c.getInputStream();

	            byte[] buffer = new byte[1024];
	            int len1 = 0;
	            while ((len1 = is.read(buffer)) != -1) {
	                fos.write(buffer, 0, len1);
	            }
	            fos.close();
	            is.close();
	            
	            Intent intent = new Intent(Intent.ACTION_VIEW);
	            intent.setDataAndType(Uri.parse("file://"+"/mnt/sdcard/download/SayacBakimClient.apk"), "application/vnd.android.package-archive");
	            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            m_ProgressDialog.dismiss();
	            startActivity(intent);
	            
	        } catch (final IOException e) 
	        {
	        	runOnUiThread(new Runnable() 
		        {                
		            public void run() 
		            {
		                //Your toast code here
		                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
			            m_ProgressDialog.dismiss();
		            }
		        });
	        
	            
	        }
	  }
	

}
