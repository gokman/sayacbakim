package com.aktekbilisim.SayacBakim;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.IOException;
import java.util.List;

import Objects.Base64Coder;
import Objects.Log;
import Objects.PhotoSenkObj;
import ServiceLocater.GlobalVariables;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FotoSenkActivity extends Activity 
{
	private ProgressDialog m_ProgressDialog = null;
	private TextView fotosenkresult;
	private Button close;
	private Button retry;
	private Runnable senk;
	private Thread senkThread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fotosenk);
		m_ProgressDialog = ProgressDialog.show(FotoSenkActivity.this,"L�tfen bekleyiniz....", "Foto�raflar Sisteme aktar�l�yor ...", false);
		fotosenkresult=(TextView)findViewById(R.id.fotosenkresult);
		close=(Button) findViewById(R.id.btnclose);
		retry=(Button) findViewById(R.id.btnretry);
		
		retry.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				senkThread= new Thread(null, senk, "MagentoBackground");
				senkThread.start();
			}
		});
		close.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		senk= new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					if(fotosenk())
					{
						runOnUiThread(new Runnable() 
				        {                
				            public void run() 
				            {
				                //Your toast code here
				            	fotosenkresult.setText("Foto�raf e�lemesi tamamlanm��t�r.");
								close.setVisibility(View.VISIBLE);
								retry.setVisibility(View.GONE);
				            }
				        });
					}
					else
					{
						runOnUiThread(new Runnable() 
				        {                
				            public void run() 
				            {
				                //Your toast code here
				            	fotosenkresult.setText("Foto�raf e�lemesi baz� foto�raflar aktar�lamad� l�tfen tekrar deneyin.Bu uyar� mesaj� tekrarlan�yorsa y�neticinize ba�vurunuz.");
								close.setVisibility(View.VISIBLE);
								retry.setVisibility(View.VISIBLE);
				            }
				        });
					}
				} catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	
		senkThread= new Thread(null, senk, "MagentoBackground");
		senkThread.start();
	}
	private boolean fotosenk() throws Exception {
		// TODO Auto-generated method stub
		boolean result=true;
		try 
		{
	
			List<PhotoSenkObj> listphotoSenk= ServiceLocater.Service.getIntance().photoSenkGet(GlobalVariables.getIntance().user.UserName);
			
			for (int i = 0; i < listphotoSenk.size(); i++) 
			{
				try 
				{
				BitmapFactory.Options options = new BitmapFactory.Options();
			    options.inSampleSize = 8;
			    
			    Bitmap firstbitmap = BitmapFactory.decodeFile( listphotoSenk.get(i).firstPhotoPath, options );
			    Bitmap secondbitmap= BitmapFactory.decodeFile( listphotoSenk.get(i).secondPhotoPath, options );
			    if(firstbitmap==null)
			    {
			    	Log.LogException(ServiceLocater.GlobalVariables.getIntance().user.UserName,new Exception(),Log.photoSenkException,listphotoSenk.get(i).firstPhotoPath+"///Foto�raf telefonda bulunamad�!!!!!!.FotoActivity.java line : 118");
			    	Log.UpdateFotoStatus(listphotoSenk.get(i).WorkOrderId);
			    }
			    if(secondbitmap==null)
			    {
			    	Log.LogException(ServiceLocater.GlobalVariables.getIntance().user.UserName,new Exception(),Log.photoSenkException,listphotoSenk.get(i).secondPhotoPath+"///Foto�raf telefonda bulunamad�!!!!!!.FotoActivity.java line : 118");
			    	Log.UpdateFotoStatus(listphotoSenk.get(i).WorkOrderId);
			    }
			    
			    if(firstbitmap!=null&&secondbitmap!=null)
			    	result = ServiceLocater.Service.getIntance().photoSenkPost(convertBmpToByte(firstbitmap),convertBmpToByte(secondbitmap),listphotoSenk.get(i).WorkOrderId);
			    
				    if(result)
				    {
				    	File file = new File(listphotoSenk.get(i).firstPhotoPath);
				    	boolean deleted = file.delete();
				    	File file2 = new File(listphotoSenk.get(i).secondPhotoPath);
				    	deleted = file2.delete();
				    }
				} 
				catch (Exception e) 
				{
					Log.LogException(ServiceLocater.GlobalVariables.getIntance().user.UserName,e,Log.photoSenkException,"Foto�raf verileri g�nderilirken hata olu�tu.FotoActivity.java line : 123");
				}
			}
			m_ProgressDialog.dismiss();
			
		} catch (Exception e) 
		{
			m_ProgressDialog.dismiss();
			// TODO Auto-generated catch block
			//Service.getIntance().writeLogFile(e.getMessage(),GlobalVariables.getIntance().UserName,"FotoSenkActivity");
			result=false;
		}
		return result;
	}
	private String convertBmpToByte(Bitmap bmp) throws IOException 
	 {
		// TODO Auto-generated method stub,
		try 
		{
			String image_string;
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
	

}
