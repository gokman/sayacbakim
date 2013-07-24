package Objects;

import org.ksoap2.serialization.SoapObject;

public class Pano 
{
	public Pano(SoapObject object)
	{
		this.PanelId=GetValue(object,"ID");
		this.Sayac1=GetValue(object,"SERIALNO1");
		this.Sayac2=GetValue(object,"SERIALNO2");
		this.ModemImeiNo=GetValue(object,"MODEMIMEINO");
		this.PhotoList=GetValue(object,"PHOTOLIST");
		this.Statu=Integer.parseInt( GetValue(object,"STATUID"));
		this.TrafoCode=GetValue(object,"TRAFOCODE");
		this.TesisatNo=GetValue(object,"TESISATNO");
		this.TrafoCarpan=GetValue(object,"TRAFOCARPAN");
		this.TesisatCarpan=GetValue(object,"TESISATCARPAN");
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