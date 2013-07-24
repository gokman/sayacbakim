package Objects;

import org.ksoap2.serialization.SoapObject;

public class Malzeme {
	public Malzeme() {
	}

	public Malzeme(SoapObject obj) {
		this.PanelId = GetValue(obj, "PanelId");
		this.MalzemeId=Integer.parseInt( GetValue(obj, "MalzemeId"));
		this.Value=Float.parseFloat(GetValue(obj, "value"));
		this.Description=GetValue(obj, "description");
		this.IsSelected=Boolean.parseBoolean(GetValue(obj, "isselected"));
		this.Name=GetValue(obj,"Name");
		this.Unit=GetValue(obj,"Unit");
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
