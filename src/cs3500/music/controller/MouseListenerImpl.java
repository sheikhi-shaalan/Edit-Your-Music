package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

/**
 * Created by baharsheikhi on 6/20/16.
 * To represent the mouseListener of this project
 */
public class MouseListenerImpl implements MouseListener {
    //each hashmap represents one of the three buttons possible for a mouse
    //the integer entry of the hashmap represents what the button does (click, press, release, etc)
    private Map<Integer, Runnable> one;
    private Map<Integer, Runnable> two;
    private Map<Integer, Runnable> three;
    private int x;
    private int y;


    /**
     * Creates a new MouseListenerImpl with empty Maps.
     * The x and y fields are not set to a default value
     */
    public MouseListenerImpl() {
        this.one = new HashMap<Integer, Runnable>();
        this.two = new HashMap<Integer, Runnable>();
        this.three = new HashMap<Integer, Runnable>();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        this.mouseHelp(e, MouseEvent.MOUSE_CLICKED);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.mouseHelp(e, MouseEvent.MOUSE_PRESSED);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mouseHelp(e, MouseEvent.MOUSE_RELEASED);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.mouseHelp(e, MouseEvent.MOUSE_ENTERED);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.mouseHelp(e, MouseEvent.MOUSE_EXITED);
    }

    /**
     * Setter method for the button 1 hashmap
     *
     * @param map the map to set all the events of the first button with
     */
    public void setOne(Map<Integer, Runnable> map) {
        this.one = map;
    }

    /**
     * Setter method for the button 2 hashmap
     *
     * @param map the map to set all the events of the second button with
     */
    public void setTwo(Map<Integer, Runnable> map) {
        this.two = map;
    }

    /**
     * Setter method for the button 3 hashmap
     *
     * @param map the map to set all the events of the third button with
     */
    public void setThree(Map<Integer, Runnable> map) {
        this.three = map;
    }

    /**
     * A helper method to help abstract out repeated code
     *
     * @param e          the event
     * @param mouseEvent the mouse event associated with a runnable
     */
    private void mouseHelp(MouseEvent e, int mouseEvent) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (this.one.containsKey(mouseEvent)) {
                this.one.get(mouseEvent).run();
            }
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            if (this.two.containsKey(mouseEvent)) {
                this.two.get(mouseEvent).run();
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (this.three.containsKey(mouseEvent)) {
                this.three.get(mouseEvent).run();
            }
        }

    }

    /**
     * @return the x coordinate of this MouseListenerImpl
     */
    protected int getXCoord() {
        return this.x;
    }

    /**
     * @return the y coordinate of this MouseListenerImpl
     */
    protected int getYCoord() {
        return this.y;
    }
}