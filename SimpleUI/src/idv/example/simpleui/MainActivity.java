package idv.example.simpleui;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SimpleAdapter;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.provider.Settings.Secure;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;

@SuppressLint("ValidFragment")
public class MainActivity extends ActionBarActivity {

	private static final String PARSE_DEVICE_ID = "deviceId";
	private static final String PARSE_USERS = "users";

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

		Parse.initialize(this, "52SSxGlsI9z7U01hQAXXIi0lFa1n183C2YGwFnZG",
				"eNQC2poRiw5SHwWJVTDLOaM0vuFwYsnHYbQHITkP");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		// When users indicate they are no longer Giants fans, we unsubscribe them.
		PushService.subscribe(this, "all", MainActivity.class);
		PushService.subscribe(this, "id_"+getDeviceId(), MainActivity.class);
		
		register();
	}
	
	private ParseQuery<ParseObject> getParseQuery_Users() {
		return new ParseQuery<ParseObject>(PARSE_USERS);
	}

	private void register() {
		
		ParseQuery<ParseObject> query = getParseQuery_Users();
		try {
			List<ParseObject> find = query.whereEqualTo(PARSE_DEVICE_ID, getDeviceId()).find();
			
			if(find.isEmpty()) {
				ParseObject obj = new ParseObject(PARSE_USERS);
				obj.put(PARSE_DEVICE_ID, getDeviceId());
				obj.saveInBackground();
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
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
	
	public String getDeviceId() {
		return Secure.getString(getContentResolver(), Secure.ANDROID_ID);
	}
	
	private void loadDeviceId() {
		ParseQuery<ParseObject> query = getParseQuery_Users();
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> list, ParseException exception) {
				
				Set<String> set = new HashSet<String>();
				
				
			}
		});
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public class PlaceholderFragment extends Fragment {

		private static final String TEXT_INPUT = "textInput";
		private static final String ENCRYPT_CHECKED = "encryptChecked";
		private Button button1;
		private EditText text1;
		private CheckBox encrypt;
		private Spinner spinner;
		private SharedPreferences sp;
		private Editor editor;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			button1 = (Button) rootView.findViewById(R.id.button1);
			text1 = (EditText) rootView.findViewById(R.id.editText1);
			encrypt = (CheckBox) rootView.findViewById(R.id.checkBox1);
			spinner = (Spinner) rootView.findViewById(R.id.spinner1);

			sp = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
			editor = sp.edit();

			encrypt.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					editor.putBoolean(ENCRYPT_CHECKED, isChecked);
				}
			});

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
					if (event.getAction() == KeyEvent.ACTION_DOWN
							&& keyCode == KeyEvent.KEYCODE_ENTER) {

						send();
						return true;
					} else {
						editor.putString(TEXT_INPUT, text1.getText().toString());
						editor.commit();
					}
					return false;
				}
			});

			text1.setText(sp.getString(TEXT_INPUT, ""));
			encrypt.setChecked(sp.getBoolean(ENCRYPT_CHECKED, false));
			
			loadDeviceId();

			return rootView;
		}
		
		void loadDeviceId() {
			ParseQuery<ParseObject> query = getParseQuery_Users();
			query.findInBackground(new FindCallback<ParseObject>() {
				
				@Override
				public void done(List<ParseObject> list, ParseException exception) {
					
					Set<String> set = new HashSet<String>();
					
					for(ParseObject obj : list) {
						set.add(obj.getString(PARSE_DEVICE_ID));
					}
					
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							getActivity(), android.R.layout.simple_spinner_item,
							set.toArray(new String[set.size()]));
					
					spinner.setAdapter(adapter);
				}
			});
		}

		private void send() {
			String text = text1.getText().toString();

			if (encrypt.isChecked()) {
				text = String.valueOf(text.hashCode());
			}
			
			String item = (String) spinner.getSelectedItem();
			
			ParsePush push = new ParsePush();
			push.setChannel("id_"+getDeviceId());
			push.setMessage(item);
			push.sendInBackground();
			
			ParsePush pushAll = new ParsePush();
			pushAll.setChannel("all");
			pushAll.setMessage(item);
			pushAll.sendInBackground();

			Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();

			text1.getText().clear();

			Intent intent = new Intent();
			intent.setClass(getActivity(), MessageActivity.class);

			intent.putExtra("text", text);
			intent.putExtra("checkBox", encrypt.isChecked());

			getActivity().startActivity(intent);

		}
	}

}
