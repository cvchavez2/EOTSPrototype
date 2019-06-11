package edu.utep.cs.cs4330.eotsprototype;

public class Report {

    private long time;
    private String crimetype;
    private double longitude;
    private double latitude;
    private String comment;
    private boolean gender;
    private int age;

    public Report(){
        this.time = System.currentTimeMillis();
    }

    public Report(String crimeType){
        this.time = System.currentTimeMillis();
        this.crimetype = crimeType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCrimetype() {
        return crimetype;
    }

    public void setCrimetype(String crimetype) {
        this.crimetype = crimetype;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
