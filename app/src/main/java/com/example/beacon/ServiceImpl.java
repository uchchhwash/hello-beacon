package com.example.beacon;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import java.io.BufferedReader;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ServiceImpl extends IntentService {
    private long seconds;
    //TODO: include needed fields
    String commaDelimiter ;
    String newLineSeperator ;
    String fileHeader ;
    File beaconFileDir;
    File file;
    ArrayList<String> beaconList = new ArrayList<>();
    ArrayList<Beacon> scannedBeaconList = new ArrayList<>();
    boolean serviceStatus = true;
    int indexBeaconList = 0;


    public ServiceImpl() {
        super("ServiceImpl");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onHandleIntent(Intent intent) {
        Log.d("Test", "Service started In the Implementation");

        commaDelimiter = intent.getStringExtra("COMMA_DELIMITER");
        newLineSeperator = intent.getStringExtra("NEW_LINE_SEPARATOR");;
        fileHeader = intent.getStringExtra("FILE_HEADER");;

        //TODO: uncomment this, when implemented setupInputReader();
        setupInputReader();


        //TODO: get the seconds from intent
        seconds = intent.getLongExtra("TimeIntervalInput",3);
        seconds = Long.parseLong(String.valueOf(seconds));

        //how long the service should sleep, in milliseconds
        long millis = seconds * 1000;
        while (serviceStatus) {
            Beacon beacon = scanBeacon();

            if(beacon != null){
                //TODO: add beacons to the List of scanned beacons
                scannedBeaconList.add(beacon);

                //TODO: notification
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel("myChannelId", "My Channel", importance);
                channel.setDescription("Notifications");


                //TODO: intent to AddBeaconsActivity
                Intent addBeaconIntent = new Intent(this, AddBeaconsActivity.class);
                //build intent to switch to activity on click
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

                //adds the back stack for the Intent (not the intent itself)
                stackBuilder.addParentStack(AddBeaconsActivity.class);

                //adds the intent that starts and puts the activity to the top of the stack
                //TODO: uncomment this and insert the above created intent as input
                stackBuilder.addNextIntent(addBeaconIntent);
                addBeaconIntent.putExtra("scannedBeaconsList", scannedBeaconList);

                //PendingIntent waits for an event
                PendingIntent scanResultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                //TODO: create notification manager
                //Notification Builder
                NotificationCompat.Builder mBuilder =
                        // Builder class for devices targeting API 26+ requires a channel ID
                        new NotificationCompat.Builder(this, "myChannelId")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("New Beacon Found")
                                .setContentText(beacon.getUUID())
                                .setContentIntent(scanResultPendingIntent)
                                .setAutoCancel(true)
                                .setPriority(NotificationCompat.PRIORITY_MAX);


                // Register the channel with the notifications manager
                NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.createNotificationChannel(channel);

                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                mNotificationManager.notify(Integer.parseInt("2"), mBuilder.build());

            }

            //TODO: put the service to sleep
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void setupInputReader() {

        //TODO: read the file "Beacon.txt"
        //        dir = getFilesDir();
        beaconFileDir = getFilesDir();
        file = new File(beaconFileDir, "Beacons.txt");
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                beaconList.add(line);
//                text.append(line);
//                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            Log.e("File Read", "Error While File Process!" + e.toString());
        }
        Log.d("data", String.valueOf(beaconList));
        //read the header in advance to exclude it from the output
    }

    private Beacon scanBeacon() {
        //TODO: Read a line and split one row into the beacon components uuid, major and minor
        //create a new beacon and return it
        if(beaconList.size() > 0 ){
            String [] beaconProperties = beaconList.get(0).split(",");
            Beacon objBeacon = new Beacon(beaconProperties[0],Integer.parseInt(beaconProperties[1]),Integer.parseInt(beaconProperties[2]));

            Log.d("Beacon Scan", String.valueOf(objBeacon));
            indexBeaconList++;

            if(indexBeaconList >= beaconList.size()){
                serviceStatus = false;
            }
            return objBeacon;
        }
        else return null;

    }

    public void onDestroy() {
        //TODO: implement this
        serviceStatus = false;
    }
}
