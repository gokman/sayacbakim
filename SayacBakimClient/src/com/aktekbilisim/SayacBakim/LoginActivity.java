package com.aktekbilisim.SayacBakim;

import java.io.File;

import Objects.User;
import ServiceLocater.GlobalVariables;
import ServiceLocater.Service;
import ServiceLocater.ServiceAydem;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements LocationListener {
	/** Called when the activity is first created. */
	private ProgressDialog m_ProgressDialog = null;
	private Runnable Login;
	private Thread loginThread;
	public static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_USERNAME = "username";
	private static final String PREF_PASSWORD = "password";
	private String  username;
	private String password;
	View view;
	@Override
	public void onCreate(Bundle savedInstanceState) {
    try 
    {
	super.onCreate(savedInstanceState);

		setContentView(R.layout.login);
		final Activity activity = new Activity();
		final EditText txtUserName = (EditText) findViewById(R.id.txtUserName);
		final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		GlobalVariables.getIntance().Deviceid=telephonyManager.getDeviceId(); 
		SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
		txtUserName.setText( pref.getString(PREF_USERNAME, null));
		txtPassword.setText( pref.getString(PREF_PASSWORD, null));
		ServiceLocater.GlobalVariables.getIntance().locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		ServiceLocater.GlobalVariables.getIntance().locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0, 0, LoginActivity.this);
		
		Login= new Runnable() {

			public void run() {
				try 
				{
					User user = ServiceLocater.ServiceAydem.getIntance().Login( txtUserName.getText().toString(),  txtPassword.getText().toString());
					if(user!=null)
					{
					GlobalVariables.getIntance().LoginResult=Objects.LoginResult.Giris_Basarili;
					}
					else
					{
					GlobalVariables.getIntance().LoginResult=Objects.LoginResult.Giris_Basarisiz;
					}
					if (GlobalVariables.getIntance().LoginResult == Objects.LoginResult.Giris_Basarili) 
					{
						GlobalVariables.getIntance().user = user;
						Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
						startActivity(intent);
					}
					else
					{
						runOnUiThread(new Runnable() 
				        {                
				            public void run() 
				            {
				                //Your toast code here
				            	Toast.makeText(LoginActivity.this, "Hatalý Kullanýcý adý veya þifre", Toast.LENGTH_LONG).show();
				            }
				        });
					}
					m_ProgressDialog.dismiss();
				}
				catch (final Exception e) 
				{
					m_ProgressDialog.dismiss();
					
					// TODO: handle exception
					Objects.Log.LogException( txtUserName.getText().toString(), e, Objects.Log.loginException, "Giriþ yapýlamadý.UnKnownHostException internet baðlantýsýnýn olmadýðý durumda alýnýr.");
					runOnUiThread(new Runnable() 
			        {                
			            public void run() 
			            {
			                //Your toast code here
			            	Intent intent = new Intent(LoginActivity.this, ErrorActivity.class);
			        		intent.putExtra("Error_Message", e.toString());
			        		intent.putExtra("ErrorType", 0);
			        		startActivity(intent);
			            }
			        });
				}
			}
		};
		btnLogin.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				m_ProgressDialog = ProgressDialog.show(LoginActivity.this,"Lütfen Bekleyiniz...", "Giriþ yapýlýyor ...", false);
				loginThread = new Thread(null, Login, "MagentoBackground");
				loginThread.start();
				
				getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
		        .edit()
		        .putString(PREF_USERNAME, txtUserName.getText().toString())
		        .putString(PREF_PASSWORD, txtPassword.getText().toString())
		        .commit();
				
				
				// Toast.makeText(SayacBakimClientActivity.this,
				 //"Hatalý Kullanýcý adý veya Þifre", Toast.LENGTH_LONG).show();

			}

		});
    } catch (Exception e) 
    {
    	Intent intent = new Intent(LoginActivity.this, ErrorActivity.class);
		intent.putExtra("Error_Message", e.toString());
		intent.putExtra("ErrorType", 0);
		startActivity(intent);
	}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "Kapat").setIcon(R.drawable.kapat);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 1:
			 Intent intent = new Intent(Intent.ACTION_MAIN);
	    	 intent.addCategory(Intent.CATEGORY_HOME);
	    	 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	 startActivity(intent);

			finish();
			return true;
		case 2:
			try 
			{
				AssetManager assetManager = getAssets();
				Intent intent2 = new Intent(Intent.ACTION_VIEW);
				intent2.addCategory(Intent.CATEGORY_BROWSABLE);
				File file = new File("/mnt/sdcard/SayacBakim/Help.htm");
				intent2.setDataAndType(Uri.fromFile(file), "text/html");
				startActivity(intent2);
			} catch (Exception e) 
			{
				// TODO: handle exception
				Log.i("getfilepath", e.getMessage());
			}
		
			return true;
		}
		return super.onOptionsItemSelected(item);

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLocationChanged(Location location) 
	{
		// TODO Auto-generated method stub
		if(location!=null)
	    {
			try 
			{
				ServiceLocater.GlobalVariables.getIntance().MapY=Double.toString(location.getLongitude());
				ServiceLocater.GlobalVariables.getIntance().MapX=Double.toString(location.getLatitude());
				OrderListActivity.SyncLocation();
				EditOrderActivity.SyncLocation();
				WorkOrderExList.SyncLocation();
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				Objects.Log.LogException( GlobalVariables.getIntance().user.UserName, e, Objects.Log.locationReadException, "Lokasyon alýnýrken hata alýndý. SayacBakimActivity Line:225");
			}
	    }
		else
		{
			Intent intent = new Intent(LoginActivity.this, ErrorActivity.class);
    		intent.putExtra("Error_Message", "Lokasyon Bilgisi bulunduðun alandan alýnamýyor.En son alýnan koordinasyon bilgileri kullanýlacaktýr.");
    		intent.putExtra("ErrorType", 0);
    		startActivity(intent);
		}
	}

	public void onProviderDisabled(String provider) 
	{
		// TODO Auto-generated method stub
		
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}