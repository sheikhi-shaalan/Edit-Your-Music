package cs3500.music.view;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.Note;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements IView, Playable {
  private final Synthesizer synth;
  private final Receiver receiver;
  protected final Sequencer sequencer;
  private final Sequence sequence;
  protected MusicCreator c;
  protected boolean isPlaying;

  /**
   * Creates a synthesizer, receiver, sequencer, and sequence and links them properly
   *
   * @param creator the model of this midiViewImpl
   */
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

  /**
   * Creates a midiViewImpl with the given sequencer and creator Creates a synthesizer, receiver,
   * sequencer, and sequence and links them properly
   *
   * @param sequencer the sequencer of this midiViewImpl
   * @param creator   the creator of this midiViewImpl
   */
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
  /**
   * plays a complete composition
   */
  private void playComposition() {
    Track track = sequence.createTrack();

    this.sequencer.setTempoInMPQ(c.getTempo());
    for (int i = 0; i <= c.getSongDuration(); i++) {
      try {
        this.playBeat(c.notesAtBeat(i), track);
      } catch (InvalidMidiDataException e) {
        e.getStackTrace();
      }
    }
    try {
      this.sequencer.setSequence(this.sequence);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }

  }

  /**
   * Plays all the notes at a specific beat
   *
   * @param list  the list of notes to play
   * @param track the track to add them to
   */
  private void playBeat(List<Note> list, Track track) throws InvalidMidiDataException {
    for (Note n : list) {

      ShortMessage start = null;
      ShortMessage end = null;
      try {
        start = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1,
                n.getKeyVal(), n.getVolume());
        end = new ShortMessage(ShortMessage.NOTE_OFF,
                n.getInstrument() - 1, n.getKeyVal(), n.getVolume());

      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }

      MidiEvent eventOn = new MidiEvent(start, n.getStartbeatNo());
      MidiEvent eventOff = new MidiEvent(end, n.getStartbeatNo() + n.getDuration() + 1);


      track.add(eventOn);
      track.add(eventOff);
    }

  }

  @Override
  public void initialize() {
    this.playComposition();
    this.sequencer.start();
  }

  @Override
  public void refresh(MusicCreator c) {
    this.c = c;
    for (Track t : this.sequence.getTracks()) {
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

  @Override
  public void pause() {
    this.isPlaying = false;
    this.sequencer.stop();
    this.sequencer.setTickPosition(this.sequencer.getTickPosition());
  }

  @Override
  public void reset() {
    this.isPlaying = false;
    this.sequencer.setTickPosition(0);
  }


  @Override
  public void skipToEnd() {
    this.isPlaying = false;
    this.sequencer.setTickPosition(sequencer.getTickLength() - 1);
    this.pause();

  }

  @Override
  public boolean isPlaying() {
    return this.sequencer.getTickPosition() <= (this.c.getSongDuration()) && this.isPlaying;
  }


}