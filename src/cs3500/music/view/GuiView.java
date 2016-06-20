package cs3500.music.view;

import java.awt.event.MouseListener;

/**
 * Created by baharsheikhi on 6/20/16.
 */
public interface GuiView extends IView {

    //methods for dealing with keyboard and mouse

    void addMouseListener(MouseListener m);

    void removeMouseListener();


}
