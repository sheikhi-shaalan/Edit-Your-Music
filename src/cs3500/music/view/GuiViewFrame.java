package cs3500.music.view;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.awt.*;
import java.awt.event.ActionListener;

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
  public GuiViewFrame(MusicCreator c, ConcreteGuiViewPanel panel) {
    this.c = c;
    this.setSize(800,600);

    this.displayPanel = panel;
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
  protected ConcreteGuiViewPanel getDisplayPanel() {
    return this.displayPanel;
  }

  @Override
  public void addActionListener(ActionListener action) {

  }

  @Override
  public void removeMouseListener() {

  }

}

