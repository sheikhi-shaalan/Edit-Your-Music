package cs3500.music.view;

import java.awt.*;

import javax.sound.midi.*;
import javax.swing.*;

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
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   * </a>
   */


  public void playNote() throws InvalidMidiDataException {
    for (int i = 0; i < 25; i++) {
      MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
      MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
      this.receiver.send(start, -1);
      this.receiver.send(stop, this.synth.getMicrosecondPosition() + 2000000000);
      // TODO CHANGE THIS TO A JAVA TIMER
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  public static void main(String[] args) {
    MidiViewImpl m = new MidiViewImpl();
      try {
        m.playNote();
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }
  }
}
