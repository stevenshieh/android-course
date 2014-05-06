package idv.and23720.hw1;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

		double calc = 0.0d;

		private EditText inputText;
		private Button num1;
		private Button num2;
		private Button num3;
		private Button num4;
		private Button num5;
		private Button num6;
		private Button num7;
		private Button num8;
		private Button num9;
		private Button num0;
		private Button buttonC;
		private Button buttonAdd;
		private Button buttonSub;
		private Button buttonMlt;
		private Button buttonDivided;

		private Button buttonPrint;

		boolean calcing = false;
		private Operator op;
		boolean flush = false;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			inputText = (EditText) rootView.findViewById(R.id.inputNumber);
			num1 = (Button) rootView.findViewById(R.id.button1);
			num2 = (Button) rootView.findViewById(R.id.button2);
			num3 = (Button) rootView.findViewById(R.id.button3);
			num4 = (Button) rootView.findViewById(R.id.button4);
			num5 = (Button) rootView.findViewById(R.id.button5);
			num6 = (Button) rootView.findViewById(R.id.button6);
			num7 = (Button) rootView.findViewById(R.id.button7);
			num8 = (Button) rootView.findViewById(R.id.button8);
			num9 = (Button) rootView.findViewById(R.id.button9);
			num0 = (Button) rootView.findViewById(R.id.button0);
			buttonC = (Button) rootView.findViewById(R.id.buttonC);
			buttonAdd = (Button) rootView.findViewById(R.id.buttonAdd);
			buttonSub = (Button) rootView.findViewById(R.id.buttonMinus);
			buttonMlt = (Button) rootView.findViewById(R.id.buttonMulti);
			buttonDivided = (Button) rootView.findViewById(R.id.buttonDivided);
			buttonPrint = (Button) rootView.findViewById(R.id.buttonPrint);

			OnClickListener clickListener = new OnClickListener() {

				@Override
				public void onClick(View v) {
					Button button = (Button) v;
					String value = button.getText().toString();
					
					if(flush) {
						inputText.setText(value);
						flush = false;
					} else {
						if (inputText.getText().toString().equals("0")) {
							inputText.setText(value);
						} else {
							inputText.setText(inputText.getText() + value);
						}
						flush = false;
					}

					Log.d("debug", value);
				}
			};
			num1.setOnClickListener(clickListener);
			num2.setOnClickListener(clickListener);
			num3.setOnClickListener(clickListener);
			num4.setOnClickListener(clickListener);
			num5.setOnClickListener(clickListener);
			num6.setOnClickListener(clickListener);
			num7.setOnClickListener(clickListener);
			num8.setOnClickListener(clickListener);
			num9.setOnClickListener(clickListener);
			num0.setOnClickListener(clickListener);

			buttonC.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					inputText.setText("");
				}
			});

			OnClickListener operatorClickListener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					double newNum = Double.parseDouble(inputText.getText()
							.toString());
					calc = newNum;
					inputText.setText("");
					calcing = true;

					Button o = (Button) v;
					String oter = o.getText().toString();
					op = getOp(oter);
				}
			};
			buttonAdd.setOnClickListener(operatorClickListener);
			buttonSub.setOnClickListener(operatorClickListener);
			buttonMlt.setOnClickListener(operatorClickListener);
			buttonDivided.setOnClickListener(operatorClickListener);
			buttonPrint.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String second = inputText.getText().toString();
					double s = 0.0d;
					try {
						s = Double.parseDouble(second);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}

					if (s != 0.0d) {
						double result = op.calc(calc, s);
						inputText.setText(Double.toString(result));
					}
					calcing = false;
					calc = 0.0d;
					op = null;
					flush = true;
				}
			});

			return rootView;
		}

		public Operator getOp(String op) {
			if ("+".equals(op)) {
				return new AddOperator();
			} else if ("-".equals(op)) {
				return new SubOperator();

			} else if ("*".equals(op)) {
				return new MultiOperator();

			} else if ("/".equals(op)) {
				return new DividedOperator();
			}
			return null;
		}
	}

}
