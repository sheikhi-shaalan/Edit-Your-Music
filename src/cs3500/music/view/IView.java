package cs3500.music.view;

import cs3500.music.model.MusicCreator;


public interface IView {
  /**
   * Creates the actual views
   */
  void initialize();

  /**
   * Refreshes a view with the given Music Creator
   * @param c the refreshed Music Creator
   */
  void refresh(MusicCreator c);

}
