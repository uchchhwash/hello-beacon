package com.example.beacon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddBeaconsActivity extends AppCompatActivity {
    //Created The Beacons List  to get the beacons from the intent
    ArrayList<Beacon> beaconsList = new ArrayList<Beacon>();
    @Override
    protected void onCreate(Bundle instanceState) {
        super.onCreate(instanceState);
        // To Set the view to activity_beacon
        setContentView(R.layout.activity_beacon);
        beaconsList = (ArrayList<Beacon>) getIntent().getSerializableExtra("scannedBeaconsList");
        loadBeacons();
    }

    public void loadBeacons(){
        LinearLayout beaconsLayout = findViewById(R.id.beaconListLayout);
        for(int i = 0; i < beaconsList.size(); i++){

            LinearLayout layoutLinear = new LinearLayout(this);
            layoutLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutLinear.setOrientation(LinearLayout.HORIZONTAL);
            layoutLinear.setWeightSum(3f);


            TextView txtUuid = new TextView(this);
            txtUuid.setLayoutParams(new LinearLayout.LayoutParams( 0, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            txtUuid.setPadding(0,15,0,15);
            txtUuid.setText(beaconsList.get(i).getUUID());
            txtUuid.setGravity(Gravity.CENTER);
            //txtUuid.setTextColor(Color.BLACK);

            TextView txtMajor = new TextView(this);
            txtMajor.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            txtMajor.setPadding(25,15,0,15);
            txtMajor.setText(Integer.toString(beaconsList.get(i).getMajor()));
            txtMajor.setGravity(Gravity.CENTER);
            //txtMajor.setTextColor(Color.BLACK);

            TextView txtMinor = new TextView(this);
            txtMinor.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            txtMinor.setPadding(20,15,0,15);
            txtMinor.setText(Integer.toString(beaconsList.get(i).getMinor()));
            txtMinor.setGravity(Gravity.CENTER);
            //txtMinor.setTextColor(Color.BLACK);

            layoutLinear.addView(txtUuid);
            layoutLinear.addView(txtMajor);
            layoutLinear.addView(txtMinor);
            beaconsLayout.addView(layoutLinear);
        }
    }

    public void addBeacons(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("scannedBeaconsList", beaconsList);
        startActivity(intent);
    }
}
