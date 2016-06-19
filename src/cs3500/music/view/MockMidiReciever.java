package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * Created by NadineShaalan on 6/18/16.
 */
public class MockMidiReciever implements Receiver {

  StringBuilder result;

  public MockMidiReciever(StringBuilder result) {
    this.result = result;
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage shortMessage = (ShortMessage) message;
    if (shortMessage.getCommand() == ShortMessage.NOTE_ON) {
      this.result.append("Status: " + "ON " + " Channel " + shortMessage.getChannel()
              + " Oct/Pitch Value: " + shortMessage.getData1() + " Volume: " + shortMessage.getData2() +
              " Length: " + shortMessage.getLength() + "\n");
    } else if (shortMessage.getCommand() == ShortMessage.NOTE_OFF) {
      this.result.append("Status: " + "OFF " + " Channel " + shortMessage.getChannel()
              + " Oct/Pitch Value: " + shortMessage.getData1() + " Volume: " + shortMessage.getData2() + "\n" +
              " Length: " + shortMessage.getLength() + "\n");
    } else if (shortMessage.getCommand() == ShortMessage.PROGRAM_CHANGE) {
      this.result.append("Instrument: " + shortMessage.getData1() + " Channel: " + shortMessage.getData2() + "\n");

    }
  }

  public String getResult() {
    return result.toString();
  }

  @Override
  public void close() {
    throw new IllegalArgumentException("Cannot call this method on a mock reciever");
  }
}