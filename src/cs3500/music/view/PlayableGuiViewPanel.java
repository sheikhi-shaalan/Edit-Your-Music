package cs3500.music.view;

import java.awt.*;

import cs3500.music.model.MusicCreator;

/**
 * Created by NadineShaalan on 6/20/16.
 */
public class PlayableGuiViewPanel extends ConcreteGuiViewPanel {
  public PlayableGuiViewPanel(MusicCreator c) {
    super(c);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.incrementLine(g, 0);
  }

  private void incrementLine(Graphics g, int beat) {
    g.setColor(new Color(255, 53, 23));
    g.drawRect(beat* ConcreteGuiViewPanel.PIXEL_SIZE, (int) getSongDimensions().getHeight(),1,
             (int) getSongDimensions().getHeight());
  }
}
