package cs3500.music.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.MusicEditor;
import cs3500.music.controller.MusicController;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by NadineShaalan on 6/26/16.
 */
public class MusicCreatorRepeatImpl extends MusicCreatorImpl implements MusicCreatorRepeat {
  public MusicCreatorRepeatImpl() {
    super();
  }
  public MusicCreatorRepeatImpl(int tempo,List<Note> list) {
    super(tempo,list);
  }
  @Override
  public void addRepeat(int start, int end) {
    MusicCreatorRepeat starting = this.trim(0, end);
    MusicCreatorRepeat rep1 = this.trim(start,end);
    MusicCreatorRepeat ending = this.trim(end + 1, this.getSongDuration());
    this.composition.clear();
    this.combineCon(starting); // BA
    this.combineCon(rep1);// AG
    this.combineConEnd(ending, end-start+1); //G#

  }
  public void combineConEnd(MusicCreator c2, int rep) {
    int dur = this.getSongDuration();
    if (c2 == null) {
      throw new IllegalArgumentException("c2 must be initialized!");
    }
//    for (int i = 0; i < c2.asList().size(); i++) {
//      Note n= c2.asList().get(i);
//      n.baharChangeStart(dur + i);
//      this.addNote(c2.asList().get(i));
//    }
    for (Note n : c2.asList()) {
      n.baharChangeStart(n.getStartbeatNo() + rep);
      this.addNote(n);
    }
  }

  private MusicCreatorRepeatImpl trim(int start, int end) {
    ArrayList<Note> n = new ArrayList<>();
    for (int i = start; i <= end; i++) {
      for (Note n1: notesAtBeat(i)) {
        n.add(new Note(n1.getStartbeatNo(), n1.getPitch(), n1.getDuration(), n1.getOctave()));
      }
    }
    return new MusicCreatorRepeatImpl(getTempo(), n);
  }

  public static void main(String[] args) throws IOException, InvalidMidiDataException {

    MusicReader reader = new MusicReader();
    CompositionBuilder<MusicCreator> b = MusicCreatorImpl.getBuilder();
    Note n1=
            new Note(0, Note.Pitch.B, 1, 4);
    Note n2 =
            new Note(1, Note.Pitch.A, 1, 4);

    Note n3 =
            new Note(2, Note.Pitch.G, 1, 4);
    Note n5 =
            new Note(3, Note.Pitch.GSHARP, 1, 4);

    Note n4 =
            new Note(4, Note.Pitch.GSHARP, 1, 4);
    ArrayList<Note> list = new ArrayList<>();
    list.add(n1);
    list.add(n2);
    list.add(n3);
    list.add(n4);
    list.add(n5);

    MusicCreatorRepeat creator = new MusicCreatorRepeatImpl(2000000, list);
    creator.addRepeat(1,2);
    for (Note n: creator.asList()) {
      System.out.println("PITCH: " + n.getPitch() + " START: " + n.getStartbeatNo() + "\n");
    }
    MusicEditor m = new MusicEditor();
    IView v = new MidiViewImpl(creator);
    MusicController controller = new MusicController(creator, v);


  }

}
