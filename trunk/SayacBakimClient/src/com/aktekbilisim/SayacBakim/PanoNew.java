package com.aktekbilisim.SayacBakim;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.IOException;

import Objects.Base64Coder;
import Objects.Pano;
import Objects.Response;
import Objects.xxaktpanel;
import ServiceLocater.GlobalVariables;
import ServiceLocater.Service;
import ServiceLocater.ServiceAydem;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

//import com.markupartist.android.widget.ActionBar;
//import com.markupartist.android.widget.ActionBar.IntentAction;

public class PanoNew extends Activity 
{
	private ProgressDialog m_ProgressDialog = null;
	final Activity activity = new Activity();
	ImageButton btnPanoBarcode;
	EditText txtPanelId;
	EditText txtModemImeiNo;
	EditText txtTrafoSayaci;
	EditText txtSokakSayaci;
	EditText txtTrafoCode;
	EditText txtTesisatNo;
	EditText txtTrafoCarpan;
	EditText txtSokakCarpan;
	TextView txtPhotoList;
	Button btnPanoKaydet;
	Button btnmalzeme;
	Button btnHome;
	private String image_string;
	Button btnPanoListesi;
	Spinner spinner;
	ImageButton btnPicture;
	Pano pano;
	xxaktpanel xxpano= new xxaktpanel();
	String processType;
	private  Uri imageUri ;
	private static final int TAKE_PHOTO_CODE = 1;
	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.panonew);
		Initiliaze();
		if(GlobalVariables.getIntance().pano!=null)
		{
			pano=GlobalVariables.getIntance().pano;
			SetInitialValue(pano);
		}
		btnHome.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PanoNew.this,MenuActivity.class);
				startActivity(intent);
				finish();
			}
		});
		btnPanoBarcode.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				 intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
				 startActivityForResult(intent, 0);
			}
		});
		btnmalzeme.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(GlobalVariables.getIntance().pano==null)
					GlobalVariables.getIntance().pano=pano;
				
				Intent intent = new Intent(PanoNew.this, PanoMalzemeListActivity.class);
				startActivity(intent);
			}
		});
		btnPanoKaydet.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!HaveNetworkConnection())
				{
					Intent intent = new Intent(PanoNew.this, ErrorActivity.class);
		    		intent.putExtra("Error_Message", "Internet Eriþiminiz Bulunmuyor.Lütfen Network Baðlanýtýnýzý Kontrol Ediniz!");
		    		startActivity(intent);
		    		return;
				}
				if((spinner.getSelectedItem().toString().equalsIgnoreCase("Çalýþýlýyor")))
				{
					if(txtPanelId.getText().toString()=="")
					{
						Intent intent = new Intent(PanoNew.this, ErrorActivity.class);
			    		intent.putExtra("Error_Message", "Pano alaný boþ býrakýlamaz!");
			    		intent.putExtra("ErrorType", 0);
			    		startActivity(intent);
			    		return ;
					}
					xxpano=ServiceAydem.getIntance().GetPanelVariable(txtPanelId.getText().toString());
					if(xxpano!=null)
					{
					txtModemImeiNo.setText(xxpano.ModemImeiNo);
					txtTrafoSayaci.setText(xxpano.Sayac1);
					txtSokakSayaci.setText(xxpano.Sayac2);
					}
					else
					{
						Intent intent = new Intent(PanoNew.this, ErrorActivity.class);
			    		intent.putExtra("Error_Message", "Pano bulunamadý!");
			    		intent.putExtra("ErrorType", 0);
			    		startActivity(intent);
			    		return ;
					}
				}
				if (getWorkOrderStatuCodeForName(pano==null ? 1 : pano.Statu).equalsIgnoreCase(spinner.getSelectedItem().toString())) 
				{
					if(!(spinner.getSelectedItem().toString().equalsIgnoreCase("Çalýþýlýyor")||spinner.getSelectedItem().toString().equalsIgnoreCase("Tamamlandý")))
					{
					Service.getIntance().ToastMessage(PanoNew.this,"Durum güncellemesi gerçekleþtiriniz.");
					return;
					}
				}
				if(txtPanelId.getText().toString().equals(""))
				{
					Intent intent = new Intent(PanoNew.this, ErrorActivity.class);
		    		intent.putExtra("Error_Message", "Lütfen bir pano giriniz!");
		    		intent.putExtra("ErrorType", 0);
		    		startActivity(intent);
		    		return ;
				}
				if((spinner.getSelectedItem().toString().equalsIgnoreCase("Tamamlandý"))&&(txtModemImeiNo.getText().toString().equals("")||txtTrafoSayaci.getText().toString().equals("")||txtSokakSayaci.getText().toString().equals("")))
				{
					Intent intent = new Intent(PanoNew.this, ErrorActivity.class);
		    		intent.putExtra("Error_Message", "Panoya ait MomdemImeiNo,Sayac1 ve Sayac2 boþ býrakýlamaz!");
		    		intent.putExtra("ErrorType", 0);
		    		startActivity(intent);
		    		return;
				}
				if(spinner.getSelectedItem().toString().equalsIgnoreCase("Tamamlandý")&&(txtTrafoCode.getText().toString().equals("")||txtTrafoCarpan.getText().toString().equals("")))
				{
					Intent intent = new Intent(PanoNew.this, ErrorActivity.class);
		    		intent.putExtra("Error_Message", "Trafo Kodu ve çarpaný boþ býrakýlamaz");
		    		intent.putExtra("ErrorType", 0);
		    		startActivity(intent);
		    		return;
				}
				if(spinner.getSelectedItem().toString().equalsIgnoreCase("Tamamlandý")&&(txtTesisatNo.getText().toString().equals("")||txtSokakCarpan.getText().toString().equals("")))
				{
					Intent intent = new Intent(PanoNew.this, ErrorActivity.class);
		    		intent.putExtra("Error_Message", "Tesisat No ve çarpaný boþ býrakýlamaz");
		    		intent.putExtra("ErrorType", 0);
		    		startActivity(intent);
		    		return;
				}
