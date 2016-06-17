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
  private final JPanel displayPanel; // You may want to refine this to a subtype of JPanel

  /**
   * Creates new GuiView
   */
  public GuiViewFrame(MusicCreator c) {
    this.c = c;
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    this.displayPanel = new ConcreteGuiViewPanel(c);

    JScrollPane scroller = new JScrollPane(displayPanel);
    this.getContentPane().add(scroller, BorderLayout.CENTER);

    //add the JScrollPane to wherever you would have added the drawPanel
    //this.add(scrolls);




   // this.getContentPane().add(displayPanel);
    this.pack();
  }


//  @Override
//  public void initialize(){
//
//    thisullpull.setVisible(true);
//  }

  @Override
  public Dimension getPreferredSize() {
    List<Note> list = c.asList();
    int min = Collections.min(list).getKeyVal();
    int max = Collections.max(list).getKeyVal();
    return new Dimension(200, (max - min + 1) * 10 + 40);

  }
  public void setCanvasSize(int width, int height) {
    displayPanel.setPreferredSize(new Dimension(width, height));
    displayPanel.revalidate();
    displayPanel.repaint();
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
    c.addNote(new Note( 8, Note.Pitch.B, 5, 3));
    c.addNote(new Note(40, Note.Pitch.G, 10,3));
    JFrame frame = new GuiViewFrame(c);
    frame.setVisible(true);
  }
}

