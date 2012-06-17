/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.screen;

import caregame.Font;
import caregame.Savegame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Markus
 */
public class TitleScreen extends Screen {

    private static String []lines = {"START GAME", "LOAD WORLD", "TEXTURE PACKS", "HELP", "ABOUT", "OPTIONS", "QUIT GAME"};
    private int selection;
    private boolean isSaved = false;
    
    public TitleScreen() {
    }
  
    
    @Override
    public void render(Graphics g) {
        if (game != null && game.canResume) {
            lines[0] = "RESUME";
        }
        else lines[0] = "START GAME";
        renderFrameText(g, game.getWidth() / 2 - 100, 0, 200, ALIGN_CENTER, "CARE GAME");
        renderFrameText(g, game.getWidth() / 2 - 100, game.getHeight() / 2 - getFrameHeight(lines.length) / 2, 200, ALIGN_CENTER, selection, lines); 
    }

    @Override
    public boolean isPausingGame() {
        return true;
    }

    @Override
    public void tick() {
        
        if (!isSaved && game != null && game.canResume) {
            //save game
            Savegame sav = new Savegame(game.worldName);
            try {
                sav.save(game);
            } catch (IOException ex) {
                Logger.getLogger(TitleScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            isSaved = true;
        }
        
        if (input.up.clicked) selection--;
        if (input.down.clicked) selection++;
        
        int maxSelection = lines.length;
        
        if (selection < 0) selection += maxSelection;
        if (selection >= maxSelection) selection -= maxSelection;
        if (input.attack.clicked) {
            if (selection == 0) {
                if (game != null && game.canResume) game.setScreen(null);
                else game.setScreen(new EnterWorldnameScreen());
            }
            if (selection == 1) game.setScreen(new LoadScreen());
            if (selection == 2) game.setScreen(new TexturepackScreen());
            if (selection == 3) game.setScreen(new HelpScreen());
            if (selection == 4) game.setScreen(new AboutScreen());
            if (selection == 5) game.setScreen(new OptionsScreen());
            if (selection == 6) game.stop();
        }
    }

    
    
}
