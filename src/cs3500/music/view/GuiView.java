package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.Note;

/**
 * Created by baharsheikhi on 6/20/16. To represent any view that is able to handle mouse and
 * keyboard input
 */
public interface GuiView extends IView {

  /**
   * Adds a mouseListener
   *
   * @param m the mouseListener to be added
   */
  void addMouseListener(MouseListener m);

  /**
   * removes a mouseListener
   */
  void removeMouseListener();

  /**
   * Adds an actionListener
   *
   * @param action the actionListener to be added
   */
  void addActionListener(ActionListener action);

  /**
   * Adds a keyListener
   *
   * @param keyListener the keyListener to be added
   */
  void addKeyListener(KeyListener keyListener);

  /**
   * Uses JOptionPane to get user input on a note
   *
   * @return a note based on user input
   */
  Note userNote();

}
