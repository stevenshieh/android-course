package idv.example.simpleui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class MessageActivity extends Activity {

	private static final String FILE_NAME = "message.txt";
	private TextView textView;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message);

		textView = (TextView) findViewById(R.id.textView1);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);

		String text = getIntent().getStringExtra("text");

		Log.d("debug", "intent extra:" + text);

		saveData(text);

		// writeFile(text);
		// textView.setText(readFile());
	}

	private void saveData(String text) {
		ParseObject testObject = new ParseObject("Message");
		testObject.put("text", text);
		testObject.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				finished();
				if (e == null) {
					loadData();
				} else {
					e.printStackTrace();
				}
			}

		});
	}

	private void loadData() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> messages, ParseException e) {
				if (e == null) {

					StringBuffer content = new StringBuffer();

					for (ParseObject message : messages) {
						content.append(message.getString("text")).append("\n");
					}

					textView.setText(content.toString());
					progressBar.setVisibility(View.GONE);
				} else {
					e.printStackTrace();
				}
			}
		});
	}

	private void finished() {
		Toast.makeText(this, "save finished.", Toast.LENGTH_SHORT).show();
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressLint("NewApi")
	private void writeFileToStorage(String text) {

		File docDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

		if (docDir.exists() == false) {
			docDir.mkdirs();
		}

		// Environment.getExternalStorageDirectory();

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
