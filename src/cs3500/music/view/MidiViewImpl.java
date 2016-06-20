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

  public MidiViewImpl() {
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

  public MidiViewImpl(Synthesizer s, Receiver r) {
    Synthesizer temps = null;
    Receiver tempr = null;
    Sequencer tempseqr = null;
    Sequence tempseq = null;
    try {
      temps = s;
      tempr = r;
      tempseqr = MidiSystem.getSequencer(false);
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
      Track track = sequence.createTrack();
    this.sequencer.setTempoInMPQ(c.getTempo());
    for (int i = 0; i <= c.getSongDuration(); i++) {
      try {
        this.playBeat(c.notesAtBeat(i), track, i);
      } catch (InvalidMidiDataException e) {
        e.getStackTrace();
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
    }

  }

  private void playBeat(List<Note> list, Track track, int beatNo) throws InvalidMidiDataException, InterruptedException {
    for (Note n : list) {

        ShortMessage start = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1, n.getKeyVal(), n.getVolume());
        ShortMessage end = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument() - 1, n.getKeyVal(), n.getVolume());


      MidiEvent eventOn = new MidiEvent(start, n.getStartbeatNo());
      MidiEvent eventOff = new MidiEvent(end, n.getStartbeatNo() + n.getDuration() + 1);


      track.add(eventOn);
      track.add(eventOff);

    }

      try {
          this.sequencer.setSequence(this.sequence);
      } catch (InvalidMidiDataException e) {
          e.printStackTrace();
      }

      this.sequencer.setTickPosition(beatNo);
      this.sequencer.start();
      Thread.sleep((int) Math.floor(sequencer.getTempoInMPQ() / 1000.0),
              Math.floorMod((int) sequencer.getTempoInMPQ(), 1000) * 1000);
      this.sequencer.stop();

  }

  public static void main(String[] args) {
    MusicCreatorImpl.Builder b = MusicCreatorImpl.getBuilder();
    MusicCreator c = b.setTempo(1000000).build();
    c.addNote(new Note(0, Note.Pitch.B, 1, 4));
    c.addNote(new Note(1, Note.Pitch.A, 1, 4));
    c.addNote(new Note(2, Note.Pitch.G, 1, 4));
    c.addNote(new Note(3, Note.Pitch.B, 1, 4));
    c.addNote(new Note(4, Note.Pitch.A, 1, 4));
    c.addNote(new Note(5, Note.Pitch.G, 1, 4));
    MidiViewImpl v = new MidiViewImpl();
    v.playComposition(c);
  }
}