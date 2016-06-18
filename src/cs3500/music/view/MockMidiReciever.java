package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 * Created by NadineShaalan on 6/18/16.
 */
public class MockMidiReciever implements Receiver {

  @Override
  public void send(MidiMessage message, long timeStamp) {

  }

  @Override
  public void close() {

  }
}
