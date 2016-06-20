package cs3500.music.view;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MockMidiReciever implements Receiver {

  StringBuilder result;

  public MockMidiReciever(StringBuilder result) {
    this.result = result;
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage shortMessage = (ShortMessage) message;
    if (shortMessage.getCommand() == ShortMessage.NOTE_ON) {
      this.result.append("Status: " + "ON " + " Instrument " + shortMessage.getChannel()
              + " Oct/Pitch Value: " +
              shortMessage.getData1() + " Volume: " + shortMessage.getData2() +
              " Length: " + shortMessage.getLength() + "\n");
    } else if (shortMessage.getCommand() == ShortMessage.NOTE_OFF) {
      this.result.append("Status: " + "OFF " + " Instrument " + shortMessage.getChannel()
              + " Oct/Pitch Value: " +
              shortMessage.getData1() + " Volume: " + shortMessage.getData2() + "\n" +
              " Length: " + shortMessage.getLength() + "\n");
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