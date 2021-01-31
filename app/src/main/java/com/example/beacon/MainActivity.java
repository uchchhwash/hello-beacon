package com.example.beacon;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.Serializable;

import android.app.ActionBar;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    //Delimiter used in file
    public static final String COMMA_DELIMITER = ",";

    //new line
    private static final String NEW_LINE_SEPARATOR = "\n";

    //file header
    private static final String FILE_HEADER = "UUID,MAJOR,MINOR";

    //TODO: add needed fields
    TextView inputInterval;
    Button btnStartScan, btnStopScan;

    ArrayList<Beacon> scannedBeaconsList = new ArrayList<Beacon>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: get intent
        inputInterval = (TextView)findViewById(R.id.inputInterval);

        btnStartScan = (Button)findViewById(R.id.btnStartScan);
        btnStopScan = (Button)findViewById(R.id.btnStopScan);

        btnStartScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputInterval = (EditText) findViewById(R.id.inputInterval);
                if(!inputInterval.getText().toString().isEmpty() && inputInterval.getText().toString().matches("^[0-9]+$")){
                    startServiceByButtonClick(v);
                }
                else{
                    Toast.makeText(MainActivity.this, "Provide a Valid Input!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnStopScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopServiceByButtonClick(v);
            }
        });

    }

    @Override
    protected void onResume() {
        //TODO: get intent and add beacons to List
        super.onResume();
        Intent intentBeacon = getIntent();
        if(intentBeacon.getSerializableExtra("scannedBeaconsList") != null){
            scannedBeaconsList = (ArrayList<Beacon>) intentBeacon.getSerializableExtra("scannedBeaconsList");
            showBeaconsInLinearLayout();
        }
    }

    private void showBeaconsInLinearLayout() {
        //TODO: implement this
        LinearLayout layout = (LinearLayout) findViewById(R.id.beaconDataLayout);
        layout.removeAllViews();

        Integer index = 0;
        while(index < scannedBeaconsList.size())
        {
            LinearLayout layoutLinear = new LinearLayout(this);
            layoutLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutLinear.setOrientation(LinearLayout.HORIZONTAL);
            layoutLinear.setWeightSum(6f);
            layoutLinear.setPadding(0,10,0,0);
            //layoutLinear.setBackground(getResources().getDrawable(R.drawable.textview_underline,null));


            TextView txtUuid = new TextView(this);
            txtUuid.setLayoutParams(new LinearLayout.LayoutParams( 0, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            txtUuid.setText(scannedBeaconsList.get(index).getUUID());
            //txtUuid.setPadding(0,0,0,0);
            txtUuid.setGravity(Gravity.CENTER);

            TextView txtMajor = new TextView(this);
            txtMajor.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            txtMajor.setText(Integer.toString(scannedBeaconsList.get(index).getMajor()));
            txtMajor.setPadding(100,0,50,0);
            txtUuid.setGravity(Gravity.CENTER);

            TextView txtMinor = new TextView(this);
            txtMinor.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            txtMinor.setText(Integer.toString(scannedBeaconsList.get(index).getMinor()));
            txtMinor.setPadding(0,0,50,0);
            txtUuid.setGravity(Gravity.CENTER);

            layoutLinear.addView(txtUuid);
            layoutLinear.addView(txtMajor);
            layoutLinear.addView(txtMinor);

            if(index + 1 >= scannedBeaconsList.size()){
                layout.addView(layoutLinear);
                break;
            }

            TextView txtUuid2 = new TextView(this);
            txtUuid2.setLayoutParams(new LinearLayout.LayoutParams( 0, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            txtUuid2.setText(scannedBeaconsList.get(index + 1).getUUID());
            txtUuid2.setPadding(0,0,0,0);
            txtUuid2.setGravity(Gravity.CENTER);

            TextView txtMajor2 = new TextView(this);
            txtMajor2.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            txtMajor2.setText(Integer.toString(scannedBeaconsList.get(index + 1).getMajor()));
            txtMajor2.setPadding(50,0,50,0);
            txtMajor2.setGravity(Gravity.CENTER);

            TextView txtMinor2 = new TextView(this);
            txtMinor2.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
            txtMinor2.setText(Integer.toString(scannedBeaconsList.get(index + 1).getMinor()));
            txtMinor2.setPadding(50,0,0,0);
            txtMinor2.setGravity(Gravity.CENTER);

            layoutLinear.addView(txtUuid2);
            layoutLinear.addView(txtMajor2);
            layoutLinear.addView(txtMinor2);

            index += 2;
            layout.addView(layoutLinear);
        }
    }

    //Do not change this!
    protected void writeBeaconSimulationFile(){

        //Create new beacon objects
        Beacon beacon1 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,1);
        Beacon beacon2 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,2);
        Beacon beacon3 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,3);
        Beacon beacon4 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,4);
        Beacon beacon5 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,5);
        Beacon beacon6 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,8);
        Beacon beacon7 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,9);
        Beacon beacon8 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",4,10);
        Beacon beacon9 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,10);
        Beacon beacon10 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,9);
        Beacon beacon11 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,8);
        Beacon beacon12 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,5);
        Beacon beacon13 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,4);
        Beacon beacon14 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,3);
        Beacon beacon15 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,2);
        Beacon beacon16 = new Beacon("EBBD7150-D911-11E4-8830-0800200C9A66",3,1);

        //Create a new list of beacons objects
        ArrayList<Beacon> beacons = new ArrayList<Beacon>();
        beacons.add(beacon1);
        beacons.add(beacon2);
        beacons.add(beacon3); 
        beacons.add(beacon4);
        beacons.add(beacon5);
        beacons.add(beacon6);
        beacons.add(beacon7);
        beacons.add(beacon8);
        beacons.add(beacon9);
        beacons.add(beacon10);
        beacons.add(beacon11);
        beacons.add(beacon12);
        beacons.add(beacon13);
        beacons.add(beacon14);
        beacons.add(beacon15);
        beacons.add(beacon16);
        beacons.add(beacon15);
        beacons.add(beacon14);
        beacons.add(beacon3);
        beacons.add(beacon2);
        beacons.add(beacon1);


        try{
            FileOutputStream testFile = openFileOutput("Beacons.txt", Context.MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(testFile);
            //outputStreamWriter.append(FILE_HEADER.toString());
            //outputStreamWriter.append(NEW_LINE_SEPARATOR);

            for (Beacon beacon : beacons) {
                outputStreamWriter.append(String.valueOf(beacon.getUUID()));
                outputStreamWriter.append(COMMA_DELIMITER);
                outputStreamWriter.append(String.valueOf(beacon.getMajor()));
                outputStreamWriter.append(COMMA_DELIMITER);
                outputStreamWriter.append(String.valueOf(beacon.getMinor()));
                outputStreamWriter.append(NEW_LINE_SEPARATOR);
            }

            outputStreamWriter.close();
        }
        catch (IOException ex){
            Log.d("Message", ex.getMessage());
        }
    }

    //TODO: button starts the service
    public void startServiceByButtonClick(View v) {
        //TODO: Get user input
         long input = Long.parseLong(inputInterval.getText().toString()) ;

        Log.d("Test", "Service Started");
        Log.d("Interval Input Value", String.valueOf(input));
        //Do not change this!
        File dir = getFilesDir();
        File file = new File(dir, "Beacons.txt");
        boolean deleted = file.delete();

        //this method writes the file containing simulated beacon data
        writeBeaconSimulationFile();

        //TODO: Service is started via intent
        Intent serviceIntent = new Intent(MainActivity.this,ServiceImpl.class);
        serviceIntent.putExtra( "COMMA_DELIMITER", COMMA_DELIMITER );
        serviceIntent.putExtra("NEW_LINE_SEPARATOR",NEW_LINE_SEPARATOR);
        serviceIntent.putExtra( "FILE_HEADER", FILE_HEADER );
        serviceIntent.putExtra( "TimeIntervalInput", input );

        startService(serviceIntent);
        Toast.makeText(MainActivity.this, "Service Started Successfully", Toast.LENGTH_SHORT).show();

    }

    //TODO: stop service
    public void stopServiceByButtonClick(View v) {
        //implement this
        stopService(new Intent(this, ServiceImpl.class));
        Toast.makeText(MainActivity.this, "Service Stopped Successfully", Toast.LENGTH_SHORT).show();
        Log.d("Service","Service Stopped Completely");

    }
}