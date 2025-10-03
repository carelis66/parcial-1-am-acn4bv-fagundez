package com.example.misquehaceresapp;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private LinearLayout contenedorPendientes, contenedorTerminadas, contenedorPrioridad;
    private EditText entradaQuehacer;
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //agregar vistas
        contenedorPendientes = findViewById(R.id.contenedorPendientes);
        contenedorTerminadas = findViewById(R.id.contenedorTerminadas);
        contenedorPrioridad = findViewById(R.id.contenedorPrioridad);
        entradaQuehacer = findViewById(R.id.entradaQuehacer);
        btnAgregar = findViewById(R.id.btnAgregar);

        //botón Agregar
        btnAgregar.setOnClickListener(v -> {
            String texto = entradaQuehacer.getText().toString().trim();
            if (!texto.isEmpty()) {
                agregarTarea(texto, contenedorPendientes, R.color.tareaNormal);
                entradaQuehacer.setText(""); // limpiar campo
            } else {
                Toast.makeText(this, "Escribe un quehacer primero", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Metodo para agregar tarea
    private void agregarTarea(String texto, LinearLayout contenedor, int color) {
        TextView nuevaTarea = new TextView(this);
        nuevaTarea.setText("• " + texto);
        nuevaTarea.setTextSize(16f);
        nuevaTarea.setPadding(8, 8, 8, 8);
        nuevaTarea.setTextColor(ContextCompat.getColor(this, color));

        // Ahora se abre el menú con un click normal
        nuevaTarea.setOnClickListener(v -> {
            mostrarMenu(v, nuevaTarea);
        });

        contenedor.addView(nuevaTarea);
    }

    // Mostrar menú contextual
    private void mostrarMenu(View vista, TextView tarea) {
        PopupMenu popup = new PopupMenu(this, vista);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> manejarAccionMenu(item, tarea));
        popup.show();
    }

    // Manejar acciones del menú contextual
    private boolean manejarAccionMenu(MenuItem item, TextView tarea) {
        int id = item.getItemId();

        if (id == R.id.action_marcar_terminada) {
            moverTarea(tarea, contenedorTerminadas, R.color.tareaTerminada);
            return true;

        } else if (id == R.id.action_marcar_prioridad) {
            moverTarea(tarea, contenedorPrioridad, R.color.tareaImportante);
            return true;

        } else if (id == R.id.action_marcar_pendiente) {
            moverTarea(tarea, contenedorPendientes, R.color.tareaNormal);
            return true;

        } else if (id == R.id.action_eliminar) {
            ((LinearLayout) tarea.getParent()).removeView(tarea);
            Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    // Mover tarea
    private void moverTarea(TextView tarea, LinearLayout nuevoContenedor, int color) {
        ((LinearLayout) tarea.getParent()).removeView(tarea);
        tarea.setTextColor(ContextCompat.getColor(this, color));
        nuevoContenedor.addView(tarea);
    }
}










