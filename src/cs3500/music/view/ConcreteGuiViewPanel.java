package cs3500.music.view;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.swing.*;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.Note;

public class ConcreteGuiViewPanel extends JPanel {
  public static int PIXEL_SIZE = 20;
  public static int distanceFromTop = 2 * PIXEL_SIZE;
  public static int distanceFromSide = 3 * PIXEL_SIZE;
  private boolean isPlaying;
  private int xlocation = (PIXEL_SIZE * 3);
  private MusicCreator c;
  private int min;
  private int max;
  private int dur;
  private int prevTick;

  private int tick;

  private List<Note> list;


  public ConcreteGuiViewPanel(MusicCreator c) {
    this.c = c;
    list = c.asList();
    min = Collections.min(list).getKeyVal();
    max = Collections.max(list).getKeyVal();
    this.dur = c.getSongDuration();
    this.prevTick = -1;
    this.setVisible(true);

  }

  // TODO fix this
  public Dimension getSongDimensions() {
    return new Dimension(dur * PIXEL_SIZE + (PIXEL_SIZE * 5), ((max - min + 1) *
            PIXEL_SIZE) + (2 * PIXEL_SIZE));
  }

    public void setMidi(int tick) {
        this.tick = tick;
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

  // TODO: Change this to match the song exactly
  private void paintLine(Graphics g) {
    g.setColor(Color.RED);
    int y = (max - min + 1) * PIXEL_SIZE;
    g.drawRect(xlocation, distanceFromTop, 1, y);
    this.updateTime();

  }


  // Paints the beat numbers so that every four beats it displays beat number
  //  We can change this so that it looks more like the picture (every 16 beats)
  private void paintBeats(Graphics g) {
    g.setFont(new Font("Courier New", Font.BOLD, PIXEL_SIZE));
    g.setColor(new Color(255, 255, 255));
    for (int i = 0; i <= dur; i += 4) {
      g.drawString("" + i, i * PIXEL_SIZE + distanceFromSide, PIXEL_SIZE);
    }
  }

  // Paint the grid
  private void paintGrid(Graphics g) {
    g.setColor(new Color(255, 255, 255));

    // For every
    for (int i = 0; i <= Math.floor(dur / 4.0); i++) {
      for (int j = min; j <= max; j++) {
        int rectX = i * (PIXEL_SIZE * 4) + distanceFromSide;
        int rectY = (j - min) * PIXEL_SIZE + distanceFromTop;

        g.drawRect(rectX, rectY, PIXEL_SIZE * 4, PIXEL_SIZE);
      }
    }

  }

  // Paints the notes
  private void paintNotes(Graphics g) {
    for (Note n : list) {
      // DRAWS THE TRAIL
      g.setColor(new Color(177, 95, 171));
      g.fillRect(n.getStartbeatNo() * PIXEL_SIZE + distanceFromSide,
              (max - n.getKeyVal()) * PIXEL_SIZE + distanceFromTop,
              n.getDuration() * PIXEL_SIZE, PIXEL_SIZE);
      // DRAWS THE STARTING HEADER
      g.setColor(new Color(204, 196, 36));
      g.fillRect(n.getStartbeatNo() * PIXEL_SIZE + distanceFromSide,
              (max - n.getKeyVal()) * PIXEL_SIZE + distanceFromTop,
              PIXEL_SIZE, PIXEL_SIZE);
    }
  }

  // Todo add more space inbetween notes
  private void paintOctKey(Graphics g) {
    g.setColor(Color.white);
    for (int i = max; i >= min; i--) {
      int noteVal = i % 12;

      int octaveVal = (int) Math.floor(i / 12) - 1;

      g.drawString(Note.Pitch.values()[noteVal].toNoteString() + octaveVal,
              // 2.5 because we want it to be in the middle
              0, (max - i) * PIXEL_SIZE + distanceFromTop);

    }
  }

  protected void play() {
    this.isPlaying = true;
    updateTime();
  }


  protected void pause() {
    this.isPlaying = false;
    updateTime();
  }


  protected void reset() {
    this.isPlaying = false;

    this.xlocation = this.tick;

    this.xlocation = distanceFromSide;

    repaint();
  }

  protected void skipToEnd() {
    this.isPlaying = false;
    // Add pixel size so that it's when the last song ENDS
    this.xlocation = c.getSongDuration() * PIXEL_SIZE + distanceFromSide + PIXEL_SIZE;
    repaint();
  }

  protected void refresh(MusicCreator c) {
    this.c = c;
    this.list = c.asList();
    min = Collections.min(list).getKeyVal();
    max = Collections.max(list).getKeyVal();
    this.dur = c.getSongDuration();
    repaint();
  }

  private void updateTime() {
    if (isPlaying && (prevTick < tick)) {
      System.out.println("POS: " + (tick * PIXEL_SIZE + distanceFromSide));
      System.out.println("TICK:" + tick);
      this.xlocation = (tick * PIXEL_SIZE) + distanceFromSide;
      this.prevTick = tick;
      if (isPlaying) {
        System.out.println(tick * PIXEL_SIZE + distanceFromSide);
        System.out.println(tick);
        this.xlocation += tick * PIXEL_SIZE + distanceFromSide;
        repaint();
      }

    }
  }

  public void setTick(int t) {
    this.tick = t;
  }
}
