package idv.example.simpleui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MessageActivity extends Activity {
	
	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message);
		
		textView = (TextView) findViewById(R.id.textView1);
		
		String text = getIntent().getStringExtra("text");
		
		Log.d("debug", "intent extra:" + text);
		
		textView.setText(text );
	}
	
}
