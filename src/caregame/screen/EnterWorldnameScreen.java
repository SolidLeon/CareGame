/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.screen;

import caregame.Game;
import caregame.InputHandler;
import java.awt.Graphics;

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
            renderFrameText(g, ox, oy+getFrameHeight(1), 300, ALIGN_CENTER, name + " ");
        else
            renderFrameText(g, ox, oy+getFrameHeight(1), 300, ALIGN_CENTER, name + "_");
    }
    
    

    @Override
    public void tick() {
        ticks++;
        if (ticks % 30 == 0) showCaret = !showCaret;
        if (input.enter.clicked && name.length() > 0) {
            input.setTextInputEnabled(false);
            if (name.equalsIgnoreCase("ponyland")) {
                game.startNewGame(name, true);
            } else {
                game.startNewGame(name);
            }
        }
        for (int i = 0; i < 26; i++) {
            if (input.abc[i].clicked) name += (char) ('a' + i);
            if (input.ABC[i].clicked) name += (char) ('A' + i);
        }
        if (input.space.clicked) name += ' ';
        if (input.backspace.clicked && name.length() > 0) name = name.substring(0, name.length() - 1);
        if (name.length() > 10) name = name.substring(0 , 10);
    }
    
    
    
    
}
