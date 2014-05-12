package idv.example.simplenetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {

	PlaceholderFragment placeholderFragment = new PlaceholderFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, placeholderFragment).commit();
		}

//		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//				.permitAll().build();
//		StrictMode.setThreadPolicy(policy);

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

	public void search(View view) {

		EditText editText = placeholderFragment.getEditText();
		searchFromGoogleMaps(editText.getText().toString());

	}

	private void searchFromGoogleMaps(String locate) {
		final String url = "http://maps.googleapis.com/maps/api/geocode/json?address="
				+ locate + "&sensor=true";

		AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				String result = "{}";
				try {
					result = placeholderFragment.fetchByApache(url);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return result;
			}

			@Override
			protected void onPostExecute(String result) {

				try {
					JSONObject json = new JSONObject(result);

					JSONArray results = json.getJSONArray("results");

					for (int i = 0; i < result.length(); i++) {
						String address = "address:"+results.getJSONObject(i).getString(
								"formatted_address");
						Log.d("debug", address);
						Toast.makeText(getApplication(), address,
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

		};

		task.execute();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		private EditText editText;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			editText = (EditText) rootView.findViewById(R.id.editText1);

			return rootView;
		}

		public String fetchByApache(String urlString) throws IOException {

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(urlString);

			ResponseHandler<String> handler = new BasicResponseHandler();
			String execute = client.execute(get, handler);

			return execute;
		}

		private String fetchByURL(String urlString) throws IOException {

			URL url = new URL(urlString);

			URLConnection conn = url.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "big5"));

			String result = "", temp = "";
			while ((temp = br.readLine()) != null) {
				result += temp;
			}

			return result;
		}

		public EditText getEditText() {
			return editText;
		}

		public void setEditText(EditText editText) {
			this.editText = editText;
		}

	}

}
