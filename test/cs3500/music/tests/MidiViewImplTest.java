package cs3500.music.tests;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.model.Note;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MockSequencer;
import org.junit.Test;

import javax.sound.midi.*;

import static org.junit.Assert.*;

/**
 * Created by baharsheikhi on 6/18/16.
 * A class to test the midi view
 */
public class MidiViewImplTest {
    MockSequencer sequencer;
    MusicCreator c;
    MidiViewImpl midiView;

    void initData() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.sequencer = new MockSequencer();
        this.c = new MusicCreatorImpl();
        this.midiView = new MidiViewImpl(this.sequencer, this.c);
    }


    @Test
    public void simpleTest() {
        this.initData();
        c.addNote(new Note(0, 59, 1, 4, 100));
        c.addNote(new Note(1, 57, 1, 4, 100));
        this.midiView.initialize();

        assertEquals("Status: ON  Instrument 3 Oct/Pitch Value: 59 Volume: 100 Position: 0\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 57 Volume: 100 Position: 1\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 59 Volume: 100\n" +
                " Position: 2\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 57 Volume: 100\n" +
                " Position: 3\n", this.sequencer.getResult());
    }

    @Test
    public void testHotCrossBuns() throws InvalidMidiDataException {
        this.initData();
        c.addNote(new Note(0, 59, 1, 4, 100));
        c.addNote(new Note(1, 57, 1, 4, 100));
        c.addNote(new Note(2, 55, 1, 4, 100));

        c.addNote(new Note(3, 59, 1, 4, 100));
        c.addNote(new Note(4, 57, 1, 4, 100));
        c.addNote(new Note(5, 55, 1, 4, 100));

        c.addNote(new Note(6, 55, 1, 4, 100));
        c.addNote(new Note(7, 55, 1, 4, 100));
        c.addNote(new Note(8, 55, 1, 4, 100));
        c.addNote(new Note(9, 55, 1, 4, 100));

        c.addNote(new Note(10, 57, 1, 4, 100));
        c.addNote(new Note(11, 57, 1, 4, 100));
        c.addNote(new Note(12, 57, 1, 4, 100));
        c.addNote(new Note(13, 57, 1, 4, 100));

        c.addNote(new Note(14, 59, 1, 4, 100));
        c.addNote(new Note(15, 57, 1, 4, 100));
        c.addNote(new Note(16, 55, 1, 4, 100));

        this.midiView.initialize();

        assertEquals("Status: ON  Instrument 3 Oct/Pitch Value: 59 Volume: 100 Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 59 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 57 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 59 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 55 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 59 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 57 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 55 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 55 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 55 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 55 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 55 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 57 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 57 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 57 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 59 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 57 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 57 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 59 Volume: 100\n" +
                " Length: 3\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 55 Volume: 100 Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 57 Volume: 100\n" +
                " Length: 3\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 55 Volume: 100\n" +
                " Length: 3\n", this.sequencer.getResult());

    }

    @Test
    public void testEmptySong() {
        this.initData();
        assertEquals("", this.sequencer.getResult());
    }

    @Test
    public void testBigRangeKeyVal() throws InvalidMidiDataException {
        this.initData();
        c.addNote(new Note(0, 3, 1, 4, 100));
        c.addNote(new Note(1, 100, 1, 4, 100));
        this.midiView.initialize();

        assertEquals("Status: ON  Instrument 3 Oct/Pitch Value: 3 Volume: 100 Position: 0\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 100 Volume: 100 Position: 1\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 3 Volume: 100\n" +
                " Position: 2\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 100 Volume: 100\n" +
                " Position: 3\n", this.sequencer.getResult());

    }

    @Test
    public void testBigRange() throws InvalidMidiDataException {
        this.initData();
    }

    @Test
    public void testBigRangeVolume() throws InvalidMidiDataException {
        this.initData();
        c.addNote(new Note(0, 55, 1, 4, 5));
        c.addNote(new Note(1, 55, 1, 4, 127));
        this.midiView.initialize();

        assertEquals("Status: ON  Instrument 3 Oct/Pitch Value: 5 Volume: 5 Position: 0\n" +
                "Status: ON  Instrument 3 Oct/Pitch Value: 55 Volume: 127 Position: 1\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 55 Volume: 5\n" +
                " Position: 2\n" +
                "Status: OFF  Instrument 3 Oct/Pitch Value: 55 Volume: 127\n" +
                " Position: 3\n", this.sequencer.getResult());


    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeKeyVal() throws InvalidMidiDataException {
        this.initData();
        c.addNote(new Note(0, -1, 1, 4, 5));
        this.midiView.initialize();
    }

    //FIXME
    @Test(expected = InvalidMidiDataException.class)
    public void testTooBigKey() throws InvalidMidiDataException {
        this.initData();
        c.addNote(new Note(0, 128, 1, 4, 5));
        this.midiView.initialize();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeVolume() throws InvalidMidiDataException {
        this.initData();
        c.addNote(new Note(0, 55, 1, 4, -1));
        this.midiView.initialize();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVolumeTooBig() throws InvalidMidiDataException {
        this.initData();
        c.addNote(new Note(0, 55, 1, 4, 128));
        this.midiView.initialize();
    }

    @Test
    public void testBigSong() throws InvalidMidiDataException {
        this.initData();
        for (int i = 0; i < 30000; i++) {
        }
        //TODO
        assertEquals(40, this.sequencer.toString().length());
    }


//TODO Channel?????
}