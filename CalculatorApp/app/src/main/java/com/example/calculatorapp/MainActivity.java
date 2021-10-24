package com.example.calculatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView showResult;
    private String[] operator = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showResult = findViewById(R.id.show_result);
        operator[0] = "0";

//      zmienne które mają zostać zapisane po obróceniu ekranu
        if (savedInstanceState != null) {
            operator[0] = savedInstanceState.getString("operator");
            String showResult_string = savedInstanceState.getString("show_result");
            showResult.setText(showResult_string);
        }

        showResult.setText("");
    }

//  przywraca zmienne, które zostaną ustracone po obróceniu urządzenia
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            operator[0] = savedInstanceState.getString("operator");
            String showResult_string = savedInstanceState.getString("show_result");
            showResult.setText(showResult_string);
        }
    }

//  sczytuje liczbę z przycisku, dopisuje do wyniku i wyświetla
    public void enterDigit(View view){
        Button b = (Button)view;
        String number = b.getText().toString();

        showResult.append(number);
    }

//  sczytuje operator z przycisku
    public void enterOperator(View view){
        Button b = (Button)view;
        String operatorClicked = b.getText().toString();

//      jeżeli operator został wciśnięty przed jakąkolwiek liczbą,
//      nie wyświtli go ani nie dopisze do wyniku
        if (showResult.getText().toString().equals(""))
            return;
        else{
//          jeżeli jakiś operator wystepuje już w równaniu, nie dopisze kolejnego
            if (operator[0].equals("0")){
                operator[0] = operatorClicked;
                showResult.append(operatorClicked);
            }
            else
                return;
        }
    }

//  podsumowuje wykonywaną operację
    public void equal(View view) {
        String result = showResult.getText().toString();
        float wynik;

//      gdy pierwszy wciśnięty zostanie znak równości
        if (result.equals("")){
            return;
        }

//      w wypadku gdy użytkownik nie poda drugiego argumentu operacji
        if (String.valueOf(result.charAt(result.length() - 1)).equals(operator[0])){
            return;
        }

//      obsługa poszczególnych operatorów
        if (operator[0].equals("+")) {
            String[] separated = result.split("\\+");

            wynik = Float.parseFloat(separated[0]) + Float.parseFloat(separated[1]);
            showResult.setText(Float.toString(wynik));
        }
        if (operator[0].equals("-")) {
//          w wypadku odejmowania od liczby ujemnej, pojawiają się dwa znaki odejmowania
//          zbieram inne części rozdzielonego stringa i do pierwszej części dopisuję minus
            if (String.valueOf(result.charAt(0)).equals(operator[0])) {
                String[] separated = result.split("-");

                wynik = -Float.parseFloat(separated[1]) - Float.parseFloat(separated[2]);
                showResult.setText(Float.toString(wynik));
            }
            else{
                String[] separated = result.split("-");

                wynik = Float.parseFloat(separated[0]) - Float.parseFloat(separated[1]);
                showResult.setText(Float.toString(wynik));
            }
        }
        if (operator[0].equals("*")) {
            String[] separated = result.split("\\*");

            wynik = Float.parseFloat(separated[0])*Float.parseFloat(separated[1]);
            showResult.setText(Float.toString(wynik));
        }
        if (operator[0].equals("/")) {
            String[] separated = result.split("/");

            if (separated[1].equals("0")) {
                Toast toast = Toast.makeText(getApplicationContext(), "Nie dziel przez zero...", Toast.LENGTH_SHORT);
                toast.show();
                operator[0] = "0";
                showResult.setText("");
            }
            else {
                wynik = Float.parseFloat(separated[0]) / Float.parseFloat(separated[1]);
                showResult.setText(Float.toString(wynik));
            }
        }

        operator[0] = "0";
    }

//        ta wersja dodawania i mnożenia nie działa
//        .split nie chciał przyjąć operator[0] który był "+" lub "*" jako regex
//
//    public void equal(View view) {
//        String result = showResult.getText().toString();
//        String[] separated = result.split(operator[0]);
//        float wynik;
//
//        if (operator[0].equals("+")) {
//            wynik = Float.parseFloat(separated[0]) + Float.parseFloat(separated[1]);
//            showResult.setText(Float.toString(wynik));
//        }
//        if (operator[0].equals("-")) {
//            wynik = Float.parseFloat(separated[0]) - Float.parseFloat(separated[1]);
//            showResult.setText(Float.toString(wynik));
//        }
//        if (operator[0].equals("*")) {
//            wynik = Float.parseFloat(separated[0])*Float.parseFloat(separated[1]);
//            showResult.setText(Float.toString(wynik));
//        }
//        if (operator[0].equals("/")) {
//            if (separated[1].equals("0")) {
//                Toast toast = Toast.makeText(getApplicationContext(), "Nie dziel przez zero...", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//            else {
//                wynik = Float.parseFloat(separated[0]) / Float.parseFloat(separated[1]);
//                showResult.setText(Float.toString(wynik));
//            }
//        }

//  czyści pole wyniku
    public void clear(View view){
        if (showResult != null)
            operator[0] = "0";
            showResult.setText("");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("show_result", showResult.getText().toString());
        outState.putString("operator", operator[0]);
    }
}