package pucp.telecom.moviles.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import pucp.telecom.moviles.lab3.otro.MedicionViewModel;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private Location lugar;
    private ArrayList<Double> mediciones = new ArrayList<Double>();
    private double[] mRuido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GPS
        if (checkLocationPermission() == false){
            askLocationPermission();
        }

        //THREAD
        MedicionViewModel medicionViewModel = new ViewModelProvider(MainActivity.this).get(MedicionViewModel.class);
        medicionViewModel.getNoise().observe(MainActivity.this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                mediciones.add(aDouble);
            }
        });

    }

    //GRABACION
    private void detenerGrabacion(){
        MedicionViewModel medicionViewModel = new ViewModelProvider(MainActivity.this).get(MedicionViewModel.class);
        medicionViewModel.getThread().interrupt();

    }


    //UBICACION
    private boolean checkLocationPermission(){

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }

    }

    private void askLocationPermission() {
        ActivityCompat.requestPermissions(MainActivity.this
                , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
    }

    private void getLocation(){

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        if (checkLocationPermission()){
            fusedLocationClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {
                        lugar = location;
                    }
                }
            });
        }

    }


}