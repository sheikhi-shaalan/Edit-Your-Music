package cs3500.music.tests;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.view.CompositeView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import org.junit.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ControllerTest {
  KeyboardHandler k;
  MusicCreator creator;
  GuiViewFrame gui;
  MidiViewImpl midi;
  CompositeView compositeView;

  KeyEvent homeKey;

  KeyEvent endKey;

  KeyEvent spaceBar;

  KeyEvent r;

  void initData() {
    this.creator = new MusicCreatorImpl();
    this.gui = new GuiViewFrame(this.creator);
    this.midi = new MidiViewImpl(this.creator);
    this.compositeView = new CompositeView(this.gui, this.midi);
    this.homeKey = new KeyEvent(new Component() {}, 0, 10,
            InputEvent.BUTTON1_MASK, KeyEvent.VK_HOME, ' ', 0);
    this.endKey = new KeyEvent(new Component() {}, 0, 10,
            InputEvent.BUTTON1_MASK, KeyEvent.VK_END, ' ', 0);
    this.spaceBar = new KeyEvent(new Component() {}, 0, 10,
            InputEvent.BUTTON1_MASK, KeyEvent.VK_SPACE, ' ', 0);
    this.r = new KeyEvent(new Component() {}, 0, 10,
            InputEvent.BUTTON1_MASK, KeyEvent.VK_R, ' ', 0);
  }


  @Test
  public void testSpacePausesMusic() {
    this.initData();


  }
}
