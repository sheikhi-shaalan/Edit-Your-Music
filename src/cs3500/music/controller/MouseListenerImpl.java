package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by baharsheikhi on 6/20/16.
 */
public class MouseListenerImpl implements MouseListener {
    private Map<Integer, Runnable> mousey;

    public MouseListenerImpl() {
        this.mousey = new HashMap<Integer, Runnable>();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.mousey.containsKey(e.getButton())){
            this.mousey.get(e.getButton()).run();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setMousey(Map<Integer, Runnable> map) {
        this.mousey = map;
    }
}
