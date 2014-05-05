package idv.example.takephone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;
import android.provider.MediaStore;

public class MainActivity extends ActionBarActivity {

	private static final String PHOTO_PNG = "photo.png";
	private static final String PHOTO_AUTO_PNG = "photoAuto.png";
	private static final int REQUEST_CODE_PHOTO = 5500;
	private PlaceholderFragment placeholderFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			placeholderFragment = new PlaceholderFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, placeholderFragment).commit();
		}
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
		if (id == R.id.action_photo) {

			Log.d("debug", "action menu photo.");

			Intent intent = new Intent();
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			Uri targetFileUri = Uri.fromFile(getTargetFile(PHOTO_AUTO_PNG));
			intent.putExtra(MediaStore.EXTRA_OUTPUT, targetFileUri);
			// startActivity(intent);
			startActivityForResult(intent, REQUEST_CODE_PHOTO);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		if (requestCode == REQUEST_CODE_PHOTO) {
			if (resultCode == RESULT_OK) {

				/*
				 * 如果使用 intent.putExtra(MediaStore.EXTRA_OUTPUT, targetFileUri);
				 * 就沒有 intent.getParcelableExtra("data") 可以拿。
				 */
				// Bitmap bitmap = intent.getParcelableExtra("data");
				// placeholderFragment.getImageView().setImageBitmap(bitmap);
				// saveImage(bitmap);

				placeholderFragment.getTextView().setText(
						getTargetFile(PHOTO_AUTO_PNG).getPath());

				Log.d("debug", "ok");
			} else if (resultCode == RESULT_CANCELED) {
				Log.d("debug", "canceled");
			} else {

			}
		}

		super.onActivityResult(requestCode, resultCode, intent);
	}

	private void saveImage(Bitmap bitmap) {

		File imageFile = getTargetFile(PHOTO_PNG);

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(imageFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

			placeholderFragment.getTextView().setText(imageFile.getPath());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private File getTargetFile(String filename) {
		File imageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

		File imageFile = new File(imageDir, filename);
		return imageFile;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private ImageView imageView;
		private TextView textView;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			imageView = (ImageView) rootView.findViewById(R.id.imageView1);
			textView = (TextView) rootView.findViewById(R.id.textView1);

			return rootView;
		}

		public ImageView getImageView() {
			return imageView;
		}

		public TextView getTextView() {
			return textView;
		}

	}

}
