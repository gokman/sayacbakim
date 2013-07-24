package Objects;

import org.ksoap2.serialization.SoapObject;

public class Pano 
{
	public Pano(SoapObject object)
	{
		this.PanelId=GetValue(object,"ID");
		this.Sayac1=GetValue(object,"SerialNo1");
		this.Sayac2=GetValue(object,"SerialNo2");
		this.ModemImeiNo=GetValue(object,"ModemImeiNo");
		this.PhotoList=GetValue(object,"PhotoList");
		this.Statu=Integer.parseInt( GetValue(object,"StatuId"));
		this.PhotoList=GetValue(object,"PhotoList");
		this.TrafoCode=GetValue(object,"TrafoCode");
		this.TesisatNo=GetValue(object,"TesisatNo");
		this.TrafoCarpan=GetValue(object,"TrafoCarpan");
		this.TesisatCarpan=GetValue(object,"TesisatCarpan");
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
	public Pano()
	{}
	public String PanelId;
	public String Sayac1;
	public String Sayac2;
	public String ModemImeiNo;
	public String PhotoList;
	public int Statu;
	public String UserId;
	public String TesisatNo;
	public String TrafoCode;
	public String TrafoCarpan;
	public String TesisatCarpan;
	
}