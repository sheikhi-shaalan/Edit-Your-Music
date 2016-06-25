package cs3500.music;

import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.controller.MusicController;
import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.CompositeView;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;


/**
 * The main method of this project
 */
public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {

    MusicReader reader = new MusicReader();
    CompositionBuilder<MusicCreator> b = MusicCreatorImpl.getBuilder();
    MusicCreator creator = reader.parseFile(new FileReader(args[1]), b);

    MusicEditor m = new MusicEditor();
    IView v = m.create(args[0], creator);

    MusicController controller = new MusicController(creator, v);
    v.initialize();


  }

  IView create(String type, MusicCreator creator) {
    switch (type) {
      case "console":
        return new ConsoleView(creator);
      case "midi":
        return new MidiViewImpl(creator);
      case "gui":
        return new GuiViewFrame(creator);
      case "composite":
        return new CompositeView(new GuiViewFrame(creator),
                new MidiViewImpl(creator));
      default:
        throw new IllegalArgumentException("Must enter valid view type");
    }

  }
}
