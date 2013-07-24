package ServiceLocater;

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

import Objects.Malzeme;
import Objects.Pano;
import Objects.PhotoInfo;
import Objects.Response;
import Objects.User;
import Objects.WorkOrder;
import Objects.xxaktpanel;
import android.app.Activity;
import android.content.Context;

public class ServiceAydem extends Activity
{
	private static ServiceAydem _service = null;
	final String NAMESPACE = "http://tempuri.org/";
	//final String URL = "http://194.54.75.66/SayacWB/";
	String URL = "http://www.aktekasos.com/ws.asmx";
	String SOAP_ACTION ;
	String METHOD_NAME ;
	
	public static ServiceAydem getIntance() 
	{
		if (_service == null)
			return _service = new ServiceAydem();
		return _service;
	}
	public User Login(String UserName, String Password) throws Exception 
	{
		try
		{
			SOAP_ACTION = "http://tempuri.org/LoginAndroidUser";
			METHOD_NAME = "LoginAndroidUser";
			Map<String, Object> Parameters = new HashMap<String, Object>();
			Parameters.put("userName", UserName);
			Parameters.put("password", Password);
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
	public Response UpdatePanoMalzeme(String panelId,int malzemeId,String value,String Description,boolean isselected)
	{
		Response response = new Response();
		SOAP_ACTION = "http://tempuri.org/UpdatePanoMalzeme";
		METHOD_NAME = "UpdatePanoMalzeme";
		Map<String, Object> Parameters = new HashMap<String, Object>();
		
		Parameters.put("panelId", panelId);
		Parameters.put("malzemeId", malzemeId);
		Parameters.put("value", value);
		Parameters.put("description", Description);
		Parameters.put("isselected", isselected);
		
		SoapObject soapObject;
		try 
		{
			soapObject= (SoapObject)HttpTransport(SOAP_ACTION,METHOD_NAME, NAMESPACE, URL, Parameters);
			
			response.Result=Integer.parseInt(soapObject.getProperty("Result").toString());	
			response.Message=soapObject.getProperty("Message").toString();
		} catch (Exception e) 
		{
			// TODO: handle exception
			response.Result=1;
			response.Message=e.getMessage();
		}
		return response;
	}
	public boolean UpSertPhone(String deviceId)
	{
		SOAP_ACTION = "http://tempuri.org/UpSertPhone";
		METHOD_NAME = "UpSertPhone";
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("deviceId", deviceId);
		String soapObject="";
		try 
		{
			return Boolean.parseBoolean(HttpTransport(SOAP_ACTION,METHOD_NAME, NAMESPACE, URL, Parameters).toString());
		} catch (Exception e) 
		{
			return false;
		}
	}
	public boolean IsPhoneVersionUpToDate(String deviceId, String version)
	{
		SOAP_ACTION = "http://tempuri.org/IsPhoneVersionUpToDate";
		METHOD_NAME = "IsPhoneVersionUpToDate";
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("deviceId", deviceId);
		Parameters.put("version", version);
		
		String soapObject="";
		try 
		{
			return Boolean.parseBoolean(HttpTransport(SOAP_ACTION,METHOD_NAME, NAMESPACE, URL, Parameters).toString());
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	public List<Malzeme> LoadMalzeme(String PanelId)throws Exception
	{
		SOAP_ACTION = "http://tempuri.org/LoadPanoMalzeme";
		METHOD_NAME = "LoadPanoMalzeme";
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("panelId", PanelId);
		SoapObject ListMalzeme=null;
		
		List<Malzeme> malzemeList = new ArrayList<Malzeme>();
		try 
		{
			ListMalzeme = ((SoapObject) HttpTransport(SOAP_ACTION,
						METHOD_NAME, NAMESPACE, URL, Parameters));
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(ListMalzeme==null)
			return malzemeList;
		
		for (int i = 0; i < ListMalzeme.getPropertyCount(); i++) 
		{
			Object property = ListMalzeme.getProperty(i);
			if (property instanceof SoapObject) 
			{
				SoapObject countryObj = (SoapObject) property;

				malzemeList.add(new Malzeme(countryObj));
			}
		}
		return malzemeList;
	}
	public   List<Pano> LoadPanel(String userId) throws Exception 
	{
		// TODO Auto-generated method stub
		SOAP_ACTION = "http://tempuri.org/LoadPanel";
		METHOD_NAME = "LoadPanel";
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("userId", userId);
		Parameters.put("panelId", userId);
		SoapObject ListWorker=null;
		List<Pano> panoList= new ArrayList<Pano>();
		try 
		{
			ListWorker = ((SoapObject) HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw  e;
		}
		if(ListWorker==null)
			return panoList;
		
		for (int i = 0; i < ListWorker.getPropertyCount(); i++) 
		{
			Object property = ListWorker.getProperty(i);
			if (property instanceof SoapObject) 
			{
				SoapObject countryObj = (SoapObject) property;

				panoList.add(new Pano(countryObj));
			}
		}
		return panoList;
	}
	public xxaktpanel GetPanelVariable(String contents) 
	{
		SOAP_ACTION = "http://tempuri.org/GetPanelVariable";
		METHOD_NAME = "GetPanelVariable";
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("panelId", contents);
		String soapObject="";
		try 
		{
			SoapObject pano=(SoapObject) HttpTransport(SOAP_ACTION,METHOD_NAME, NAMESPACE, URL, Parameters);
			
			if(pano==null)
				return null;
			else
			return new xxaktpanel(pano);
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	public boolean IsTrafoAvailable(String trafoCode)
	{
		try 
		{
			SOAP_ACTION = "http://tempuri.org/IsTrafoAvailable";
			METHOD_NAME = "IsTrafoAvailable";
			Map<String, Object> Parameters = new HashMap<String, Object>();
			Parameters.put("trafoCode", trafoCode);
			try 
			{
				return Boolean.parseBoolean(HttpTransport(SOAP_ACTION,METHOD_NAME, NAMESPACE, URL, Parameters).toString());
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	public boolean IsTesisatNoAvailable(String tesisatNo)
	{
		SOAP_ACTION = "http://tempuri.org/IsTesisatNoAvailable";
		METHOD_NAME = "IsTesisatNoAvailable";
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("tesisatNo", tesisatNo);
		try 
		{
			return Boolean.parseBoolean(HttpTransport(SOAP_ACTION,METHOD_NAME, NAMESPACE, URL, Parameters).toString());
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}	
	public Response UpsertPanel(Pano pano) 
	{
		// TODO Auto-generated method stub
		Response response= new Response();
		Map<String, Object> Parameters = new HashMap<String, Object>();
			SOAP_ACTION = "http://tempuri.org/UpsertPanel";
			METHOD_NAME = "UpsertPanel";
			
			Parameters.put("photoList", pano.PhotoList);
			Parameters.put("modemImeiNo", pano.ModemImeiNo);
			Parameters.put("panelId", pano.PanelId);
			Parameters.put("serialNo1", pano.Sayac1);
			Parameters.put("serialNo2", pano.Sayac2);
			Parameters.put("statu", pano.Statu);	
			Parameters.put("userId", pano.UserId);
			Parameters.put("tesisatNo", pano.TesisatNo);
			Parameters.put("tesisatCarpan", pano.TesisatCarpan);
			Parameters.put("trafoCode", pano.TrafoCode);
			Parameters.put("trafoCarpan", pano.TrafoCarpan);
			Parameters.put("mapX", GlobalVariables.getIntance().MapX);
			Parameters.put("mapY", GlobalVariables.getIntance().MapY);
	
		SoapObject soapObject;
		try {
			soapObject= (SoapObject)HttpTransport(SOAP_ACTION,
					METHOD_NAME, NAMESPACE, URL, Parameters);
			
					response.Result=Integer.parseInt(soapObject.getProperty("Result").toString());	
					response.Message=soapObject.getProperty("Message").toString();
		} catch (Exception e) 
		{
			// TODO: handle exception
			response.Result=1;
			response.Message=e.getMessage();
		}
		return response;
	}
	public List<WorkOrder> LoadWorkOrdersOfUserEx(String userId,
			int WorkOrderStatu, String WorkOrderDate,String AboneNo) throws Exception {
		SOAP_ACTION = "http://tempuri.org/LoadWorkOrder";
		METHOD_NAME = "LoadWorkOrder";
		
		List<WorkOrder> ListWorkOrder = new ArrayList<WorkOrder>();
		Map<String, Object> Parameters = new HashMap<String, Object>();
		
		Parameters.put("workOrderDate", WorkOrderDate);
		Parameters.put("workOrderStatu", WorkOrderStatu);
		Parameters.put("aboneno", AboneNo);
		Parameters.put("userId", userId);

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
	public Response updateWorkOrder (WorkOrder workorder,int updateType) throws Exception
	{
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Response response= new Response();
		switch (updateType) 
		{
		case 1://PLANLANDI
			SOAP_ACTION = "http://tempuri.org/UpdateWorkOrderStatu_PLANLANDI";
			METHOD_NAME = "UpdateWorkOrderStatu_PLANLANDI";
			
			Parameters.put("workOrderStatu", workorder.StatuId);
			Parameters.put("description", workorder.Description);
			Parameters.put("updateUser", workorder.UserId);
			Parameters.put("workOrderId", workorder.ID);
			break;
		case 2://MONTAJ_TAMAMLANDI
			
			SOAP_ACTION = "http://tempuri.org/UpdateWorkOrderStatu_MONTAJTAMAMLANDI";
			METHOD_NAME = "UpdateWorkOrderStatu_MONTAJTAMAMLANDI";
			Parameters.put("workOrderStatu", workorder.StatuId);
			Parameters.put("description", workorder.Description);
			Parameters.put("updateUser", workorder.UserId);
			Parameters.put("workOrderId", workorder.ID);
			Parameters.put("modemNumber", workorder.ModemImeiNo);
			Parameters.put("serialNumber",workorder.SerialNo);
			Parameters.put("oldSerialNumber", workorder.OldSerialNo);
			Parameters.put("secondImagePath", workorder.secondimagepath);
			Parameters.put("firstImagePath", workorder.firstimagepath);
			//Parameters.put("secondImage", ServiceLocater.GlobalVariables.getIntance().secondImageByte);
			break;
		case 3://CALISILIYOR
			SOAP_ACTION = "http://tempuri.org/UpdateWorkOrderStatu_CALISILIYOR";
			METHOD_NAME = "UpdateWorkOrderStatu_CALISILIYOR";
			
			Parameters.put("workOrderStatu", workorder.StatuId);
			Parameters.put("description", workorder.Description);
			Parameters.put("updateUser", workorder.UserId);
			Parameters.put("workOrderId", workorder.ID);
			Parameters.put("modemNumber", workorder.ModemImeiNo);
			Parameters.put("serialNumber",workorder.SerialNo);
			Parameters.put("oldSerialNumber", workorder.OldSerialNo);
			Parameters.put("mapX", workorder.LAT);
			Parameters.put("mapY",workorder.LONG);
			Parameters.put("firstImagePath", workorder.firstimagepath);
			//Parameters.put("firstImage",ServiceLocater.GlobalVariables.getIntance().firstImageByte);
			break;
		case 4://BEKLEMEDE
			SOAP_ACTION = "http://tempuri.org/UpdateWorkOrderStatu_BEKLEMEDE";
			METHOD_NAME = "UpdateWorkOrderStatu_BEKLEMEDE";
			
			Parameters.put("workOrderStatu", workorder.StatuId);
			Parameters.put("description", workorder.Description);
			Parameters.put("updateUser", workorder.UserId);
			Parameters.put("modemNumber", workorder.ModemImeiNo);
			Parameters.put("serialNumber",workorder.SerialNo);
			Parameters.put("mapX", workorder.LAT);
			Parameters.put("mapY",workorder.LONG);
			Parameters.put("workOrderId", workorder.ID);
			Parameters.put("firstImagePath", workorder.firstimagepath);			
			break;
		case 5://IPTAL
			SOAP_ACTION = "http://tempuri.org/UpdateWorkOrderStatu_IPTAL";
			METHOD_NAME = "UpdateWorkOrderStatu_IPTAL";
			
			Parameters.put("workOrderStatu", workorder.StatuId);
			Parameters.put("description", workorder.Description);
			Parameters.put("updateUser", workorder.UserId);
			Parameters.put("modemNumber", workorder.ModemImeiNo);
			Parameters.put("serialNumber",workorder.SerialNo);
			Parameters.put("mapX", workorder.LAT);
			Parameters.put("mapY",workorder.LONG);
			Parameters.put("workOrderId", workorder.ID);
			Parameters.put("firstImagePath", workorder.firstimagepath);
			break;
		case 6://ÝS_EMRÝ_TAMAMLANDI
			SOAP_ACTION = "http://tempuri.org/UpdateWorkOrderStatu_ISEMRITAMAMLANDI";
			METHOD_NAME = "UpdateWorkOrderStatu_ISEMRITAMAMLANDI";
			
			Parameters.put("workOrderStatu", workorder.StatuId);
			Parameters.put("description", workorder.Description);
			Parameters.put("updateUser", workorder.UserId);
			Parameters.put("workOrderId", workorder.ID);
			Parameters.put("modemNumber", workorder.ModemImeiNo);
			Parameters.put("serialNumber",workorder.SerialNo);
			Parameters.put("oldSerialNumber", workorder.OldSerialNo);
			break;
		}
			SoapObject soapObject;
			try {
				soapObject=(SoapObject) HttpTransport(SOAP_ACTION,
						METHOD_NAME, NAMESPACE, URL, Parameters);
				response.Result=Integer.parseInt(soapObject.getProperty("Result").toString());	
				response.Message=soapObject.getProperty("Message").toString();
			} catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
			return response;
	}
	public Boolean IsMeterRead(String serialNo) 
	{
		// TODO Auto-generated method stub
		final String SOAP_ACTION = "http://tempuri.org/IsMeterReadOutOk";
		final String METHOD_NAME = "IsMeterReadOutOk";

		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("serialNo", serialNo);
		
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
	public boolean IsModemAvailable(String ModemImeiNo) 
	{
		// TODO Auto-generated method stub
		SOAP_ACTION = "http://tempuri.org/IsModemAvailable";
		METHOD_NAME = "IsModemAvailable";
		
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("modemImeiNo", ModemImeiNo);
		String soapObject="";
		boolean result=false;
		try 
		{
			 result=Boolean.parseBoolean(( HttpTransport(SOAP_ACTION,METHOD_NAME, NAMESPACE, URL, Parameters)).toString());
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
	public boolean IsModemUsable(String ModemImeiNo,boolean check) 
	{
		if(!check)return true;
		// TODO Auto-generated method stub
		SOAP_ACTION = "http://tempuri.org/IsModemUsable";
		METHOD_NAME = "IsModemUsable";
		
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("modemImeiNo", ModemImeiNo);
		String soapObject="";
		boolean result=false;
		try 
		{
			 result=Boolean.parseBoolean(( HttpTransport(SOAP_ACTION,METHOD_NAME, NAMESPACE, URL, Parameters)).toString());
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
	public boolean IsSayacAvailable(String SayacSeriNo) 
	{
		// TODO Auto-generated method stub
		SOAP_ACTION = "http://tempuri.org/IsSayacAvailable";
		METHOD_NAME = "IsSayacAvailable";
		
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("serialNo", SayacSeriNo);
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
	public boolean IsSayacUsable(String SayacSeriNo) 
	{
		// TODO Auto-generated method stub
		SOAP_ACTION = "http://tempuri.org/IsSayacUsable";
		METHOD_NAME = "IsSayacUsable";
		
		Map<String, Object> Parameters = new HashMap<String, Object>();
		Parameters.put("serialNo", SayacSeriNo);
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
			
	}

	
}
