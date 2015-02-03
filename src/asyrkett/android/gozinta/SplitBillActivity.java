package asyrkett.android.gozinta;

import java.text.DecimalFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SplitBillActivity extends Activity
{

	DecimalFormat currency = new DecimalFormat("$###,##0.00");
	DecimalFormat percent = new DecimalFormat("##%");
	String qualityChoice;
	int numPeople;
	double billAmount;
	double individualBill;
	double individualTip;
	double individualTotal;
	double tipRate;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_split_bill);
	    final EditText billField = (EditText) findViewById(R.id.txtBillAmount);
	    final EditText peopleField = (EditText) findViewById(R.id.txtNumPeople);
	    final Spinner qualityField = (Spinner) findViewById(R.id.spinServiceQuality);
	    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    
		builder.setTitle(getString(R.string.builder_title));
		builder.setPositiveButton(getString(R.string.builder_positive_button), null);
		
	    Button calculateBtn = (Button) findViewById(R.id.btnCalculateTip);
        calculateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				if (billField.getText().toString().trim().length() == 0 ||
						Double.parseDouble(billField.getText().toString().trim()) == 0)
				{
					Toast.makeText(getApplicationContext(), getString(R.string.empty_bill_prompt),
							Toast.LENGTH_SHORT).show();
					return;
				}
				else
				{
					billAmount = Double.parseDouble(billField.getText().toString());
				}
				
				if (peopleField.getText().toString().trim().length() == 0 ||
						Integer.parseInt(peopleField.getText().toString().trim()) == 0)
				{
					Toast.makeText(getApplicationContext(), getString(R.string.empty_people_prompt),
							Toast.LENGTH_SHORT).show();
					return;
				}
				else
				{
					numPeople = Integer.parseInt(peopleField.getText().toString());
				}
				
				qualityChoice = qualityField.getSelectedItem().toString().toLowerCase(Locale.US);
				
				if (qualityChoice.equalsIgnoreCase(getString(R.string.excellent))) //EXCELLENT
				{
					tipRate = 0.20;
				}
				else if (qualityChoice.equalsIgnoreCase(getString(R.string.average))) //AVERAGE
				{
					tipRate = 0.18;
				}
				else //POOR and default
				{
					tipRate = 0.15;
				}
				
				individualBill = billAmount / numPeople;
				individualTip = billAmount / numPeople * tipRate;
				individualTotal = individualBill + individualTip;
				
				builder.setMessage(String.format(getString(R.string.individual_bill_msg), currency.format(billAmount),
						Integer.valueOf(numPeople), percent.format(tipRate), qualityChoice, currency.format(individualBill),
						currency.format(individualTip), currency.format(individualTotal)));
				
				AlertDialog dialog = builder.show();
				TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
				messageView.setGravity(Gravity.CENTER);
			}
        	
        });
	}

}
