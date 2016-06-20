package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by baharsheikhi on 6/20/16.
 */
public class KeyboardHandler implements KeyListener {

    private  Map<Integer, Runnable> typed;
    private  Map<Integer, Runnable> pressed;
    private  Map<Integer, Runnable> released;

    public KeyboardHandler() {
        this.typed = new HashMap<Integer, Runnable>();
        this.pressed = new HashMap<Integer, Runnable>();
        this.released = new HashMap<Integer, Runnable>();
    }

    public void setTyped(Map<Integer, Runnable> map) {
        this.typed = map;
    }
    public void setPressed(Map<Integer, Runnable> map) {
        this.pressed = map;
    }
    public void setReleased(Map<Integer, Runnable> map) {
        this.released = map;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (this.typed.containsKey(e.getKeyCode())) {
            this.typed.get(e.getKeyCode()).run();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.pressed.containsKey(e.getKeyCode())) {
            this.pressed.get(e.getKeyCode()).run();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.released.containsKey(e.getKeyCode())) {
            this.released.get(e.getKeyCode()).run();
        }
    }
}
