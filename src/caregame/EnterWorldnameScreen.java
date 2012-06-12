/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.screen;

import caregame.Game;
import caregame.InputHandler;
import com.sun.org.apache.bcel.internal.generic.Select;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author Markus
 */
public class EnterWorldnameScreen extends Screen {

    private String name = "";
    private int ticks;
    private boolean showCaret;

    @Override
    public void init(Game game, InputHandler input) {
        super.init(game, input);
        input.setTextInputEnabled(true);
    }
    
    
    
    @Override
    public boolean isPausingGame() {
        return true;
    }

    @Override
    public void render(Graphics g) {
        int ox = game.getWidth() / 2 - 150;
        int oy = 20;
        
        renderFrameText(g, ox, oy, 300, ALIGN_CENTER, "Enter world name:");
        if (showCaret)
            renderFrameText(g, ox, oy+getFrameHeight(1), 300, ALIGN_CENTER, name);
        else
            renderFrameText(g, ox, oy+getFrameHeight(1), 300, ALIGN_CENTER, name + "_");
    }
    
    

    @Override
    public void tick() {
        ticks++;
        if (ticks % 60 == 0) showCaret = !showCaret;
        if (input.attack.clicked) {
            game.startNewGame("World " + 0);
        }
        for (int i = 0; i < 26; i++) {
            if (input.abc[i].clicked) name += ('a' + i);
            if (input.ABC[i].clicked) name += ('A' + i);
        }
        if (input.space.clicked) name += ' ';
        if (input.enter.clicked) input.setTextInputEnabled(false);
//        if (ke.getKeyChar() >= 'a' && ke.getKeyChar() <= 'z') {
//            name += ke.getKeyChar();
//        }
//        if (ke.getKeyChar() >= 'A' && ke.getKeyChar() <= 'Z') {
//            name += ke.getKeyChar();
//        }
//        if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
//            if (name.length() > 0) {
//                name = name.substring(0, name.length() - 1);
//            }
//        }
//        if (name.length() > 10) name = name.substring(0, 10);
//        
//        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) game.setScreen(new TitleScreen());
//        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
//            game.startNewGame(name);
//        }
    }
    
    
    
    
}
