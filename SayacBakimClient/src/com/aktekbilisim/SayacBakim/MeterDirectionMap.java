package com.aktekbilisim.SayacBakim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Objects.WorkOrder;
import Objects.Overlays.MeterDirectionOverlay;
import Objects.Overlays.MeterOverlay;
import ServiceLocater.GlobalVariables;
import ServiceLocater.Service;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MeterDirectionMap extends MapActivity implements LocationListener 
{
	private MapView myMapView = null;
	private MapController myMC = null;
	private GeoPoint geoPoint = null;
	private MeterOverlay itemizedoverlay;
	private LocationManager locationManager;
	private String provider;
	private ProgressDialog m_ProgressDialog = null;
	 private static final int TAKE_PHOTO_CODE = 1;  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.googlemap);
		WorkOrder workorder=GlobalVariables.getIntance().workorder;
		myMapView = (MapView) findViewById(R.id.mapView);
		geoPoint = null;
		myMapView.setSatellite(false);
        String curLocAdress=null;
        m_ProgressDialog = ProgressDialog.show(MeterDirectionMap.this,
				"Lütfen Bekleyiniz...", "Yol tarifi alýnýyor ...", true);
        //while(curLocAdress==null)curLocAdress=getCurrentLocationAdress();
        curLocAdress=getUserCurrLonLat();
        String tarLocAdress="";
			
	    tarLocAdress =workorder.LAT+","+workorder.LONG;
			
	
		String pairs[] = getDirectionData(curLocAdress,tarLocAdress);
		String[] lngLat = pairs[0].split(",");

		// STARTING POINT
		GeoPoint startGP = new GeoPoint(
				(int) (Double.parseDouble(lngLat[1]) * 1E6),
				(int) (Double.parseDouble(lngLat[0]) * 1E6));
		
		myMC = myMapView.getController();
		geoPoint = startGP;
		myMC.setCenter(geoPoint);
		myMC.setZoom(19);
		myMapView.getOverlays().add(new MeterDirectionOverlay(startGP, startGP));

		// NAVIGATE THE PATH
		GeoPoint gp1;
		GeoPoint gp2 = startGP;

		for (int i = 1; i < pairs.length; i++) {
			lngLat = pairs[i].split(",");
			gp1 = gp2;
			// watch out! For GeoPoint, first:latitude, second:longitude
			gp2 = new GeoPoint((int) (Double.parseDouble(lngLat[1]) * 1E6),(int) (Double.parseDouble(lngLat[0]) * 1E6));
			myMapView.getOverlays().add(new MeterDirectionOverlay(gp1, gp2));
			Log.d("xxx", "pair:" + pairs[i]);
		}
		m_ProgressDialog.dismiss();
		// END POINT
		Drawable drawable = this.getResources().getDrawable(R.drawable.kirmizinokta);
		itemizedoverlay = new MeterOverlay(this, drawable);
		createMarker();
		myMapView.getOverlays().add(new MeterDirectionOverlay(gp2, gp2));
		myMapView.setSatellite(true);
		myMapView.getController().animateTo(startGP);
		myMapView.setBuiltInZoomControls(true);
		myMapView.displayZoomControls(true);
		
		
		} catch (Exception e) 
		{
			Intent intent = new Intent(MeterDirectionMap.this, ErrorActivity.class);
			intent.putExtra("Error_Message","Bulunduðunuz yer ile gideceðiniz adres yol tarifi tanýmlanamadý.");
			intent.putExtra("ErrorType", 0);
			startActivity(intent);
		}
		m_ProgressDialog.dismiss();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	private void createMarker() {
		GeoPoint p = myMapView.getMapCenter();
		OverlayItem overlayitem = new OverlayItem(p, "", "");
		itemizedoverlay.addOverlay(overlayitem);
		if (itemizedoverlay.size() > 0) {
			myMapView.getOverlays().add(itemizedoverlay);
		}
	}
	private String[] getDirectionData(String srcPlace, String destPlace) {

	        String urlString = "http://maps.google.com/maps?f=d&hl=en&saddr="
	                + srcPlace + "&daddr=" + destPlace
	                + "&ie=UTF8&0&om=0&output=kml";
	        Log.d("URL", urlString);
	        Document doc = null;
	        HttpURLConnection urlConnection = null;
	        URL url = null;
	        String pathConent = "";
	        try 
	        {

	            url = new URL(urlString.toString());
	            urlConnection = (HttpURLConnection) url.openConnection();
	            urlConnection.setRequestMethod("GET");
	            urlConnection.setDoOutput(true);
	            urlConnection.setDoInput(true);
	            urlConnection.connect();
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = dbf.newDocumentBuilder();
	            doc = (Document) db.parse(urlConnection.getInputStream());

	        } 
	        catch (Exception e) 
	        {
	        	Log.i("HttpPost", e.getMessage());
	        }

	        NodeList nl =  doc.getElementsByTagName("LineString");
	        for (int s = 0; s < nl.getLength(); s++) {
	            Node rootNode = nl.item(s);
	            NodeList configItems = rootNode.getChildNodes();
	            for (int x = 0; x < configItems.getLength(); x++) {
	                Node lineStringNode = configItems.item(x);
	                NodeList path = lineStringNode.getChildNodes();
	                pathConent = path.item(0).getNodeValue();
	            }
	        }
	        String[] tempContent = pathConent.split(" ");
	        return tempContent;
	    }
    private String getCurrentLocationGeoPoint()
    {
    	  GeoPoint p = ServiceLocater.GlobalVariables.getIntance().currentGeoPoint;
    
    	  
              return  p.getLatitudeE6()  / 1E6+","+ 
			  p.getLongitudeE6() / 1E6;
		
    }
    private String getUserCurrLonLat() {
		// TODO Auto-generated method stub
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
			.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// Check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to 
		// go to the settings
		//if (!enabled) {
		 // Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		//	startActivity(intent);
		//}
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		
		return Double.toString(location.getLatitude())+","+Double.toString(location.getLongitude());
	}

	private String clearTrCharacter(String addressLine) {
		// TODO Auto-generated method stub
		try 
		{
			addressLine=addressLine.replace("ç", "c");
			addressLine=addressLine.replace("ð", "g");
			addressLine=addressLine.replace("ö", "o");
			addressLine=addressLine.replace("ü", "u");
			addressLine=addressLine.replace("þ", "s");
			addressLine=addressLine.replace("Ð", "G");
			addressLine=addressLine.replace("Ö", "O");
			addressLine=addressLine.replace("Ý", "I");
			addressLine=addressLine.replace("Þ", "S");
			addressLine=addressLine.replace("Ü", "U");
			addressLine=addressLine.replace("ý", "i");
			addressLine=addressLine.replace("Ç", "C");
			//addressLine=addressLine.replace(" ", "%20");
			
			
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
		return addressLine;
	}
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu)
	    {
	     menu.add(0,1,1,"Kapat").setIcon(R.drawable.kapat);
	    
	     menu.add(0, 2, 2, "Yeni Ýþ Emri Sorgula").setIcon(
					R.drawable.yeni_is_emri);
	     return true;
	    }
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	     
	     switch(item.getItemId())
	     {
	     case 1:
	    	 Intent intent  = new Intent(getBaseContext(), LoginActivity.class);
	         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        
	         startActivity(intent);

	            finish();
	      return true;
	  
	     case 2:
				Intent intent2 = new Intent(getBaseContext(),FilterActivity.class);
				startActivity(intent2);
				finish();
				return true;


	     }
	     return super.onOptionsItemSelected(item);

	    }
	 private void takePhoto(){  
	      final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
	      intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(this)) );   
	      startActivityForResult(intent, TAKE_PHOTO_CODE);  
	    }  
	      
	    private File getTempFile(Context context){  
	      //it will return /sdcard/image.tmp  
	      final File path = new File( Environment.getExternalStorageDirectory(), context.getPackageName() );  
	      if(!path.exists()){  
	        path.mkdir();  
	      }  
	      return new File(path, "image.tmp");  
	    }  
	      
	    @Override  
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	      if (resultCode == RESULT_OK) {  
	        switch(requestCode){  
	          case TAKE_PHOTO_CODE:  
	            final File file = getTempFile(this);  
	            try {  
	              Bitmap captureBmp = Media.getBitmap(getContentResolver(), Uri.fromFile(file) );  
	              // do whatever you want with the bitmap (Resize, Rename, Add To Gallery, etc)  
	            } catch (FileNotFoundException e) {  
	              e.printStackTrace();  
	            } catch (IOException e) {  
	              e.printStackTrace();  
	            }  
	          break;  
	        }  
	      }  
	    }

		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		} 

}
