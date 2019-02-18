package com.test.backroundpostion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.test.backroundpostion.Service.TrackerService;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST = 1;
    Button stop ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stop= findViewById(R.id.stopservice);
        chekGpsPermisson();
    }
  public  void chekGpsPermisson (){
      // Check GPS is enabled
      LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
      if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
          Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show();
          finish();
      }
      // Check location permission is granted - if it is, start
      // the service, otherwise request the permission
      int permission = ContextCompat.checkSelfPermission(this,
              Manifest.permission.ACCESS_FINE_LOCATION);
      if (permission == PackageManager.PERMISSION_GRANTED) {
          startTrackerService();
      } else {
          ActivityCompat.requestPermissions(this,
                  new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                  PERMISSIONS_REQUEST);
      }
    }
    private void startTrackerService() {
        startService(new Intent(this, TrackerService.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start the service when the permission is granted
            startTrackerService();
        } else {
            finish();
        }
    }

    public void stopserviceBackground(View view) {
        stopService(new Intent(MainActivity.this , TrackerService.class));
    }
}
