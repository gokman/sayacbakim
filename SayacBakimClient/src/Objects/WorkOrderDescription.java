package Objects;

import org.ksoap2.serialization.SoapObject;

public class WorkOrderDescription {
	public String Description;
	public String UpdateDate;
	public int WorkOrderStatu;
	public WorkOrderDescription(SoapObject _descriptions)
	{
		this.Description=_descriptions.getProperty("Description").toString().equals("anyType{}") ? "" : _descriptions.getProperty("Description").toString();
		this.UpdateDate=_descriptions.getProperty("UpdateDate").toString().equals("anyType{}") ? "" : _descriptions.getProperty("UpdateDate").toString().replace('T', ' ').substring(0,19);
		this.WorkOrderStatu=EnumWorkOrderStatus.getWorkOrderStatuCodeForNo(_descriptions.getProperty("WorkStatu").toString().equals("anyType{}") ? "" : _descriptions.getProperty("WorkStatu").toString());
	}

}
