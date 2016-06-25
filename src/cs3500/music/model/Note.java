package cs3500.music.model;

import java.util.Objects;


/**
 * To represent notes of this project
 */
public class Note implements Comparable<Note> {
    /**
     * An enum to represent pitches of notes: starting with
     * C and up until B. Only includes Sharp, not flat.
     */
    public enum Pitch {
        C, CSHARP, D, DSHARP, E, F, FSHARP, G, GSHARP, A, ASHARP, B;

        Pitch() {
        }

        /**
         * @return each Pitch's string representation
         */
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
    private int instrument;
    private int volume;


    /**
     * Default constructor where everythign is null
     */
    public Note() {
    }

    /**
     * Custom constructor with the volume set to 64 and the
     * instrument to 1 (piano).
     *
     * @param beat     the starting beat number of the new note
     * @param value    the midi value of the new note
     * @param duration the duration of the new note
     * @param octave   the octave of the new note
     *                 Throws an IllegalArgumentException if the beat or duration is less than 0.
     *                 Throws an IllegalArgumentException if the octave is less than or equal to zero.
     */
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
        this.keyVal = this.octave * 12 + value.ordinal() + 12;
        this.start = true;
        this.volume = 64;
        this.instrument = 1;
    }

    /**
     * Creates a new note with the given values.
     *
     * @param beat       the starting beat number of the new Note
     * @param pitch      the pitch of the new note
     * @param duration   the duration of the new note
     * @param instrument the instrument of the new note
     * @param volume     the volume of the new note
     *                   Throws an IllegalArgumentException if:
     *                   The beat, pitch, duration, instrument, or volume are less than 0
     *                   The instrument is greater than 128
     *                   The volume is greater than 127
     */
    public Note(int beat, int pitch, int duration, int instrument, int volume) {
        if (beat < 0 || pitch < 0 || duration < 0 || instrument < 0 || volume < 0 || instrument > 128
                || volume > 127) {
            throw new IllegalArgumentException("Please enter valid inputs");
        }
        this.duration = duration;
        this.startbeatNo = beat;
        this.octave = (int) Math.floor(pitch / 12) - 1;
        this.pitch = Note.Pitch.values()[pitch % 12];
        this.keyVal = pitch;
        this.instrument = instrument;
        this.volume = volume;
    }

    /**
     * Changes the start time of the note by the specified length
     *
     * @param growth the amount the note should grow by
     */
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
        if (!(o instanceof Note)) {
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

    /**
     * @return the starting beat number of the note
     */
    public int getStartbeatNo() {
        return this.startbeatNo;
    }

    /**
     * @return the duration of the note
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * @return the pitch of the note
     */
    protected Pitch getPitch() {
        return this.pitch;
    }


    /**
     * @return the keyVal of the note
     */
    public int getKeyVal() {
        return this.keyVal;
    }

    /**
     * @return the instrument of the note
     */
    public int getInstrument() {
        return this.instrument;
    }

    /**
     * @return the volume of the note
     */
    public int getVolume() {
        return this.volume;
    }

}

