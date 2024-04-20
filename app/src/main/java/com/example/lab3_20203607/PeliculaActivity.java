package com.example.lab3_20203607;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3_20203607.dto.Pelicula;

public class PeliculaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula);

        // Obtener el objeto Pelicula pasado como extra en el Intent
        Pelicula pelicula = (Pelicula) getIntent().getSerializableExtra("pelicula");

        // Mostrar los atributos de la película en los TextViews
        if (pelicula != null) {
            TextView tituloTextView = findViewById(R.id.movietitle);
            tituloTextView.setText(pelicula.getTitle());

            TextView plotTextView = findViewById(R.id.textView8);
            plotTextView.setText(pelicula.getPlot());


            TextView imdbRatingTextView = findViewById(R.id.internetdb);
            imdbRatingTextView.setText("IMDb Rating: " + pelicula.getImdbRating());

            TextView rottentomatoesTextView = findViewById(R.id.rottentomatoes);
            // Aquí puedes mostrar otros atributos de la película si lo deseas
        }
    }
}
