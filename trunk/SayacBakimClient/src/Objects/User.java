package Objects;

import org.ksoap2.serialization.SoapObject;

public class User 
{
	public User(SoapObject user)
	{
		this.UserId=user.getProperty("Id").toString().equals("anyType{}") ? "0" :user.getProperty("Id").toString();
		this.UserName=user.getProperty("UserName").toString().equals("anyType{}") ? "" : user.getProperty("UserName").toString();
	}
	public String UserId;
	public String UserName;
}
