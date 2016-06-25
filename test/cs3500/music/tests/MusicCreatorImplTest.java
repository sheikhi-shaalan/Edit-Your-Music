package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.model.Note;

import static org.junit.Assert.assertEquals;

/**
 * Created by NadineShaalan on 6/13/16. To test the model
 */
public class MusicCreatorImplTest {
  MusicCreator model;
  MusicCreator model2;

  public void initData() {
    this.model = new MusicCreatorImpl();
    this.model2 = new MusicCreatorImpl();
  }

  @Test
  public void testEmptyGetSongDuration() {
    this.initData();
    assertEquals(0, this.model.getSongDuration());
  }

  @Test
  public void testSimpleAddPrint() {
    this.initData();
    //at beat zero, Pitch E, lasts 3 beats, and fourth octave
    this.model.addNote(new Note(0, Note.Pitch.E, 3, 4));
    assertEquals(2, this.model.getSongDuration());
    //adding the exact same note of the same duration
    this.model.addNote(new Note(0, Note.Pitch.E, 3, 4));
    assertEquals(2, this.model.getSongDuration());
    this.model2.addNote(new Note(0, Note.Pitch.E, 3, 4));
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
    assertEquals(3, this.model.getSongDuration());
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
  }

  @Test
  public void testAddRangePrint() {
    this.initData();
    this.model.addNote(
            new Note(0, Note.Pitch.C, 3, 7));
    this.model.addNote(
            new Note(2, Note.Pitch.C, 2, 7));
    //add a song at beat 0 of duration 4 and in the 9th octave
    this.model.addNote(
            new Note(0, Note.Pitch.CSHARP, 4, 9));
    assertEquals(3,
            this.model.getSongDuration());

  }

  //Exceptions for removing
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNoteNonExistentNote() {
    this.initData();
    this.model.removeNote(
            new Note(5, Note.Pitch.DSHARP, 4, 4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNoteInvalidBeat() {
    this.initData();
    this.model.addNote(
            new Note(0, Note.Pitch.DSHARP, 3, 4));
    this.model.removeNote(
            new Note(-1, Note.Pitch.DSHARP, 4, 4));
    this.model.removeNote(
            new Note(5, Note.Pitch.DSHARP, 4, 4));
  }

  @Test
  public void testBasicRemove() {
    this.initData();
    this.model.addNote(
            new Note(0, Note.Pitch.DSHARP, 3, 4));
    this.model2.addNote(
            new Note(0, Note.Pitch.DSHARP, 3, 4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullConcat() {
    this.initData();
    this.model.combineCon(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullMix() {
    this.initData();
    this.model.combineSym(null);
  }

  @Test
  public void createMediumModel() {
    this.initData();
    for (int i = 0; i < 100; i++) {
      this.model.addNote(
              new Note(i, Note.Pitch.G, 1, 1));
    }

    assertEquals(99, this.model.getSongDuration());
  }

  @Test
  public void createBigModel() {
    this.initData();
    for (int i = 0; i < 2000; i++) {
      this.model.addNote(
              new Note(i, Note.Pitch.G, 1, 1));
    }

    assertEquals(1999, this.model.getSongDuration());
  }

  @Test
  public void testBigRange() {
    this.initData();
    this.model.addNote(
            new Note(0, Note.Pitch.C, 1, 1));
    this.model.addNote(
            new Note(1, Note.Pitch.B, 1, 500));
    assertEquals(1, model.getSongDuration());
    assertEquals(2, model.asList().size());
  }

  @Test
  public void testBigConcat() {
    this.initData();
    for (int i = 0; i < 5000; i++) {
      this.model.addNote(
              new Note(i, Note.Pitch.G, 1, 1));
    }

    for (int i = 0; i < 1000; i++) {
      this.model2.addNote(
              new Note(i, Note.Pitch.E, 1, 1));
    }

    model2.combineCon(model);

  }

  @Test
  public void testBigSym() {
    this.initData();
    for (int i = 0; i < 1000; i++) {
      this.model.addNote(
              new Note(i, Note.Pitch.G, 1, 1));
    }

    for (int i = 0; i < 1000; i++) {
      this.model2.addNote(
              new Note(i, Note.Pitch.E, 1, 1));
    }
    model2.combineSym(model);

  }

}