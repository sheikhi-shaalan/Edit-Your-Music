package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.Note;

/**
 * A dummy view that simply draws a string
 */
public class ConcreteGuiViewPanel extends JPanel {
  int PIXEL_SIZE = 10;
  MusicCreator c;

  // TODO should this be in the construtor?
  public ConcreteGuiViewPanel(MusicCreator c) {
    this.c = c;
  }

  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);
    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    //g.drawString("Hello World", 25, 25);
    //g.drawOval(100,100,100,100);
    this.paintGrid(g);
    this.paintNotes(g);
  }

  // private drawHeader(g)
  private void paintGrid(Graphics g) {
    List<Note> list = c.asList();
    int min = Collections.min(list).getKeyVal();
    int max = Collections.max(list).getKeyVal();
    // For every
    for (int i = 0; i <= Math.ceil(c.getSongDuration() / 4.0); i++) {
      for (int j = min; j <= max; j++) {
        g.drawRect(i * PIXEL_SIZE * 4, (j - min) * PIXEL_SIZE, PIXEL_SIZE * 4, PIXEL_SIZE);
      }
    }

  }

  private void paintNotes(Graphics g) {
    List<Note> list = c.asList();
    int min = Collections.min(list).getKeyVal();
    int max = Collections.max(list).getKeyVal();
    g.setColor(new Color(177, 149, 169));
    for (Note n : list) {
      g.fillRect(n.getStartbeatNo() * PIXEL_SIZE, (n.getKeyVal() - min) * PIXEL_SIZE,
              n.getDuration()*PIXEL_SIZE, PIXEL_SIZE);
    }
  }

}
