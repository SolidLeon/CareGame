/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.screen;

import caregame.Font;
import java.awt.Graphics;

/**
 *
 * @author Markus
 */
public class DeadScreen extends Screen {

    @Override
    public void render(Graphics g) {
        Font.render(g, "You died!", game.getWidth() / 2 - Font.getWidth("You died!") / 2, game.getHeight() / 2 - Font.getLineHeight() / 2);
    }

    @Override
    public void tick() {
        if (input.attack.clicked || input.menu.clicked) {
            game.setScreen(new TitleScreen());
        }
    }
    
    
    
}
