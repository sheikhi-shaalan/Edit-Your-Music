package cs3500.music;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.model.Note;
import cs3500.music.view.ConcreteGuiViewPanel;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;

import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    MusicCreator c = new MusicCreatorImpl();
    c.addNote(new Note(0, Note.Pitch.C, 3, 4));
      ConcreteGuiViewPanel s = new ConcreteGuiViewPanel(c);
    GuiViewFrame view = new GuiViewFrame(c);
    //MidiView midiView = new MidiViewImpl();
    // You probably need to connect these views to your model, too...
  }
}
