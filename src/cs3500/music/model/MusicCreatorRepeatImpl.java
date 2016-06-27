package cs3500.music.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.MusicEditor;
import cs3500.music.controller.MusicController;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;

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
    MusicCreatorRepeat rep1 = this.trim(start, end);
    MusicCreatorRepeat ending = this.trim(end + 1, this.getSongDuration());
    for (Note n : rep1.asList()) {
      n.setShadowBeat(start + (n.getStartbeatNo() - start));
    }
    this.composition.clear();
    this.combineCon(starting); // BA
    this.combineConEnd(rep1, end-start+1);// AG
    this.combineConEnd(ending, end - start + 1); //G#
    int trueBro = start;
  }

  public void combineConEnd(MusicCreator c2, int rep) {
    int dur = this.getSongDuration();
    if (c2 == null) {
      throw new IllegalArgumentException("c2 must be initialized!");
    }
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
      System.out.println("PITCH: " + n.getPitch() + " START: " + n.getStartbeatNo() + "" +
              "TRUE BRO: " + n.getShadowBeat() +"\n" );
    }
    MusicEditor m = new MusicEditor();
    IView v = new MidiViewImpl(creator);
    MusicController controller = new MusicController(creator, v);


  }
  /**
   * @return a builder for this class
   */

  public static Builder getBuilderRep() {
    return new MusicCreatorRepeatImpl.Builder();
  }



  /**
   * represents a builder for creating MusicCreatorImpl objects
   */
  public static final class Builder implements CompositionBuilder<MusicCreatorRepeat> {

    private List<Note> composition;
    private int tempo;

    /**
     * A constructor that default sets the tempo to 2,000,000 microseconds per quarter note and the
     * composition to an empty arraylist of notes
     */
    private Builder() {
      this.tempo = 200000;
      this.composition = new ArrayList<Note>();
    }

    @Override
    public MusicCreatorRepeat build() {
      MusicCreatorRepeat c = new MusicCreatorRepeatImpl(tempo, composition);
      return c;
    }

    @Override
    public CompositionBuilder<MusicCreatorRepeat> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    @Override
    public CompositionBuilder<MusicCreatorRepeat>
    addNote(int start, int end, int instrument, int pitch, int volume) {
      this.composition.add(new Note(start, pitch, (end - start), instrument, volume));
      return this;
    }
  }

}
