package com.example.lab3_20203607;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3_20203607.dto.Pelicula;
import com.example.lab3_20203607.dto.Rating;

public class PeliculaActivity extends AppCompatActivity {
    private CheckBox checkBox;
    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula);


        mostrarToast("Estás en la vista de la película");
        checkBox = findViewById(R.id.checkBox);
        backButton = findViewById(R.id.button2);
        backButton.setVisibility(View.INVISIBLE);

        // Configurar un Listener para el CheckBox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Mostrar u ocultar el botón "Regresar" según el estado del CheckBox
                if (isChecked) {
                    backButton.setVisibility(View.VISIBLE);
                } else {
                    backButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Configurar un Listener para el botón "Regresar"
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir al MainActivity
                Intent intent = new Intent(PeliculaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });





        // Obtener el objeto Pelicula pasado como extra en el Intent
        Pelicula pelicula = (Pelicula) getIntent().getSerializableExtra("pelicula");

        // Mostrar los atributos de la película en los TextViews
        if (pelicula != null) {
            TextView tituloTextView = findViewById(R.id.movietitle);
            tituloTextView.setText(pelicula.getTitle());

            TextView directorTextView = findViewById(R.id.director);
            directorTextView.setText(pelicula.getDirector());

            TextView fechaestrenoTextView = findViewById(R.id.fecha);
            fechaestrenoTextView.setText(pelicula.getReleased());

            TextView generosTextView = findViewById(R.id.generos);
            generosTextView.setText(pelicula.getGenre());

            TextView escritoresTextView = findViewById(R.id.escritores);
            escritoresTextView.setText(pelicula.getWriter());

            TextView plotTextView = findViewById(R.id.textView7);
            plotTextView.setText(pelicula.getPlot());

            TextView imdbRatingTextView = findViewById(R.id.internetdb);
            imdbRatingTextView.setText(pelicula.getImdbRating());



            String rottenTomatoesRating = "";
            if (pelicula.getRatings() != null && !pelicula.getRatings().isEmpty()) {
                for (Rating rating : pelicula.getRatings()) {
                    if ("Rotten Tomatoes".equals(rating.getSource())) {
                        rottenTomatoesRating = rating.getValue();
                        break;
                    }
                }
            }

            TextView rottentomatoesTextView = findViewById(R.id.rottentomatoes);
            rottentomatoesTextView.setText(rottenTomatoesRating);


                       // Aquí puedes mostrar otros atributos de la película si lo deseas
        }
    }

    // Método para mostrar un Toast con un mensaje
    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