//				if(spinner.getSelectedItem().toString().equalsIgnoreCase("Tamamlandý")&&!ServiceAydem.getIntance().IsTrafoAvailable(txtTrafoCode.getText().toString()))
//				{
//					Intent intent = new Intent(PanoNew.this, ErrorActivity.class);
//		    		intent.putExtra("Error_Message", "Trafo Kodu sistemde bulunamadý.");
//		    		intent.putExtra("ErrorType", 0);
//		    		startActivity(intent);
//		    		return;
//				}
//				if(spinner.getSelectedItem().toString().equalsIgnoreCase("Tamamlandý")&&!ServiceAydem.getIntance().IsTesisatNoAvailable(txtTesisatNo.getText().toString()))
//				{
//					Intent intent = new Intent(PanoNew.this, ErrorActivity.class);
//		    		intent.putExtra("Error_Message", "Tesisat No sistemde bulunamadý.");
//		    		intent.putExtra("ErrorType", 0);
//		    		startActivity(intent);
//		    		return;
//				}
				if(spinner.getSelectedItem().toString().equalsIgnoreCase("Tamamlandý")&&(ServiceLocater.GlobalVariables.getIntance().MapX==null||ServiceLocater.GlobalVariables.getIntance().MapX==null))
				{
					Service.getIntance().ToastMessage(PanoNew.this, "Koordinasyon bilgileriniz alýnamadý.X Y koordinatlarýnýn dolmasýný saðlayýnýz.");
					return;
				}
				m_ProgressDialog = ProgressDialog.show(
						PanoNew.this, "Lütfen Bekleyiniz...",
						"Deðiþikler Kaydediliyor ...", true);
				activity.runOnUiThread(new Runnable() {
					
					public void run() 
					{
						// TODO Auto-generated method stub
					pano= new Pano();
					pano.PanelId=xxpano.PanelId;
					pano.PhotoList=ServiceLocater.GlobalVariables.getIntance().PhotoList;
					pano.ModemImeiNo=xxpano.ModemImeiNo;
					pano.Sayac1=xxpano.Sayac1;
					pano.Sayac2=xxpano.Sayac2;
					pano.Statu=getStatuFromText(spinner.getSelectedItem().toString());
					pano.UserId=GlobalVariables.getIntance().user.UserId;
					pano.TrafoCode=txtTrafoCode.getText().toString();
					pano.TesisatNo=txtTesisatNo.getText().toString();
					pano.TesisatCarpan=txtSokakCarpan.getText().toString();
					pano.TrafoCarpan=txtTrafoCarpan.getText().toString();
					Response response= ServiceAydem.getIntance().UpsertPanel(pano);
					m_ProgressDialog.dismiss();
					if(response.Result==0)
					{
						if(spinner.getSelectedItem().toString().equalsIgnoreCase("Tamamlandý"))
						{
							Intent intent = new Intent(PanoNew.this,PanoListActivity.class);
							startActivity(intent);
							finish();
							return;
						}
						//19.08.2013 tarihinde kaldýrýldý
						//Service.getIntance().ToastMessage(PanoNew.this, "Deðiþiklikler kaydedildi.");
					}
					else
					{
						Intent intent = new Intent(PanoNew.this, ErrorActivity.class);
			    		intent.putExtra("Error_Message", response.Message);
			    		intent.putExtra("ErrorType", 0);
			    		startActivity(intent);
					}
					}
				});
			}
		});
		btnPanoListesi.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent = new Intent(PanoNew.this,PanoListActivity.class);
			startActivity(intent);
			finish();
			}
		});
		btnPicture.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(xxpano==null)
					{
						Intent intent = new Intent(PanoNew.this, ErrorActivity.class);
			    		intent.putExtra("Error_Message", "Fotoðraf çekmeden önce pano seçmelisiniz.");
			    		intent.putExtra("ErrorType", 0);
			    		startActivity(intent);
						return;
					}
					takePhoto();
					ServiceLocater.GlobalVariables.getIntance().WhichImage="first";
				}
			});
	}
	private boolean HaveNetworkConnection() {
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
	private String getWorkOrderStatuCodeForName(int Status) 
	{
		if (Status== 1)
			return "Planlandý";
		else if (Status==2)
			return "Çalýþýlýyor";
		else if (Status==3)
			return "Tamamlandý";
		return "";
	}
	private int getStatuFromText(String Statu)
	{
		if(Statu=="Planlandý")
			return 1;
		if(Statu=="Çalýþýlýyor")
			return 2;
		if(Statu=="Tamamlandý")
			return 3;
		return 0;
	}
	private void takePhoto()
	{  
		 //Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		 //startActivityForResult(i, TAKE_PHOTO_CODE);  
		 
		 ContentValues values = new ContentValues();
         values.put(MediaStore.Images.Media.TITLE, "New Picture1");
         values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
         imageUri =getContentResolver().insert( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
         Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
         startActivityForResult(intent, TAKE_PHOTO_CODE);
	}
	private void SetInitialValue(Pano pano)
	{
		txtPanelId.setText(pano.PanelId);
		txtPhotoList.setText(pano.PhotoList);
		txtTrafoCode.setText(pano.TrafoCode);
		txtTrafoCarpan.setText(pano.TrafoCarpan);
		txtTesisatNo.setText(pano.TesisatNo);
		txtSokakCarpan.setText(pano.TesisatCarpan);
		txtSokakSayaci.setText(pano.Sayac2);
		txtTrafoSayaci.setText(pano.Sayac1);
		txtModemImeiNo.setText(pano.ModemImeiNo);
		txtPhotoList.setText(pano.PhotoList);
		int spinnerPosition = adapter.getPosition(getWorkOrderStatuCodeForName(pano.Statu));
		spinner.setSelection(spinnerPosition);
		xxpano.PanelId=pano.PanelId;
		xxpano.ModemImeiNo=pano.ModemImeiNo;
		xxpano.Sayac1=pano.Sayac1;
		xxpano.Sayac2=pano.Sayac2;
	}
	private void Initiliaze()
	{
		Bundle extras = getIntent().getExtras();
		processType=extras.getString("processType");
		pano=GlobalVariables.getIntance().pano;
		//final ActionBar trafoBar = (ActionBar) findViewById(R.id.TrafoBar);
		//trafoBar.setTitle("Trafo Sayacý");
		//final ActionBar sokakBar = (ActionBar) findViewById(R.id.SokakBar);
		//sokakBar.setTitle("Sokak Sayacý");
		btnHome=(Button)findViewById(R.id.btnHome);
		btnPanoBarcode=(ImageButton)findViewById(R.id.btnPanoBarcode);
		txtPanelId=(EditText)findViewById(R.id.txtPanelId);
		txtPhotoList=(TextView)findViewById(R.id.txtPhotoList);
		txtTrafoSayaci=(EditText)findViewById(R.id.txtTrafoSayaci);
		txtSokakSayaci=(EditText)findViewById(R.id.txtSokakSayaci);
		txtModemImeiNo=(EditText)findViewById(R.id.txtModemImeiNo);
		txtTrafoCode=(EditText)findViewById(R.id.txtTrafoCode);
		txtTesisatNo=(EditText)findViewById(R.id.txtTesisatNo);
		txtTrafoCarpan=(EditText)findViewById(R.id.txtTrafoCarpan);
		txtSokakCarpan=(EditText)findViewById(R.id.txtSokakCarpan);
		btnPanoKaydet=(Button) findViewById(R.id.btnPanoSave);
		btnPanoListesi=(Button) findViewById(R.id.btnPanoList);
		btnmalzeme=(Button)findViewById(R.id.btnmalzeme);
		btnPicture=(ImageButton)findViewById(R.id.btnPicture);
		spinner = (Spinner)findViewById(R.id.drpStatus);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, new String[] { "Planlandý","Çalýþýlýyor","Tamamlandý"});
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	public static Intent createIntent(Context context) 
	{
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// TODO Auto-generated method stub
				if (requestCode == 0) 
				{
					if (resultCode == RESULT_OK) 
					{
						String contents = data.getStringExtra("SCAN_RESULT");
						String format = data.getStringExtra("SCAN_RESULT_FORMAT");
						txtPanelId.setText(contents);
					}
				}
				else if (requestCode==1){
					
				try 
				{
					//Bitmap thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

					String imageurl = getRealPathFromURI(imageUri);
					
					BitmapFactory.Options options = new BitmapFactory.Options();
				    options.inSampleSize = 8;
				    
				    Bitmap bitmap = BitmapFactory.decodeFile(imageurl, options);
				    File sdcard = Environment.getExternalStorageDirectory();
				    File from = new File(imageurl);
				    String newFilePath ="DCIM/Camera/Panel_"+xxpano.PanelId.toString()+"_"+Math.random()+".jpg";
				    
				    File to = new File(sdcard, newFilePath);
				    from.renameTo(to);
				    
			        ServiceLocater.GlobalVariables.getIntance().PhotoList+=","+sdcard+"/"+newFilePath;
			        txtPhotoList.setText(txtPhotoList.getText().toString()+System.getProperty("line.separator")+newFilePath.replace("DCIM/Camera/", ""));
				}
				catch (Exception e) 
				{
					Objects.Log.LogException(GlobalVariables.getIntance().user.UserName, e, Objects.Log.photoReadException, "Resim dosyasý alýnýrken hata alýndý fotoðran çektikten sonra okunmaya çalýþýrken hata alýndý.EditOrderActivity Line : 610");
					Intent intent1 = new Intent(PanoNew.this, ErrorActivity.class);
					intent1.putExtra("Error_Message",e.getMessage());
					startActivity(intent1);
				}
				}
	}
	private String convertBmpToByte(Bitmap bmp) throws IOException 
	 {
		// TODO Auto-generated method stub,
		try 
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
			bmp.compress(CompressFormat.JPEG, 100 /*ignored for PNG*/, bos); 
			byte[] bitmapdata = bos.toByteArray();
			bos.close();
			bos=null;
			image_string=Base64Coder.encodeLines(bitmapdata);
			return image_string;
		} catch (Exception e) 
		{
			// TODO: handle exception
			return "";
		}
	}
	public String getRealPathFromURI(Uri contentUri) 
	{
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
