package idv.example.simplelinearlayout2;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

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

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_main, container,
//					false);
//			return rootView;
			
			LinearLayout layout = new LinearLayout(getActivity());
			
			
			EditText toEditText = new EditText(getActivity());
			EditText subjectEditText = new EditText(getActivity());
			EditText messageEditText = new EditText(getActivity());
								
			
			layout.addView(toEditText);
			layout.addView(subjectEditText);
			layout.addView(messageEditText);
			
			layout.setOrientation(LinearLayout.VERTICAL);
			
			toEditText.setHint("To");
			subjectEditText.setHint("Subject");
			messageEditText.setHint("Message");
			LinearLayout.LayoutParams messageLayoutParams =  (LinearLayout.LayoutParams) messageEditText.getLayoutParams();
			messageLayoutParams.weight = 1;
			messageEditText.setGravity(Gravity.TOP);
			
			
			Button sendButton = new Button(getActivity());
			
			layout.addView(sendButton);

			LinearLayout.LayoutParams sendLayoutParams =  (LinearLayout.LayoutParams) sendButton.getLayoutParams();
			sendLayoutParams.gravity = LinearLayout.LayoutParams.WRAP_CONTENT;
			
			
			return layout;
		}
	}

}
