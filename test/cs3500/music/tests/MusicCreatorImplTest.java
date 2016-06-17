package cs3500.music.tests;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.model.Note;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.IView;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by NadineShaalan on 6/13/16.
 */
public class MusicCreatorImplTest {
  MusicCreator model;
  MusicCreator model2;

  public void initData() {
    this.model = new MusicCreatorImpl();
    this.model2 = new MusicCreatorImpl();
  }

  @Test
  public void testOutConsole() {
    this.initData();
    this.model.addNote(
            new Note(0, Note.Pitch.C, 2, 2));
    this.model.addNote(
            new Note(1, Note.Pitch.D, 1, 2));
    this.model.addNote(
            new Note(2, Note.Pitch.E, 3, 2));
    ConsoleView view = new ConsoleView();
    System.out.println(view.render(model));
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
    assertEquals(3, this.model.getSongDuration());
    //adding the exact same note of the same duration
    this.model.addNote(new Note(0, Note.Pitch.E, 3, 4));
    assertEquals(3, this.model.getSongDuration());
    this.model2.addNote(new Note(0, Note.Pitch.E, 3, 4));
    //assertEquals(this.model.render(), this.model2.render());
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
    assertEquals(5, this.model.getSongDuration());
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
    assertEquals(5,
            this.model.getSongDuration());

  }

  //Exceptions for adding
  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteBeatNoExist() {
    this.initData();
    this.model.addNote(
            new Note(0, Note.Pitch.DSHARP, 3, 4));
    assertEquals(3, model.getSongDuration());
    //this should throw an exception
    this.model.addNote(
            new Note(4, Note.Pitch.E, 4, 4));
    //this should throw an exception
    this.model.addNote(
            new Note(7, Note.Pitch.DSHARP, 4, -1));

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
    //TODO actually add <code></code>
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
  public void testBasicConcat() {
    this.initData();
    this.model.addNote(
            new Note(0, Note.Pitch.C, 2, 2));
    this.model.addNote(
            new Note(1, Note.Pitch.D, 1, 2));
    this.model.addNote(
            new Note(2, Note.Pitch.E, 3, 2));
   /* assertEquals("   C2  C♯2   D2 " +
                    " D♯2   E2 \n" +
                    "0  X                 " +
                    "                        " +
                    "       " +
                    "    " +
                    "     \n" +
                    "1            X         " +
                    "                              " +
                    "  " +
                    "   " +
                    "   \n" +
                    "2                      X      " +
                    "      " +
                    "           " +
                    "       " +
                    "       \n",
            this.model.render());
    this.model2.addNote(
            new Note(0, Note.Pitch.C, 1, 2));
    this.model2.addNote(
            new Note(1, Note.Pitch.D, 1, 2));
    this.model2.addNote(
            new Note(2, Note.Pitch.E, 1, 2));
    assertEquals("   C2  C♯2   D2  D♯2   E2 \n" +
                    "0  X                              " +
                    "         " +
                    "                  \n" +
                    "1            X           " +
                    "                                    \n" +
                    "2                      X   " +
                    "                                  \n",
            this.model2.render());
    this.model.combineCon(this.model2);
    assertEquals("   C2  C♯2   D2  D♯2   E2 \n" +
                    "0  X                      " +
                    "                                   \n" +
                    "1            X              " +
                    "                                 \n" +
                    "2                      X      " +
                    "                               \n" +
                    "3  X                         " +
                    "                                \n" +
                    "4            X                 " +
                    "                              \n" +
                    "5                      X       " +
                    "                              \n",
            this.model.render());
    System.out.println(this.model.render()); */
  }

  @Test
  public void createMediumModel() {
    this.initData();
    for (int i = 0; i < 100; i++) {
      this.model.addNote(
              new Note(i, Note.Pitch.G, 1, 1));
    }

    assertEquals(100, this.model.getSongDuration());
  }

  @Test
  public void createBigModel() {
    this.initData();
    for (int i = 0; i < 2000; i++) {
      this.model.addNote(
              new Note(i, Note.Pitch.G, 1, 1));
    }

    assertEquals(2000, this.model.getSongDuration());
  }

  @Test
  public void testBigRange() {
    this.initData();
    this.model.addNote(
            new Note(0, Note.Pitch.C, 1, 0));
    this.model.addNote(
            new Note(1, Note.Pitch.B, 1, 500));
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
//
//  //just a test I used kind of as a main method to see if things were printing right
//  @Test
//  public void intermediate() {
//    this.initData();
//    this.model.addNote(new Note(0, Note.Pitch.C, 2, 3));
//    this.model.addNote(new Note(1, Note.Pitch.D, 1, 3));
//    this.model.addNote(new Note(2, Note.Pitch.E, 3, 3));
//    this.model.addNote(new Note(3, Note.Pitch.B, 4, 5));
//    this.model.removeNote(new Note(0, Note.Pitch.C, 2, 3));
//    //REALLY IMPORTANT EXAMPLE OF A BUG!!!!
//
//    System.out.print(this.model.render());
//  }
//
//
}