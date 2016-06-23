package cs3500.music.view;

import cs3500.music.model.Note;
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.model.MusicCreator;


/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements GuiView, Playable {
    private MusicCreator c;
    private final ConcreteGuiViewPanel displayPanel;
    private JScrollPane scrollPane = new JScrollPane();

    /**
     * Creates new GuiView
     */
    public GuiViewFrame(MusicCreator c) {
        this.c = c;
        this.setSize(800, 600);

        this.displayPanel = new ConcreteGuiViewPanel(c);
        this.displayPanel.setPreferredSize(displayPanel.getSongDimensions());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.scrollPane = new JScrollPane(displayPanel);
        this.scrollPane.setPreferredSize(new Dimension(600, 200));

        //add the JScrollPane to wherever you would have added the drawPanel
        this.add(scrollPane);
        this.setFocusable(true);
        this.requestFocus();
    }


  @Override
  public void initialize() {
    this.setVisible(true);

  }

  @Override
  public void refresh(MusicCreator c) {
    this.c = c;
    this.displayPanel.refresh(c);
  }

  @Override
  public void removeMouseListener() {

  }

  @Override
  public void addMouseListener(MouseListener m) {
    this.displayPanel.addMouseListener(m);
  }

  @Override
  public void addActionListener(ActionListener action) {
  }

    @Override
    public Note userNote() throws NumberFormatException, IllegalArgumentException {
        Note ret = null;
        JPanel panel = new JPanel();
        JTextField pitch = new JTextField();
        JTextField octave = new JTextField();
        JTextField duration = new JTextField();
        JTextField startBeatNo = new JTextField();

        panel.add(pitch);
        panel.add(octave);
        panel.add(duration);
        panel.add(startBeatNo);

        int result = JOptionPane.showConfirmDialog(null, panel, "Please enter the following values: ",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            ret = new Note(Integer.parseInt(startBeatNo.getText()), Note.Pitch.values()[Integer.parseInt(pitch.getText())],
            Integer.parseInt(duration.getText()), Integer.parseInt(octave.getText()));
        }

        return ret;
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

