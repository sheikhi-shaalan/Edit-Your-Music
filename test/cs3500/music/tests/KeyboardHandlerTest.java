package cs3500.music.tests;

import cs3500.music.controller.KeyboardHandler;

import org.junit.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class KeyboardHandlerTest {
  private final ByteArrayOutputStream oc = new ByteArrayOutputStream();

  // Cleans and then resets
  public void init() {
    System.setOut(null);
    System.setOut(new PrintStream(oc));
  }

  KeyEvent homeKey = new KeyEvent(new Component() {}, 0, 10,
          InputEvent.BUTTON1_MASK, KeyEvent.VK_HOME, ' ', 0);

  KeyEvent endKey = new KeyEvent(new Component() {}, 0, 10,
          InputEvent.BUTTON1_MASK, KeyEvent.VK_END, ' ', 0);

  KeyEvent spaceBar= new KeyEvent(new Component() {}, 0, 10,
          InputEvent.BUTTON1_MASK, KeyEvent.VK_SPACE, ' ', 0);

  KeyEvent r = new KeyEvent(new Component() {}, 0, 10,
          InputEvent.BUTTON1_MASK, KeyEvent.VK_R, ' ', 0);


  private Runnable home = () -> System.out.print("home key pressed");

  private Runnable end = () -> System.out.print("end key pressed");

  private Runnable rKey = () -> System.out.print("r key pressed");

  private Runnable space = () -> System.out.print("space bar pressed");


  @Test
  public void testHome() {
    init();
    KeyboardHandler kb = new KeyboardHandler();
    Map<Integer, Runnable> hash = new HashMap<>();
    hash.put(KeyEvent.VK_HOME, home);
    kb.setPressed(hash);
    kb.keyPressed(homeKey);
    assertEquals("home key pressed", oc.toString());
  }

  @Test
  public void testEnd() {
    init();
    KeyboardHandler kb = new KeyboardHandler();
    Map<Integer, Runnable> hash = new HashMap<>();
    hash.put(KeyEvent.VK_END, end);
    kb.setPressed(hash);
    kb.keyPressed(endKey);
    assertEquals("end key pressed", oc.toString());
  }

  @Test
  public void testSpace() {
    init();
    KeyboardHandler kb = new KeyboardHandler();
    Map<Integer, Runnable> hash = new HashMap<>();
    hash.put(KeyEvent.VK_SPACE, space);
    kb.setPressed(hash);
    kb.keyPressed(spaceBar);
    assertEquals("space bar pressed", oc.toString());
  }

  @Test
  public void testR() {
    init();
    KeyboardHandler kb = new KeyboardHandler();
    Map<Integer, Runnable> hash = new HashMap<>();
    hash.put(KeyEvent.VK_R, rKey);
    kb.setPressed(hash);
    kb.keyPressed(r);
    assertEquals("r key pressed", oc.toString());
  }

}