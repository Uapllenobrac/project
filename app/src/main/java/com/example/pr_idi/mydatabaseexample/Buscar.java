package com.example.pr_idi.mydatabaseexample;


import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Buscar extends BaseActivity {
    FilmData filmData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar);
        setTitle("Buscar");

        filmData = new FilmData(this);
        filmData.open();

        SearchView sv = (SearchView) findViewById(R.id.searchview);
        sv.setQueryHint("Buscar aquí...");
        sv.setIconifiedByDefault(false);

        //Listener que esta esperan a que l'usuari escrigui algo
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!callSearch(query)){

                    CharSequence text = "No s'han trobat pel·lícules on hi participi aquest actor o que tinguin aquest títol";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(),text,duration);
                    toast.show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                callSearch(query);
                return true;
            }
            //Funcio que fa la busqueda

            public boolean callSearch(String query) {

                int s = query.length();

                List<Film> values = filmData.getAllFilms();
                List<Film> res = new LinkedList<>();
                for (Film f : values) {
                    if (s > 0){
                        String t = f.getProtagonist();
                    String n = f.getTitle();
                    if (s < t.length()) t = t.substring(0, s);
                    if (s < n.length()) n = n.substring(0, s);
                    if (t.equalsIgnoreCase(query) || n.equalsIgnoreCase(query)) res.add(f);
                    }
                }
                Collections.sort(res, new Comparator<Film>() {
                    @Override
                    public int compare(Film f1, Film f2) {
                        return f1.getTitle().compareTo(f2.getTitle());
                    }
                });

                ArrayList<HashMap<String, String>> data;

                ListView lv = (ListView)findViewById(R.id.listbusca);
                List<String> t = new LinkedList<>();
                List<String> p = new LinkedList<>();
                data = new ArrayList<>();

                for (Film f : res){
                    t.add(f.getTitle());
                    p.add(f.getProtagonist());
                }


                for(int i=0;i<res.size();i++){
                    HashMap<String,String> datum = new HashMap<>();
                    datum.put("a",t.get(i));
                    datum.put("b", p.get(i));
                    data.add(datum);
                }
                SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), data, android.R.layout.simple_list_item_2, new String[] {"a", "b"}, new int[] {android.R.id.text1, android.R.id.text2});
                lv.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                if (!res.isEmpty()) return true;
                else return false;
            }
        });
        //Quan l'usuari selecciona una peli es salta a l activity de ratedelete
        final ListView lv = (ListView) findViewById(R.id.listbusca);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = adapterView.getAdapter().getItem(i).toString();
                s = s.substring(3);
                Intent intent = new Intent(getBaseContext(),PeliRateDelete.class);
                intent.putExtra("id",s);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        filmData.open();
        SearchView sv = (SearchView) findViewById(R.id.searchview);
        String query = sv.getQuery().toString();
        int s = query.length();

        List<Film> values = filmData.getAllFilms();
        List<Film> res = new LinkedList<>();
        for (Film f : values) {
            if (s > 0){
                String t = f.getProtagonist();
                String n = f.getTitle();
                if (s < t.length()) t = t.substring(0, s);
                if (s < n.length()) n = n.substring(0, s);
                if (t.equalsIgnoreCase(query) || n.equalsIgnoreCase(query)) res.add(f);
            }
        }
        Collections.sort(res, new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                return f1.getTitle().compareTo(f2.getTitle());
            }
        });
        ArrayList<HashMap<String, String>> data;

        ListView lv = (ListView)findViewById(R.id.listbusca);
        List<String> t = new LinkedList<>();
        List<String> p = new LinkedList<>();
        data = new ArrayList<>();

        for (Film f : res){
            t.add(f.getTitle());
            p.add(f.getProtagonist());
        }


        for(int i=0;i<res.size();i++){
            HashMap<String,String> datum = new HashMap<>();
            datum.put("a",t.get(i));
            datum.put("b", p.get(i));
            data.add(datum);
        }
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), data, android.R.layout.simple_list_item_2, new String[] {"a", "b"}, new int[] {android.R.id.text1, android.R.id.text2});
        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        super.onResume();
    }

}
