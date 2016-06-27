package cs3500.music.model;

/**
 * Created by NadineShaalan on 6/26/16.
 */
public interface MusicCreatorRepeat extends MusicCreator {
  /**
   *
   * @param start starting beat of the repetition
   * @param end end beat of the repetition
   */
  void addRepeat(int start, int end);
}
