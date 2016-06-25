package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.Note;
import cs3500.music.view.ConcreteGuiViewPanel;
import cs3500.music.view.GuiView;
import cs3500.music.view.IView;
import cs3500.music.view.Playable;


/**
 * Represents a controller for this project
 */
public class MusicController implements ActionListener {
  private final MusicCreator creator;
  private IView view;
  private final KeyboardHandler kbd;
  private final MouseListenerImpl ml;
  private boolean isPlaying;
  private boolean removalState;

  /**
   * Creates a new MusicController object with the given MusicCreator(model), and IView (view).
   *
   * @param m model
   * @param v view Sets the keyboardHandler to a new keyBoardHandler Sets the mouseListener to a new
   *          MouseListener the controller's isPlaying starts out as false the controller's
   *          removalState starts out as false If the given view supports keyboard and mouse events:
   *          This constructor also configures the keyboard and mouse commands for the project: By
   *          clicking anywhere, users are able to add a note By pressing r, users enter "removal
   *          mode" and are able to click on a note to remove it
   */
  public MusicController(MusicCreator m, IView v) {
    this.kbd = new KeyboardHandler();
    this.ml = new MouseListenerImpl();
    this.creator = m;
    this.view = v;
    this.isPlaying = false;
    this.removalState = false;

    if (view instanceof GuiView) {
      configureMouseListener();
      configureKeyBoardListener();
      GuiView view2 = (GuiView) view;
      view2.addActionListener(this);
      this.view = view2;
    }
    this.view.initialize();
  }


  /**
   * Sets up a mouse listener and adds it to the view Under the assumption the view it is a GuiView,
   * which is ensured in the constructor
   */
  private void configureMouseListener() {

    Map<Integer, Runnable> one = new HashMap<>();
    Map<Integer, Runnable> two = new HashMap<>();
    Map<Integer, Runnable> three = new HashMap<>();

    one.put(MouseEvent.MOUSE_CLICKED, new Runnable() {
      public void run() {
        GuiView view2 = (GuiView) view;
        try {
          creator.addNote(view2.userNote());
        } catch (NullPointerException e) {
        }
        view2.refresh(creator);
      }
    });
    three.put(MouseEvent.MOUSE_CLICKED, new Runnable() {
      public void run() {
        if (removalState) {
          GuiView view2 = (GuiView) view;

          Note remove = null;
          // An array List of notes
          List<Note> list = creator.notesAtBeat(getBeatFromLocation(ml.getXCoord()));
          for (Note n : list) {
            if (n.getKeyVal() == getPitchFromLocation(ml.getYCoord())) {
              remove = n;
              break;
            }
          }
          try {
            creator.removeNote(remove);
            view2.refresh(creator);
          } catch (NullPointerException e) {
            // Do nothing if they try to click a non note area of the screen
          }

        }
      }
    });

    this.ml.setOne(one);
    this.ml.setTwo(two);
    this.ml.setThree(three);
    GuiView view2 = (GuiView) view;
    view2.addMouseListener(ml);
  }


  /**
   * Sets up a keyboard listener Under the assumption the view is a GuiView
   */
  private void configureKeyBoardListener() {
    Map<Integer, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    keyPresses.put(KeyEvent.VK_SPACE, new Runnable() {
      public void run() {
        if (view instanceof Playable) {
          isPlaying = !isPlaying;
          Playable view2 = (Playable) view;
          if (isPlaying) {
            view2.play();
          } else {
            view2.pause();
          }
        }
      }
    });


    keyPresses.put(KeyEvent.VK_R, new Runnable() {
      public void run() {
        removalState = true;
        JOptionPane.showMessageDialog(null, "Right click on a note to remove it");
      }
    });

    keyPresses.put(KeyEvent.VK_HOME, new Runnable() {
      public void run() {
        if (view instanceof Playable) {
          Playable view2 = (Playable) view;
          view2.reset();
        }
      }
    });
    keyPresses.put(KeyEvent.VK_END, new Runnable() {
      public void run() {
        if (view instanceof Playable) {
          Playable view2 = (Playable) view;
          view2.skipToEnd();
        }
      }
    });
    keyPresses.put(KeyEvent.VK_END, new Runnable() {
      public void run() {
        if (view instanceof Playable) {
          Playable view2 = (Playable) view;
          view2.skipToEnd();
        }
      }
    });
    kbd.setTyped(keyTypes);
    kbd.setPressed(keyPresses);
    kbd.setReleased(keyReleases);

    GuiView view2 = (GuiView) view;
    view2.addKeyListener(kbd);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // Does Nothing
  }

  /**
   * Gets a beat from an Xcoord
   *
   * @param x the x coord to get the beat from
   * @return the beat that is corresponds to the given x location
   */
  private static int getBeatFromLocation(int x) {
    return (x - ConcreteGuiViewPanel.DISTANCE_FROM_SIDE) / ConcreteGuiViewPanel.PIXEL_SIZE;
  }

  /**
   * Gets an pitch from a YCoordinate
   *
   * @param y The y coordinate to get the pitch from
   * @return the pitch that corresponds to the given y location
   */
  private int getPitchFromLocation(int y) {
    List<Note> list = creator.asList();
    int max = Collections.max(list).getKeyVal();
    return -(((y - ConcreteGuiViewPanel.DISTANCE_FROM_TOP) /
            ConcreteGuiViewPanel.PIXEL_SIZE) - max);
  }

  /**
   * Gets this controller's key listener
   *
   * @return this controller's key listener
   */
  public KeyboardHandler getKeyListener() {
    return this.kbd;
  }

  /**
   * @return this controller's mouseListener
   */
  public MouseListenerImpl getMouseListener() {
    return this.ml;
  }

}
