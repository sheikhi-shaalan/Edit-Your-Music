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
    Sequence  tempseq = null;

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
      this.sequencer.setTempoInMPQ(c.getTempo());
    for (int i = 0; i <= c.getSongDuration(); i++) {
      try {
          this.playBeat(c.notesAtBeat(i));
          //this.sequencer.setTickPosition(this.sequencer.getTickPosition() + 1);
      } catch (InvalidMidiDataException e) {
        e.getStackTrace();
      }
    }
      try {
          this.sequencer.setSequence(this.sequence);
      } catch (InvalidMidiDataException e) {
          e.printStackTrace();
      }

      //this.sequencer.setTickPosition(0);
      this.sequencer.start();

      if (this.sequencer.getTickPosition() == this.sequencer.getTickLength()) {
          this.sequencer.close();
          this.receiver.close();
      }
  }

  private void playBeat(List<Note> list) throws InvalidMidiDataException {
      Track track = sequence.createTrack();
    for (Note n : list) {

        ShortMessage start = null;
        ShortMessage end = null;
        ShortMessage changeSound = null;
        try {
            changeSound = new ShortMessage(ShortMessage.PROGRAM_CHANGE, n.getInstrument(), 0);
            start = new ShortMessage(ShortMessage.NOTE_ON, 0, n.getKeyVal(), n.getVolume());
            end = new ShortMessage(ShortMessage.NOTE_OFF, 0, n.getKeyVal(), n.getVolume());

        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }


        MidiEvent eventOn = new MidiEvent(start, n.getStartbeatNo());
        MidiEvent eventChangeSound = new MidiEvent(changeSound, n.getStartbeatNo());
        MidiEvent eventOff = new MidiEvent(end, n.getStartbeatNo() + n.getDuration() + 1);


        track.add(eventChangeSound);
        track.add(eventOn);
        track.add(eventOff);

    }

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

    /**
    public static void main(String[] args) {
        Synthesizer synth;
        Receiver receiver;
        Sequencer sequencer;
        Sequence sequence;
        Synthesizer temps = null;
        Receiver tempr = null;
        Sequencer tempseqr = null;
        Sequence  tempseq = null;

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


        synth = temps;
        receiver = tempr;
        sequencer = tempseqr;
        sequence = tempseq;
        sequencer.setTempoInMPQ(1000000);

//----------------------------------------------------------------------

        Track track = sequence.createTrack();


        ShortMessage start = null;
        ShortMessage end = null;
        ShortMessage changeSound = null;
        try {
            changeSound = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 15, 0);
            start = new ShortMessage(ShortMessage.NOTE_ON, 0, 59, 100);
            end = new ShortMessage(ShortMessage.NOTE_OFF, 0, 59, 100);

        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }


        MidiEvent eventOn = new MidiEvent(start, 1);
        MidiEvent eventChangeSound = new MidiEvent(changeSound, 1);
        MidiEvent eventOff = new MidiEvent(end, 2);

        track.add(eventChangeSound);
        track.add(eventOn);
        track.add(eventOff);


//----------------------------------------------------------------------


        ShortMessage start2 = null;
        ShortMessage end2 = null;
        ShortMessage changeSound2 = null;
        try {
            changeSound2 = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 70, 0);
            start2 = new ShortMessage(ShortMessage.NOTE_ON, 0, 57, 100);
            end2 = new ShortMessage(ShortMessage.NOTE_OFF, 0, 57, 100);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

        MidiEvent evenOn2 = new MidiEvent(start2, 2);
        MidiEvent eventChangeSound2 = new MidiEvent(changeSound2, 2);
        MidiEvent eventOff2 = new MidiEvent(end2, 3);

        track.add(eventChangeSound2);
        track.add(evenOn2);
        track.add(eventOff2);


//----------------------------------------------------------------------

        ShortMessage start3 = null;
        ShortMessage end3 = null;
        ShortMessage changeSound3 = null;
        try {
            changeSound3 = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 27, 0);
            start3 = new ShortMessage(ShortMessage.NOTE_ON, 0, 55, 100);
            end3 = new ShortMessage(ShortMessage.NOTE_OFF, 0, 55, 100);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        MidiEvent evenOn3 = new MidiEvent(start3, 3);
        MidiEvent eventChangeSound3 = new MidiEvent(changeSound3, 3);
        MidiEvent eventOff3 = new MidiEvent(end3, 4);

        track.add(eventChangeSound3);
        track.add(evenOn3);
        track.add(eventOff3);

//----------------------------------------------------------------------
        try {
            sequencer.setSequence(sequence);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

        sequencer.start();

        if (sequencer.getTickPosition() == sequencer.getTickLength()) {
            System.out.print("end");
            sequencer.close();
            receiver.close();
        }

    }
     **/
}