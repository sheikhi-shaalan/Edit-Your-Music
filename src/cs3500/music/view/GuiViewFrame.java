package cs3500.music.view;

import cs3500.music.model.Note;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.model.MusicCreator;


/**
 * To represent the guiViewFrame
 */
public class GuiViewFrame extends JFrame implements GuiView, Playable {
    private MusicCreator c;
    private final ConcreteGuiViewPanel displayPanel;
    private JScrollPane scrollPane = new JScrollPane();

    /**
     * Creates new GuiView with the given MusicCreator
     * The default size is 800 by 600.
     * The default close operation is to exit on close
     * Adds a scroll pane to the MusicCreator and sets its preferred size
     * to be 600 by 200
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
        Note.Pitch[] pitches = {Note.Pitch.C, Note.Pitch.CSHARP, Note.Pitch.D, Note.Pitch.DSHARP, Note.Pitch.F,
                Note.Pitch.FSHARP, Note.Pitch.G, Note.Pitch.GSHARP, Note.Pitch.A, Note.Pitch.ASHARP, Note.Pitch.B};
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
                ret = new Note(Integer.parseInt(startBeatNo.getText()), (Note.Pitch) pitchesList.getSelectedItem(),
                        Integer.parseInt(duration.getText()), Integer.parseInt(octave.getText()));
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Please enter valid values: ");
            this.userNote();
        }

        return ret;
    }


    @Override
    public void play() {
        this.displayPanel.play();
    }

    /**
     * moves the screen of the frame by the given amount
     *
     * @param tick the amount to move the screen by
     */
    protected void moveScreen(int tick) {
        if (tick * displayPanel.PIXEL_SIZE >= this.getBounds().getSize().getWidth()
                - displayPanel.PIXEL_SIZE) {
            this.scrollPane.getHorizontalScrollBar().setValue(tick * displayPanel.PIXEL_SIZE);
        }
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

    @Override
    public boolean isPlaying() {
        return true;
    }

    @Override
    public int getBeat() {
        return 0;
    }

    /*
     * Sets the pane tick in the GUI panel
     */
    protected void setPaneTick(int tick) {
        this.displayPanel.setTick(tick);
    }
}
