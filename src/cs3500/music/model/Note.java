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

  private int startbeatNo;
  private Pitch pitch;
  private int duration;
  private int octave;
  private int keyVal;
  private boolean start;


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
    this.pitch = value;
    this.duration = duration;
    this.octave = octave;
    this.keyVal = this.octave * 12 + value.ordinal();
    this.start = true;
  }

  // Changes the start time of the note by the specified length
  public void changeStart(int growth) {

    this.startbeatNo += growth;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.keyVal, this.start, this.duration, this.startbeatNo);
  }

  protected String renderString() {
    StringBuilder s = new StringBuilder();
    s.append("  X  ");
    for (int i = 1; i < this.duration ; i ++) {
      s.append("\n  |  ");
    }
     return s.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof  Note)) {
      return false;
    }
    Note that = (Note) o;
    return this.pitch == that.pitch && this.startbeatNo == that.startbeatNo &&
            this.duration == that.duration && this.octave == that.octave
            && this.start == that.start;
  }

  @Override
  public int compareTo(Note n) {
    if (this.octave == n.octave) {
      return this.pitch.ordinal() - n.pitch.ordinal();
    } else {
      return this.octave - n.octave;
    }
  }

  public int getStartbeatNo() {
    return this.startbeatNo;
  }
  public int getDuration() {
    return this.duration;
  }
  protected int getOctave() {
    return this.octave;
  }
  protected Pitch getPitch() {
    return this.pitch;
  }

  protected boolean isStart() {
    return this.start;
  }

  public int getKeyVal() {
    return this.keyVal;
  }

}

