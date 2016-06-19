package cs3500.music.view;

import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.sound.midi.VoiceStatus;

/**
 * Created by NadineShaalan on 6/17/16.
 */
public class MockMidi implements Synthesizer {

  StringBuilder result;

  public MockMidi() {
    this.result = new StringBuilder();
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return new MockMidiReciever(this.result);
  }

  @Override
  public int getMaxPolyphony() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public long getLatency() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public MidiChannel[] getChannels() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public VoiceStatus[] getVoiceStatus() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public boolean isSoundbankSupported(Soundbank soundbank) {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public boolean loadInstrument(Instrument instrument) {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public void unloadInstrument(Instrument instrument) {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public boolean remapInstrument(Instrument from, Instrument to) {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public Soundbank getDefaultSoundbank() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public Instrument[] getAvailableInstruments() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public Instrument[] getLoadedInstruments() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public boolean loadAllInstruments(Soundbank soundbank) {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public void unloadAllInstruments(Soundbank soundbank) {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public boolean loadInstruments(Soundbank soundbank, Patch[] patchList) {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public void unloadInstruments(Soundbank soundbank, Patch[] patchList) {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public Info getDeviceInfo() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public void open() throws MidiUnavailableException {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public void close() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public boolean isOpen() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public long getMicrosecondPosition() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public int getMaxReceivers() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public int getMaxTransmitters() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public List<Receiver> getReceivers() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }

  @Override
  public List<Transmitter> getTransmitters() {
    throw new IllegalArgumentException("Cannot call this method on a mock midi device");
  }
}