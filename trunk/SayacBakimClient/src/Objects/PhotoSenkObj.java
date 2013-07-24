package Objects;

import org.ksoap2.serialization.SoapObject;

public class PhotoSenkObj 
{
	public int WorkOrderId;
	public String firstPhotoPath;
	public String secondPhotoPath;
	public String firstPhoto;
	public String secondPhoto;

	public PhotoSenkObj(){}
	public PhotoSenkObj(SoapObject _workorder)
	{
		this.WorkOrderId = Integer.parseInt(_workorder.getProperty("WorkOrderId").toString());
		this.firstPhotoPath = _workorder.getProperty("firstImagePath").toString().equals("anyType{}") ? "" : _workorder.getProperty("firstImagePath").toString();
		this.secondPhotoPath= _workorder.getProperty("secondImagePath").toString().equals("anyType{}") ? "" : _workorder.getProperty("secondImagePath").toString();
	}
}
