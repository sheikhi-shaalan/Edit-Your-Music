package cs3500.music.model;

import java.util.List;

public interface MusicCreator {
  /**
   * Adds a single note to a piece of music
   * @param note
   */
  void addNote(Note note);

  /**
   * Removes a single note from a piece of music
   * @param note
   */
  void removeNote(Note note);

  /**
   * Changes a specified note to a newly specified note
   * @param original
   * @param newNote
   */
  void changeNote(Note original, Note newNote);

  /**
   * Combines this piece with that peice so that they play simultaneously
   * @param c2
   */
  void combineSym(MusicCreator c2);

  /**
   * Combines this piece so that peice plays right after
   * @param c2
   */
  void combineCon(MusicCreator c2);

  /**
   * Renders this song as a string
   * Prints the 3rd octave if no notes have been added yet
   * @return a string representation of the song
   */
  String render();

  /**
   *
   * @return the song as a list of notes
   */
  List<Note> asList();

  /**
   *
   * @return the length of a song
   */
  public int getSongDuration();

  /**
   * @param beat
   * @returns notes at given beat
   */
  public List<Note> notesAtBeat(int beat);
}
