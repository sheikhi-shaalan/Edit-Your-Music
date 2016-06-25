package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.model.Note;
import cs3500.music.view.ConsoleView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by baharsheikhi on 6/15/16. To test the consoleView
 */
public class ConsoleViewTest {
  MusicCreator model;
  MusicCreator model2;
  ConsoleView view;
  ConsoleView view2;
  ConsoleView view3;


  MusicCreator mc;
  Note e2;
  Note g1;
  Note b;
  Note n;
  Note j;
  Note m;

  public void init() {
    this.model = new MusicCreatorImpl();
    this.mc = new MusicCreatorImpl();

    this.model2 = new MusicCreatorImpl();
    this.view = new ConsoleView(model);
    this.view2 = new ConsoleView(model2);
    this.view3 = new ConsoleView(mc);


    e2 = new Note(2, Note.Pitch.E, 2, 2);
    g1 = new Note(1, Note.Pitch.G, 4, 1);
    b = new Note(2, Note.Pitch.E, 2, 2);
    n = new Note(1, Note.Pitch.G, 4, 1);
    j = new Note(8, Note.Pitch.G, 3, 1);
    m = new Note(4, Note.Pitch.B, 1, 1);
  }

  @Test
  public void testSimpleAdd() {
    this.init();
    this.model.addNote(new Note(0, Note.Pitch.E, 3, 4));
    assertEquals("   E4 \n" +
            "0  X  \n" +
            "1  |  \n" +
            "2  |  \n", this.view.render());
    //adding the exact same note of the same duration
    this.model.addNote(new Note(0, Note.Pitch.E, 3, 4));
    assertEquals("   E4 \n" +
            "0  X  \n" +
            "1  |  \n" +
            "2  |  \n", this.view.render());
    this.model2.addNote(new Note(0, Note.Pitch.E, 3, 4));
    assertEquals(this.view.render(), this.view2.render());
  }

