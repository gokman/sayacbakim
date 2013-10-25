package com.aktekbilisim.SayacBakim;

import ServiceLocater.ServiceAydem;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AppStartActivity extends Activity 
{
	TextView version;
	TelephonyManager telephonyManager;
	ProgressBar progressBar1;
	private Thread connectionThread;
	private Runnable controller = new Runnable() {
		
		public void run() {
			// TODO Auto-generated method stub
			Looper.prepare();
			Resources res = getResources();
			if(!HaveNetworkConnection())
			{
				Intent intent = new Intent(AppStartActivity.this, ErrorActivity.class);
	    		intent.putExtra("Error_Message", "Internet Eriþiminiz Bulunmuyor.Lütfen Network Baðlanýtýnýzý Kontrol Ediniz!");
	    		startActivity(intent);
	    		return;
			}
			ServiceAydem.getIntance().UpSertPhone(telephonyManager.getDeviceId());
			
//			if(!ServiceAydem.getIntance().IsPhoneVersionUpToDate(telephonyManager.getDeviceId(),res.getString(R.string.app_name)))
//			{
//				Intent intent = new Intent(AppStartActivity.this, ErrorActivity.class);
//	    		intent.putExtra("Error_Message", "Uygulumanýn yeni bir sürümü var.Güncellemek ister misiniz?");
//	    		intent.putExtra("ErrorType", 1);
//	    		startActivity(intent);
//	    		return;
//			}
			
			//(res.getString(R.string.app_name), GlobalVariables.getIntance().user.UserId,GlobalVariables.getIntance().Deviceid);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent(AppStartActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_start);
		Initiliaze();
		connectionThread=new Thread(null, controller, "MagentoBackground");
		connectionThread.start();
	}
	private void Initiliaze() {
		// TODO Auto-generated method stub
		telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		
		Resources res = getResources();
		version=(TextView)findViewById(R.id.txtVersion);
		version.setText(res.getString(R.string.app_name));
		progressBar1=(ProgressBar)findViewById(R.id.progressBar1);
	}
	public boolean HaveNetworkConnection() {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
}
