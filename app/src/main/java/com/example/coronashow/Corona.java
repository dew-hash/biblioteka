package com.example.coronashow;

public class Corona {
    private String country;
    private String lastUpdate;
    private String keyID;
    private int confirmed;
    private int deaths;

    public String getCountry() {
        return country;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getKeyID() {
        return keyID;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public Corona(String country, String lastUpdate, String keyID, int confirmed, int deaths) {
        this.country = country;
        this.lastUpdate = lastUpdate;
        this.keyID = keyID;
        this.confirmed = confirmed;
        this.deaths = deaths;
    }

    @Override
    public String toString() {
        return "Corona{" +
                "country='" + country + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", keyID='" + keyID + '\'' +
                ", confirmed=" + confirmed +
                ", deaths=" + deaths +
                '}';
    }
}
