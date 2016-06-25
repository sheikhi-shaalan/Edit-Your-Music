package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.Note;

/**
 * To represent a compositeView
 */
public class CompositeView implements GuiView, Playable {
    private GuiViewFrame gui;
    private MidiViewImpl midi;

    /**
     * Creates a compositeView with the given gui and midi
     *
     * @param gui  the gui of this compositeView
     * @param midi the midi of this compositeView
     */
    public CompositeView(GuiViewFrame gui, MidiViewImpl midi) {
        this.gui = gui;
        this.midi = midi;
    }

    /**
     * A new thread for moving the screen
     * and setting the red bar of the midi
     */
    class RedLine extends Thread {
        @Override
        public void run() {
            while (midi.isPlaying()) {
                gui.setPaneTick(midi.getBeat());
                gui.moveScreen(midi.getBeat());
                gui.play();
            }
        }
    }

    @Override
    public void play() {
        Thread redLine = new RedLine();
        redLine.start();
        this.midi.play();
    }

    @Override
    public void pause() {
        gui.pause();
        midi.pause();
    }

    @Override
    public void reset() {
        gui.reset();
        midi.reset();
        midi.pause();
    }

    @Override
    public void skipToEnd() {
        gui.skipToEnd();
        midi.skipToEnd();
    }

    @Override
    public boolean isPlaying() {
        return this.midi.isPlaying();
    }

    @Override
    public int getBeat() {
        return this.midi.getBeat();
    }

    @Override
    public void initialize() {
        this.gui.initialize();
        this.midi.initialize();
        this.midi.sequencer.stop();
    }

    @Override
    public void refresh(MusicCreator c) {
        this.gui.refresh(c);
        this.midi.refresh(c);
    }


    @Override
    public void addActionListener(ActionListener action) {

        this.gui.addActionListener(action);
    }

    @Override
    public void addKeyListener(KeyListener keyListener) {
        this.gui.addKeyListener(keyListener);
    }

    @Override
    public Note userNote() {
        return this.gui.userNote();
    }

    @Override
    public void addMouseListener(MouseListener m) {
        this.gui.addMouseListener(m);
    }

    @Override
    public void removeMouseListener() {
    }

}