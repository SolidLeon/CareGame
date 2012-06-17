/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.screen;

import caregame.Inventory;
import caregame.Savegame;
import caregame.field.GameField;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Markus
 */
public class LoadScreen extends Screen {

    private int selection = 0;
    private List<Savegame> saves;

    public LoadScreen() {
        saves = Savegame.getSavegamesPacks();
    }
    
    @Override
    public void render(Graphics g) {
        int ox = game.getWidth() / 2 - 250;
        int oy = 20;
        renderFrameText(g, ox, oy, 500, ALIGN_CENTER, "SELECT WORLD");
        renderItems(g, ox, oy+getFrameHeight(1), 500, ALIGN_LEFT, selection, saves);
    }

    @Override
    public boolean isPausingGame() {
        return true;
    }

    @Override
    public void tick() {
        if (input.menu.clicked) game.setScreen(new TitleScreen());
        if (input.up.clicked) selection--;
        if (input.down.clicked) selection++;
        
        int len = saves.size();
        if (selection < 0) selection += len;
        if (selection >= len) selection -= len;
        
        if (input.attack.clicked && len > 0) {
            try {
                game.startLoadedGame(saves.get(selection).title);
                saves.get(selection).load(game, input);
                game.setScreen(null);
            } catch (IOException ex) {
                Logger.getLogger(LoadScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    
    
}
