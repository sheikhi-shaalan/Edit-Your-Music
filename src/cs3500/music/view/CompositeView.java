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

/**
 * Created by NadineShaalan on 6/20/16.
 */
public class CompositeView implements GuiView {
  JLabel label;
  GuiViewFrame gui;
  MidiViewImpl midi;

  public CompositeView(GuiViewFrame gui, MidiViewImpl midi) {
    this.gui= gui;
    this.midi = midi;
  }

  // Action
  private void play() {

  }

  // Shows up
  @Override
  public void initialize() {
    this.gui.initialize();
    this.midi.initialize();
  }


  @Override
  public void addActionListener(ActionListener action) {

  }

  @Override
  public void addKeyListener(KeyListener keyListener) {

  }

  @Override
  public void addMouseListener(MouseListener m) {

  }

  @Override
  public void removeMouseListener() {

  }

  public static void main(String[] args) throws IOException, InvalidMidiDataException {

    MusicReader reader = new MusicReader();
    CompositionBuilder<MusicCreator> b = MusicCreatorImpl.getBuilder();
    MusicCreator creator = reader.parseFile(new FileReader("mary-little-lamb.txt"), b);

    MusicEditor m = new MusicEditor();
    CompositeView v = new CompositeView(new GuiViewFrame(creator, 2), new MidiViewImpl(creator));
    v.initialize();

  }


}
