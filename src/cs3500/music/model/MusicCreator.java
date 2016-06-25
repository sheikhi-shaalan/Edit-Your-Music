package cs3500.music.model;

import java.util.List;

public interface MusicCreator {
  /**
   * Adds a single note to a piece of music
   *
   * @param note Note to be added into the Composition
   */
  void addNote(Note note);

  /**
   * Removes a single note from a piece of music
   *
   * @param note Note to be removed from the Composition
   */
  void removeNote(Note note);

  /**
   * Changes a specified note to a newly specified note
   *
   * @param original Note be changed
   * @param newNote  Note to change it to
   */
  void changeNote(Note original, Note newNote);

  /**
   * Combines this piece with that peice so that they play simultaneously
   *
   * @param c2 piece to play interwoven with this
   */
  void combineSym(MusicCreator c2);

  /**
   * Combines this piece so that peice plays right after
   *
   * @param c2 piece to play after this
   */
  void combineCon(MusicCreator c2);

  /**
   * @return the song as a list of notes
   */
  List<Note> asList();

  /**
   * @return the length of a song
   */
  int getSongDuration();

  /**
   * @param beat beat number of the desired notes
   * @return notes at given beat
   */
  List<Note> notesAtBeat(int beat);

  /**
   * @return the tempo of the song in microseconds per beat
   */
  int getTempo();
}
