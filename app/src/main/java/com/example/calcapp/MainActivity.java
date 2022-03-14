package com.example.calcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView resultField; // текстовое поле для вывода результата
    EditText numberField;   // поле для ввода числа
    TextView operationField;    // текстовое поле для вывода знака операции
    Double operand = null;  // операнд операции
    String lastOperation = "="; // последняя операция
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // получаем все поля по id из activity_main.xml
        resultField = findViewById(R.id.resultField);
        numberField = findViewById(R.id.numberField);
        operationField = findViewById(R.id.operationField);
    }
    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if(operand!=null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }
    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand= savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }
    // обработка нажатия на числовую кнопку
    public void onNumberClick(View view){

        Button button = (Button)view;
        numberField.append(button.getText());

        if((lastOperation.equals("=")||lastOperation.equals("!")) && operand!=null){
            operand = null;
        }
    }
    // обработка нажатия на кнопку операции
    public void onOperationClick(View view){

        Button button = (Button)view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();
        if (op.equals("cos")||op.equals("sin"))
            operand=0.0;
        // если введенно что-нибудь
        if(number.length()>0){
            try{
                if (op.equals("!")) {
                    lastOperation = op;
                    operand=Double.valueOf(number);
                }
                performOperation(Double.valueOf(number), op);
            }catch (NumberFormatException ex){
                numberField.setText("");
            }
        }
        lastOperation = op;
        if (!(lastOperation.equals("=")))
            operationField.setText(lastOperation);
        else
            operationField.setText("");
    }

    public void onCleanClick(View view){
        Button button = (Button)view;
        String op = button.getText().toString();
        lastOperation = op;
        operand = null;
        numberField.setText("");
        resultField.setText("");
        operationField.setText("");
    }

    private void performOperation(Double number, String operation){

        // если операнд ранее не был установлен (при вводе самой первой операции)
        if(operand ==null){
            operand = number;
        }
        else{
            if(lastOperation.equals("=") || lastOperation.equals("!")){
                lastOperation = operation;
            }

            switch(lastOperation){
                case "=":
                    operand = number;
                    break;
                case "/":
                    if(number==0){
                        operand =0.0;
                    }
                    else{
                        operand /=number;
                    }
                    break;
                case "*":
                    operand *=number;
                    break;
                case "+":
                    operand +=number;
                    break;
                case "-":
                    operand -=number;
                    break;
                case "cos":
                    operand = Math.cos(Math.toRadians(number));
                    break;
                case "sin":
                    operand = Math.sin(Math.toRadians(number));
                    break;
                case "!":
                    MathFunctions mathFunctions = new MathFunctions();
                    operand = mathFunctions.fact(number);
                    break;
            }
        }
        MathFunctions mathFunctions = new MathFunctions();
        double res = mathFunctions.roundAvoid(operand,5);
        resultField.setText(Double.toString(res));
        numberField.setText("");
    }
}