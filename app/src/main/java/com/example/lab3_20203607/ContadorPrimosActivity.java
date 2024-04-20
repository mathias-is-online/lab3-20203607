package com.example.lab3_20203607;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Callback;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3_20203607.dto.Primo;
import com.example.lab3_20203607.services.PrimeNumberAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContadorPrimosActivity extends AppCompatActivity {


    private TextView primoTextView;
    private EditText ordenEditText;
    private Button buscarButton;



    private List<Primo> listaPrimos = new ArrayList<>();

    private boolean ordenAscendente = true;
    private boolean paused = false; // Variable para controlar el estado de pausa
    private int currentIndex = 0; // Índice actual en la lista de primos
    private ExecutorService executorService;

    private PrimeNumberAPI primeNumberAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contador);

        mostrarToast("Estás en la vista de contador de primos");

        EditText ordenPrimoEditText = findViewById(R.id.ordenprimo);

        Button buttonOrden = findViewById(R.id.buttonorden);
// Listener para el botón de buscar orden
        buttonOrden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ordenString = ordenPrimoEditText.getText().toString();
                if (!ordenString.isEmpty()) {
                    int orden = Integer.parseInt(ordenString);
                    if (orden >= 1 && orden <= listaPrimos.size()) {
                        // Mostrar el primo correspondiente al orden ingresado
                        mostrarPrimoPorOrden(orden);
                    } else {
                        Toast.makeText(ContadorPrimosActivity.this, "El orden debe estar entre 1 y " + listaPrimos.size(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ContadorPrimosActivity.this, "Ingrese un número de orden", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button buttonDescender = findViewById(R.id.buttondescender);
        Button buttonPausa = findViewById(R.id.buttonpausa);

// listener para el botón de descender/ascender
        buttonDescender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descenderAscenderNumeros();
            }
        });

        // listener para el botón de pausa/reinicio
        buttonPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!paused) {
                    pausarActualizacion();
                    buttonPausa.setText("Reiniciar");
                    buttonDescender.setVisibility(View.INVISIBLE);
                } else {
                    reanudarActualizacion();
                    buttonPausa.setText("Pausar");
                    buttonDescender.setVisibility(View.VISIBLE);
                }
            }
        });

        // vistas
        primoTextView = findViewById(R.id.primo);
        buscarButton = findViewById(R.id.button3);


        // creo el retrofit para iniciar el consum odel api
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://prime-number-api.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        // creo de la interfaz pimeNumberAPI
        primeNumberAPI = retrofit.create(PrimeNumberAPI.class);

//inicia el hilo



        buscarPrimo();


    }



    private void mostrarPrimoPorOrden(int orden) {
        Primo primo = listaPrimos.get(orden - 1); // Restamos 1 porque los índices comienzan desde 0
        primoTextView.setText(primo.getNumber());
        paused = true; // Pausar la actualización del contador
    }





    private void buscarPrimo() {
        if (true) {
            int orden = 1;
            // Verificar si el orden está dentro del rango
            if (orden >= 1 && orden <= 999) {
                // Realizar la solicitud HTTP para obtener el número primo
                Call<List<Primo>> call = primeNumberAPI.getPrimeNumbers(999, 1);
                call.enqueue(new Callback<List<Primo>>() {
                    @Override
                    public void onResponse(Call<List<Primo>> call, Response<List<Primo>> response) {
                        if (response.isSuccessful()) {
                            listaPrimos = response.body();
                            // Mostrar los números primos en el TextView
                            mostrarPrimos();
                        } else {
                            // Mostrar un mensaje de error si la solicitud no fue exitosa
                            Toast.makeText(ContadorPrimosActivity.this, "Error al obtener el número primo", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Primo>> call, Throwable t) {
                        // Mostrar un mensaje de error si la solicitud falla
                        Toast.makeText(ContadorPrimosActivity.this, "Error al realizar la solicitud", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                // Mostrar un mensaje de error si el orden está fuera del rango
                Toast.makeText(this, "El orden debe estar entre 1 y 999", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Mostrar un mensaje de error si el campo de orden está vacío
            Toast.makeText(this, "Ingrese un número de orden", Toast.LENGTH_SHORT).show();
        }
    }
    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void mostrarPrimos() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!paused && currentIndex < listaPrimos.size()) {
                    Primo primo = listaPrimos.get(currentIndex);
                    primoTextView.setText(primo.getNumber());
                    if (ordenAscendente) {
                        currentIndex++; // Si es ascendente, incrementa el índice
                    } else {
                        currentIndex--; // Si es descendente, decrementa el índice
                    }
                    // Ejecutar de nuevo después de cierto tiempo (por ejemplo, 1 segundo)
                    new Handler().postDelayed(this, 1000); // Intervalo de 1 segundo
                } else {
                    // Se ha mostrado todos los primos, puedes hacer algo aquí si es necesario
                }
            }
        }, 0); // Comenzar inmediatamente
    }

    private void pausarActualizacion() {
        paused = true;
    }

    private void reanudarActualizacion() {
        paused = false;
        // Llamar a buscarPrimo para continuar mostrando los primos
        mostrarPrimos();
    }

    private void descenderAscenderNumeros() {
        if (!paused) {
            if (ordenAscendente) {
                // Descendiendo números
                if (currentIndex > 0) {
                    currentIndex--;
                } else {
                    currentIndex = listaPrimos.size() - 1; // Ir al último primo
                }
            } else {
                // Ascendiendo números
                if (currentIndex < listaPrimos.size() - 1) {
                    currentIndex++;
                } else {
                    currentIndex = 0; // Ir al primer primo
                }
            }
        }

        Primo primo = listaPrimos.get(currentIndex);
        primoTextView.setText(primo.getNumber());

        // Cambiar el estado de ordenAscendente y el texto del botón
        if (ordenAscendente) {
            ordenAscendente = false;
            ((Button) findViewById(R.id.buttondescender)).setText("Ascender");
        } else {
            ordenAscendente = true;
            ((Button) findViewById(R.id.buttondescender)).setText("Descender");
        }

        // Validar si el índice ha alcanzado el mínimo o máximo orden
        if ((ordenAscendente && currentIndex == 0) || (!ordenAscendente && currentIndex == listaPrimos.size() - 1)) {
            // Detener la actualización de primos
            paused = true;
        }
    }




}
