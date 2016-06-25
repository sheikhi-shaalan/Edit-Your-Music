package cs3500.music.view;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.Note;

public class ConcreteGuiViewPanel extends JPanel {
  public static int PIXEL_SIZE = 20;
  public static int DISTANCE_FROM_TOP = 2 * PIXEL_SIZE;
  public static int DISTANCE_FROM_SIDE = 3 * PIXEL_SIZE;
  private boolean isPlaying;
  private int xLocation;
  private MusicCreator c;
  private int min;
  private int max;
  private int dur;
  private int tick;
  private int prevTick;
  private List<Note> list;


  /**
   * Sets the list of notes to be the list of notes in the model Sets the min to be the min of the
   * list (its keyVal) Sets the max to the max of the list (its keyVal) The duration is the duration
   * of the model the previous tick is initialized to -1 the tick is set to zero
   *
   * @param c the model of this panel
   */
  public ConcreteGuiViewPanel(MusicCreator c) {
    this.c = c;
    list = c.asList();
    min = Collections.min(list).getKeyVal();
    max = Collections.max(list).getKeyVal();
    this.dur = c.getSongDuration();
    this.prevTick = -1;
    this.tick = 0;
    this.setVisible(true);
    this.xLocation = (PIXEL_SIZE * 3);

  }


  /**
   * @return the dimension of this panel
   */
  protected Dimension getSongDimensions() {
    return new Dimension(dur * PIXEL_SIZE + (PIXEL_SIZE * 5), ((max - min + 1) *
            PIXEL_SIZE) + (2 * PIXEL_SIZE));
  }

  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);
    this.paintNotes(g);
    this.paintGrid(g);
    this.paintBeats(g);
    this.paintOctKey(g);
    this.setBackground(new Color(37, 67, 91));
    this.paintLine(g);
  }

  /**
   * Paints the red line that keeps track of the beat
   *
   * @param g the graphics
   */
  private void paintLine(Graphics g) {
    g.setColor(Color.RED);
    int y = (max - min + 1) * PIXEL_SIZE;
    g.drawRect(xLocation, DISTANCE_FROM_TOP, 1, y);
    this.updateTime();

  }


  // Paints the beat numbers so that every four beats it displays beat number
  // We can change this so that it looks more like the picture (every 16 beats)
  private void paintBeats(Graphics g) {
    g.setFont(new Font("Courier New", Font.BOLD, PIXEL_SIZE));
    g.setColor(new Color(255, 255, 255));
    for (int i = 0; i <= dur; i += 4) {
      g.drawString("" + i, i * PIXEL_SIZE + DISTANCE_FROM_SIDE, PIXEL_SIZE);
    }
  }

  // Paint the grid
  private void paintGrid(Graphics g) {
    g.setColor(new Color(255, 255, 255));

    // For every
    for (int i = 0; i <= Math.floor(dur / 4.0); i++) {
      for (int j = min; j <= max; j++) {
        int rectX = i * (PIXEL_SIZE * 4) + DISTANCE_FROM_SIDE;
        int rectY = (j - min) * PIXEL_SIZE + DISTANCE_FROM_TOP;

        g.drawRect(rectX, rectY, PIXEL_SIZE * 4, PIXEL_SIZE);
      }
    }

  }

  // Paints the notes
  private void paintNotes(Graphics g) {
    for (Note n : list) {
      // DRAWS THE TRAIL
      g.setColor(new Color(177, 95, 171));
      g.fillRect(n.getStartbeatNo() * PIXEL_SIZE + DISTANCE_FROM_SIDE,
              (max - n.getKeyVal()) * PIXEL_SIZE + DISTANCE_FROM_TOP,
              n.getDuration() * PIXEL_SIZE, PIXEL_SIZE);
      // DRAWS THE STARTING HEADER
      g.setColor(new Color(204, 196, 36));
      g.fillRect(n.getStartbeatNo() * PIXEL_SIZE + DISTANCE_FROM_SIDE,
              (max - n.getKeyVal()) * PIXEL_SIZE + DISTANCE_FROM_TOP,
              PIXEL_SIZE, PIXEL_SIZE);
    }
  }

  private void paintOctKey(Graphics g) {
    g.setColor(Color.white);
    for (int i = max; i >= min; i--) {
      int noteVal = i % 12;

      int octaveVal = (int) Math.floor(i / 12) - 1;

      g.drawString(Note.Pitch.values()[noteVal].toNoteString() + octaveVal,
              // 2.5 because we want it to be in the middle
              0, (max - i) * PIXEL_SIZE + (int) (2.5 * PIXEL_SIZE));

    }
  }

  /**
   * helper for the frame's play
   */
  protected void play() {
    this.isPlaying = true;
    updateTime();
  }


  /**
   * helper for the frame's pause
   */
  protected void pause() {
    this.isPlaying = false;
    updateTime();
  }


  /**
   * helper for the frame's reset
   */
  protected void reset() {
    this.isPlaying = false;
    this.tick = 0;
    this.prevTick = -1;
    this.xLocation = DISTANCE_FROM_SIDE;
    repaint();
  }

  /**
   * helper for the frame's skipToEnd
   */
  protected void skipToEnd() {
    this.isPlaying = false;
    // Add pixel size so that it's when the last song ENDS
    this.xLocation = c.getSongDuration() * PIXEL_SIZE + DISTANCE_FROM_SIDE + PIXEL_SIZE;
    repaint();
  }

  /**
   * helper for the frame's skip to end
   *
   * @param c the model to refresh with
   */
  protected void refresh(MusicCreator c) {
    this.c = c;
    this.list = c.asList();
    min = Collections.min(list).getKeyVal();
    max = Collections.max(list).getKeyVal();
    this.dur = c.getSongDuration();
    repaint();
  }

  /**
   * Update's the tick of this musicCreator
   */
  private void updateTime() {
    if (isPlaying && (prevTick < tick)) {
      this.xLocation = (tick * PIXEL_SIZE) + DISTANCE_FROM_SIDE;
      this.prevTick = tick;
      repaint();
    }
  }

  /**
   * @param tick the tick of this panel
   */
  protected void setTick(int tick) {
    this.tick = tick;
  }
}