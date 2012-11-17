package com.example.yodenuncio;
/* @author Carlos Quijada Fuentes
 * @author Cristóbal Ortiz Gálvez
 * @author Diego Sepúlveda Briones
 * @version 1.0 */
import android.app.Activity;

import android.content.Context;


import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;


public class CapturaPosicionActivity extends Activity {

	private Bundle datos;
	private Intent myLocalIntent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		datos = new Bundle();
		myLocalIntent = getIntent();
		GPSTracker gps;
		LocationManager locationManager;
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(context);
		gps = new GPSTracker(CapturaPosicionActivity.this);

		// check if GPS enabled
		if (gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			datos.putString("latitud", "" + latitude);
			datos.putString("longitud", "" + longitude);
			myLocalIntent.putExtras(datos);
			setResult(Activity.RESULT_OK, myLocalIntent);
		} else {
			//gps.showSettingsAlert();
		}
		finish();
	}
}
