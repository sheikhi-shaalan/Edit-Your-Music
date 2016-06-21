package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.model.MusicCreator;

/**
 * Created by NadineShaalan on 6/21/16.
 */
public class PlayablePanel extends ConcreteGuiViewPanel {
 MusicCreator m;
  boolean isPlaying;
  int xlocation;

  public PlayablePanel(MusicCreator m) {
    super(m);
    this.isPlaying = true;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.paintLine(g);
  }

  private void paintLine(Graphics g) {
    g.setColor(Color.RED);
    g.drawRect(xlocation,0,1, this.getSongDimensions().height);
    this.updateTime();

  }

  public void visualPlay() {
    this.isPlaying = true;
    updateTime();
  }

  private void updateTime() {
    if (isPlaying) {
      this.xlocation += 1;
      repaint();
    }
  }


}
