package cs3500.music.tests;

import cs3500.music.model.*;
import cs3500.music.view.*;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Created by baharsheikhi on 6/15/16.
 */
public class ConsoleViewTest {
    MusicCreator model;
    MusicCreator model2;
    ConsoleView view;

    public void initData() {
        this.model = new MusicCreatorImpl();
        this.model2 = new MusicCreatorImpl();
        this.view = new ConsoleView();
    }

    @Test
    public void testSimpleAdd() {
        this.initData();
        this.model.addNote(new Note(0, Note.Pitch.E, 3, 4));
        assertEquals("   E4 \n" +
                "0  X  \n" +
                "1  |  \n" +
                "2  |  \n", this.view.render(this.model));
        //adding the exact same note of the same duration
        this.model.addNote(new Note(0, Note.Pitch.E, 3, 4));
        assertEquals("   E4 \n" +
                "0  X  \n" +
                "1  |  \n" +
                "2  |  \n", this.view.render(this.model));
        this.model2.addNote(new Note(0, Note.Pitch.E, 3, 4));
        assertEquals(this.view.render(this.model), this.view.render(this.model2));
    }

    @Test
    public void testOverlapNote() {
            this.initData();
            //at beat 0, octave 7, lasts 3 beats, and is in octave 7
            this.model.addNote(
                    new Note(0, Note.Pitch.C, 3, 7));
            //at beat 2, octave 7, lasts 2 beats, and is in octave 7
            this.model.addNote(
                    new Note(2, Note.Pitch.C, 2, 7));
    assertEquals("   C7 \n" +
                    "0  X  \n" +
                    "1  |  \n" +
                    "2  X  \n" +
                    "3  |  \n",
            this.view.render(this.model));
    }

    @Test
    public void testOverlapNoteRemove() {
            this.initData();
            this.model.addNote(
                    new Note(0, Note.Pitch.C, 3, 7));
            this.model.addNote(
                    new Note(2, Note.Pitch.C, 2, 7));
            this.model.removeNote(
                    new Note(2, Note.Pitch.C, 2, 7));
            assertEquals("   C7 \n" +
                            "0  X  \n" +
                            "1  |  \n" +
                            "2  |  \n",
                    this.view.render(this.model));

        }
    @Test
    public void testAddRange() {
            this.initData();
            this.model.addNote(
                    new Note(0, Note.Pitch.C, 3, 7));
            this.model.addNote(
                    new Note(2, Note.Pitch.C, 2, 7));
            //add a song at beat 0 of duration 4 and in the 9th octave
            this.model.addNote(
                    new Note(0, Note.Pitch.CSHARP, 4, 9));
            assertEquals("   C7  C#7   D7  D#7   E7   F7  F#7   G7  G#7   A7  A#7   B7   C8  C#8   D8  D#8   E8   F8  F#8   G8  G#8   A8  A#8   B8   C9  C#9 \n" +
                    "0  X                                                                                                                            X  \n" +
                    "1  |                                                                                                                            |  \n" +
                    "2  X                                                                                                                            |  \n" +
                    "3  |                                                                                                                            |  \n", this.view.render(this.model));

    }

    @Test
    public void testBasicRemove() {
        this.initData();
        this.model.addNote(
                new Note(0, Note.Pitch.DSHARP, 3, 4));
        this.model2.addNote(
                new Note(0, Note.Pitch.DSHARP, 3, 4));
        assertEquals(this.view.render(this.model2),
                this.view.render(this.model));
        this.model2.addNote(
                new Note(0, Note.Pitch.E, 3, 4));
        assertNotEquals(this.view.render(this.model2),
                this.view.render(this.model));
        this.model2.removeNote(
                new Note(0, Note.Pitch.E, 3, 4));
        assertEquals(this.view.render(this.model2),
                this.view.render(this.model));
    }
}