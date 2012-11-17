package com.example.yodenuncio;
/* @author Carlos Quijada Fuentes
 * @author Cristóbal Ortiz Gálvez
 * @author Diego Sepúlveda Briones
 * @version 1.0 */
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormularioDenunciaActivity extends Activity {
	private Button btn_envio;
	private EditText txt_nombre;
	private EditText txt_descripcion;
	private Bundle datos;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulariodenuncia);
		btn_envio = (Button) findViewById(R.id.button1);
		txt_nombre = (EditText) findViewById(R.id.editText1);
		txt_descripcion = (EditText) findViewById(R.id.editText2);
		intent = getIntent();
		btn_envio.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (txt_nombre.getText().toString().equals("")|| txt_descripcion.getText().toString().equals("")) {
					Context context = getApplicationContext();
					Toast.makeText(context,"Debe ingresar nombre y descripción",Toast.LENGTH_LONG).show();
				} else {
					datos = new Bundle();
					datos.putString("titulo", txt_nombre.getText().toString());
					datos.putString("descripcion", txt_descripcion.getText().toString());
					intent.putExtras(datos);
					setResult(Activity.RESULT_OK, intent);
					finish();
				}
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.formulariodenuncia, menu);
		return true;
	}

}
