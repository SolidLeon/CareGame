/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Markus
 */
public class InputHandler implements KeyListener {


    public class Key {
        public boolean down, clicked;
        public int presses, absorbs;

        public Key() {
            keys.add(this);
        }
        
        public void toggle(boolean pressed) {
            if (pressed != down) {
                down = pressed;
            }
            if (pressed) {
                presses++;
            }
        }
        
        public void tick() {
            if (absorbs < presses) {
                absorbs++;
                clicked = true;
            } else {
                clicked = false;
            }
        }
    }
    
    public List<Key> keys = new ArrayList<Key>();
    public Key up = new Key();
    public Key down = new Key();
    public Key right = new Key();
    public Key left = new Key();
    public Key attack = new Key();
    public Key menu = new Key();
    public Key crafting = new Key();
    public Key drop = new Key();
    // text input
    public Key escape = new Key();
    public Key enter = new Key();
    public Key space = new Key();
    public Key backspace = new Key();
    public Key []abc;
    public Key []ABC;
    public boolean textInputActive = false;
    
    public InputHandler(Game game) {
        game.addKeyListener(this);
        //abcde fghij klmno pqrst uvwxy z
        abc = new Key[26];
        ABC = new Key[26];
        for (int i = 0; i < 26; i++) {
            abc[i] = new Key();
            ABC[i] = new Key();
        }
    }
    public void tick() {
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).tick();
        }
    }
    
    public void releaseAll() {
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).down = false;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        toggle(ke, true);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        toggle(ke, false);
    }
    
    private void toggle(KeyEvent ke, boolean pressed) {
        if (ke.getKeyCode() == KeyEvent.VK_F3 && pressed) Game.DEBUG = !Game.DEBUG;
        if (ke.getKeyCode() == KeyEvent.VK_LEFT) left.toggle(pressed);
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) right.toggle(pressed);
        if (ke.getKeyCode() == KeyEvent.VK_UP) up.toggle(pressed);
        if (ke.getKeyCode() == KeyEvent.VK_DOWN) down.toggle(pressed);
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) escape.toggle(pressed);
        if (!textInputActive) {
            if (ke.getKeyCode() == KeyEvent.VK_A) left.toggle(pressed);
            if (ke.getKeyCode() == KeyEvent.VK_D) right.toggle(pressed);
            if (ke.getKeyCode() == KeyEvent.VK_W) up.toggle(pressed);
            if (ke.getKeyCode() == KeyEvent.VK_S) down.toggle(pressed);
            if (ke.getKeyCode() == KeyEvent.VK_SPACE) attack.toggle(pressed);
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) attack.toggle(pressed);
            if (ke.getKeyCode() == KeyEvent.VK_E) menu.toggle(pressed);
            if (ke.getKeyCode() == KeyEvent.VK_C) crafting.toggle(pressed);
            if (ke.getKeyCode() == KeyEvent.VK_Q) drop.toggle(pressed);
        } else {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) enter.toggle(pressed);
            if (ke.getKeyCode() == KeyEvent.VK_SPACE) space.toggle(pressed);
            if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) backspace.toggle(pressed);
            int kc = ke.getKeyCode();
            if (kc >= KeyEvent.VK_A && kc <= KeyEvent.VK_Z) {
//                System.out.println("" + kc + ", " + ke.getKeyChar() + " = " + (int) ke.getKeyChar());
                if (ke.isShiftDown()) {
                    ABC[ke.getKeyChar() - 'A'].toggle(pressed);
                } else {
                    abc[ke.getKeyChar() - 'a'].toggle(pressed);
                }
            }
        }
    }
    
    public void setTextInputEnabled(boolean enabled) {
        this.textInputActive = enabled;
    }
    
    public Key getLetter(int ch) {
        if (ch >= 'a' && ch <= 'z') return abc[ch - 'a'];
        if (ch >= 'A' && ch <= 'Z') return ABC[ch - 'A'];
        return null;
    }
}
