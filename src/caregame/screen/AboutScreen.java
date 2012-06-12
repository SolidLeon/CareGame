/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.screen;

import caregame.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author Markus
 */
public class AboutScreen extends Screen {

    
    
    @Override
    public boolean isPausingGame() {
        return true;
    }

    @Override
    public void render(Graphics g) {
        renderFrameText(g, game.getWidth() / 2 - 150, 0, 300, ALIGN_CENTER, "ABOUT");
        renderFrameText(g, game.getWidth() / 2 - 150, 22+Font.getLineHeight(), 300, ALIGN_LEFT,
                "This game is made by Markus Mannel.", 
                "Graphics made by Desiree Hackl.", 
                "Copyright by Markus Mannel 2012");
        renderFrameText(g, game.getWidth() / 2 - 150, game.getHeight() - getFrameHeight(1)*2, 300, ALIGN_CENTER, "Press any key");
    }

    @Override
    public void tick() {
        if (input.attack.down || input.menu.down) 
            game.setScreen(new TitleScreen());
    }
    
    
    
    
    
}
