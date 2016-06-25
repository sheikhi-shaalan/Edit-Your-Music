package cs3500.music.tests;

import org.junit.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseListenerImpl;
import cs3500.music.controller.MusicController;
import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.model.Note;
import cs3500.music.view.CompositeView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;


public class ControllerTest {
  MusicController controller;
  KeyboardHandler kh;
  MouseListenerImpl ml;
  MusicCreator model;
  CompositeView view;
  GuiViewFrame guiView;
  MidiViewImpl midiView;
  KeyEvent spaceBar;
  KeyEvent endKey;
  KeyEvent homeKey;
  KeyEvent r;

  public void init() {
    this.model = new MusicCreatorImpl();
    this.model.addNote(new Note(0, 0, 60, 1, 1));
    this.model.addNote(new Note(1, 0, 60, 1, 1));
    this.model.addNote(new Note(4, 0, 60, 1, 1));
    this.model.addNote(new Note(5, 0, 60, 1, 1));
    this.model.addNote(new Note(6, 0, 60, 1, 1));
    this.guiView = new GuiViewFrame(model);
    this.midiView = new MidiViewImpl(model);
    this.view = new CompositeView(guiView, midiView);
    this.controller = new MusicController(model, view);
    this.kh = this.controller.getKeyListener();
    this.ml = this.controller.getMouseListener();
    this.view.addKeyListener(kh);
    this.view.addMouseListener(ml);
    this.spaceBar = new KeyEvent(new Component() {
    }, 0, 10,
            InputEvent.BUTTON1_MASK, KeyEvent.VK_SPACE, ' ', 0);
    this.endKey = new KeyEvent(new Component() {
    }, 0, 10,
            InputEvent.BUTTON1_MASK, KeyEvent.VK_END, ' ', 0);

    this.homeKey = new KeyEvent(new Component() {
    }, 0, 10,
            InputEvent.BUTTON1_MASK, KeyEvent.VK_HOME, ' ', 0);

    this.r = new KeyEvent(new Component() {
    }, 0, 10,
            InputEvent.BUTTON1_MASK, KeyEvent.VK_R, ' ', 0);

  }

  @Test
  public void testSpacePlaysPausesMusic() {
    init();
    // Before spacebar is pressed
    assertFalse(this.midiView.isPlaying());

    // Space bar is pressed and plays the music
    kh.keyPressed(spaceBar);
    assertTrue(this.midiView.isPlaying());

    // space bar is pressed again and pauses the music
    kh.keyPressed(spaceBar);
    assertFalse(this.midiView.isPlaying());

  }

  // Tests that pressing the home key brings you to the beginning of a song
  @Test
  public void testHomeKeyWorks() {
    init();
    // Song is at the beginning
    assertEquals(0, this.view.getBeat());

    //Play the song
    kh.keyPressed(spaceBar);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // Song is no longer at the beginning
    assertNotEquals(0, this.view.getBeat());

    // Go to the beginning of the song
    kh.keyPressed(homeKey);
    // Song is now at the beginning again
    assertEquals(0, this.view.getBeat());


  }

  @Test
  public void testKey() {

  }
}