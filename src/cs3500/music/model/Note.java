package cs3500.music.model;

import java.util.Objects;


public class Note implements Comparable<Note> {
  public enum Pitch {
    C, CSHARP, D, DSHARP, E, F, FSHARP, G, GSHARP, A, ASHARP, B;

    Pitch() {
    }

    public String toNoteString() {
      String result = "";
      switch (this) {
        case C:
          result += "C";
          break;
        case CSHARP:
          result += "C#";
          break;
        case D:
          result += "D";
          break;
        case DSHARP:
          result += "D#";
          break;
        case E:
          result += "E";
          break;
        case F:
          result += "F";
          break;
        case FSHARP:
          result += "F#";
          break;
        case G:
          result += "G";
          break;
        case GSHARP:
          result += "G#";
          break;
        case A:
          result += "A";
          break;
        case ASHARP:
          result += "A#";
          break;
        case B:
          result += "B";
          break;
        default: {
          result += "     ";
        }
      }
      return result;
    }
  }

  protected int startbeatNo;
  protected Pitch value;
  protected int duration;
  protected int octave;
  protected int keyVal;
  protected boolean start;


  public Note() {
  }

  public Note(int beat, Pitch value, int duration, int octave) {
    if (beat < 0 || duration < 0) {
      throw new IllegalArgumentException("Must enter a non negative number");
    }
    if (octave <= 0) {
      throw new IllegalArgumentException("Octaves start at 1");
    }
    this.startbeatNo = beat;
    this.value = value;
    this.duration = duration;
    this.octave = octave;
    this.keyVal = this.octave * 12 + value.ordinal();
    this.start = true;
  }

  // Creates the tail of a note
  protected Note trim(int beatNo) {
    Note n = new cs3500.music.model.Note(beatNo,
            this.value, this.duration - 1, this.octave);
    n.start = false;
    return n;

  }

  // Changes the start time of the note by the specified length
  public void changeStart(int growth) {

    this.startbeatNo += growth;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.keyVal, this.start, this.duration, this.startbeatNo);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof cs3500.music.model.Note)) {
      return false;
    }
    cs3500.music.model.Note that = (cs3500.music.model.Note) o;
    return this.value == that.value && this.startbeatNo == that.startbeatNo &&
            this.duration == that.duration && this.octave == that.octave
            && this.start == that.start;
  }

  @Override
  public int compareTo(Note n) {
    if (this.octave == n.octave) {
      return this.value.ordinal() - n.value.ordinal();
    } else {
      return this.octave - n.octave;
    }
  }

}

