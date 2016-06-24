package cs3500.music.view;

/**
 * Created by NadineShaalan on 6/22/16.
 */
public interface Playable {

  int getMyLocation();
  void play(int where);
  void pause();
  void reset();
  void skipToEnd();

}
