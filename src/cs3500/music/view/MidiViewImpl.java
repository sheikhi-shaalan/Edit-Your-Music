package cs3500.music.view;

import java.util.*;

import javax.sound.midi.*;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.Note;


public class MidiViewImpl implements IView, Playable {
  private final Synthesizer synth;
  private final Receiver receiver;
  protected final Sequencer sequencer;
  private final Sequence sequence;
  protected MusicCreator c;


  protected boolean midiPlaying;


  public MidiViewImpl(MusicCreator creator) {
    this.c = creator;
    Synthesizer temps = null;
    Receiver tempr = null;
    Sequencer tempseqr = null;
    Sequence tempseq = null;
    this.midiPlaying = true;

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

  // Plays a complete composition
  protected void playComposition() {
    Track track = sequence.createTrack();

    this.sequencer.setTempoInMPQ(c.getTempo());
    for (int i = 0; i <= c.getSongDuration(); i++) {
      try {
        this.playBeat(c.notesAtBeat(i), track);
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

  // Plays all the notes at a specific beat
  private void playBeat(List<Note> list, Track track) throws InvalidMidiDataException {
    for (Note n : list) {

      ShortMessage start = null;
      ShortMessage end = null;
      ShortMessage changeSound = null;
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

  // Full culmination
  @Override
  public void initialize() {
    this.playComposition();
    if (midiPlaying) {
      this.sequencer.start();
    }
  }


  @Override
  public void refresh(MusicCreator c) {
    this.c = c;
    for (Track t : this.sequence.getTracks()) {
      sequence.deleteTrack(t);
    }
    this.playComposition();
  }


  // Plays the music
  @Override
  public void play() {
    this.midiPlaying = true;
    this.sequencer.setTickPosition(this.sequencer.getTickPosition());
    this.sequencer.setTempoInMPQ(this.c.getTempo());
    this.sequencer.start();
  }

  // Pauses the music
  @Override
  public void pause() {
    this.midiPlaying = false;
    this.sequencer.stop();

    this.sequencer.setTickPosition(this.sequencer.getTickPosition());
  }

  //Sets the song to the beginning
  @Override
  public void reset() {
    this.sequencer.setTickPosition(0);
  }

  // Sends the song to the end
  @Override
  public void skipToEnd() {
    this.sequencer.setTickPosition(sequencer.getTickLength() - 1);
    this.midiPlaying = false;
    this.pause();

  }

  @Override
  public boolean isPlaying() {
    return this.sequencer.getTickPosition() < this.sequencer.getTickLength();
  }
}