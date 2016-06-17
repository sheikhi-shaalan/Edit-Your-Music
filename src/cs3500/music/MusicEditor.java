package cs3500.music;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.MusicCreatorImpl;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import cs3500.music.view.MidiViewImpl;

import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    //GuiViewFrame view = new GuiViewFrame();
    MidiViewImpl midiView = new MidiViewImpl();
    ConsoleView consoleView = new ConsoleView();
    CompositionBuilder<MusicCreator> b = MusicCreatorImpl.getBuilder();
    MusicReader reader = new MusicReader();
    MusicCreator creator = reader.parseFile(new FileReader(args[1]), b);

    // You probably need to connect these views to your model, too...
    switch (args[0]) {
//      case "console":
//        System.out.println(consoleView.render(creator));
//        break;
      case "midi" :
        midiView.playComposition(creator);
        break;
    }

  }
}
