package com.example.lab3_20203607;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//esta clase debe registrarse en el manifest su nombre
public class ApplicationThreads extends Application {

    //crear HILOS ES COSTOSO POR LO QUE SE DEBE HACER SOLO AL INICIO DE LA APLICACION
    //UN HILO ES UAN HERRAMIENTA QUE PERMITE A LA APLICACION EJECUTAR TAREAS SIMULTANEAMENTE
    //se estan creando 4 hilos
    public ExecutorService executorService = Executors.newFixedThreadPool(4);
}
