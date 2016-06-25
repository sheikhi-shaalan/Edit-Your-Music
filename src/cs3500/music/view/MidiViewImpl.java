package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.*;

import javax.sound.midi.*;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.model.Note;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements IView, Playable{
  private final Synthesizer synth;
  private final Receiver receiver;
  protected final Sequencer sequencer;
  private final Sequence sequence;
  private final int ONE_BEAT_COEFF = 1000000;
  private final int SLEEP_NUMBER = 1000;
  protected MusicCreator c;
  private boolean isPlaying;

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

  public MidiViewImpl(Sequencer sequencer, MusicCreator creator) {
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
    this.sequencer = sequencer;
    this.sequence = tempseq;
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

    //this.sequencer.start();
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

  @Override
  public void refresh(MusicCreator c) {
    this.c = c;
    for (Track t: this.sequence.getTracks()) {
      sequence.deleteTrack(t);
    }
    this.playComposition();
  }

  @Override
  public void play() {
      this.isPlaying = true;
    this.sequencer.setTickPosition(this.sequencer.getTickPosition());
    this.sequencer.setTempoInMPQ(this.c.getTempo());
      this.playComposition();
    this.sequencer.start();
  }

  public void pause() {
      this.isPlaying = false;
    this.sequencer.stop();
    this.sequencer.setTickPosition(this.sequencer.getTickPosition());
  }

  public void reset() { this.sequencer.setTickPosition(0);}


  @Override
  public void skipToEnd() {
      this.isPlaying = false;
    this.sequencer.setTickPosition(sequencer.getTickLength() -1 );
    this.pause();

  }

  @Override
  public boolean isPlaying() {
    return (this.sequencer.getTickPosition() <= this.c.getSongDuration()) && this.isPlaying;
  }

}