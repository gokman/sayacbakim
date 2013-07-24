package com.aktekbilisim.SayacBakim;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import Objects.Base64Coder;
import ServiceLocater.GlobalVariables;
import ServiceLocater.Service;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;

public class PhotoListActivity extends Activity {
	private ProgressDialog m_ProgressDialog = null;
	private Runnable senk;
	private Thread senkThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		    m_ProgressDialog = new ProgressDialog(PhotoListActivity.this);
		    m_ProgressDialog.setCancelable(true);
		    m_ProgressDialog.setMessage("Fotoðraflar sisteme aktarýlýyor....");
			m_ProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			m_ProgressDialog.setProgress(0);
			m_ProgressDialog.show();
		    m_ProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

		    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		        if (keyCode == KeyEvent.KEYCODE_SEARCH && event.getRepeatCount() == 0) {
		            return true; // Pretend we processed it
		        }
		        return false; // Any other keys are still processed as normal
		    }
		});
		
		
		senk = new Runnable() {

			public void run() {
				// TODO Auto-generated method stub

				String[] listphotoSenk = LoadPhotoPaths();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 8;
				File sdcard = Environment.getExternalStorageDirectory();
				m_ProgressDialog.setMax(listphotoSenk.length);	
				for (int j = 0; j < listphotoSenk.length; j++) {
					try {
						String sourceDestination = sdcard + "/DCIM/Camera/"
								+ listphotoSenk[j];
						Bitmap firstbitmap = BitmapFactory.decodeFile(
								sourceDestination, options);

						if (Service
								.getIntance()
								.UpLoad(GlobalVariables.getIntance().user.UserName,
										listphotoSenk[j],
										convertBmpToByte(firstbitmap))) {
							new File(sourceDestination).delete();
						}
					} catch (Exception ex) {
						Service.getIntance().ToastMessage(
								getApplicationContext(),
								"Upload Hatasý : " + listphotoSenk[j]
										+ " Mesaj : " + ex.getMessage());
					}
					m_ProgressDialog.setProgress(j);
				}
				m_ProgressDialog.dismiss();

				if (listphotoSenk.length == 0) {
					runOnUiThread(new Runnable() {
						public void run() {
							// Your toast code here
							Service.getIntance().ToastMessage(
									PhotoListActivity.this,
									"Aktarýlacak Fotoðraf Bulunamadý");
						}
					});
					Intent intent = new Intent(PhotoListActivity.this,
							MenuActivity.class);
					startActivity(intent);
					finish();

				} else {
					runOnUiThread(new Runnable() {
						public void run() {
							// Your toast code here
							Service.getIntance().ToastMessage(
									PhotoListActivity.this,
									"Fotoðraflar sisteme aktarýlmýþtýr.");
						}
					});
					Intent intent = new Intent(PhotoListActivity.this,
							MenuActivity.class);
					startActivity(intent);
					finish();
				}
			}
		};
		senkThread = new Thread(null, senk, "MagentoBackground");
		senkThread.start();
	}

	private String[] LoadPhotoPaths() {
		// TODO Auto-generated method stub
		File sdcard = Environment.getExternalStorageDirectory();
		String newFilePath = "DCIM/Camera";
		File file = new File(sdcard, newFilePath);
		String[] filelist = new String[file.listFiles().length];
		for (int i = 0; i < file.listFiles().length; i++) {
			filelist[i] = file.listFiles()[i].getName();
		}
		return filelist;
	}

	private String convertBmpToByte(Bitmap bmp) throws IOException {
		// TODO Auto-generated method stub,
		try {
			String image_string;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bmp.compress(CompressFormat.JPEG, 100 /* ignored for PNG */, bos);
			byte[] bitmapdata = bos.toByteArray();
			bos.close();
			bos = null;
			image_string = Base64Coder.encodeLines(bitmapdata);
			return image_string;
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}
}