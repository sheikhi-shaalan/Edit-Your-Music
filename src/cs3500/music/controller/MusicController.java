package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.MusicCreator;
import cs3500.music.view.IView;

/**
 * Created by NadineShaalan on 6/20/16.
 */
public class MusicController implements ActionListener {
  private final MusicCreator creator;
  private final IView view;
  private final KeyboardHandler kbd = new KeyboardHandler();

  public MusicController(MusicCreator m, IView v) {
    this.creator = m;
    this.view = v;
    configureKeyBoardListener();
    //this.view.addActionListener(this);
  }

  // TODO install the runnables we want
  private void configureKeyBoardListener() {
    Map<Integer, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    // TODO add arrow keys to the key pressed? Key typed?
    keyPresses.put(2, new Runnable() {
      public void run() {
        // TODO ?????? SHOULD THIS BE IN THE STANDARD GUI
      //  view.moveScreen();
      }
    });
    kbd.setTyped(keyTypes);
    kbd.setPressed(keyPresses);
    kbd.setReleased(keyReleases);

    //view.addKeyListener(kbd);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
