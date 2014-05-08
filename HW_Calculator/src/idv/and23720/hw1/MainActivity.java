package idv.and23720.hw1;

import idv.and23720.hw1.operator.AddOperator;
import idv.and23720.hw1.operator.DividedOperator;
import idv.and23720.hw1.operator.MultiOperator;
import idv.and23720.hw1.operator.Operator;
import idv.and23720.hw1.operator.SubOperator;

import java.math.BigDecimal;

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

		BigDecimal calc = new BigDecimal("0");

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
		private Button numPoint;

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
			numPoint = (Button) rootView.findViewById(R.id.buttonPoint);
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

					if (flush) {
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
			numPoint.setOnClickListener(clickListener);

			buttonC.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					inputText.setText("");
				}
			});

			OnClickListener operatorClickListener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					Button o = (Button) v;
					String oter = o.getText().toString();
					try {
						if (op == null) {
							BigDecimal newNum = getInputNumber();
							calc = newNum;
							calcing = true;
							flush = false;
						} else {
							BigDecimal calcing2 = calcing(getInputNumber());
							calc = calcing2;
						}
					} catch (NumberFormatException e) {
						Log.d("debug",
								"button operator exception. " + e.getMessage());
					} finally {
						inputText.setText("");
						op = getOp(oter);
					}
				}

			};
			buttonAdd.setOnClickListener(operatorClickListener);
			buttonSub.setOnClickListener(operatorClickListener);
			buttonMlt.setOnClickListener(operatorClickListener);
			buttonDivided.setOnClickListener(operatorClickListener);
			buttonPrint.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						if (op != null) {
							inputText.setText(String
									.valueOf(calcing(getInputNumber())));
							calcing = false;
							calc = new BigDecimal("0");
							op = null;
						}
						flush = true;
					} catch (Exception e) {
						Log.d("debug",
								"button flush exception. " + e.getMessage());
					}
				}

			});

			return rootView;
		}

		private BigDecimal getInputNumber() {
			String second = inputText.getText().toString();
			return new BigDecimal(second);
		}

		private BigDecimal calcing(BigDecimal second) {
			BigDecimal result = new BigDecimal("0");
			try {
				if (flush == false) {
					if (second.compareTo(new BigDecimal("0")) != 0) {
						result = op.calc(calc, second);
					}
				}
			} catch (Exception e) {
				Log.d("debug", "button flush exception. " + e.getMessage());
			}
			return result;
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
			throw new IllegalArgumentException(op);
		}
	}

}
