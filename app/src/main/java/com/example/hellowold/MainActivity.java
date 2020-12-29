package com.example.hellowold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCelsius, editTextFahrenheit;
    private Button buttonSave;
    private ListView listViewTemperature;
    private List<String> stringList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //affichage du menu

        //chargement du menu
        getMenuInflater().inflate(R.menu.menu_default, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /*
        if(item.getItemId() == R.id.menuDelete {

        }
         */

        switch (item.getItemId()) {
            case R.id.menuDelete:
                //effacer les deux champs Celsius / Fahrenheit
                editTextCelsius.setText("");
                editTextFahrenheit.setText(null);
                editTextFahrenheit.getText().clear();

                //effacer les éléments de la listeViewTemperature
                stringList.clear();
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCelsius = findViewById(R.id.editTextCelsius);
        editTextFahrenheit = findViewById(R.id.editTextFahrenheit);
        buttonSave = findViewById(R.id.buttonSave);
        listViewTemperature = findViewById(R.id.listViewTemperature);

        editTextCelsius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String valeur = s.toString(); //récupération de la valeur saisie
                Log.i("test", valeur);

                if (valeur.length() > 0 && isNumeric(valeur) && editTextCelsius.hasFocus()) {
                    String resultat = TemperatureConverter.fahrenheitFromCelcius(Double.parseDouble(valeur));
                    editTextFahrenheit.setText(resultat);
                }
            }
        });

        editTextFahrenheit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String valeur = s.toString(); //récupération de la valeur saisie
                Log.i("test", valeur);

                if (valeur.length() > 0 && isNumeric(valeur) && editTextFahrenheit.hasFocus()) {
                    String resultat = TemperatureConverter.celsiusFromFahrenheit(Double.parseDouble(valeur));
                    editTextCelsius.setText(resultat);
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test", "clique sur le bouton");

                String celcius = editTextCelsius.getText().toString();
                String fahrenheit = editTextFahrenheit.getText().toString();
                stringList.add(celcius+"°C est égal à "+fahrenheit+"°F");
                adapter.notifyDataSetChanged();// demande de rafraichissement
            }
        });

        adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                stringList
        );
        listViewTemperature.setAdapter(adapter);
        listViewTemperature.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                stringList.remove(position);
                adapter.notifyDataSetChanged(); //demande de rafraichissement
                return false;
            }
        });
    }

    public boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}