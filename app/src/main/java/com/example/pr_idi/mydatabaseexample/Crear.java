package com.example.pr_idi.mydatabaseexample;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Crear extends BaseActivity {

    FilmData filmData;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear);
        setTitle("Crear");

        filmData = new FilmData(this);
        filmData.open();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.crear:
                text = (EditText) findViewById(R.id.titol);
                String title = text.getText().toString();
                text = (EditText) findViewById(R.id.director);
                String director = text.getText().toString();
                text = (EditText) findViewById(R.id.pais);
                String country = text.getText().toString();
                text = (EditText) findViewById(R.id.any);
                String yearText=text.getText().toString();
                int year= Integer.parseInt(yearText);
                text = (EditText) findViewById(R.id.protagonista);
                String prota = text.getText().toString();
                text = (EditText) findViewById(R.id.nota);
                String noteText=text.getText().toString();
                int note= Integer.parseInt(noteText);
                if (note<0 || note>10) {
                    CharSequence textNote = "La puntuació ha d'estar entre 0 i 10";
                    int duration = Toast.LENGTH_SHORT;
                    Toast t = Toast.makeText(getApplicationContext(),textNote,duration);
                    t.show();
                }
                else {
                    filmData.createFilm(title, director, country, year, prota, note);
                    CharSequence text = "S'ha creat una nova pel·lícula";
                    int duration = Toast.LENGTH_SHORT;
                    Toast t = Toast.makeText(getApplicationContext(), text, duration);
                    t.show();
                    startActivity(new Intent(getApplicationContext(), Inici.class));
                }
                break;
            case R.id.cancelar_crear:
                startActivity(new Intent(getApplicationContext(),Inici.class));
                break;
        }

    }

}
