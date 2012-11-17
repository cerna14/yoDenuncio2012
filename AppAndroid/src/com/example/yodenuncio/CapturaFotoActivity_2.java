package com.example.yodenuncio;
/* @author Carlos Quijada Fuentes
 * @author Cristóbal Ortiz Gálvez
 * @author Diego Sepúlveda Briones
 * @version 1.0 */
import android.os.Bundle;
import android.app.Activity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CapturaFotoActivity_2 extends Activity {

	private static final int REQUEST_CAMERA = 1;

	protected Uri imageUri;
	private String photoPath = "";
	private Bundle datos;
	private Intent myLocalIntent;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("ON CREATE FOTOS");
		String state = Environment.getExternalStorageState();
		datos = new Bundle();
		myLocalIntent = getIntent();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			long captureTime = System.currentTimeMillis();
			photoPath = Environment.getExternalStorageDirectory()
					+ "/DCIM/Camera/Point" + captureTime + ".jpg";
			try {
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				File photo = new File(photoPath);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("onActivityResult FOTOS");
		switch (requestCode) {
		case REQUEST_CAMERA:
			if (!photoPath.equals("") && (photoPath != null)) {
				datos.putString("path", "" + photoPath);
				myLocalIntent.putExtras(datos);
				setResult(Activity.RESULT_OK, myLocalIntent);
				finish();
			}

			break;

		default:
			break;

		}

	}

//	public static File convertImageUriToFile(Uri imageUri, Activity activity) {
//		Cursor cursor = null;
//		try {
//			String[] proj = { MediaStore.Images.Media.DATA,
//					MediaStore.Images.Media._ID,
//					MediaStore.Images.ImageColumns.ORIENTATION };
//			cursor = activity.managedQuery(imageUri, proj, // Which columns to
//															// return
//					null, // WHERE clause; which rows to return (all rows)
//					null, // WHERE clause selection arguments (none)
//					null); // Order-by clause (ascending by name)
//			int file_ColumnIndex = cursor
//					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//			int orientation_ColumnIndex = cursor
//					.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);
//			if (cursor.moveToFirst()) {
//				String orientation = cursor.getString(orientation_ColumnIndex);
//				Log.d("PingDroid", "OR " + orientation);
//				return new File(cursor.getString(file_ColumnIndex));
//			}
//			return null;
//		} catch (Exception ex) {
//			return null;
//		} finally {
//			if (cursor != null) {
//				cursor.close();
//			}
//		}
//	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("foto", photoPath);

	}

}