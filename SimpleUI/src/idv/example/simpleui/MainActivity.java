package idv.example.simpleui;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	public void send(View view) {
		Log.d("debug", "click:" + view.getId());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private Button button1;
		private EditText text1;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			button1 = (Button) rootView.findViewById(R.id.button1);
			text1 = (EditText) rootView.findViewById(R.id.editText1);

			button1.setText("Send");
			button1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					send();
				}
			});
			
			text1.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if(event.getAction() == KeyEvent.ACTION_DOWN && 
							keyCode == KeyEvent.KEYCODE_ENTER){
						
						send();
						return true;
					}
					return false;
				}
			});

			return rootView;
		}
		
		private void send() {
			String text = text1.getText().toString();

			Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT)
					.show();

			text1.getText().clear();
		}
	}

}
