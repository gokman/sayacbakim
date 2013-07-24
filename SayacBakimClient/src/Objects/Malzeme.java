package Objects;

import org.ksoap2.serialization.SoapObject;

public class Malzeme {
	public Malzeme() {
	}

	public Malzeme(SoapObject obj) {
		this.PanelId = GetValue(obj, "PANELID");
		this.MalzemeId=Integer.parseInt( GetValue(obj, "MALZEMEID"));
		this.Value=Float.parseFloat(GetValue(obj, "VALUE"));
		this.Description=GetValue(obj, "DESCRIPTION");
		this.IsSelected=Boolean.parseBoolean(GetValue(obj, "ISSELECTED"));
		this.Name=GetValue(obj,"NAME");
		this.Unit=GetValue(obj,"UNIT");
	}
	public int Position;
	public String PanelId;
	public int MalzemeId;
	public float Value;
	public String Description;
	public boolean IsSelected;
	public String Name;
	public String Unit;

	public String GetValue(SoapObject object, String Field) {
		try {
			return object.getProperty(Field).toString();
		} catch (Exception e) {
			return "";
		}
	}
}
