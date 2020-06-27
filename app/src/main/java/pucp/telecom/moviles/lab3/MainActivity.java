package pucp.telecom.moviles.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pucp.telecom.moviles.lab3.Fragments.DialogFragmentGuardarLocal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button buttonAbrirAct2 = findViewById(R.id.buttonAbrirAct2);
        Button btnGuardarLcoal = findViewById(R.id.btnGuardarLocal);
        btnGuardarLcoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentGuardarLocal guardarLocalFr = new DialogFragmentGuardarLocal();
                guardarLocalFr.show(getSupportFragmentManager(), "guardarLocal");

                /*
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "fecha");

                DialogFragmentGuardarLocal fr =new DialogFragmentGuardarLocal();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.
                */
            }
        });


    }

    public void guardarLocal(Medicion m) {
        // medición_20062020_0225.json

        SimpleDateFormat parser = new SimpleDateFormat("ddMMyyy_HHmm");
        String fileName = "medicion_" + parser.format(new Date()) + ".json";

        try (FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD());) {
            Gson gson = new Gson();
            String listaMediciones = gson.toJson(m);
            fileWriter.write(m);

            Log.d("infoApp", "Guardado exitoso");
        } catch (IOException e) {
            Log.d("infoApp", "Error al guardar");
            e.printStackTrace();
        }


    }

    public void guardarRemoto(Medicion m) {

    }


}