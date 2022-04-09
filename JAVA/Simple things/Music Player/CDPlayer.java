import java.util.ArrayList;

public class CDPlayer extends MusicPlayer {

    private int deviceID;
    private int thisTrack;

    /**
     * Constructor for CD-Player
     */
    public CDPlayer(int id) {
        this.deviceID=id;

    }

    /**
     * Over-ride Method: turnOn
     */
    public void turnOn() {
        super.turnOn();
    }

    /**
     * Over-ride Method: turnOff
     */
    public void turnOff() {
        super.turnOff();
        this.thisTrack=0;
    }

    /**
     * Method to play next track in the playlist by
     * printing it to stdout and changing current
     */
    public void nextTrack() {
        int next=this.thisTrack+1;
        System.out.println("Playing: "+playlist.get(next));
    }

    /**
     * Method to play previous track in the playlist by
     * printing it to stdout and changing current
     */
    public void previousTrack() {
        int previous=this.thisTrack;
        System.out.println("Playing: "+playlist.get(previous));
    }

    /**
     * Method to play current track
     */
    public void play() {
        if(thisTrack>5){
            System.out.println("Track "+thisTrack+" doesn't exist");
        }
        else {
            System.out.println("Playing: " + playlist.get(thisTrack));
        }
    }

}