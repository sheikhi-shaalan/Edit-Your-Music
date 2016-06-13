package cs3500.music.model;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.Note;

import java.util.HashMap;
import java.util.*;

/**
 * Created by baharsheikhi on 6/13/16.
 */
public class MusicCreatorImpl implements MusicCreator {

    Map<Integer, List<Note>> composition;

    public MusicCreatorImpl() {
        this.composition = new HashMap<Integer, List<Note>>();
    }

    @Override
    public void addNote(Note note) {

    }

    @Override
    public void removeNote(Note note) {

    }

    @Override
    public void changeNote(Note original, Note newNote) {

    }

    @Override
    public void combineSym(MusicCreator c2) {

    }

    @Override
    public void combineCon(MusicCreator c2) {

    }

    @Override
    public String render() {
        return null;
    }

    @Override
    public List<Note> asList() {
        return null;
    }

    @Override
    public int getSongDuration() {
        return 0;
    }

    @Override
    public List<Note> notesAtBeat(int beat) {
        return null;
    }

}
