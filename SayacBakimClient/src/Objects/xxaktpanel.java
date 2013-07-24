package Objects;

import org.ksoap2.serialization.SoapObject;

public class xxaktpanel
{
	public String PanelId;
	public String Sayac1;
	public String Sayac2;
	public String ModemImeiNo;
	
	public xxaktpanel()
	{
		
	}
	public xxaktpanel(SoapObject object)
	{
		this.PanelId=object.getProperty("PANELID").toString().equals("anyType{}") ? "0" :object.getProperty("PANELID").toString();
		this.Sayac1=object.getProperty("SERIALNO1").toString().equals("anyType{}") ? "" : object.getProperty("SERIALNO1").toString();
		this.Sayac2=object.getProperty("SERIALNO2").toString().equals("anyType{}") ? "" : object.getProperty("SERIALNO2").toString();
		this.ModemImeiNo=object.getProperty("MODEMIMEINO").toString().equals("anyType{}") ? "" : object.getProperty("MODEMIMEINO").toString();
	}
}
