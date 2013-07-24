package ServiceLocater;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import Objects.PhotoInfo;
import Objects.PhotoSenkObj;
import Objects.User;
import Objects.WorkOrder;
import Objects.WorkOrderDescription;
import android.content.Context;
import android.widget.Toast;

	public class Service {
	private static Service _service = null;
	final String NAMESPACE = "http://tempuri.org/";
	//final String URL = "http://194.54.75.66/SayacWB/";
	String URL = "http://www.aktekasos.com/ws/SahaPortal.asmx";
	String SOAP_ACTION ;
	String METHOD_NAME ;
	
	public static Service getIntance() 
	{
		if (_service == null)
			return _service = new Service();
		return _service;
	}

	public User Login(String UserName, String Password) throws Exception 
	{
		try
		{
			SOAP_ACTION = "http://tempuri.org/LoginAndroidUser";
			METHOD_NAME = "LoginAndroidUser";
			Map<String, Object> Parameters = new HashMap<String, Object>();
			Parameters.put("UserName", UserName);
			Parameters.put("Password", Password);

			SoapObject user=(SoapObject) HttpTransport(SOAP_ACTION,METHOD_NAME, NAMESPACE, URL, Parameters);
			
			if(user==null)
				return null;
			else
			return new User(user);
		}
		catch (Exception e)
		{
         throw e;
		}
	}
	public void LoadPhotoInfo(ArrayList<String> getFiles) 
	{
		// TODO Auto-generated method stub
		SOAP_ACTION = "http://tempuri.org/LogIn";
		METHOD_NAME = "LogIn";
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("ArrayList<String>", getFiles);
		
	}
	public void setApplicationVersion(String version,String UserId,String DeviceId) throws Exception
	{
		SOAP_ACTION = "http://tempuri.org/InsertAndroidAppVersion";
		METHOD_NAME = "InsertAndroidAppVersion";
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("version", version);
		Parameters.put("androidUserId", UserId);
		Parameters.put("deviceId", DeviceId);
		String soapObject="";
		try 
		{
			soapObject=HttpTransport(SOAP_ACTION,METHOD_NAME, NAMESPACE, URL, Parameters).toString();
		} catch (Exception e) 
		{
			throw  e;
		}
	}
	public List<WorkOrder> LoadWorkOrdersOfUser(String UserName,
			int WorkOrderStatu, String WorkOrderDate, String Orderby,
			String Sort) throws Exception {
		SOAP_ACTION = "http://tempuri.org/LoadWorkOrdersOfUser";
		METHOD_NAME = "LoadWorkOrdersOfUser";
		
		List<WorkOrder> ListWorkOrder = new ArrayList<WorkOrder>();
		Map<String, Object> Parameters = new HashMap<String, Object>();

		Parameters.put("Sort", Sort);
		Parameters.put("Orderby", Orderby);
		Parameters.put("WorkOrderDate", WorkOrderDate);
		Parameters.put("WorkOrderStatu", WorkOrderStatu);
		Parameters.put("UserName", UserName);

		SoapObject ListWorker=null;
		try 
		{
			ListWorker = ((SoapObject) HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw  e;
		}
		for (int i = 0; i < ListWorker.getPropertyCount(); i++) 
		{
			Object property = ListWorker.getProperty(i);
			if (property instanceof SoapObject) 
			{
				SoapObject countryObj = (SoapObject) property;

				ListWorkOrder.add(new WorkOrder(countryObj));
			}
		}
		return ListWorkOrder;
	}
	public List<WorkOrder> LoadWorkOrdersOfUserEx(String UserName,
			int WorkOrderStatu, String WorkOrderDate,String AboneNo, String Orderby,
			String Sort) throws Exception {
		SOAP_ACTION = "http://tempuri.org/LoadWorkOrdersOfUserEx";
		METHOD_NAME = "LoadWorkOrdersOfUserEx";
		
		List<WorkOrder> ListWorkOrder = new ArrayList<WorkOrder>();
		Map<String, Object> Parameters = new HashMap<String, Object>();

		Parameters.put("Sort", Sort);
		Parameters.put("Orderby", Orderby);
		Parameters.put("WorkOrderDate", WorkOrderDate);
		Parameters.put("WorkOrderStatu", WorkOrderStatu);
		Parameters.put("aboneno", AboneNo);
		Parameters.put("UserName", UserName);

		SoapObject ListWorker=null;
		try {
			ListWorker = ((SoapObject) HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw  e;
		}
		for (int i = 0; i < ListWorker.getPropertyCount(); i++) {
			Object property = ListWorker.getProperty(i);
			if (property instanceof SoapObject) {
				SoapObject countryObj = (SoapObject) property;

				ListWorkOrder.add(new WorkOrder(countryObj));
			}
		}
		return ListWorkOrder;
	}
	
	public String updateWorkOrder (WorkOrder workorder,int updateType) throws Exception
	{
		Map<String, Object> Parameters = new HashMap<String, Object>();
		switch (updateType) 
		{
		case 1://PLANLANDI
			SOAP_ACTION = "http://tempuri.org/UpdateWorkOrderStatu_PLANLANDI";
			METHOD_NAME = "UpdateWorkOrderStatu_PLANLANDI";
			
			Parameters.put("WorkOrderStatu", workorder.StatuId);
			Parameters.put("Description", workorder.Description);
			Parameters.put("UpdateUser", workorder.UserId);
			Parameters.put("WorkOrderId", workorder.ID);
			break;
		case 2://MONTAJ_TAMAMLANDI
			
			SOAP_ACTION = "http://tempuri.org/UpdateWorkOrderStatu_MONTAJTAMAMLANDI";
			METHOD_NAME = "UpdateWorkOrderStatu_MONTAJTAMAMLANDI_1_0_7";
			Parameters.put("WorkOrderStatu", workorder.StatuId);
			Parameters.put("Description", workorder.Description);
			Parameters.put("UpdateUser", workorder.UserId);
			Parameters.put("WorkOrderId", workorder.ID);
			Parameters.put("ModemNumber", workorder.ModemImeiNo);
			Parameters.put("SerialNumber",workorder.SerialNo);
			Parameters.put("OldSerialNumber", workorder.OldSerialNo);
			Parameters.put("TrafoCode",workorder.TrafoCode);
			Parameters.put("secondImagePath", workorder.secondimagepath);
			//Parameters.put("secondImage", ServiceLocater.GlobalVariables.getIntance().secondImageByte);
			break;
		case 3://CALISILIYOR
			SOAP_ACTION = "http://tempuri.org/UpdateWorkOrderStatu_CALISILIYOR";
			METHOD_NAME = "UpdateWorkOrderStatu_CALISILIYOR_1_0_7";
			
			Parameters.put("WorkOrderStatu", workorder.StatuId);
			Parameters.put("Description", workorder.Description);
			Parameters.put("UpdateUser", workorder.UserId);
			Parameters.put("WorkOrderId", workorder.ID);
			Parameters.put("ModemNumber", workorder.ModemImeiNo);
			Parameters.put("SerialNumber",workorder.SerialNo);
			Parameters.put("OldSerialNumber", workorder.OldSerialNo);
			Parameters.put("MapX", workorder.LAT);
			Parameters.put("MapY",workorder.LONG);
			Parameters.put("TrafoCode", workorder.TrafoCode);
			Parameters.put("firstImagePath", workorder.firstimagepath);
			//Parameters.put("firstImage",ServiceLocater.GlobalVariables.getIntance().firstImageByte);
			break;
		case 4://BEKLEMEDE
			SOAP_ACTION = "http://tempuri.org/UpdateWorkOrderStatu_BEKLEMEDE";
			METHOD_NAME = "UpdateWorkOrderStatu_BEKLEMEDE";
			
			Parameters.put("WorkOrderStatu", workorder.StatuId);
			Parameters.put("Description", workorder.Description);
			Parameters.put("UpdateUser", workorder.UserId);
			Parameters.put("ModemNumber", workorder.ModemImeiNo);
			Parameters.put("SerialNumber",workorder.SerialNo);
			Parameters.put("MapX", workorder.LAT);
			Parameters.put("MapY",workorder.LONG);
			Parameters.put("WorkOrderId", workorder.ID);
			Parameters.put("firstImagePath", workorder.firstimagepath);			
			break;
		case 5://IPTAL
			SOAP_ACTION = "http://tempuri.org/UpdateWorkOrderStatu_IPTAL";
			METHOD_NAME = "UpdateWorkOrderStatu_IPTAL";
			
			Parameters.put("WorkOrderStatu", workorder.StatuId);
			Parameters.put("Description", workorder.Description);
			Parameters.put("UpdateUser", workorder.UserId);
			Parameters.put("ModemNumber", workorder.ModemImeiNo);
			Parameters.put("SerialNumber",workorder.SerialNo);
			Parameters.put("MapX", workorder.LAT);
			Parameters.put("MapY",workorder.LONG);
			Parameters.put("WorkOrderId", workorder.ID);
			Parameters.put("firstImagePath", workorder.firstimagepath);
			break;
		case 6://ÝS_EMRÝ_TAMAMLANDI
			SOAP_ACTION = "http://tempuri.org/UpdateWorkOrderStatu_ISEMRITAMAMLANDI";
			METHOD_NAME = "UpdateWorkOrderStatu_ISEMRITAMAMLANDI";
			
			Parameters.put("WorkOrderStatu", workorder.StatuId);
			Parameters.put("Description", workorder.Description);
			Parameters.put("UpdateUser", workorder.UserId);
			Parameters.put("WorkOrderId", workorder.ID);
			Parameters.put("ModemNumber", workorder.ModemImeiNo);
			Parameters.put("SerialNumber",workorder.SerialNo);
			Parameters.put("OldSerialNumber", workorder.OldSerialNo);
			Parameters.put("TrafoCode",workorder.TrafoCode);
			break;
		}
		
			String soapObject="";
			try {
				soapObject= HttpTransport(SOAP_ACTION,
						METHOD_NAME, NAMESPACE, URL, Parameters).toString();
			} catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
			return soapObject;
	}
	
	public Boolean checkMetersConnectivity(String MeterSerialNo) 
	{
		// TODO Auto-generated method stub
		final String SOAP_ACTION = "http://tempuri.org/CheckMeterConnectivity";
		final String METHOD_NAME = "CheckMeterConnectivity";

		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("SerialNo", MeterSerialNo);
		
		String soapObject="";
		boolean result=false;
		try 
		{
			 result=Boolean.parseBoolean(( HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters)).toString());
			 return result;
		} catch (Exception e) 
		{
			// TODO: handle exception
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return result;
		
	}
	public List<WorkOrderDescription> getWorkOrderDescriptions(String workOrderId) throws Exception  {
		// TODO Auto-generated method stub
		SOAP_ACTION = "http://tempuri.org/LoadDetailWorkOrder";
		METHOD_NAME = "LoadDetailWorkOrder";
		
		List<WorkOrderDescription> ListWorkOrder = new ArrayList<WorkOrderDescription>();
		Map<String, Object> Parameters = new HashMap<String, Object>();

		Parameters.put("WorkOrderId", workOrderId);
		List<WorkOrderDescription> WorkOrderDesc=null;

		SoapObject ListDescription=null;
		try {
			ListDescription = ((SoapObject) HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw  e;
		}
		for (int i = 0; i < ListDescription.getPropertyCount(); i++) {
			Object property = ListDescription.getProperty(i);
			if (property instanceof SoapObject) {
				SoapObject countryObj = (SoapObject) property;

				ListWorkOrder.add(new WorkOrderDescription(countryObj));
			}
		}
		return ListWorkOrder;
	}
	public List<PhotoSenkObj> photoSenkGet(String UserName) throws Exception 
	{
		// TODO Auto-generated method stub
		SOAP_ACTION = "http://tempuri.org/getPhotoPath";
		METHOD_NAME = "getPhotoPath";
		
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("UserName", UserName);
		List<PhotoSenkObj> photosenk = new ArrayList<PhotoSenkObj>();
		
		SoapObject ListDescription=null;
		try {
			ListDescription = ((SoapObject) HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw  e;
		}
		
		for (int i = 0; i < ListDescription.getPropertyCount(); i++) {
			Object property = ListDescription.getProperty(i);
			if (property instanceof SoapObject) {
				SoapObject countryObj = (SoapObject) property;

				photosenk.add(new PhotoSenkObj(countryObj));
			}
		}
		return photosenk;
	}
	public boolean photoSenkPost(String firstImage,String secondImage, int workOrderId) throws Exception 
	{
		SOAP_ACTION = "http://tempuri.org/postPhotoPath";
		METHOD_NAME = "postPhotoPath";
		
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("firstImage", firstImage);
		Parameters.put("secondImage", secondImage);
		Parameters.put("WorkOrderId", workOrderId);
		boolean result=false;
		SoapObject retval=null;
		try {
			retval = ((SoapObject) HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return result=false;
		}
		return result=true;
	}
	public boolean checkModemAvailable(String ModemImeiNo,boolean check) 
	{
		// TODO Auto-generated method stub
		SOAP_ACTION = "http://tempuri.org/checkModemAvaiblable_1_0_9";
		METHOD_NAME = "checkModemAvaiblable_1_0_9";
		
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("ModemImeiNo", ModemImeiNo);
		Parameters.put("check", check);
		String soapObject="";
		boolean result=false;
		try 
		{
			 result=Boolean.parseBoolean(( HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters)).toString());
			 return result;
		} catch (Exception e) 
		{
			// TODO: handle exception
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return result;
	}
	public boolean checkSayacAvailable(String SayacSeriNo) 
	{
		// TODO Auto-generated method stub
		SOAP_ACTION = "http://tempuri.org/checkSayacAvailable";
		METHOD_NAME = "checkSayacAvailable";
		
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("SayacSeriNo", SayacSeriNo);
		String soapObject="";
		boolean result=false;
		try 
		{
			 result=Boolean.parseBoolean(( HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters)).toString());
			 return result;
		} catch (Exception e) 
		{
			// TODO: handle exception
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		return result;
	}
	
	public void writeLogFile(String Content,String UserId,String Activity) throws Exception 
	{
		SOAP_ACTION = "http://tempuri.org/writeLogFile";
		METHOD_NAME = "writeLogFile";
		
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("Content", Content);
		Parameters.put("UserId", UserId);
		Parameters.put("ActivityName", Activity);
		String soapObject="";
		try 
		{
			soapObject=HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters).toString();
		} catch (Exception e) 
		{
			// TODO: handle exception
				throw e;
		}
	}
	public void LogException(String UserId,String exMessage,int exType,String Description) throws Exception 
	{
		// TODO Auto-generated method stub
		SOAP_ACTION = "http://tempuri.org/LogException";
		METHOD_NAME = "LogException";
		
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("UserId", UserId);
		Parameters.put("exMessage", exMessage);
		Parameters.put("extype", Integer.toString(exType));
		Parameters.put("Description", Description);
		String soapObject="";
		try 
		{
			HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters).toString();
		} catch (Exception e) 
		{
			// TODO: handle exception
				throw e;
		}
		
		
	}
	public void UpdateFotoStatus(int workOrderId) throws Exception 
	{
		// TODO Auto-generated method stub
		SOAP_ACTION = "http://tempuri.org/UpdateFotoStatus";
		METHOD_NAME = "UpdateFotoStatus";
		
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("WorkOrderId", workOrderId);
		try 
		{
			HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters).toString();
		} catch (Exception e) 
		{
			// TODO: handle exception
				throw e;
		}
	}
	public boolean UpLoad(String userName, String fileName, String imagecontent) throws Exception 
	{
		// TODO Auto-generated method stub
		SOAP_ACTION = "http://tempuri.org/LoadPhoto";
		METHOD_NAME = "LoadPhoto";
		
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("user", userName);
		Parameters.put("FileName", fileName);
		Parameters.put("imageContent", imagecontent);
		try 
		{
			return Boolean.parseBoolean(HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters).toString());
		} catch (Exception e) 
		{
			// TODO: handle exception
				throw e;
		}
	}
	private Object HttpTransport(String SOAP_ACTION, String METHOD_NAME,
			String NAMESPACE, String URL, Map<String, Object> Parameters) throws Exception {
		try 
		{
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			PropertyInfo pi = null;
			Iterator it = Parameters.entrySet().iterator();

			while (it.hasNext()) 
			{
				pi = new PropertyInfo();
				Map.Entry pairs = (Map.Entry) it.next();
				pi.setName(pairs.getKey().toString());
				pi.setValue(pairs.getValue());
				pi.setType(pairs.getValue().getClass());
				request.addProperty(pi);
			}

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.addMapping(NAMESPACE, "WorkOrder",new WorkOrder().getClass());
			envelope.addMapping(NAMESPACE, "PhotoInfo[]",new ArrayList<PhotoInfo>().getClass());

			envelope.bodyOut = request;
			envelope.setOutputSoapObject(request);

			HttpTransportSE httpTransport = new HttpTransportSE(URL);
			Object response = null;

			try 
			{
				httpTransport.call(SOAP_ACTION, envelope);
				response = envelope.getResponse();

			} 
			catch (Exception exception) 
			{
				throw exception;				
			}
			return response;
		} catch (Exception e) 
		{
			throw e;
		}

	}

	public  void ToastMessage(Context ctx,String Message)
	{
		
		 Toast.makeText(ctx, Message, Toast.LENGTH_LONG).show();
	}
}
