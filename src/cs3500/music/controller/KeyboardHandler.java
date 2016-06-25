package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by baharsheikhi on 6/20/16. To represent a keyboard handler for this project
 */
public class KeyboardHandler implements KeyListener {

  private Map<Integer, Runnable> typed;
  private Map<Integer, Runnable> pressed;
  private Map<Integer, Runnable> released;

  /**
   * An empty constructor that creates empty hashmaps for the different types of key handles: typed,
   * pressed, released
   */
  public KeyboardHandler() {
    this.typed = new HashMap<Integer, Runnable>();
    this.pressed = new HashMap<Integer, Runnable>();
    this.released = new HashMap<Integer, Runnable>();
  }

  /**
   * Sets the typed hashmap of this class
   *
   * @param map the new map for the typed field
   */
  public void setTyped(Map<Integer, Runnable> map) {
    this.typed = map;
  }

  /**
   * Sets the pressed hashmap for this class
   *
   * @param map the new  map for the pressed field
   */
  public void setPressed(Map<Integer, Runnable> map) {
    this.pressed = map;
  }

  /**
   * Sets the released hashmap for this class
   *
   * @param map the new map for the released field
   */
  public void setReleased(Map<Integer, Runnable> map) {
    this.released = map;
  }


  /**
   * Executes the runnable of the given Keyevent for the typed commands
   *
   * @param e the given keyevent
   */
  @Override
  public void keyTyped(KeyEvent e) {
    if (this.typed.containsKey(e.getKeyCode())) {
      this.typed.get(e.getKeyCode()).run();
    }
  }

  /**
   * Executes the runnable of the given Keyevent for the pressed commands
   *
   * @param e the given keyevent
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (this.pressed.containsKey(e.getKeyCode())) {
      this.pressed.get(e.getKeyCode()).run();
    }
  }

  /**
   * Executes the runnable of the given Keyevent for the released commands
   *
   * @param e the given keyevent
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if (this.released.containsKey(e.getKeyCode())) {
      this.released.get(e.getKeyCode()).run();
    }
  }

}
