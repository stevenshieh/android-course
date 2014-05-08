package idv.example.simpleactivity;

import idv.example.simpleactivity.fragment.MyFragment;
import idv.example.simpleactivity.fragment.PlaceholderFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	private static final int REQUEST_CODE_1 = 111;
	private static final int REQUEST_CODE_2 = 222;
	private static final int REQUEST_CODE_3 = 333;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.d("debug", "MainActivity onCreate");

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

	public void gotoView(View view) {
		Button b = (Button)view;
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.button1:
			intent.setClass(this, ViewActivity.class);
			intent.putExtra("message", "hi"+b.getText().toString());
			startActivityForResult(intent, REQUEST_CODE_1);
			break;
		case R.id.button2:
			intent.setClass(this, View2Activity.class);
			intent.putExtra("message", "hi"+b.getText().toString());
			startActivityForResult(intent, REQUEST_CODE_2);
			break;
		case R.id.button3:
			intent.setClass(this, View3Activity.class);
			intent.putExtra("message", "hi"+b.getText().toString());
			startActivityForResult(intent, REQUEST_CODE_3);
			break;

		default:
			break;
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		Log.d("debug", "requestCode=" + requestCode + ", resultCode="
				+ resultCode);
		if (intent != null) {
			Log.d("debug", "(which button) = " + intent.getStringExtra("which"));
		}

		super.onActivityResult(requestCode, resultCode, intent);
	}
	
	public void replaceFragment(View view) {
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		switch (view.getId()) {
		case R.id.gotoMyFragment:
			ft.replace(R.id.container, new MyFragment());
			break;
		case R.id.gotoOriginFragment:
			ft.replace(R.id.container, new PlaceholderFragment());
			break;
		default:
			break;
		}
		
		ft.commit();
		
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("debug", "MainActivity onDestroy");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("debug", "MainActivity onPause");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d("debug", "MainActivity onRestart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("debug", "MainActivity onResume");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("debug", "MainActivity onStart");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("debug", "MainActivity onStop");
	}

}
