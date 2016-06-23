package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

import cs3500.music.MusicEditor;
import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;


public class CompositeView implements GuiView, Playable {
  GuiViewFrame gui;
  MidiViewImpl midi;
// TODO think about ways to abstract
  public CompositeView(GuiViewFrame gui, MidiViewImpl midi) {
    this.gui = gui;
    this.midi = midi;

  }

  // Action
  public void play() {
    gui.play();
    midi.play();
  }

  public void pause() {
    gui.pause();
    midi.pause();
  }

  public void reset() {
    gui.reset();
    midi.reset();
    midi.pause();
  }

  public void skipToEnd() {
    gui.skipToEnd();
    midi.pause();
  }

  // Shows up
  @Override
  public void initialize() {
    this.gui.initialize();
    this.midi.initialize();
  }

  @Override
  public void refresh() {
    this.gui.refresh();
    this.midi.refresh();
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
  public void addMouseListener(MouseListener m) {
    this.gui.addMouseListener(m);
  }

  @Override
  public void removeMouseListener() {

  }

  public static void main(String[] args) throws IOException, InvalidMidiDataException {

    MusicReader reader = new MusicReader();
    CompositionBuilder<MusicCreator> b = MusicCreatorImpl.getBuilder();
    MusicCreator creator = reader.parseFile(new FileReader("mystery-1.txt"), b);

    MusicEditor m = new MusicEditor();
    CompositeView v = new CompositeView(new GuiViewFrame(creator),
            new MidiViewImpl(creator));
    v.initialize();

  }


}
