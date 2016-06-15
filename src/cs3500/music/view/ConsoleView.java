package cs3500.music.view;

import java.awt.*;
import java.util.*;
import java.util.List;

import cs3500.music.model.MusicCreator;
import cs3500.music.model.Note;

/**
 * Created by NadineShaalan on 6/14/16.
 */
public class ConsoleView implements IView {
  public ConsoleView() {}

  public String Render(MusicCreator c) {
    int min = this.getMin(c);
    int max = this.getMax(c);
    int dur = c.getSongDuration();
    // -------------- Creates empty array list to modify
    ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
    for (int i = 0; i <= dur; i++) {
      ArrayList<String> temp = new ArrayList<String>();
      for (int j = min; j <= max; j++) {
        temp.add("     ");
      }
      list.add(temp);
    }

    // ----------- Fills the arraylist with proper strings ------------
    for (int i = 0; i <= dur; i++) {
      for (int j = min; j <= max; j++) {
        if(c.notesAtBeat(i).size() == 0) {
          // Do nothing
        }
        else if (noteAt(c.notesAtBeat(i), j) != null) {
          list.get(i).set(j - min, "  X  ");
          for (int k = 1; k < noteAt(c.notesAtBeat(i), j).getDuration(); k++) {
            if (!(list.get(i+k).get(j-min).equals("  X  "))) {
              list.get(i+k).set(j - min, "  |  ");
            }
          }
        }
      }
    }


    //---------- CONVERTS TO FINAL STRING ----------------------//
    // Create a new string builder
    StringBuilder result= new StringBuilder();
    // Amount of padding necessary
    int width = String.valueOf(c.getSongDuration()).length();

    // Print out the beat numbers with the proper padding
    for (int i = 0; i <= dur; i++) {
      result.append(String.format("%" + width + "s", i));
      for (String s: list.get(i)) {
        result.append(s);
      }
      result.append("\n");
    }
    return octaveRow(c) + result.toString();
  }


  private Note noteAt(java.util.List<Note> list, int key) {
    Note result = null;
    for (Note n: list) {
      if (n.getKeyVal() == key) {
        result = n;
      }
    }
    return result;
  }

  // Gets the top row of a render
  private String octaveRow(MusicCreator c) {
    StringBuilder s = new StringBuilder();
    int width = String.valueOf(c.getSongDuration()).length();
    int minNoteValue;
    int maxNoteValue;
    int dur = c.getSongDuration();
    if (dur == 0) {
      minNoteValue = 12;
      maxNoteValue = 24;
    } else {
      minNoteValue = getMin(c);
      maxNoteValue = getMax(c);
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

  // Get the min note
  private int getMin(MusicCreator c) {
    ArrayList<Note> temp = new ArrayList<Note>(c.asList());
    Note minNoteValue = temp.get(0);
    return minNoteValue.getKeyVal();
  }

  // Get the highest note
  private int getMax(MusicCreator c) {
    ArrayList<Note> temp = new ArrayList<Note>(c.asList());

    Collections.sort(temp);

    return Collections.max(temp).getKeyVal();

  }

//  private int getMin() {
//    int min = Integer.MAX_VALUE;
//    for (Integer i : this.composition.keySet()) {
//      if (this.composition.get(i).size() != 0) {
//        int temp = composition.get(i).get(0).getKeyVal();
//        if (temp < min) {
//          min = temp;
//        }
//      }
//    }
//    return min;
//  }
//
//  private int getMax() {
//    int max = Integer.MIN_VALUE;
//    for (Integer i : this.composition.keySet()) {
//      List<Note> list = composition.get(i);
//      if (list.size() != 0) {
//        int temp = list.get(list.size() - 1).getKeyVal();
//        if (temp > max) {
//          max = temp;
//        }
//      }
//    }
//    return max;
//  }

}
