package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.music.util.CompositionBuilder;

/**
 * The implementation for the MusicCreator model of this project INVARIANT: The startBeatNo of the
 * note must match the note in the composition
 */
public class MusicCreatorImpl implements MusicCreator {

  protected Map<Integer, List<Note>> composition;
  private int tempo;

  /**
   * Creates a default MusicCreatorImpl object: the composition is set to an empty hashmap The tempo
   * is default set to 120 BPM
   */
  public MusicCreatorImpl() {
    this.composition = new HashMap<Integer, List<Note>>();
    //120 BPM
    this.tempo = 2000;
  }

  /**
   * Creates a MusicCreatorImpl object: The notes in the given List<Note></Note> are added to the
   * composition
   *
   * @param tempo the desired tempo of this MusicCreatorImpl
   * @param comp  the notes to start the MusicCreatorImpl with
   */
  public MusicCreatorImpl(int tempo, List<Note> comp) {
    this.tempo = tempo;
    this.composition = new HashMap<Integer, List<Note>>();
    for (Note n : comp) {
      this.addNote(n);
    }
  }

  @Override
  public void addNote(Note note) {
    // if this beat already has notes in it
    if (this.composition.containsKey(note.getStartbeatNo())) {
      // add the note to the arrayList
      List<Note> list = this.composition.get(note.getStartbeatNo());
      list.add(note);
      Collections.sort(list);
    }
    // if the composition has no notes at this beat, create a new entry
    else {
      ArrayList<Note> list = new ArrayList<>();
      list.add(note);
      this.composition.put(note.getStartbeatNo(), list);
    }
  }

  @Override
  public void removeNote(Note note) {
    int noteBeat = note.getStartbeatNo();
    // If there is a beatNo that corresponds to this note
    if (this.composition.containsValue(this.composition.get(noteBeat))) {
      ArrayList<Note> newArray =
              new ArrayList<Note>(this.composition.get(noteBeat));
      newArray.remove(note);
      this.composition.put(noteBeat, newArray);
    } else {
      throw new IllegalArgumentException("Must delete a note that exists!");
    }
  }


  @Override
  public void changeNote(Note original, Note newNote) {
    if (composition.get(original.getStartbeatNo()).contains(original)) {
      this.removeNote(original);
      this.addNote(newNote);
    } else {
      throw new IllegalArgumentException("Must change a note that exists");
    }
  }

  @Override
  public void combineSym(MusicCreator c2) {
    if (c2 == null) {
      throw new IllegalArgumentException("c2 must be initialized!");
    }
    for (Note n : c2.asList()) {
      this.addNote(n);
    }
  }

  @Override
  public void combineCon(MusicCreator c2) {
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
      n.changeStart(dur);
      this.addNote(n);
    }
  }

  @Override
  public List<Note> asList() {
    ArrayList<Note> temp = new ArrayList<Note>();
    for (int i : composition.keySet()) {
      for (Note n : composition.get(i)) {
        temp.add(n);
      }
    }
    return temp;
  }


  @Override
  public int getSongDuration() {
    ArrayList<Note> temp = new ArrayList<Note>(this.asList());
    int maxBeatNo = 0;
    for (Note n : temp) {
      if (n.getStartbeatNo() + n.getDuration() - 1 > maxBeatNo) {
        maxBeatNo = n.getStartbeatNo() + n.getDuration() - 1;
      }
    }
    return maxBeatNo;
  }

  @Override
  public List<Note> notesAtBeat(int beat) {
    if (composition.containsKey(beat)) {
      return this.composition.get(beat);
    } else {
      return new ArrayList<Note>();
    }
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  /**
   * @return a builder for this class
   */
  public static Builder getBuilder() {
    return new Builder();
  }


  /**
   * represents a builder for creating MusicCreatorImpl objects
   */
  public static final class Builder implements CompositionBuilder<MusicCreator> {

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
    public MusicCreator build() {
      MusicCreator c = new MusicCreatorImpl(tempo, composition);
      return c;
    }

    @Override
    public CompositionBuilder<MusicCreator> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    @Override
    public CompositionBuilder<MusicCreator>
    addNote(int start, int end, int instrument, int pitch, int volume) {
      this.composition.add(new Note(start, pitch, (end - start), instrument, volume));
      return this;
    }
  }

}