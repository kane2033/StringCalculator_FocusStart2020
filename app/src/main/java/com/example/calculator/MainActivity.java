package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.EmptyStackException;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    TextView outputText;

    ReversePolishNotation rpn; //класс с методом подсчета выражения

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        inputText = findViewById(R.id.InputText);
        outputText = findViewById(R.id.OutputText);

        rpn = new ReversePolishNotation();
    }

    //метод перехода в activity справки
    public void onHelpButtonClick(View view) {

        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    //при нажатии кнопки "посчитать"
    public void onCalculateButtonClick(View view) {
        String in = inputText.getText().toString(); //берется строка из поля ввода
        in = in.replaceAll("\\s+",""); //убирает все пробелы

        String regex = "([0-9]|[-+*/)(.,])+"; //проверка на наличии запрещенных символов
        if (in.matches(regex)) { //если ошибки не найдено
            try {
                double result = rpn.CountString(in); //подсчет
                outputText.setText(String.format("%.3f", result)); //вывод результата в textview
            }
            //исключение emptystackexc возникает при попытке достать из стэка несуществующее число, что
            //возникает при недостатке операндов для совершения операции
            catch (EmptyStackException emptyStack) {
                inputText.setError(getString(R.string.operands_lack_err));
            }
            //при получении любой непредвиденной ошибки уведомляет о неправильности строки
            catch (Throwable t) {
                inputText.setError(getString(R.string.calculate_exc_errtxt));
            }
        }
        //если в строке присутствуют запрещенные символы, выводится уведомление
        else {
            inputText.setError(getString(R.string.regex_check_err));
        }
    }


}
