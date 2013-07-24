package com.aktekbilisim.SayacBakim;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Objects.Base64Coder;
import Objects.EnumWorkOrderStatus;
import Objects.LoginResult;
import Objects.Response;
import Objects.UpdateProcessType;
import Objects.WorkOrder;
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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditOrderActivity extends Activity implements LocationListener {

	private TextView description;
	private static WorkOrder workorder;
	private Spinner spinner;
	private LocationManager locationManager;
	private String provider;
	private ProgressDialog m_ProgressDialog = null;
	final Activity activity = new Activity();
	private TextView lblBuildingName ;
	private TextView lblBuildingCode ;
	private TextView lblCityName ;
	private TextView lblStreetName ;
	private TextView lblMeterModel;
	private TextView txtSerialNumber ;
	private TextView txtModemNumber ;
	private TextView lblModemGsmNo;
	private TextView SubscriberNumber;
	private TextView lblAssigned ;
	private TextView lblTown ;
	private TextView lblAdressDetail;
	private TextView lblWorkType ;
	private TextView txtOldSerialNumber;
	private TextView Multiplier;
	private TextView txtElecCutIntrvl;
	private ImageButton firstImage;
	private ImageButton secondImage;
	private Button GeoMap ;
	private Button btnBack ;
	private Button btnSave ;
	private ImageButton btnSerialBarcode;
	private ImageButton btnOldSerialNumber;
	private ImageButton btnModemBarcode;
	private ImageButton btnDoc;
	private CheckBox chcUnique;
	private static final int TAKE_PHOTO_CODE = 1;
	private String image_string;
	private  Uri imageUri ;
	public static TextView MapX ;
	public static TextView MapY ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editorder);
		
		InitializeContent();
		GeoMap.setOnClickListener(new OnClickListener() 
		{

			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					new Thread(new Runnable() {
						public void run() {
							// TODO Auto-generated method stub
							Intent intent = new Intent(EditOrderActivity.this,
									MeterDirectionMap.class);
							startActivity(intent);
						}
					}).start();
				} catch (Exception e) {
					// TODO: handle exception
					Log.i("googlemap", e.getMessage());
				}
			}
		});
		btnSave.setOnClickListener(new OnClickListener() 
		{

			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					
					if (workorder.Statu.equalsIgnoreCase(spinner.getSelectedItem().toString())) 
					{
						if(!(spinner.getSelectedItem().toString().equalsIgnoreCase("Montaj Tamamlandý")||(spinner.getSelectedItem().toString().equalsIgnoreCase("Çalýþýlýyor"))))
						{
						Service.getIntance().ToastMessage(EditOrderActivity.this,"Durum güncellemesi gerçekleþtiriniz.");
						
						return;
						}
					}
					if (spinner.getSelectedItem().toString().equalsIgnoreCase("Ýptal")|| spinner.getSelectedItem().toString().equalsIgnoreCase("Beklemede")) 
					{
						if (description.getText().toString().equalsIgnoreCase("")) {
							
							Toast.makeText(EditOrderActivity.this,"Açýklama alaný boþ býrakýlamaz",Toast.LENGTH_LONG).show();
							return;
						}
					}
					
					if (spinner.getSelectedItem().toString().equalsIgnoreCase("Çalýþýlýyor")) 
					{
						if(!ServiceLocater.GlobalVariables.getIntance().firstImageValidation)
						{
							Toast.makeText(EditOrderActivity.this,"Çalýþmaya baþlamadan önce ilk fotoðrafý çekiniz.",Toast.LENGTH_LONG).show();
							return;
						}
						if(ServiceLocater.GlobalVariables.getIntance().MapX==null||ServiceLocater.GlobalVariables.getIntance().MapX==null)
						{
							Service.getIntance().ToastMessage(EditOrderActivity.this, "Koordinasyon bilgileriniz alýnamadý.X Y koordinatlarýnýn dolmasýný saðlayýnýz.");
							return;
						}
					}
					
					if (txtSerialNumber.getText().toString().trim().equals("")&& (spinner.getSelectedItem().toString().equalsIgnoreCase("Montaj Tamamlandý")||spinner.getSelectedItem().toString().equalsIgnoreCase("Ýþ Emri Tamamlandý")) ) {
						Service.getIntance().ToastMessage(EditOrderActivity.this, "Lütfen Sayaç Seri Numarasýný Giriniz.");
						return;
					}
					if (spinner.getSelectedItem().toString().equalsIgnoreCase("Montaj Tamamlandý") ) 
					{
						//Sayac ve Modemin Envanterde bulunup bulunmadýðýný kontrolünü gerçekleþtiriyor.
						if(!ServiceLocater.ServiceAydem.getIntance().IsModemAvailable(txtModemNumber.getText().toString().trim()) )
						{
							Intent intent = new Intent(EditOrderActivity.this, ErrorActivity.class);
							intent.putExtra("Error_Message", "Girmiþ olduðunuz Modem Ýmei Numarasý sistemde bulunamadý.Modem Ýmei numarasýný doðruluðundan emin olunuz.");
							startActivity(intent);
							return;
						}
						if(!ServiceAydem.getIntance().IsModemUsable(txtModemNumber.getText().toString().trim(), chcUnique.isChecked()))
						{
							Intent intent = new Intent(EditOrderActivity.this, ErrorActivity.class);
							intent.putExtra("Error_Message", "Modem Envanterde bulundu ancak daha önceden kullanýlmýþ gözüküyor.");
							startActivity(intent);
							return;
						}
						if(!ServiceLocater.ServiceAydem.getIntance().IsSayacAvailable(txtSerialNumber.getText().toString().trim()))
						{
							Intent intent = new Intent(EditOrderActivity.this, ErrorActivity.class);
							intent.putExtra("Error_Message", "Sayaç Envanterde bulunamadý.");
							startActivity(intent);
							return;
						}
						if(!ServiceLocater.ServiceAydem.getIntance().IsSayacUsable(txtSerialNumber.getText().toString().trim()))
						{
							Intent intent = new Intent(EditOrderActivity.this, ErrorActivity.class);
							intent.putExtra("Error_Message", "Sayaç Envanterde bulundu ancak önceden kullanýlmýþ gözüküyor.");
							startActivity(intent);
							return;
						}
						if(!ServiceLocater.GlobalVariables.getIntance().secondImageValidation)
						{
							Toast.makeText(EditOrderActivity.this,"Çalýþma sonrasý son durumun fotoðrafýný çekiniz.",Toast.LENGTH_LONG).show();
							return;
						}
						if(txtOldSerialNumber.getText().toString().trim().equals(""))
						{
							Service.getIntance().ToastMessage(EditOrderActivity.this, "Lütfen Eski Sayaç Seri Numarasýný Giriniz.");
							return;
						}
						if (txtModemNumber.getText().toString().trim().equals("") ) 
						{
							Service.getIntance().ToastMessage(EditOrderActivity.this, "Lütfen Modem Seri Numarasýný Giriniz.");
							return;
						}
					}
				} 
				catch (Exception e2) 
				{	
					// TODO: handle exception
					Objects.Log.LogException( GlobalVariables.getIntance().user.UserName, e2, Objects.Log.EditArea1_Exception, "Web Servis çaðýrýlmadan önce gerçekleþtirelen kontrollerde hata alýndý. EditOrderActivity line : 187");
					
					Intent intent = new Intent(EditOrderActivity.this, ErrorActivity.class);
					intent.putExtra("Error_Message","Deðiþikler kaydedilemedi.Sistem yöneticinize baþvurunuz.\n"+e2.toString());
					intent.putExtra("ErrorType", 0);
					startActivity(intent);
				}
					m_ProgressDialog = ProgressDialog.show(
							EditOrderActivity.this, "Lütfen Bekleyiniz...",
							"Deðiþikler Kaydediliyor ...", true);

					activity.runOnUiThread(new Runnable() 
					{
						
						public void run() {
							try {
								Response Result;
								workorder.StatuId = EnumWorkOrderStatus.getWorkOrderStatuCodeForName(spinner.getSelectedItem().toString());
								workorder.Statu=spinner.getSelectedItem().toString();
								workorder.Description=description.getText().toString();
								workorder.ModemImeiNo=txtModemNumber.getText().toString().trim();
								workorder.SerialNo=txtSerialNumber.getText().toString().trim();
								//workorder.secondImage=ServiceLocater.GlobalVariables.getIntance().secondImageByte;
								workorder.OldSerialNo=txtOldSerialNumber.getText().toString().trim();
								workorder.LAT=ServiceLocater.GlobalVariables.getIntance().MapX;
								workorder.LONG=ServiceLocater.GlobalVariables.getIntance().MapY;
								workorder.firstimagepath=ServiceLocater.GlobalVariables.getIntance().firstImagePath;
								workorder.secondimagepath=ServiceLocater.GlobalVariables.getIntance().secondImagePath;
								UpdateProcessType updateprocess= UpdateProcessType.PLANLANDI;
								
								
								
								BitmapFactory.Options options = new BitmapFactory.Options();
							    options.inSampleSize = 8;
								Bitmap firstbitmap = BitmapFactory.decodeFile( workorder.firstimagepath, options );
							    Bitmap secondbitmap = BitmapFactory.decodeFile( workorder.secondimagepath, options );
							    ServiceLocater.GlobalVariables.getIntance().firstImageByte=convertBmpToByte(firstbitmap);
							    ServiceLocater.GlobalVariables.getIntance().secondImageByte=convertBmpToByte(secondbitmap);
							    
							    
							    
								if (spinner.getSelectedItem().toString().equalsIgnoreCase("Ýþ Emri Tamamlandý"))
								{
									Intent intent = new Intent(EditOrderActivity.this,MeterSync.class);
									intent.putExtra("WorkOrder", workorder);
									startActivity(intent);
									m_ProgressDialog.dismiss();
									return;
								}
								Result=ServiceAydem.getIntance().updateWorkOrder(workorder,updateprocess.getName(spinner.getSelectedItem().toString()));
								
								if (Result.Result==0)
								{
									Service.getIntance().ToastMessage(EditOrderActivity.this,"Deðiþikler kaydedildi.");
									GlobalVariables.getIntance().StatusCodeForFilter = 0;
									InitializeContent();
									//Intent intent = new Intent(EditOrderActivity.this,OrderListActivity.class);
									//startActivity(intent);
								} 
								else 
								{
									Intent intent = new Intent(EditOrderActivity.this, ErrorActivity.class);
									intent.putExtra("Error_Message","Deðiþikler kaydedilemedi.-->"+Result.Message);
									intent.putExtra("ErrorType", 0);
									startActivity(intent);
								}
							} 
							catch (Exception e2) {
								// TODO: handle exception
								Objects.Log.LogException( GlobalVariables.getIntance().user.UserName, e2, Objects.Log.EditArea2_Exception, "Web Servis çaðýrýlmadan önce gerçekleþtirelen kontrollerde hata alýndý.EditOrderActivity line : 187");
								Intent intent = new Intent(EditOrderActivity.this, ErrorActivity.class);
								intent.putExtra("Error_Message","Deðiþikler kaydedilemedi.Sistem yöneticinize baþvurunuz.\n"+e2.toString());
								intent.putExtra("ErrorType", 0);
								startActivity(intent);
								m_ProgressDialog.dismiss();
								return;
							}
							m_ProgressDialog.dismiss();
						
							}
					});
				
			}

		});
		btnBack.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EditOrderActivity.this,
						OrderListActivity.class);
				startActivity(intent);
			}
		});
        btnSerialBarcode.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				 
				 intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
				 
				 startActivityForResult(intent, 0);
				 GlobalVariables.getIntance().BarcodeType=LoginResult.SerialBarcode;
			}
		});	
        btnModemBarcode.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				 
				 intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
				 
				 startActivityForResult(intent, 0);
				 GlobalVariables.getIntance().BarcodeType=LoginResult.ModemBarcode;
			}
		});
        btnOldSerialNumber.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				 
				 intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
				 
				 startActivityForResult(intent, 0);
				 GlobalVariables.getIntance().BarcodeType=LoginResult.OldSerialBarcode;
			}
		});
        firstImage.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				takePhoto();
				ServiceLocater.GlobalVariables.getIntance().WhichImage="first";
				firstImage.setTag("full");
			}
		});
        secondImage.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				takePhoto();
				ServiceLocater.GlobalVariables.getIntance().WhichImage="second";
				secondImage.setTag("full");
			}
		});
		} catch (Exception e2) 
		{
			// TODO: handle exception
			Service.getIntance().ToastMessage(EditOrderActivity.this, e2.toString());
		}
		btnDoc.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 Intent intent = new Intent(EditOrderActivity.this,DescriptionHistory.class);
			 intent.putExtra("WorkOrderId", workorder.ID);
			 startActivity(intent);
			}
		});
	}

	private void InitializeContent() 
	{
		MapX=(TextView) findViewById(R.id.lblMapX);
		MapY=(TextView) findViewById(R.id.lblMapY);
		SyncLocation();
		// TODO Auto-generated method stub
		chcUnique=(CheckBox) findViewById(R.id.chkUnique);
		chcUnique.setChecked(true);
		SubscriberNumber=(TextView) findViewById(R.id.lblSubscriberNumber);
		workorder = GlobalVariables.getIntance().workorder;
		lblCityName = (TextView) findViewById(R.id.lblCityName);
		lblModemGsmNo=(TextView) findViewById(R.id.lblModemGsmNo);
		lblAdressDetail =(TextView) findViewById(R.id.lblAddressDetail);
		lblMeterModel = (TextView) findViewById(R.id.lblMeterModel);
		txtSerialNumber = (TextView) findViewById(R.id.txtSerialNumber);
		txtModemNumber = (TextView) findViewById(R.id.txtModemNumber);
		lblAssigned = (TextView) findViewById(R.id.lblAssigned);
		lblTown = (TextView) findViewById(R.id.lblTownName);
		GeoMap = (Button) findViewById(R.id.btnGeoMap);
		btnBack = (Button) findViewById(R.id.btnBack);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSerialBarcode=(ImageButton) findViewById(R.id.btnSerialBarcode);
		btnModemBarcode=(ImageButton)findViewById(R.id.btnModemBarcode);
		btnOldSerialNumber=(ImageButton)findViewById(R.id.btnOldSerialNumber);
		firstImage=(ImageButton)findViewById(R.id.firstPicture);
		secondImage=(ImageButton)findViewById(R.id.secondPicture);
		txtOldSerialNumber=(TextView) findViewById(R.id.txtOldSerialNumber);		
		Multiplier =(TextView) findViewById(R.id.lblMultiplier);
		description = (TextView) findViewById(R.id.lblExplaination);
		txtElecCutIntrvl=(TextView) findViewById(R.id.txtElecCutIntrvl);
		spinner = (Spinner) findViewById(R.id.drpStatus);
		btnDoc = (ImageButton) findViewById(R.id.ImgBtnDoc);
		Bitmap bitmap ;
		BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inSampleSize = 8;
	    ServiceLocater.GlobalVariables.getIntance().firstImageValidation=false;
		ServiceLocater.GlobalVariables.getIntance().secondImageValidation=false;
		ServiceLocater.GlobalVariables.getIntance().secondImagePath="";
		ServiceLocater.GlobalVariables.getIntance().firstImagePath="";
		
		try {
			
		
	    if(workorder.firstimagepath!=null )
	    {
	    	if(workorder.firstimagepath!="" )
	    	{
			    ServiceLocater.GlobalVariables.getIntance().firstImagePath=workorder.firstimagepath;
			    bitmap = BitmapFactory.decodeFile(workorder.firstimagepath,options);
			    firstImage.setImageBitmap(bitmap);
			    if(bitmap==null)
			    ServiceLocater.GlobalVariables.getIntance().firstImageValidation=false;
			    else
			    ServiceLocater.GlobalVariables.getIntance().firstImageValidation=true;
	    	}
	    }
	    if(workorder.secondimagepath!=null)
	    {
	    	if(workorder.secondimagepath!="" )
	    	{
		    	ServiceLocater.GlobalVariables.getIntance().secondImagePath=workorder.secondimagepath;
		    	bitmap = BitmapFactory.decodeFile(workorder.secondimagepath,options);
			    secondImage.setImageBitmap(bitmap);
			    
			    if(bitmap==null)
			    ServiceLocater.GlobalVariables.getIntance().secondImageValidation=false;
			    else
			    ServiceLocater.GlobalVariables.getIntance().secondImageValidation=true;	
	    	}
	    }
		} catch (Exception e) 
		{
			// TODO: handle exception
			Objects.Log.LogException(GlobalVariables.getIntance().user.UserName, e, Objects.Log.photoSetException, "Veritabanýndan gelen fotoðraf verisi set edilirken hata alýndý.EditOrderActivity Line : 398");
		}
		txtElecCutIntrvl.setText(workorder.CutInterval);
		txtOldSerialNumber.setText(workorder.OldSerialNo);
		lblCityName.setText(workorder.CityName.trim());
		lblTown.setText(workorder.TownName.trim());
		lblAdressDetail.setText(workorder.Address);
		lblMeterModel.setText(workorder.MeterType.trim());
		txtSerialNumber.setText(workorder.SerialNo.trim());
		txtModemNumber.setText(workorder.ModemImeiNo.trim());
		lblAssigned.setText(GlobalVariables.getIntance().user.UserName);
		Multiplier.setText(workorder.Multiplier.trim());
		SubscriberNumber.setText(workorder.TesisatNo);
		lblModemGsmNo.setText(workorder.ModemGsmNo);
		
		// Initiliaze Spinner
		setControllerValues();
	
	}
	public void setControllerValues()
	{
		String[] items = null;
		if (workorder.Statu.equalsIgnoreCase("Planlandý"))
		{
			items = new String[] { "Planlandi","Çalýþýlýyor", "Beklemede","Ýptal" };
			setVisualComponent("Planlandý");
		} 
		else if (workorder.Statu.equalsIgnoreCase("Çalýþýlýyor")) 
		{
			items = new String[] { "Çalýþýlýyor", "Beklemede","Montaj Tamamlandý","Ýptal"  };
			setVisualComponent("Çalýþýlýyor");
		} 
		else if (workorder.Statu.equalsIgnoreCase("Beklemede")) 
		{
			setVisualComponent("Beklemede");
			items = new String[] { "Çalýþýlýyor", "Beklemede","Ýptal"  };
		} 
		else if (workorder.Statu.equalsIgnoreCase("Montaj Tamamlandý")) 
		{
			items = new String[] {  "Beklemede","Montaj Tamamlandý","Ýþ Emri Tamamlandý" ,"Ýptal"  };
			setVisualComponent("Montaj Tamamlandý");
		}
		else if (workorder.Statu.equalsIgnoreCase("Ýþ Emri Tamamlandý")) 
		{
			items = new String[] { "Ýþ Emri Tamamlandý"};
			setVisualComponent("Ýþ Emri Tamamlandý");
			
		}
		else if (workorder.Statu.equalsIgnoreCase("Ýptal")) 
		{
			items = new String[] { "Ýptal","Çalýþýlýyor"};
			setVisualComponent("Ýptal");
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		int spinnerPosition = adapter.getPosition(workorder.Statu);
		spinner.setSelection(spinnerPosition);
		spinner.setSelected(true);
	}
	
	public void setVisualComponent(String Status)
	{
		txtModemNumber.setEnabled(EnumWorkOrderStatus.setVisualEnable("txtModemNumber", Status));
		txtSerialNumber.setEnabled(EnumWorkOrderStatus.setVisualEnable("txtSerialNumber", Status));
		txtOldSerialNumber.setEnabled(EnumWorkOrderStatus.setVisualEnable("txtOldSerialNumber", Status));
		btnSerialBarcode.setEnabled(EnumWorkOrderStatus.setVisualEnable("btnSerialBarcode", Status));
		btnModemBarcode.setEnabled(EnumWorkOrderStatus.setVisualEnable("btnModemBarcode", Status));
		btnOldSerialNumber.setEnabled(EnumWorkOrderStatus.setVisualEnable("btnOldSerialNumber", Status));
		description.setEnabled(EnumWorkOrderStatus.setVisualEnable("description", Status));
		btnSave.setEnabled(EnumWorkOrderStatus.setVisualEnable("btnSave", Status));
		firstImage.setEnabled(EnumWorkOrderStatus.setVisualEnable("firstImage", Status));
		secondImage.setEnabled(EnumWorkOrderStatus.setVisualEnable("secondImage", Status));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "Kapat").setIcon(R.drawable.kapat);
		menu.add(0, 2, 2, "Yeni Ýþ Emri Sorgula").setIcon(
				R.drawable.yeni_is_emri);
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

			//finish();
			return true;
		
		case 2:
			Intent intent2 = new Intent(getBaseContext(), FilterExActivity.class);
			startActivity(intent2);
			//finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public boolean hasImageCaptureBug() {

	    // list of known devices that have the bug
	    ArrayList<String> devices = new ArrayList<String>();
	    devices.add("android-devphone1/dream_devphone/dream");
	    devices.add("generic/sdk/generic");
	    devices.add("vodafone/vfpioneer/sapphire");
	    devices.add("tmobile/kila/dream");
	    devices.add("verizon/voles/sholes");
	    devices.add("google_ion/google_ion/sapphire");

	    return devices.contains(android.os.Build.BRAND + "/" + android.os.Build.PRODUCT + "/"
	            + android.os.Build.DEVICE);

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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		
		// TODO Auto-generated method stub
		if (requestCode == 0) 
		{

			if (resultCode == RESULT_OK) 
			{
				try 
				{
				
				if (GlobalVariables.getIntance().BarcodeType == LoginResult.SerialBarcode) 
				{
					String contents = intent.getStringExtra("SCAN_RESULT");
					String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
					txtSerialNumber.setText(contents);
					// Handle successful scan
				}
				else if(GlobalVariables.getIntance().BarcodeType == LoginResult.ModemBarcode)
				{
						String contents = intent.getStringExtra("SCAN_RESULT");
						String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
						txtModemNumber.setText(contents);
				}
				else if(GlobalVariables.getIntance().BarcodeType == LoginResult.OldSerialBarcode)
				{
					String contents = intent.getStringExtra("SCAN_RESULT");
					String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
					txtOldSerialNumber.setText(contents);
				}
				
				Toast.makeText(getBaseContext(), "Barcode numarasý baþarýlý bir þekilde alýndý.", Toast.LENGTH_LONG).show();
				} 
				catch (Exception e) 
				{
					// TODO: handle exception
					Objects.Log.LogException(GlobalVariables.getIntance().user.UserName, e, Objects.Log.barcodeReadException, "Barcode okunmaya çalýþýrken hata alýndý.EditOrderActivity Line : 568");
				}
			}
				else if (resultCode == RESULT_CANCELED) {

				// Handle cancel
					Toast.makeText(getBaseContext(), "Barcode okunamadý.Lütfen tekrar deneyiniz.", Toast.LENGTH_LONG).show();
			}
		}
		else if(requestCode==1)
		{
			try 
			{
				//Bitmap thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

				String imageurl = getRealPathFromURI(imageUri);
				
				BitmapFactory.Options options = new BitmapFactory.Options();
			    options.inSampleSize = 8;
			    
			    Bitmap bitmap = BitmapFactory.decodeFile(imageurl, options);
			    File sdcard = Environment.getExternalStorageDirectory();
			    File from = new File(imageurl);
			    String newFilePath ="DCIM/Camera/"+Integer.toString(workorder.ID)+"_"+ServiceLocater.GlobalVariables.getIntance().WhichImage+".jpg";
			    
			    File to = new File(sdcard, newFilePath);
			    from.renameTo(to);
			    
				if(ServiceLocater.GlobalVariables.getIntance().WhichImage.equals("first"))
				{
		            firstImage.setImageBitmap(bitmap);
		            ServiceLocater.GlobalVariables.getIntance().firstImagePath=sdcard+"/"+newFilePath;
		            ServiceLocater.GlobalVariables.getIntance().firstImageByte=convertBmpToByte(bitmap);
		            ServiceLocater.GlobalVariables.getIntance().firstImageValidation=true;
				}
				else if(ServiceLocater.GlobalVariables.getIntance().WhichImage.equals("second"))
				{
					ServiceLocater.GlobalVariables.getIntance().secondImagePath=sdcard+"/"+newFilePath;
					secondImage.setImageBitmap(bitmap);
					ServiceLocater.GlobalVariables.getIntance().secondImageByte=convertBmpToByte(bitmap);
					ServiceLocater.GlobalVariables.getIntance().secondImageValidation=true;
				}
			}
			catch (Exception e) 
			{
				Objects.Log.LogException(GlobalVariables.getIntance().user.UserName, e, Objects.Log.photoReadException, "Resim dosyasý alýnýrken hata alýndý fotoðran çektikten sonra okunmaya çalýþýrken hata alýndý.EditOrderActivity Line : 610");
				Intent intent1 = new Intent(EditOrderActivity.this, ErrorActivity.class);
				intent1.putExtra("Error_Message",e.getMessage());
				startActivity(intent1);
			}
		}
	}

	public String getRealPathFromURI(Uri contentUri) 
	{
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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
	private void getUserCurrLonLat() throws Exception 
	{
		// TODO Auto-generated method stub
		try {
			
		
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		
		if(location!=null)
		{
		Toast.makeText(
				EditOrderActivity.this,
				"lat : " + Double.toString(location.getLatitude()) + "Lon : "
						+ Double.toString(location.getLongitude()),
				Toast.LENGTH_LONG).show();
		ServiceLocater.GlobalVariables.getIntance().MapX=Double.toString(location.getLatitude());
		ServiceLocater.GlobalVariables.getIntance().MapY=Double.toString(location.getLongitude());
		}
		else
		{
			
			Service.getIntance().ToastMessage(EditOrderActivity.this,"Lokasyon Alýnamadý.");
			ServiceLocater.GlobalVariables.getIntance().MapX="";
			ServiceLocater.GlobalVariables.getIntance().MapY="";
			
		}
		} catch (Exception e) 
		{
			throw new RuntimeException("hede", e);
//			throw e;
			// TODO: handle exception
		}
		
	}
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
	public static void SyncLocation()
	{
		if(MapY!=null&&MapX!=null&&ServiceLocater.GlobalVariables.getIntance().MapX!=null&&ServiceLocater.GlobalVariables.getIntance().MapX!=null)
		{
		MapX.setText("X :" +ServiceLocater.GlobalVariables.getIntance().MapX);
		MapY.setText("Y :" +ServiceLocater.GlobalVariables.getIntance().MapY);
		}
	}
}
