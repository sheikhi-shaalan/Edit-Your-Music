package cs3500.music.tests;

import cs3500.music.view.MockMidi;
import cs3500.music.view.MockMidiReciever;
import org.junit.Test;

import javax.sound.midi.*;

import static org.junit.Assert.*;

/**
 * Created by baharsheikhi on 6/18/16.
 */
public class MidiViewImplTest {
  Synthesizer synth;
  MockMidiReciever receiver;

  void initData() {
    this.synth = new MockMidi();
    try {
      this.receiver = (MockMidiReciever) synth.getReceiver();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testHotCrossBuns() throws InvalidMidiDataException {
    this.initData();
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 59, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 57, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 55, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 59, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 57, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 55, 100), 1);

    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 55, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 55, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 55, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 55, 100), 1);

    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 57, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 57, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 57, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 57, 100), 1);

    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 59, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 57, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 55, 100), 1);


    assertEquals("Status: ON  Instrument 0 Oct/Pitch Value: 59 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 59 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 59 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 55 Volume: 100 Length: 3\n",
            this.receiver.getResult());

  }

  @Test
  public void testNoteChange() throws InvalidMidiDataException {
    this.initData();

    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 59, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 57, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 55, 100), 1);

    this.receiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE, 127, 0), 1);

    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 59, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 57, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 55, 100), 1);

    assertEquals("Status: ON  Instrument 0 Oct/Pitch Value: 59 Volume: 100 Length: 3\n" +
            "Status: ON  Instrument 0 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
            "Status: ON  Instrument 0 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
            "Status: ON  Instrument 0 Oct/Pitch Value: 59 Volume: 100 Length: 3\n" +
            "Status: ON  Instrument 0 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
            "Status: ON  Instrument 0 Oct/Pitch Value: 55 Volume: 100 Length: 3\n", this.receiver.getResult());
  }


  @Test
  public void testEmptySong() {
    this.initData();
    assertEquals("", this.receiver.getResult());
  }

  @Test
  public void testBigRangeKeyVal() throws InvalidMidiDataException {
    this.initData();

    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 3, 100), 1);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 120, 100), 1);

    assertEquals("Status: ON  Instrument 0 Oct/Pitch Value: 3 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 120 Volume: 100 Length: 3\n",
            this.receiver.getResult());
  }

  @Test
  public void testBigRange() throws InvalidMidiDataException {
    this.initData();
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 3, 100), 3);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 3, 100), 100);

    assertEquals("Status: ON  Instrument 0 Oct/Pitch Value: 3 Volume: 100 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 3 Volume: 100 Length: 3\n",
            this.receiver.getResult());
  }

  @Test
  public void testBigRangeVolume() throws InvalidMidiDataException {
    this.initData();

    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 3, 2), 3);
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 3, 120), 100);

    assertEquals("Status: ON  Instrument 0 Oct/Pitch Value: 3 Volume: 2 Length: 3\n" +
                    "Status: ON  Instrument 0 Oct/Pitch Value: 3 Volume: 120 Length: 3\n",
            this.receiver.getResult());

  }

  @Test(expected = InvalidMidiDataException.class)
  public void testNegativeKeyVal() throws InvalidMidiDataException{
    this.initData();
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, -3, 2), 3);
  }

  @Test(expected = InvalidMidiDataException.class)
  public void testTooBigKey() throws InvalidMidiDataException {
    this.initData();
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 128, 2), 3);
  }

  @Test(expected = InvalidMidiDataException.class)
  public void testNegativeVolume() throws InvalidMidiDataException{
    this.initData();
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 50, -5), 3);
  }

  @Test(expected = InvalidMidiDataException.class)
  public void testVolumeTooBig() throws InvalidMidiDataException {
    this.initData();
    this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 50, 128), 3);
  }

  @Test
  public void testBigSong() throws InvalidMidiDataException{
    this.initData();
    for (int i = 0; i < 30000; i++) {
      this.receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 59, 100), 1);
    }

    assertEquals(2010000, this.receiver.getResult().length());
  }
}