  @Test
  public void testOverlapNote() {
    this.init();
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
            this.view.render());
  }

  @Test
  public void testOverlapNoteRemove() {
    this.init();
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
            this.view.render());

  }

  @Test
  public void testBasicRemove() {
    this.init();
    this.model.addNote(
            new Note(0, Note.Pitch.DSHARP, 3, 4));
    this.model2.addNote(
            new Note(0, Note.Pitch.DSHARP, 3, 4));
    assertEquals(view2.render(),
            this.view.render());
    this.model2.addNote(
            new Note(0, Note.Pitch.E, 3, 4));
    assertNotEquals(this.view2.render(),
            this.view.render());
    this.model2.removeNote(
            new Note(0, Note.Pitch.E, 3, 4));
    assertEquals(this.view2.render(),
            this.view.render());
  }

  // Combines with no overlap
  @Test
  public void testSym() {
    init();
    MusicCreator c = new MusicCreatorImpl();
    c.addNote(b);
    c.addNote(n);
    mc.addNote(j);
    mc.addNote(m);

    mc.combineSym(c);
    assertEquals("    G1  G#1   A1  A#1   B1   C2  C#2   D2  D#2   E2 \n" +
            " 0                                                  \n" +
            " 1  X                                               \n" +
            " 2  |                                            X  \n" +
            " 3  |                                            |  \n" +
            " 4  |                   X                           \n" +
            " 5                                                  \n" +
            " 6                                                  \n" +
            " 7                                                  \n" +
            " 8  X                                               \n" +
            " 9  |                                               \n" +
            "10  |                                               \n", this.view3.render());

  }

  // Combines with overlap
  @Test
  public void testSymOverlap() {
    init();
    MusicCreator c = new MusicCreatorImpl();
    c.addNote(n);
    c.addNote(new Note(3, Note.Pitch.G, 2, 1));
    mc.addNote(j);
    mc.addNote(m);

    mc.combineSym(c);
    assertEquals("    G1  G#1   A1  A#1   B1 \n" +
            " 0                         \n" +
            " 1  X                      \n" +
            " 2  |                      \n" +
            " 3  X                      \n" +
            " 4  |                   X  \n" +
            " 5                         \n" +
            " 6                         \n" +
            " 7                         \n" +
            " 8  X                      \n" +
            " 9  |                      \n" +
            "10  |                      \n", this.view3.render());

  }

  //******************************** Combine Consecutively ***************************************/
  // Combines
  @Test
  public void testCombineCon() {
    init();
    MusicCreator c = new MusicCreatorImpl();
    c.addNote(b);
    c.addNote(n);
    mc.addNote(j);
    mc.addNote(m);
    mc.combineCon(c);
    assertEquals("    G1  G#1   A1  A#1   B1   C2  C#2   D2  D#2   E2 \n" +
            " 0                                                  \n" +
            " 1                                                  \n" +
            " 2                                                  \n" +
            " 3                                                  \n" +
            " 4                      X                           \n" +
            " 5                                                  \n" +
            " 6                                                  \n" +
            " 7                                                  \n" +
            " 8  X                                               \n" +
            " 9  |                                               \n" +
            "10  |                                               \n" +
            "11                                                  \n" +
            "12  X                                               \n" +
            "13  |                                            X  \n" +
            "14  |                                            |  \n" +
            "15  |                                               \n", this.view3.render());
  }

  //************************************ Render *********************************************/
  // Renders in same octave complete
  @Test
  public void rendersCompleteOctave() {
    init();
    mc.addNote(new Note(1, Note.Pitch.C, 4, 4));
    mc.addNote(new Note(1, Note.Pitch.B, 4, 4));
    //
    assertEquals("   C4  C#4   D4  D#4   E4   F4  F#4   G4  G#4   A4  A#4   B4 ",
            this.view3.render().split("\n")[0]);

  }

  // Renders a song with no notes --> the entire 3rd Octave
  @Test
  public void renderEmpty() {
    init();
    String defaultPrintOut = "   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3 ";
    assertEquals(defaultPrintOut, this.view3.render().split("\n")[0]);
  }

  // Renders in same octave (incomplete)
  @Test
  public void rendersIncompleteOctave() {
    init();
    mc.addNote(new Note(1, Note.Pitch.C, 4, 4));
    mc.addNote(new Note(1, Note.Pitch.FSHARP, 4, 4));
    assertEquals("   C4  C#4   D4  D#4   E4   F4  F#4 ", this.view3.render().split("\n")[0]);
  }

  // Renders in multiple octaves
  @Test
  public void rendersMultipleOctaves() {
    init();
    mc.addNote(new Note(1, Note.Pitch.C, 4, 4));
    mc.addNote(new Note(1, Note.Pitch.C, 4, 6));
    assertEquals("   C4  C#4   D4  D#4   E4   F4  F#4   G4  G#4   A4  A#4   B4   " +
                    "C5  C#5   D5  D#5   E5   F5  F#5   G5  G#5   A5  A#5   B5   C6 ",
            this.view3.render().split("\n")[0]);
  }

  // Render padding  for beats is correct for 1 digit
  @Test
  public void renderPaddingisCorrectOneDigit() {
    init();
    mc.addNote(g1);
    //Song duration is 4 --> No padding
    assertEquals("", this.view3.render().split("\n")[1].split("0")[0]);
  }

  // Render padding for beats is correct for beats > 100
  @Test
  public void renderPaddingisCorrectHundred() {
    init();
    mc.addNote(new Note(100, Note.Pitch.E, 4, 4));
    //Song duration is 104 --> 3 digit padding
    assertEquals("  ", this.view3.render().split("\n")[1].split("0")[0]);
  }

  // Render padding for beats is correct for beats > 1000
  @Test
  public void renderPaddingisCorrectFour() {
    init();
    mc.addNote(new Note(1000, Note.Pitch.E, 4, 4));
    //Song duration is 1004 --> 4 digit padding
    assertEquals("   ", this.view3.render().split("\n")[1].split("0")[0]);
  }

  // Can handle over 9 octaves
  @Test
  public void renderOverTenOctaves() {
    init();
    mc.addNote(new Note(1, Note.Pitch.FSHARP, 2, 12));
    assertTrue(this.view3.render().contains(" F#12"));
  }

  // Can render a normal Piece
  @Test
  public void renderCompletePeice() {
    init();
    mc.addNote(b);
    mc.addNote(n);
    mc.addNote(j);
    mc.addNote(m);
    assertEquals("    G1  G#1   A1  A#1   B1   C2  C#2   D2  D#2   E2 \n" +
            " 0                                                  \n" +
            " 1  X                                               \n" +
            " 2  |                                            X  \n" +
            " 3  |                                            |  \n" +
            " 4  |                   X                           \n" +
            " 5                                                  \n" +
            " 6                                                  \n" +
            " 7                                                  \n" +
            " 8  X                                               \n" +
            " 9  |                                               \n" +
            "10  |                                               \n", this.view3.render());
  }

  // Can render a piece with overlap
  @Test
  public void renderCompleteOverlap() {
    init();
    mc.addNote(n);
    mc.addNote(new Note(3, Note.Pitch.G, 2, 1));
    mc.addNote(j);
    mc.addNote(m);
    assertEquals("    G1  G#1   A1  A#1   B1 \n" +
            " 0                         \n" +
            " 1  X                      \n" +
            " 2  |                      \n" +
            " 3  X                      \n" +
            " 4  |                   X  \n" +
            " 5                         \n" +
            " 6                         \n" +
            " 7                         \n" +
            " 8  X                      \n" +
            " 9  |                      \n" +
            "10  |                      \n", this.view3.render());
  }


}
