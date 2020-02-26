package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    TextView outputText;
    //Button calculateButton;

    ReversePolishNotation rpn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = findViewById(R.id.InputText);
        outputText = findViewById(R.id.OutputText);
        rpn = new ReversePolishNotation();
    }

    public void onCalculateButtonClick(View view) {
        String in = inputText.getText().toString();
        String regex = "([0-9]|[-+*/.\\,\\(\\)])+"; //надо перенести в класс
        if (in.matches(regex)) {
            //double result = 0.0;
            try {
                double result = rpn.CountString(in);
                outputText.setText(String.format("%f", result));
            }
            catch (Throwable t) {
                //outputText.setText(t.toString());
                inputText.setError(getString(R.string.calculate_exc_errtxt));
            }
        }
        else {
            inputText.setError(getString(R.string.regex_check_err));
        }



    }


}
