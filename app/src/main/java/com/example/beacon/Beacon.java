package com.example.beacon;

import java.io.Serializable;

public class Beacon implements Serializable, BeaconIF {
    private String uuid;
    private int major;
    private int minor;

    public Beacon(String uuid, int major, int minor) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public Integer getMajor() {
        return major;
    }

    @Override
    public void setMajor(int major) {
        this.major = major;
    }

    @Override
    public Integer getMinor() {
        return minor;
    }

    @Override
    public void setMinor(int minor) {
        this.minor = minor;
    }

    @Override
    public String toString() {
        return uuid + " " + major + " " + minor;
    }

    //Use this to compare beacons
    @Override
    public boolean equals(Object objectToCompare) {
        if (objectToCompare instanceof Beacon) {
            Beacon beaconToCompare = (Beacon) objectToCompare;
            return this.uuid.equals(beaconToCompare.uuid) && this.major == beaconToCompare.major && this.minor == beaconToCompare.minor;
        } else {
            return false;
        }
    }

    //The hashcode() method provides an integer sometimes based loosely on the memory adress of an object.
    //You have to override the hash code method to ensure that the hashcode of objects that are not equal, isn't the same, too.
    //Return something that might be unique (e.g. object values)
    @Override
    public int hashCode(){
        return major + minor;
    }

}
