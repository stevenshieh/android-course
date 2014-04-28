package idv.example.simpleui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.anim;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class MessageActivity extends Activity {

	private static final String FILE_NAME = "message.txt";

	private ListView listView;
	private ProgressBar progressBar;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message);

		listView = (ListView) findViewById(R.id.listView1);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle("Message");
		progressDialog.setMessage("Loading ... ");
		progressDialog.setCancelable(false);
		progressDialog.show();

		String text = getIntent().getStringExtra("text");
		boolean isChecked = getIntent().getBooleanExtra("checkBox", false);

		Log.d("debug", "intent extra:" + text);

		saveData(text, isChecked);

		// writeFile(text);
		// textView.setText(readFile());
	}

	private void saveData(String text, boolean isChecked) {
		ParseObject testObject = new ParseObject("Message");
		testObject.put("text", text);
		testObject.put("checkBox", isChecked);
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
		query.orderByAscending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> messages, ParseException e) {
				if (e == null) {

					List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
					String[] from = new String[] { "text", "checkBox" };
					int[] to = new int[] { android.R.id.text1,
							android.R.id.text2 };
					SimpleAdapter adapter = new SimpleAdapter(
							MessageActivity.this, data,
							android.R.layout.simple_list_item_2, from, to);

					// StringBuffer content = new StringBuffer();

					for (ParseObject message : messages) {

						Map<String, Object> item = new HashMap<String, Object>();
						item.put("text", message.getString("text"));
						item.put("checkBox", message.getBoolean("checkBox"));
						data.add(item);
						// content.append(message.getString("text")).append("\n");
					}

					listView.setAdapter(adapter);

					progressBar.setVisibility(View.GONE);
					progressDialog.dismiss();
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
