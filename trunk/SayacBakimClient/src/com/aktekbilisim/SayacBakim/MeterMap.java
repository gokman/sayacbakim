package com.aktekbilisim.SayacBakim;

import Objects.Overlays.MeterOverlay;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

public class MeterMap extends MapActivity {
	MapView mapView;
	MapController mc;
	GeoPoint p;
	MeterOverlay itemizedoverlay;
	LocationManager locationManager;
	MyLocationOverlay myLocationOverlay;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.googlemap);

		try {
			
		mapView = (MapView) findViewById(R.id.mapView);
		//mapView.setBuiltInZoomControls(true);
		//mapView.setSatellite(true);
		//mc = mapView.getController();
		//mc.setZoom(14); // Zoon 1 is world view
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, new GeoUpdateHandler());
		
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		
		mapView.getOverlays().add(myLocationOverlay);
		GeoPoint p = myLocationOverlay.getMyLocation();
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				ServiceLocater.GlobalVariables.getIntance().currentGeoPoint=myLocationOverlay.getMyLocation();
				Intent intent= new Intent(MeterMap.this,MeterDirectionMap.class);
				startActivity(intent);
			}
		});

		Drawable drawable = this.getResources().getDrawable(R.drawable.kirmizinokta);
		itemizedoverlay = new MeterOverlay(this, drawable);
		createMarker();
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("google", e.getMessage());
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	public class GeoUpdateHandler implements LocationListener {

		public void onLocationChanged(Location location) {
			int lat = (int) (location.getLatitude() * 1E6);
			int lng = (int) (location.getLongitude() * 1E6);
			GeoPoint point = new GeoPoint(lat, lng);
			createMarker();
			//mc.animateTo(point); // mapController.setCenter(point);
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

	private void createMarker() {
		GeoPoint p = mapView.getMapCenter();
		OverlayItem overlayitem = new OverlayItem(p, "", "");
		itemizedoverlay.addOverlay(overlayitem);
		if (itemizedoverlay.size() > 0) {
			mapView.getOverlays().add(itemizedoverlay);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.enableCompass();
	}

	@Override
	protected void onPause() {
		super.onResume();
		myLocationOverlay.disableMyLocation();
		myLocationOverlay.disableCompass();
	}

	 @Override
	    public boolean onCreateOptionsMenu(Menu menu)
	    {
	     menu.add(0,1,1,"Kapat").setIcon(R.drawable.kapat);
	     return true;
	    }
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	     
	     switch(item.getItemId())
	     {
	     case 1:
	    	 Intent intent = new Intent(Intent.ACTION_MAIN);
	    	 intent.addCategory(Intent.CATEGORY_HOME);
	    	 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	 startActivity(intent);

	            finish();
	      return true;

	     }
	     return super.onOptionsItemSelected(item);

	    }
}
