package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;

import javax.sound.midi.*;
import javax.swing.*;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.model.Note;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements IView {
  private final Synthesizer synth;
  private final Receiver receiver;

  public MidiViewImpl() {
    Synthesizer temps = null;
    Receiver tempr = null;

    try {
      temps = MidiSystem.getSynthesizer();
      tempr = temps.getReceiver();
      temps.open();

    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }

    this.synth = temps;
    this.receiver = tempr;
  }


  /**
   * Relevant classes and methods from the javax.sound.midi library: <ul> <li>{@link
   * MidiSystem#getSynthesizer()}</li> <li>{@link Synthesizer} <ul> <li>{@link
   * Synthesizer#open()}</li> <li>{@link Synthesizer#getReceiver()}</li> <li>{@link
   * Synthesizer#getChannels()}</li> </ul> </li> <li>{@link Receiver} <ul> <li>{@link
   * Receiver#send(MidiMessage, long)}</li> <li>{@link Receiver#close()}</li> </ul> </li> <li>{@link
   * MidiMessage}</li> <li>{@link ShortMessage}</li> <li>{@link MidiChannel} <ul> <li>{@link
   * MidiChannel#getProgram()}</li> <li>{@link MidiChannel#programChange(int)}</li> </ul> </li>
   * </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI"> https://en.wikipedia.org/wiki/General_MIDI
   * </a>
   */
  public void playComposition(MusicCreator c) {
    for (int i = 0; i <= c.getSongDuration(); i++) {
      try {
        playBeat((ArrayList<Note>) c.notesAtBeat(i));

      } catch (InvalidMidiDataException e) {
        e.getStackTrace();
      }
    }
    this.receiver.close();
  }

  public void playBeat(ArrayList<Note> list) throws InvalidMidiDataException {
    for (Note n : list) {
      //TODO Uncouple this (DIVORCE)
      this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 1, n.getKeyVal(), 64), -1);
      try {
        Thread.sleep(Integer.MAX_VALUE);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      this.receiver.send(new ShortMessage(ShortMessage.NOTE_OFF, 1, n.getKeyVal(), 64)
              , this.synth.getMicrosecondPosition() +  1000000);
    }
  }


  public static void main(String[] args) {
    MidiViewImpl m = new MidiViewImpl();
    MusicCreator c = new MusicCreatorImpl();
    c.addNote(new Note(0, Note.Pitch.B, 1, 5));
    c.addNote(new Note(1, Note.Pitch.A, 1,5));
    c.addNote(new Note(2, Note.Pitch.G, 1, 5));
    c.addNote(new Note(4, Note.Pitch.B, 1,5));
    c.addNote(new Note(5, Note.Pitch.A, 1,5));
    c.addNote(new Note(6, Note.Pitch.G, 1, 5));
    m.playComposition(c);
  }
}
