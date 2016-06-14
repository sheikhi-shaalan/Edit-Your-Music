package cs3500.music.model;

import java.util.HashMap;
import java.util.*;

public class MusicCreatorImpl implements MusicCreator {

    // INVARIANT: The startBeatNo of the note must match the note in the composition

    Map<Integer, List<Note>> composition;

    public MusicCreatorImpl() {
        this.composition = new HashMap<Integer, List<Note>>();
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
        for (Note n : c2.asList()) {
            n.changeStart(dur);
            this.addNote(n);
        }
    }

    @Override
    public String render() {
        StringBuffer ret = new StringBuffer();
        ret.append(this.octaveRow());
        //pads the 0th row with the correct number of spaces
        for (int i = 0; i < (int) (Math.log10(this.getSongDuration())); i++) {
            ret.append(" ");
        }

        //starts padding the songDuration amount of notes
        for (int i = 0; i < this.getSongDuration(); i++) {
            //pads the beginning of each row appropriately
            for (int j = 0; j < (((int)
                    Math.log10(getSongDuration())) - ((int) Math.log10(i))); j++) {
                ret.append(" ");
            }

            ret.append(Integer.toString(i));
            if (ret.charAt(ret.length() - 1) == '\n') {
                ret.deleteCharAt(ret.charAt(ret.length() - 1));
                ret.append(" ");
            }
            if (this.composition.containsKey(i)) {
                this.renderHelp(ret, i);
            }
            else {
                for (int h = 0; h < 12; h++) {
                    ret.append("     ");
                }
            }
            ret.append("\n");

        }
        return ret.toString();
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
        return this.composition.get(beat);
    }

    private int getMin() {
        int min = Integer.MAX_VALUE;
        for (Integer i : this.composition.keySet()) {
            if (this.composition.get(i).size() != 0) {
                int temp = composition.get(i).get(0).getKeyVal();
                if (temp < min) {
                    min = temp;
                }
            }
        }
        return min;
    }

    private int getMax() {
        int max = Integer.MIN_VALUE;
        for (Integer i : this.composition.keySet()) {
            List<Note> list = composition.get(i);
            if (list.size() != 0) {
                int temp = list.get(list.size() - 1).getKeyVal();
                if (temp > max) {
                    max = temp;
                }
            }
        }
        return max;
    }

    // Gets the top row of a render
    public String octaveRow() {
        StringBuilder s = new StringBuilder();
        int width = String.valueOf(getSongDuration()).length();
        int minNoteValue;
        int maxNoteValue;
        if (getSongDuration() == 0) {
            minNoteValue = 12;
            maxNoteValue = 24;
        } else {
            minNoteValue = getMin();
            maxNoteValue = getMax();
        }
        for (int i = 0; i < width; i++) {
            s.append(" ");
        }
        for (int i = minNoteValue; i <= maxNoteValue; i++) {

            int noteVal = i % 12;

            int octaveVal = (int) Math.floor(i / 12);
            String actualString = Note.Pitch.values()[noteVal].toNoteString() + octaveVal;
            int strlen = actualString.length();
            if (strlen == 2) {
                s.append("  " + actualString + " ");
            } else if (strlen == 3) {
                s.append(" " + actualString + " ");
            } else {
                s.append(" " + actualString);
            }
        }
        return s.toString() + "\n";

    }

    public void renderHelp(StringBuffer ret, int beat) {
        int minKeyVal = this.getMin();
        for (int i = minKeyVal; i <= this.getMax(); i++) {
            for (Note n : this.composition.get(beat)) {
            int noteVal = i % 12;
            int octaveVal = (int) Math.floor(i / 12);
            String actualString = Note.Pitch.values()[noteVal].toNoteString() + octaveVal;

                if (actualString.equals(n.getPitch().toNoteString() + n.getOctave())) {
                    ret.append(n.renderString());
                }
                else {
                    if (ret.charAt(ret.length() - 1) == '\n') {
                        ret.deleteCharAt(ret.charAt(ret.length() - 1));
                        ret.append(" ");
                    }
                    ret.append("     ");
                }
            }

        }
    }

}

