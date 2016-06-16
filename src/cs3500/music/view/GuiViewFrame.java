package cs3500.music.view;

import java.awt.*;
import java.awt.event.MouseListener; // Possibly of interest for handling mouse events

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.model.Note;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements IView {
  MusicCreator c;
  private final JPanel displayPanel; // You may want to refine this to a subtype of JPanel

  /**
   * Creates new GuiView
   */
  public GuiViewFrame(MusicCreator c) {
    this.c = c;
    this.displayPanel = new ConcreteGuiViewPanel(c);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    this.pack();
  }


//  @Override
//  public void initialize(){
//
//    this.setVisible(true);
//  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(100, 100);
  }

  public static void main(String[] args) {
    MusicCreator c = new MusicCreatorImpl();
    c.addNote(
            new Note(0, Note.Pitch.C, 2, 1));
    c.addNote(
            new Note(1, Note.Pitch.D, 1, 2));
    c.addNote(
            new Note(2, Note.Pitch.B, 10, 2));
    System.out.println("DUR :" + c.getSongDuration());
    System.out.println(Math.ceil(13.0/4));
    JFrame frame = new GuiViewFrame(c);
    frame.setVisible(true);
  }
}

