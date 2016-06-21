package cs3500.music.view;

import javax.sound.midi.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by baharsheikhi on 6/20/16.
 */
public class MockSequencer implements Sequencer {
    private  final StringBuilder stringBuiler;
    private final Sequencer sequencer;
    private  Sequence sequence;

    public MockSequencer() {
        this.stringBuiler = new StringBuilder();
        Sequencer temps = null;
        Sequence tempseq = null;
        try {
            temps = MidiSystem.getSequencer();
            tempseq = new Sequence(Sequence.PPQ, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.sequencer = temps;
        this.sequence = tempseq;
        try {
            this.sequencer.setSequence(this.sequence);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start() {
        for (Track t: this.getSequence().getTracks()) {
            for (int i = 0; i < t.size(); i++) {
                try {
                    MidiEvent event = t.get(i);
                    ShortMessage shortMessage = (ShortMessage) t.get(i).getMessage();
                    if (shortMessage.getCommand() == ShortMessage.NOTE_ON) {
                        this.stringBuiler.append("Status: " + "ON " + " Instrument " + shortMessage.getChannel()
                                + " Oct/Pitch Value: " +
                                shortMessage.getData1() + " Volume: " + shortMessage.getData2() +
                                " Position: " + event.getTick() + "\n");
                    } else if (shortMessage.getCommand() == ShortMessage.NOTE_OFF) {
                        this.stringBuiler.append("Status: " + "OFF " + " Instrument " + shortMessage.getChannel()
                                + " Oct/Pitch Value: " +
                                shortMessage.getData1() + " Volume: " + shortMessage.getData2() + "\n" +
                                " Position: " + event.getTick() + "\n");
                    }
                }
                catch (ClassCastException c) {

                }
            }
        }
    }

    @Override
    public void setSequence(Sequence sequence) throws InvalidMidiDataException {
       this.sequence = sequence;
    }

    @Override
    public void setSequence(InputStream stream) throws IOException, InvalidMidiDataException {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public Sequence getSequence() {
        return this.sequence;
    }

    public String getResult() {
        return this.stringBuiler.toString();
    }

    @Override
    public void stop() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public boolean isRunning() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void startRecording() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void stopRecording() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public boolean isRecording() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void recordEnable(Track track, int channel) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void recordDisable(Track track) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public float getTempoInBPM() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void setTempoInBPM(float bpm) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public float getTempoInMPQ() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void setTempoInMPQ(float mpq) {
        this.sequencer.setTempoInMPQ(mpq);
    }

    @Override
    public void setTempoFactor(float factor) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public float getTempoFactor() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public long getTickLength() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public long getTickPosition() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void setTickPosition(long tick) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public long getMicrosecondLength() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public Info getDeviceInfo() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void open() throws MidiUnavailableException {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void close() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public boolean isOpen() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public long getMicrosecondPosition() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public int getMaxReceivers() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public int getMaxTransmitters() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public Receiver getReceiver() throws MidiUnavailableException {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public List<Receiver> getReceivers() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public Transmitter getTransmitter() throws MidiUnavailableException {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public List<Transmitter> getTransmitters() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void setMicrosecondPosition(long microseconds) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void setMasterSyncMode(SyncMode sync) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public SyncMode getMasterSyncMode() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public SyncMode[] getMasterSyncModes() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void setSlaveSyncMode(SyncMode sync) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public SyncMode getSlaveSyncMode() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public SyncMode[] getSlaveSyncModes() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void setTrackMute(int track, boolean mute) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public boolean getTrackMute(int track) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void setTrackSolo(int track, boolean solo) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public boolean getTrackSolo(int track) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public boolean addMetaEventListener(MetaEventListener listener) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void removeMetaEventListener(MetaEventListener listener) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public int[] addControllerEventListener(ControllerEventListener listener, int[] controllers) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public int[] removeControllerEventListener(ControllerEventListener listener, int[] controllers) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void setLoopStartPoint(long tick) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public long getLoopStartPoint() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void setLoopEndPoint(long tick) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public long getLoopEndPoint() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public void setLoopCount(int count) {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }

    @Override
    public int getLoopCount() {
        throw new IllegalArgumentException("Cannot access this method in a mock Sequencer");
    }
}
