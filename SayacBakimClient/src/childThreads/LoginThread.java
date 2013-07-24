package childThreads;

import Objects.User;
import ServiceLocater.GlobalVariables;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LoginThread extends Thread {
	private String UserName = "";
	private String Password = "";
	private Context context = null;
	private String LoginResult="";
	final Activity activity = new Activity();
    public LoginThread(Context context,String UserName, String Password)
    {
        super("UIHandler");
    	this.context=context;
    	this.UserName= UserName;
    	this.Password=Password;
    }
	@Override
	public void run()
	{
		try 
		{
			//)ServiceLocater.Service.getIntance().Login(UserName, Password)

			//if(true)
			User user=ServiceLocater.Service.getIntance().Login(UserName, Password);
			if(user!=null)
			{
			GlobalVariables.getIntance().LoginResult=Objects.LoginResult.Giris_Basarili;
			}
			else
			{
			GlobalVariables.getIntance().LoginResult=Objects.LoginResult.Giris_Basarisiz;
			}
			GlobalVariables.getIntance().mainTreadAlive=false;
			
		} catch (Exception e) 
		{
			Log.d("toast", e.getMessage());
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		
	}

	   
}
