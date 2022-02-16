package br.fmu.tipcalculatorn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();
    private double billAmount = 0.0;
    private double customPercent = 0.18;
    private TextView textViewFormattedAmount;
    private EditText editTextAmount;
    private TextView textViewOtherPct;
    private TextView textViewTip15;
    private TextView textViewTotal15;
    private TextView textViewTipOther;
    private TextView textViewTotalOther;
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewFormattedAmount = (TextView) findViewById(R.id.textViewFormattedAmount);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        textViewOtherPct = (TextView) findViewById(R.id.textViewOtherPct);
        textViewTip15 = (TextView) findViewById(R.id.textViewTip15);
        textViewTotal15 = (TextView) findViewById(R.id.textViewTotal15);
        textViewTipOther = (TextView) findViewById(R.id.textViewTipOther);
        textViewTotalOther = (TextView) findViewById(R.id.textViewTotalOther);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textViewFormattedAmount.setText(currencyFormat.format(billAmount));
        updateStandard();
        updateCustom();
        editTextAmount.addTextChangedListener(amountEditTextWatcher);

        seekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    private SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            customPercent = progress / 100.0;
            updateCustom();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                billAmount = Double.parseDouble(charSequence.toString()) / 100.0;
            } catch( NumberFormatException e ) {
                billAmount = 0;
            }
            textViewFormattedAmount.setText(currencyFormat.format(billAmount));
            updateStandard();
            updateCustom();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void updateStandard() {
        double tip15Pct = billAmount * .15;
        double total15Pct = billAmount + tip15Pct;
        textViewTip15.setText(currencyFormat.format(tip15Pct));
        textViewTotal15.setText(currencyFormat.format(total15Pct));
    }

    private void updateCustom() {
        textViewOtherPct.setText(percentFormat.format(customPercent));
        double customTip = billAmount * customPercent;
        double customTotal = billAmount + customTip;
        textViewTipOther.setText(currencyFormat.format(customTip));
        textViewTotalOther.setText(currencyFormat.format(customTotal));
    }

}