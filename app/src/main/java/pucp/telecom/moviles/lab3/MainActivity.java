package pucp.telecom.moviles.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationClient;
    Location lugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GPS
        if (checkLocationPermission() == false){
            askLocationPermission();
        }

    }


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