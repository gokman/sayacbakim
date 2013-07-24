package Objects;

public class PanoMalzeme 
{
	public PanoMalzeme()
	{}
	public PanoMalzeme(boolean isselected,String Id,double Value)
	{
		this.isSelected=isselected;
		this.Id=Id;
		this.Value=Value;
	}
	public boolean isSelected;
	public String Name;
	public double Value;
	public String Id;	
}
