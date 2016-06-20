package cs3500.music.view;

import java.util.*;

import javax.sound.midi.*;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.model.Note;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements IView {
  private final Synthesizer synth;
  private final Receiver receiver;
  private final Sequencer sequencer;
  private final Sequence sequence;
  private final int ONE_BEAT_COEFF = 1000000;
  private final int SLEEP_NUMBER = 1000;
  MusicCreator c;
  public MidiViewImpl(MusicCreator creator) {
    this.c = creator;
    Synthesizer temps = null;
    Receiver tempr = null;
    Sequencer tempseqr = null;
    Sequence tempseq = null;

    try {
      temps = MidiSystem.getSynthesizer();
      tempr = temps.getReceiver();
      tempseqr = MidiSystem.getSequencer();
      tempseq = new Sequence(Sequence.PPQ, 1);
      temps.open();
      tempseqr.open();
      tempseqr.getTransmitter().setReceiver(tempr);

    } catch (Exception e) {
      e.printStackTrace();
    }

    this.synth = temps;
    this.receiver = tempr;
    this.sequencer = tempseqr;
    this.sequence = tempseq;

  }

  public MidiViewImpl(Synthesizer s, Receiver r, MusicCreator creator) {
    this.c = creator;
    Synthesizer temps = null;
    Receiver tempr = null;
    Sequencer tempseqr = null;
    Sequence tempseq = null;
    try {
      temps = s;
      tempr = r;
      tempseqr = MidiSystem.getSequencer();
      tempseq = new Sequence(Sequence.PPQ, 1);
      temps.open();
      tempseqr.open();
      tempseqr.getTransmitter().setReceiver(tempr);

    } catch (Exception e) {
      e.printStackTrace();
    }
    this.synth = temps;
    this.receiver = tempr;
    this.sequencer = tempseqr;
    this.sequence = tempseq;
  }


  /**
   * Relevant classes and methods from the javax.sound.midi library: <ul> <li>{@link
   * MidiSystem#getSynthesizer()}</li> <li>{@link Synthesizer} <ul> <li>{@link
   * Synthesizer#open()}</li> <li>{@link Synthesizer#getReceiver()}</li> <li>{@link
   * SynthesiCCzer#getChannels()}</li> </ul> </li> <li>{@link Receiver} <ul> <li>{@link
   * Receiver#send(MidiMessage, long)}</li> <li>{@link Receiver#close()}</li> </ul> </li> <li>{@link
   * MidiMessage}</li> <li>{@link ShortMessage}</li> <li>{@link MidiChannel} <ul> <li>{@link
   * MidiChannel#getProgram()}</li> <li>{@link MidiChannel#programChange(int)}</li> </ul> </li>
   * </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   * </a>
   */
  // Plays a complete composition
  private void playComposition() {
    Track track = sequence.createTrack();

    this.sequencer.setTempoInMPQ(c.getTempo());
    for (int i = 0; i <= c.getSongDuration(); i++) {
      try {
        this.playBeat(c.notesAtBeat(i),track);
      } catch (InvalidMidiDataException e) {
        e.getStackTrace();
      }
    }
    try {
      this.sequencer.setSequence(this.sequence);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }

    this.sequencer.start();

    if (this.sequencer.getTickPosition() == this.sequencer.getTickLength()) {
      System.out.print("end");
      this.sequencer.close();
      this.receiver.close();
    }
  }

  // Plays all the notes at a specific beat
  private void playBeat(List<Note> list, Track track) throws InvalidMidiDataException {
    for (Note n : list) {

      ShortMessage start = null;
      ShortMessage end = null;
      ShortMessage changeSound = null;
      try {
        start = new ShortMessage(ShortMessage.NOTE_ON,  n.getInstrument()-1,
                n.getKeyVal(), n.getVolume());
        end = new ShortMessage(ShortMessage.NOTE_OFF,
                n.getInstrument()-1, n.getKeyVal(), n.getVolume());

      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }

      MidiEvent eventOn = new MidiEvent(start, n.getStartbeatNo());
      MidiEvent eventOff = new MidiEvent(end, n.getStartbeatNo() + n.getDuration() + 1);


      track.add(eventOn);
      track.add(eventOff);

    }

  }


  // Full culmination
  @Override
  public void initialize() {
    this.playComposition();
  }
}