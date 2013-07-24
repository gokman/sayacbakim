package Objects;

import org.ksoap2.serialization.SoapObject;

public class User 
{
	public User(SoapObject user)
	{
		this.UserId=user.getProperty("ID").toString().equals("anyType{}") ? "0" :user.getProperty("ID").toString();
		this.UserName=user.getProperty("USERNAME").toString().equals("anyType{}") ? "" : user.getProperty("USERNAME").toString();
	}
	public String UserId;
	public String UserName;
}
