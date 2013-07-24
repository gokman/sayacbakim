package Objects;

import java.util.Date;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class PhotoInfo implements  KvmSerializable 
{
	public String  PhotoName ;
    public Date FileCreationDate ;
    public String   DeviceId;
    public String UserId;
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		
	}
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
