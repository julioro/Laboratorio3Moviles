package pucp.telecom.moviles.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pucp.telecom.moviles.lab3.Fragments.DialogFragmentGuardarLocal;

public class MainActivity extends AppCompatActivity {
    final String apiKey = "SE NOS OLVIDO LA LLAVE, PERDON MACE2";

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

    public void guardarRemoto(final Medicion m, final double latitud, final double longitud) {
        String URL = "http://ec2-34-234-229-191.compute-1.amazonaws.com:3000/";
        StringRequest listarTrabajosRequest = new StringRequest(StringRequest.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Api-Token", apiKey);
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tiempo", m.getDuracion());
                params.put("latitud", String.valueOf(latitud));
                params.put("longitud", String.valueOf(longitud));
                params.put("mediciones", Arrays.toString(m.getMedidas()));

                return params;
            }
        };


    }


}