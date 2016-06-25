package cs3500.music.view;

import cs3500.music.model.Note;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.*;

import cs3500.music.model.MusicCreator;


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

  // Creates a JOption Pane for accepting User inputs
  // Passes this info to Controller
  @Override
  public Note userNote() throws NumberFormatException, IllegalArgumentException {
    Note ret = null;
    Note.Pitch[] pitches = {Note.Pitch.C, Note.Pitch.CSHARP, Note.Pitch.D, Note.Pitch.DSHARP,
            Note.Pitch.F, Note.Pitch.FSHARP, Note.Pitch.G, Note.Pitch.GSHARP, Note.Pitch.A,
            Note.Pitch.ASHARP, Note.Pitch.B};
    JComboBox pitchesList = new JComboBox(pitches);
    JPanel panel = new JPanel();
    JLabel pitchLabel = new JLabel("Pitch: ");
    JLabel octaveLabel = new JLabel("Octave: ");
    JLabel durationLabel = new JLabel("Duration: ");
    JLabel startBeatNoLabel = new JLabel("Starting beat number: ");
    JTextField octave = new JTextField(5);
    JTextField duration = new JTextField(5);
    JTextField startBeatNo = new JTextField(5);

    panel.add(pitchLabel);
    panel.add(pitchesList);
    panel.add(octaveLabel);
    panel.add(octave);
    panel.add(durationLabel);
    panel.add(duration);
    panel.add(startBeatNoLabel);
    panel.add(startBeatNo);

    int result = JOptionPane.showConfirmDialog(null, panel, "Add A New Note",
            JOptionPane.OK_CANCEL_OPTION);
    try {
      if (result == JOptionPane.OK_OPTION) {
        ret = new Note(Integer.parseInt(startBeatNo.getText()),
                (Note.Pitch) pitchesList.getSelectedItem(),
                Integer.parseInt(duration.getText()), Integer.parseInt(octave.getText()));
      }
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(null, "Please enter valid values: ");
      this.userNote();
    }

    return ret;
  }

  public void setPaneMidi(int tick) {
    this.displayPanel.setMidi(tick);
  }



  @Override
  public void play() {
    this.displayPanel.play();

   // this.scrollIt();
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


  public void setPanelTick(int tick) {
    this.displayPanel.setTick(tick);
  }

  @Override
  public boolean isPlaying() {
    return true;
  }
}

