package cs3500.music.view;

import java.awt.*;
import javax.swing.*;

import cs3500.music.model.MusicCreator;


/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements GuiView {
  MusicCreator c;
  private final ConcreteGuiViewPanel displayPanel;
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

  @Override
  public void initialize() {
    this.setVisible(true);

  }
}

