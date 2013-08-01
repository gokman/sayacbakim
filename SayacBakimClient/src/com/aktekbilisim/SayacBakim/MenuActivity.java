package com.aktekbilisim.SayacBakim;



import java.io.File;


import ServiceLocater.Service;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

//import com.markupartist.android.widget.ActionBar;
//import com.markupartist.android.widget.ActionBar.IntentAction;

public class MenuActivity extends Activity 
{
	ImageButton btnnetwork;
	ImageButton btnworkorder;
	ImageButton btnsetting;
	ImageButton	btnhelp;
	ImageButton	btnupdate;
	ImageButton btnLoadPhoto;
	ImageButton btnPano;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		try {
			Initialize();
		} catch (Exception e) {
			// TODO: handle exception
			String message = e.getMessage();
		}
	}
	
	 private void Initialize() {
		// TODO Auto-generated method stubasd
		 btnnetwork=(ImageButton) findViewById(R.id.imageButton1);
		 btnPano=(ImageButton) findViewById(R.id.imageButton4);
		 btnworkorder=(ImageButton)findViewById(R.id.imageButton2);
		 //btnsetting=(ImageButton) findViewById(R.id.btnsetting);
		 btnhelp=(ImageButton)findViewById(R.id.imageButton3);
		 btnupdate=(ImageButton)findViewById(R.id.imageButton6);
		 btnLoadPhoto=(ImageButton)findViewById(R.id.imageButton5);
		 btnnetwork.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Service.getIntance().ToastMessage(getApplicationContext(), "Geliþtirilme aþamasýnda");
			}
		});
		 btnworkorder.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuActivity.this,FilterExActivity.class);
				startActivity(intent);
			}
		});
//		 btnsetting.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Service.getIntance().ToastMessage(getApplicationContext(), "Geliþtirilme aþamasýnda");
//			}
//		});
		 btnhelp.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
			}
		});
		 btnupdate.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intnt = new Intent(MenuActivity.this,UpdateActivity.class);
				startActivity(intnt);
			}
		});
		 btnLoadPhoto.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent intent= new Intent(MenuActivity.this,PhotoListActivity.class);
				startActivity(intent);
			}
		});
		 btnPano.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(MenuActivity.this,PanoProcessSelection.class);
				startActivity(intent);
			}
		});
	}
	public static Intent createIntent(Context context) {
	        Intent i = new Intent(context, MenuActivity.class);
	        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        return i;
	    }
	    private Intent createShareIntent() {
	        final Intent intent = new Intent(Intent.ACTION_SEND);
	        intent.setType("text/plain");
	        intent.putExtra(Intent.EXTRA_TEXT, "Shared from the ActionBar widget.");
	        return Intent.createChooser(intent, "Share");
	    }
	
}
