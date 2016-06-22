package cs3500.music.view;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs3500.music.model.MusicCreator;


/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements GuiView, Playable {
  private MusicCreator c;
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

  @Override
  public void refresh() {
    this.displayPanel.repaint();
  }

  @Override
  public void removeMouseListener() {

  }

  @Override
  public void addActionListener(ActionListener action) {

  }


  @Override
  public void play() {
    this.displayPanel.play();
  }

  @Override
  public void pause() {
    this.displayPanel.pause();
  }

  @Override
  public void reset() {
    this.displayPanel.reset();
  }

  @Override
  public void skipToEnd() {
  this.displayPanel.skipToEnd();
  }
}

