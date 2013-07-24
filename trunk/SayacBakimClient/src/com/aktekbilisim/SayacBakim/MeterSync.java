package com.aktekbilisim.SayacBakim;

import Objects.Response;
import Objects.UpdateProcessType;
import Objects.WorkOrder;
import ServiceLocater.GlobalVariables;
import ServiceLocater.ServiceAydem;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MeterSync extends Activity 
{
		// TODO Auto-generated method stub
		ProgressDialog progressBar;
		private int progressBarStatus = 0;
		private int fileSize = 0;
		private String workorderId;
		private String SerialNo;
		private Handler progressBarHandler = new Handler();
		private boolean ResultStatus=false;
		private TextView syncResult;
		private Button okBtn;
		private Button updateBtn;
		private WorkOrder workorder;
		private Response Result;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.metersyncprogressbar);
	       // progressBar=(ProgressDialog) findViewById(R.id.progressBar1);
	        Bundle extras =getIntent().getExtras();
	        workorder=(WorkOrder)extras.getSerializable("WorkOrder");
	        
	        InitializeContent();
	       
	       
			progressBar.setOnKeyListener(new DialogInterface.OnKeyListener() {

			    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			        if (keyCode == KeyEvent.KEYCODE_SEARCH && event.getRepeatCount() == 0) {
			            return true; // Pretend we processed it
			        }
			        return false; // Any other keys are still processed as normal
			    }
			});
			
			
			new Thread(new Runnable() {
				  public void run() {
					  Looper.prepare();
					while (progressBarStatus < 100) 
					{
					  progressBarStatus = Calculate5Minute();
					  try 
					  {
						Thread.sleep(1000);
					  } catch (InterruptedException e) 
					  {
						e.printStackTrace();
					  }
					  
					  if(progressBarStatus%10==0)
					  {
						  if(tryToestablishConnection())
						  {
							 ConnectionEstablished();
							 break;
						  }
					  }
					  progressBarHandler.post(new Runnable() {
						public void run() {
						  progressBar.setProgress(progressBarStatus);
						}
					  });
					}
					if (progressBarStatus >= 100) {
						try 
						{
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						ConnectionNotEstablished();
						progressBar.dismiss();
					}
				  }

				private void ConnectionEstablished() 
				{
					// TODO Auto-generated method stub
					try {
						
								try 
								{
									progressBar.dismiss();
									ResultStatus=true;
									syncResult.post(new Runnable() {
										
										public void run() 
										{
											// TODO Auto-generated method stub
											
											
											try {
												//Service.getIntance().updateWorkOrder(workorderId);
												
												UpdateProcessType updateprocess= UpdateProcessType.PLANLANDI;
												Result = ServiceLocater.ServiceAydem.getIntance().updateWorkOrder(workorder,updateprocess.getName("Ýþ Emri Tamamlandý"));
												updateBtn.setEnabled(false);
											} catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											if(Result.Result==0)
											{
											GlobalVariables.getIntance().StatusCodeForFilter = 0;
											Intent intent = new Intent(MeterSync.this,OrderListActivity.class);
											startActivity(intent);
											finish();
											}
											else
											{
												syncResult.setText(Result.Message);
												updateBtn.setVisibility(View.GONE);
												okBtn.setVisibility(View.VISIBLE);
											}
										}
									});
								}
								catch (Exception e) 
								{
									// TODO: handle exception
							//	Service.getIntance().ToastMessage(MeterSync.this, e.toString());	
								}
					} catch (Exception e) {
						// TODO: handle exception
						Log.i("activityfailure", e.toString());
					}
					
				}

				private boolean tryToestablishConnection() 
				{
					// TODO Auto-generated method stub
					boolean result=false;
					try
					{
						result= ServiceAydem.getIntance().IsMeterRead(workorder.SerialNo);
						return result;
					} 
					catch (Exception e) 
					{
						return result;
					}
				}

				private void ConnectionNotEstablished() 
				{
					// TODO Auto-generated method stub
					syncResult.post(new Runnable() 
					{
						
						public void run() 
						{
							syncResult.setText("Sayaç 5 dakika içerisinde okunamadý.Lütfen sistem yöneticisinize baþvurunuz!!");
							updateBtn.setVisibility(View.GONE);
							okBtn.setVisibility(View.VISIBLE);
						}
					});
				}
			       }).start();
			
			
			
			okBtn.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		
	    }
	    private void InitializeContent() 
	    {
	    	okBtn= (Button)findViewById(R.id.okBtn);
		    updateBtn=(Button)findViewById(R.id.updateBtn);
		    syncResult=(TextView) findViewById(R.id.syncResulttxt);
		    progressBar = new ProgressDialog(MeterSync.this);
		    progressBar.setCancelable(true);
			progressBar.setMessage("Modem/Sayaç ile iletiþim kuruluyor lütfen bekleyiniz...Bekleme süresi ortalama 2 dakikadýr.");
			progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressBar.setProgress(0);
			progressBar.setMax(100);			
			progressBar.show();
			progressBar.setCancelable(false);
		}
		public int Calculate5Minute() 
		{
				fileSize++;
				return fileSize;
		}
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			return false;
		}
	    
	}


