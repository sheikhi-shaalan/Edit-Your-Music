package cs3500.music.view;

/**
 * Created by NadineShaalan on 6/22/16.
 * To represent any view that can play
 */
public interface Playable {

    /**
     * Plays a piece
     */
    void play();

    /**
     * Pauses a piece
     */
    void pause();

    /**
     * Resets a peice to the beginning
     */
    void reset();

    /**
     * Skips to the end of a piece
     */
    void skipToEnd();


    /**
     * Is this view playing?
     */
    boolean isPlaying();

    /**
     * @return where are we in the song?
     */
    int getBeat();
}
