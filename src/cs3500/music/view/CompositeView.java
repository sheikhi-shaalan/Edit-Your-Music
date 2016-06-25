package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import cs3500.music.model.MusicCreator;
import cs3500.music.model.Note;


public class CompositeView implements GuiView, Playable {
  GuiViewFrame gui;
  MidiViewImpl midi;
  boolean isNotPaused;


    //TODO the position of the red line in the beginning and the end
  public CompositeView(GuiViewFrame gui, MidiViewImpl midi) {
    this.gui = gui;
    this.midi = midi;
  }
  class RedLine extends Thread {
    @Override
    public void run() {
      while (midi.isPlaying() && isNotPaused) {
        gui.setPanelTick(Math.toIntExact(midi.sequencer.getTickPosition()));
        gui.play();
      }
    }
  }

     class redLine extends Thread {
        @Override
        public void run() {
                while (midi.isPlaying() && isNotPaused) {
                    //System.out.println(midi.sequencer.getTickPosition());
                    gui.setPaneMidi(Math.toIntExact(midi.sequencer.getTickPosition()));
                    gui.play();
                }
        }
    }

  @Override
  public void play() {
      this.isNotPaused = true;
      Thread redLine = new redLine();
      redLine.start();
      this.midi.play();
    }

  @Override
  public void pause() {
      this.isNotPaused = false;
      gui.pause();
      midi.pause();
  }

  public void reset() {
    this.isNotPaused = false;
    Thread redline = new RedLine();
    redline.start();
    this.midi.play();
   // gui.play();

  }


  public void skipToEnd() {
    this.isNotPaused = false;
    gui.skipToEnd();
    midi.skipToEnd();
  }

  @Override
  public boolean isPlaying() {
    return this.midi.isPlaying();
  }

  // Shows up
  @Override
  public void initialize() {
    this.gui.initialize();
    this.midi.midiPlaying = false;
    this.midi.initialize();
  }

  @Override
  public void refresh(MusicCreator c) {
    this.gui.refresh(c);
    this.midi.refresh(c);
  }

  @Override
  public void addActionListener(ActionListener action) {

    this.gui.addActionListener(action);
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    this.gui.addKeyListener(keyListener);
  }

  @Override
  public Note userNote() {
    return this.gui.userNote();
  }

  @Override
  public void addMouseListener(MouseListener m) {
    this.gui.addMouseListener(m);
  }

  @Override
  public void removeMouseListener() {

  }

}
