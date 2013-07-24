package Objects;

import ServiceLocater.Service;

public class Log 
{
	public static int photoSenkException=1;
	public static int loginException=2;
	public static int EditArea1_Exception=3;
	public static int EditArea2_Exception=4;
	public static int photoSetException=5;
	public static int barcodeReadException=6;	
	public static int photoReadException=7;
	public static int locationReadException=8;	
	
	public static boolean LogException(String UserId,Exception ex,int exType,String Description)
	{
		try 
		{
			String message ="";
			if(ex.getMessage()==null)
			message="";
			else 
				message=ex.getMessage();
			Service.getIntance().LogException(UserId,message,exType,Description);
			return true;
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			return false;
		}
	}
	public static void UpdateFotoStatus(int workOrderId) throws Exception 
	{
		// TODO Auto-generated method stub
		Service.getIntance().UpdateFotoStatus(workOrderId);
	}
}
