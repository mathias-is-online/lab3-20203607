package com.example.lab3_20203607;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Callback;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab3_20203607.dto.Pelicula;
import com.example.lab3_20203607.services.OMDBService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    EditText editText;
    Button buscarButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //primero checo la coenxion a internets
        verificarConexionInternet();


        editText = findViewById(R.id.editText);
        buscarButton = findViewById(R.id.button3);

        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imdbID = editText.getText().toString().trim();
                if (!imdbID.isEmpty()) {
                    buscarPelicula(imdbID);
                } else {
                    Toast.makeText(MainActivity.this, "Ingrese un ID de IMDb", Toast.LENGTH_SHORT).show();
                }
            }
        });










        Button visualizarButton = findViewById(R.id.button);

        visualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para iniciar ContadorPrimosActivity
                Intent intent = new Intent(MainActivity.this, ContadorPrimosActivity.class);
                // Iniciar la actividad
                startActivity(intent);
            }
        });
    }


    // para verificar la conexión a Internet
    private void verificarConexionInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean tieneInternet = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        if (tieneInternet) {
            mostrarToast("Conexión a Internet establecida");
        } else {
            mostrarToast("No se detectó conexión a Internet");
        }
    }
    // mostrar un Toast con un mensaje
    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }





    private void buscarPelicula(String imdbID) {
        String apiKey = "bf81d461";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OMDBService service = retrofit.create(OMDBService.class);
        Call<Pelicula> call = service.getPelicula(apiKey, imdbID);
        Log.d("URL", call.request().url().toString()); // Imprimir el URL completo antes de la solicitud

        call.enqueue(new Callback<Pelicula>() {
            @Override
            public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Pelicula pelicula = response.body();
                    if (pelicula != null) {
                        //  se verifica si el título de la película no es nulo
                        Intent intent = new Intent(MainActivity.this, PeliculaActivity.class);
                        intent.putExtra("pelicula", pelicula);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Error: ID incorrecto", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Error: ID incorrecto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pelicula> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error al obtener la película", Toast.LENGTH_SHORT).show();
            }
        });
    }







}