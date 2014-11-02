package com.seeheng.checkroute;

import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
	private final LatLng LOCATION_CA6 = new LatLng(1.327296, 103.761054);
	private final LatLng LOCATION_CR = new LatLng(1.330214, 103.773006);
	private final LatLng LOCATION_BBR = new LatLng(1.356486,103.734461);
	
	private GoogleMap map;
	private Timer updateTimer;
	private MyTimerTask mtt;
	private LatLng curLL=LOCATION_CA6;
	private OnCameraChangeListener occl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.moveCamera( CameraUpdateFactory.newLatLngZoom(curLL , 15.0f) );
		map.setTrafficEnabled(true);
	
		// display GPS my location icon
		map.setMyLocationEnabled(true);

		// set curLL after user changes the camera
		map.setOnCameraChangeListener(new OnCameraChangeListener() {
	        @Override
	        public void onCameraChange(CameraPosition cameraPosition) {
	            curLL = map.getCameraPosition().target;
	            }
	        });
		
		/*
		mtt = new MyTimerTask();
		updateTimer = new Timer("UpdateTimer", true);
		updateTimer.schedule(mtt, 60*1000, 60*1000);
		*/
		Log.d("CheckRoute", "Start");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClick_CA6(View v){
		curLL = LOCATION_CA6;
		doClickTask(curLL, "Clementi Ave 6");
	}

	public void onClick_CR(View v){
		curLL = LOCATION_CR;
		doClickTask(curLL, "Clementi Road");
		
	}

	public void onClick_BBR(View v){
		curLL = LOCATION_BBR;
		doClickTask(curLL, "Bt Batok Road");
		
	}
	
	public void onDestroy()
	{
		mtt=null;
		updateTimer=null;
		super.onDestroy();
	
	}

	private void doClickTask(LatLng loc, String title)
	{
		map.addMarker(new MarkerOptions().position(loc).title(title));
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(loc,15);
		map.animateCamera(update);	
	}
	
	
	class MyTimerTask extends TimerTask{
		@Override
		public void run(){
			runOnUiThread(new Runnable() {
					@Override
					public void run() {
						map.moveCamera( CameraUpdateFactory.newLatLngZoom(curLL , 15.0f) );
						//Log.d("CheckRoute", "MyTimerTask");
					}
				});
		}
	}
	

}


