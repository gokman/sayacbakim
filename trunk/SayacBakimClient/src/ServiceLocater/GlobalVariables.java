package ServiceLocater;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Objects.Malzeme;
import Objects.Pano;
import Objects.PanoMalzeme;
import Objects.User;
import Objects.WorkOrder;
import android.location.LocationListener;
import android.location.LocationManager;

import com.google.android.maps.GeoPoint;

public  class GlobalVariables 
{
public static GlobalVariables _variables=null;
public static ArrayList<WorkOrder> WorkOrderList;
public static ArrayList<Pano> panoList;

public static GlobalVariables getIntance()
{
	if(_variables==null)return _variables=new GlobalVariables();
	return _variables;
}
public List<PanoMalzeme> PanoMalzemeList;
public Boolean mainTreadAlive=true;
public int LoginResult=3;
public WorkOrder workorder;
public User user;
public GeoPoint currentGeoPoint;
public int StatusCodeForFilter;
public String datetimeForFilter;
public int BarcodeType=0;
public String firstImageByte;
public String secondImageByte;
public String WhichImage;	
public boolean firstImageValidation;
public boolean secondImageValidation;
public LocationManager locationManager ;
public LocationListener locationListener;
public String MapX;
public String MapY;
public String firstImagePath="";
public String PhotoList="";
public String secondImagePath="";
public String Deviceid;
public String AboneNoForFilter;
public Pano pano;
public Malzeme malzeme;
public List<Malzeme> malzemeList;
public void appendLog(String text)
{       
   File logFile = new File("sdcard/log.file");
   if (!logFile.exists())
   {
      try
      {
         logFile.createNewFile();
      } 
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   try
   {
      //BufferedWriter for performance, true to set append to file flag
      BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true)); 
      buf.append(text);
      buf.newLine();
      buf.close();
   }
   catch (IOException e)
   {
      // TODO Auto-generated catch block
      e.printStackTrace();
   }
}


}
