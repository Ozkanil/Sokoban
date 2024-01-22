package sokoban;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameListener implements KeyListener {

    static int number;
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this context
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                System.out.println("Up arrow key pressed. Returning 1");
                number=1;
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("Down arrow key pressed. Returning 2");
                number=2;
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("Left arrow key pressed. Returning 3");
                number=3;
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("Right arrow key pressed. Returning 4");
                number=4;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used in this context
    }}
