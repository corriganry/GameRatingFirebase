package edu.lewisu.cs.corriganry.gameratingfirebase;

public class GameRating {

    private String uid;
    private String gameName;
    private String developerName;
    private int rating;

    public GameRating(String uid, String gameName, String developerName, int rating) {
        this.uid = uid;
        this.gameName = gameName;
        this.developerName = developerName;
        this.rating = rating;
    }

    public GameRating(String uid) {
        this.uid = uid;
    }
    public GameRating(){
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
