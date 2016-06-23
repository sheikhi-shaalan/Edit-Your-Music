package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.MusicEditor;
import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.model.Note;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.CompositeView;
import cs3500.music.view.ConcreteGuiViewPanel;
import cs3500.music.view.GuiView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.Playable;

import javax.swing.*;

/**
 * Created by NadineShaalan on 6/20/16.
 */
public class MusicController implements ActionListener {
  private final MusicCreator creator;
  private IView view;
  private final KeyboardHandler kbd = new KeyboardHandler();
  private final MouseListenerImpl ml = new MouseListenerImpl();
  private boolean isPlaying;

  public MusicController(MusicCreator m, IView v) {
    this.creator = m;
    this.view = v;
    isPlaying = false;
    if (view instanceof GuiView) {
      configureMouseListener();
      configureKeyBoardListener();
      GuiView view2 = (GuiView) view;
      view2.addActionListener(this);
      this.view = view2;
    }
    this.view.initialize();
  }


  private void configureMouseListener() {

    Map<Integer, Runnable> one = new HashMap<>();
    Map<Integer, Runnable> two = new HashMap<>();
    Map<Integer, Runnable> three = new HashMap<>();

      one.put(MouseEvent.MOUSE_CLICKED, new Runnable() {
      public void run() {
        GuiView view2 = (GuiView) view;
        creator.addNote(view2.userNote());
        view2.refresh(creator);
      }
    });

    this.ml.setOne(one);
    this.ml.setTwo(two);
    this.ml.setThree(three);
   // ml.setMousey(mouseClick);
    GuiView view2 = (GuiView) view;
    view2.addMouseListener(ml);
  }

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
//    keyPresses.put(KeyEvent.VK_1, new Runnable() {
//      public void run() {
//        if (view instanceof Playable) {
//          Playable view2 = (Playable) view;
//          view2.reset();
//        }
//      }
//    });
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
    kbd.setTyped(keyTypes);
    kbd.setPressed(keyPresses);
    kbd.setReleased(keyReleases);

    GuiView view2 = (GuiView) view;
    view2.addKeyListener(kbd);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  public static void main(String[] args) throws IOException {
    MusicReader reader = new MusicReader();
    CompositionBuilder<MusicCreator> b = MusicCreatorImpl.getBuilder();
    MusicCreator creator = reader.parseFile(new FileReader("mystery-3.txt"), b);

    MusicEditor m = new MusicEditor();
    MusicController controller = new MusicController(creator, new CompositeView(
            new GuiViewFrame(creator), new MidiViewImpl(creator)));

  }
}
