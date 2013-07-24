/**
 * 
 */
package Objects;

import java.io.Serializable;
import java.util.Date;

import org.ksoap2.serialization.SoapObject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author hikmet.yarbasi
 * 
 */
public class WorkOrder implements Serializable 
{
	public int ID ;
    public String Address ;
    public String CutInterval;
    public String LAT ;
    public String LONG ;
    public String ModemImeiNo ;
    public String ModemGsmNo ;
    public String Multiplier ;
    public String OldSerialNo ;
    public String SerialNo ;
    public String TesisatNo ;
    public String TrafoCode ;
    public String CityName ;
    public String TownName ;
    public String MeterType ;
    public String Statu ;
    public String UserName ;
    public String UNVAN ;
    public String SAYACID ;
    public String WORKNAME ;
    public String Description ;
    public int UserId ;
    public int StatuId;
	public String firstimagepath;
	public String secondimagepath;
	public WorkOrder() {}
	public WorkOrder(SoapObject _person) {
		// TODO Auto-generated constructor stub
		try {
		
		this.ID = Integer.parseInt(GetValue(_person,"ID"));
		this.CityName =GetValue(_person,"CITYNAME");
		this.TownName =GetValue(_person,"TOWNNAME");
		this.MeterType =GetValue(_person,"METERTYPE");
		this.WORKNAME = GetValue(_person,"WORKNAME");
		this.StatuId =EnumWorkOrderStatus.getWorkOrderStatuCodeForNo(GetValue(_person,"STATUID"));
		this.SerialNo =GetValue(_person,"SERIALNO");
		this.ModemImeiNo = GetValue(_person,"MODEMIMEINO");
	    this.Statu=EnumWorkOrderStatus.getWorkOrderStatus(GetValue(_person,"STATUID"));
        this.UserName= GetValue(_person,"USERNAME");
        this.Address= GetValue(_person,"ADDRESS");
        this.LAT=GetValue(_person,"LATITUDE");
        this.LONG=GetValue(_person,"LONGITUDE");
        this.TesisatNo= GetValue(_person,"TESISATNO");
        this.OldSerialNo=GetValue(_person,"OLDSERIALNO");
        this.Multiplier =GetValue(_person,"MULTIPLIER");
        this.CutInterval=GetValue(_person,"CUTINTERVAL");
        this.firstimagepath =GetValue(_person,"FIRSTIMAGEPATH");
        this.secondimagepath=GetValue(_person,"SECONDIMAGEPATH");
        this.ModemGsmNo=GetValue(_person,"MODEMGSMNO");
		}
		catch (Exception e) 
		{
			String message=e.getCause().toString();
		}
	}
    public String GetValue(SoapObject object, String Field)
    {
    	try 
    	{
		return object.getProperty(Field).toString();	
		} catch (Exception e) 
		{
			return "";
		}
    }
	public String getImageName() {
		
		

		switch (StatuId) {
		case 1:

			return "maviNokta";
			
		case 2 :

			return "sariNokta";
			
		case 3:

			return "siyahNokta";
			
		case 4:

			return "yesilNokta";
		case 5:
			return "turuncuNokta";
		case 6:
			return "kirmiziNokta";
			
           default:
			return "yesilNokta";
		}
	}
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
}

