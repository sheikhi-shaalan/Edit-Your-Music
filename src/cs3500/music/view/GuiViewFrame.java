package cs3500.music.view;

import java.awt.*;
//import java.awt.event.MouseListener; // Possibly of interest for handling mouse events
import java.util.Collections;
import javax.swing.*;
import java.util.List;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.model.Note;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements IView {
  MusicCreator c;
  private final ConcreteGuiViewPanel displayPanel; // You may want to refine this to a subtype of JPanel
  private		JScrollPane scrollPane = new JScrollPane();
  /**
   * Creates new GuiView
   */
  public GuiViewFrame(MusicCreator c) {
    this.c = c;
    this.setSize(800,600);

    this.displayPanel = new ConcreteGuiViewPanel(c);
    this.displayPanel.setPreferredSize(displayPanel.getSongDimensions());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.scrollPane = new JScrollPane(displayPanel);
    this.scrollPane.setPreferredSize(new Dimension(600,200));
    //add the JScrollPane to wherever you would have added the drawPanel
    this.add(scrollPane);
  }


  public static void main(String[] args) {
        MusicCreator c = new MusicCreatorImpl();
        c.addNote(
                new Note(0, Note.Pitch.C, 2, 1));
        c.addNote(
                new Note(1, Note.Pitch.D, 1, 2));
        c.addNote(
                new Note(2, Note.Pitch.B, 10, 2));
        c.addNote(
                new Note(5, Note.Pitch.B, 4, 2));
        c.addNote(new Note(8, Note.Pitch.B, 5, 3));
        c.addNote(new Note(40, Note.Pitch.G, 10, 3));

       JFrame frame = new GuiViewFrame(c);

  }

  @Override
  public void initialize() {
    this.setVisible(true);

  }
}

