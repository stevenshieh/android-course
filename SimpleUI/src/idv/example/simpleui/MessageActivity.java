package idv.example.simpleui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

public class MessageActivity extends Activity {

	private static final String FILE_NAME = "message.txt";
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message);

		textView = (TextView) findViewById(R.id.textView1);

		String text = getIntent().getStringExtra("text");

		Log.d("debug", "intent extra:" + text);

		writeFile(text);
		textView.setText(readFile());
	}

	private void writeFile(String text) {

		text += "\n";
		try {
			FileOutputStream fos = openFileOutput(FILE_NAME,
					Context.MODE_APPEND);
			fos.write(text.getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readFile() {

		try {
			FileInputStream fis = openFileInput(FILE_NAME);

			byte[] buffer = new byte[1024];
			StringBuffer sb = new StringBuffer();
			while (fis.read(buffer) != -1) {
				sb.append(new String(buffer));
			}

			return sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@SuppressLint("NewApi")
	private void writeFileToStorage(String text) {

		File docDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

		File file = new File(docDir, FILE_NAME);
		
		text += "\n";
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(text.getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
