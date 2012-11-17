package com.example.yodenuncio;

/* @author Carlos Quijada Fuentes
 * @author Cristóbal Ortiz Gálvez
 * @author Diego Sepúlveda Briones
 * @version 1.0 */

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class YoDenuncioActivity extends Activity {
	// constantes para las actividades a lanzar
	private static final int ACTIVIDAD_CAPTURA_GPS = 1;
	private static final int ACTIVIDAD_ENVIA_VIA_WEB = 2;
	private static final int ACTIVIDAD_FORMULARIO_DENUNCIA = 4;
	private static final int ACTIVIDAD_CAPTURA_CAMARA = 3;
	private boolean enviaWeb = false;
	private boolean capturaGPS = false;
	private boolean capturaImagen = false;
	private boolean capturaDenuncia = false;
	private Button btn_camera;
	protected Uri imageUri;
	private static final int REQUEST_CAMERA = 5;
	private Button btn_denuncia;
	private String latitud = "12";
	private String longitud = "123";
	private String titulo = "";
	private String descripcion = "";
	// private String url =
	// "http://10.50.77.178/yoDenuncio/denunciamovilfotoservlet";
	//ip joel
	private String ip = "172.20.10.4";
	//ip wifi
	//private String ip = "10.50.77.178";
	//URL de subida de fotos de la denuncia
	private String url = "http://" + ip+ "/yoDenuncio/denunciamovilfotoservlet";
	//URL de subida de denuncias
	private String url2 = "http://" + ip + "/yoDenuncio/denunciamovilservlet";
	// URL del Portal de denuncias
	private String url3 = "http://" + ip + "/yoDenuncio";
	private static final String path = Environment.getExternalStorageDirectory() + "/DCIM/Camera/Point_1.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yo_denuncio);
		btn_camera = (Button) findViewById(R.id.button1);
		btn_denuncia = (Button) findViewById(R.id.button2);
		btn_camera.setEnabled(true);
		btn_denuncia.setEnabled(false);
		btn_denuncia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("", "ACTIVIDAD_FORMULARIO_DENUNCIA");
				iniciaActividades(ACTIVIDAD_FORMULARIO_DENUNCIA);
				System.out.println("Estado del boton de denuncia "+btn_denuncia.isEnabled());
				System.out.println("Estado del boton de camara "+btn_camera.isEnabled());
			}
		});
		// llamar a GPS para la captura de datos
		iniciaActividades(ACTIVIDAD_CAPTURA_GPS);
		btn_camera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("Estado del boton de denuncia "+btn_denuncia.isEnabled());
				System.out.println("Estado del boton de camara "+btn_camera.isEnabled());
				Log.e("", "ACTIVIDAD_CAPTURA_CAMARA");
				// iniciaActividades(ACTIVIDAD_CAPTURA_CAMARA);
				String state = Environment.getExternalStorageState();
				if (Environment.MEDIA_MOUNTED.equals(state)) {
					try {
						Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
						File photo = new File(path);
						System.out.println("Largo del photo antes de borrar "+photo.length());
						if(photo.length()>0){
							photo.delete();
							System.out.println("Largo del photo despues de borrar "+photo.length());
							photo = new File(path);
						}
						intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photo));
						startActivityForResult(
								Intent.createChooser(intent, "Capture Image"),
								REQUEST_CAMERA);
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), "Error",
								Toast.LENGTH_LONG).show();
						Log.e(e.getClass().getName(), e.getMessage(), e);
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_yo_denuncio, menu);
		return true;
	}

	/*
	 * @param actividad a iniciar
	 */
	private void iniciaActividades(int i) {
		switch (i) {
		// lanzar la actividad de captura de datos desde la camapra
		case ACTIVIDAD_CAPTURA_CAMARA:
			System.out.println("INICIA CAMARA");
			startActivityForResult(
					new Intent(this, CapturaFotoActivity_2.class),
					ACTIVIDAD_CAPTURA_CAMARA);
			break;
		case ACTIVIDAD_FORMULARIO_DENUNCIA:
			startActivityForResult(new Intent(this,
					FormularioDenunciaActivity.class),
					ACTIVIDAD_FORMULARIO_DENUNCIA);
			break;
		case ACTIVIDAD_CAPTURA_GPS:
			startActivityForResult(new Intent(this,
					CapturaPosicionActivity.class), ACTIVIDAD_CAPTURA_GPS);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case ACTIVIDAD_CAPTURA_GPS:
			if (Activity.RESULT_OK == resultCode) {
				// extraer las coordenadas que me envió el GPS
				Bundle datosGPS = data.getExtras();
				// String accion=datosGPS.getString("acion");
				latitud = datosGPS.getString("latitud");
				longitud = datosGPS.getString("longitud");
				capturaGPS = true;
			} else {
				Toast.makeText(this.getApplicationContext(),"Error datos GPS no capturados", Toast.LENGTH_LONG).show();
			}
			break;
		case REQUEST_CAMERA:
			System.out.println("se devuelde de tomar la foto con el path "+ path);
			File comprueba = new File(path);
			int largoComprueba = (int) comprueba.length();
			System.out.println("Tamaño del archivo despues de camara "+largoComprueba);
			if(largoComprueba>0){
				btn_camera.setEnabled(false);
				System.out.println("Boton Camara DESHABILITADO");
				btn_denuncia.setEnabled(true);
				System.out.println("Boton DENUNCIA HABILITADO");
			}
			else{
				btn_camera.setEnabled(true);
				System.out.println("Boton Camara HABILITADO");
				btn_denuncia.setEnabled(false);
				System.out.println("Boton DENUNCIA desHABILITADO");
			}
			break;
		case ACTIVIDAD_CAPTURA_CAMARA:
			if (Activity.RESULT_OK == resultCode) {
				// Bundle datosCamara = data.getExtras();
				// path = datosCamara.getString("path");
				// capturaImagen = true;
			}
			break;
		case ACTIVIDAD_FORMULARIO_DENUNCIA:
			Log.e("", "entro el el swtich");
			if (Activity.RESULT_OK == resultCode) {
				Toast.makeText(getApplicationContext(), "Enviando...", Toast.LENGTH_LONG).show();
				// extraer las coordenadas que me envió el GPS
				Bundle datosGPS = data.getExtras();
				titulo = datosGPS.getString("titulo");
				descripcion = datosGPS.getString("descripcion");
				System.out.println("TItulo " + titulo + " descrp " + descripcion
								+ " lat " + latitud + " long " + longitud);
				capturaDenuncia = true;
				System.out.println("Entra a la cosa");
				System.out.println("el path es " + path);
				// ahora se debe enviar los datos
				if (true) {
					Log.e("", "dentro del if");
					// enviar foto
					// Url of the server

					File file = new File(path);
					System.out.println("otra vez el path " + path);
					Log.d("largo", "" + file.length());

					try {
						//Traspasar denuncias al portal
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(url);
						InputStreamEntity reqEntity = new InputStreamEntity(new FileInputStream(file), -1);
						reqEntity.setContentType("binary/octet-stream");
						reqEntity.setChunked(true); 
						httppost.setEntity(reqEntity);
						HttpResponse response = httpclient.execute(httppost);
						Header[] h = response.getHeaders("id");
						for (int i = 0; i < h.length; i++) {
							System.out.println("header " + h[i].getValue()+ " name " + h[i].getName());
						}
						List<NameValuePair> postValues = new ArrayList<NameValuePair>(2);
						postValues.add(new BasicNameValuePair("lat", latitud));
						postValues.add(new BasicNameValuePair("log", longitud));
						postValues.add(new BasicNameValuePair("titulo", titulo));
						postValues.add(new BasicNameValuePair("desc",descripcion));
						HttpPost httppost2 = new HttpPost(url2);
						httppost2.setEntity(new UrlEncodedFormEntity(postValues));
						response = httpclient.execute(httppost2);
						AlertDialog.Builder builder = new AlertDialog.Builder(this);
						builder.setMessage(
								"Su denuncia se recivió exitosamente ¿Desea ir al Portal de denuncias?")
								.setCancelable(false).setPositiveButton("Si",
										new DialogInterface.OnClickListener() {
											public void onClick(
												@SuppressWarnings("unused") final DialogInterface dialog,
												@SuppressWarnings("unused") final int id) {
												// Do something with response...
												startActivity(new Intent("android.intent.action.VIEW",Uri.parse(url3)));
											}
										}).setNegativeButton("No",
										new DialogInterface.OnClickListener() {
											public void onClick(final DialogInterface dialog,
													@SuppressWarnings("unused") final int id) {
												dialog.cancel();
											}
										});
						//alerta
						final AlertDialog alert = builder.create();
						//mostrar alerta
						alert.show();
						System.out.println(response.toString());						
						btn_camera.setEnabled(true);
						btn_denuncia.setEnabled(false);
					} catch (Exception e) {
						// show error
						Toast.makeText(getApplicationContext(), "Problemas de conectividad, intente mas tarde.", Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				}
			} else {
				Toast.makeText(this.getApplicationContext(), "Salir ",Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}

	}
	
}
