package com.example.beacon;

interface BeaconIF {
    public String getUUID();

    public void setUUID(String uuid);

    public Integer getMajor();

    public void setMajor(int major);

    public Integer getMinor();

    public void setMinor(int minor);
}
