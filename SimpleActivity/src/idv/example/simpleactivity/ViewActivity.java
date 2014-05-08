package idv.example.simpleactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ViewActivity extends Activity {
	
	private static final int RESULT_FINISHED = 5;

	public void finished(View view) {
		
		Button b = (Button) view;
		Intent intent = new Intent();
		switch(view.getId()) {
		case R.id.button1:
			intent.putExtra("which", b.getText());
			break;
		case R.id.button2:
			intent.putExtra("which", b.getText());
			break;
		}
		
		setResult(RESULT_FINISHED, intent);
		
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_view);
		
		 Intent intent = getIntent();
		 String msg = intent.getStringExtra("message");
		
		 Log.d("debug", "msg ="+msg);
		
		Log.d("debug", "ViewActivity onCreate");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("debug", "ViewActivity onDestroy");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("debug", "ViewActivity onPause");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d("debug", "ViewActivity onRestart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("debug", "ViewActivity onResume");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("debug", "ViewActivity onStart");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("debug", "ViewActivity onStop");
	}
	
}
