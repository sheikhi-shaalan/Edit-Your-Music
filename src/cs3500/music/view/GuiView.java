package cs3500.music.view;

import cs3500.music.model.Note;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Created by baharsheikhi on 6/20/16.
 */
public interface GuiView extends IView {

    //methods for dealing with keyboard and mouse
    void addMouseListener(MouseListener m);
    void removeMouseListener();
    void addActionListener(ActionListener action);
    void addKeyListener(KeyListener keyListener);
    Note userNote();

}
