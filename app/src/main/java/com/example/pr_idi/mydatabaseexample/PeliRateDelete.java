package com.example.pr_idi.mydatabaseexample;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class PeliRateDelete extends BaseActivity {
    FilmData filmData;
    Film peli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peliratedelete);

        Intent intent = getIntent();
        String s = intent.getExtras().getString("id");

        int n = s.length();

        filmData = new FilmData(this);
        filmData.open();

        final List<Film> pelis = filmData.getAllFilms();
        for (Film f : pelis){
            String t = f.getTitle();
            int m = t.length();
            String s2 = s;
            if (n > m) s2 = s.substring(0,m);
            if (s2.equalsIgnoreCase(t)) peli = f;
        }

        setTitle(peli.getTitle());
        TextView tv = (TextView) findViewById(R.id.title);
        tv.setText(peli.getTitle());
        tv = (TextView) findViewById(R.id.director);
        tv.setText(peli.getDirector());
        tv = (TextView) findViewById(R.id.year);
        tv.setText(String.valueOf(peli.getYear()));
        tv = (TextView) findViewById(R.id.country);
        tv.setText(peli.getCountry());
        tv = (TextView) findViewById(R.id.prota);
        tv.setText(peli.getProtagonist());
        tv = (TextView) findViewById(R.id.rate);
        tv.setText(String.valueOf(peli.getCritics_rate()));

        RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
        rb.setRating((float)peli.getCritics_rate()/2);

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                peli.setCritics_rate((int)v*2);
                TextView t = (TextView) findViewById(R.id.rate);
                t.setText(String.valueOf(peli.getCritics_rate()));
                filmData.changeRate(peli,(int)v*2);
            }
        });
    }

   public void onClick(View view) {

        switch (view.getId()) {
            case R.id.borrabut:
                filmData.deleteFilm(peli);
                finish();
                break;
        }
    }
}
