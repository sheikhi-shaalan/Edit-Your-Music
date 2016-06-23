package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.model.MusicCreator;

/**
 * Created by NadineShaalan on 6/14/16.
 */
public interface IView {
  /**
   * Creates the actual views
   */
  void initialize();
  void refresh(MusicCreator c);

}
