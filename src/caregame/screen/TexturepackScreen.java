/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.screen;

import caregame.Texturepack;
import caregame.Texturepacks;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 *
 * @author Markus
 */
public class TexturepackScreen extends Screen {

    private int selection = 0;
    private List<Texturepack> texturePacks;
    
    public TexturepackScreen() {
        texturePacks = Texturepacks.getTexturePacks("texturepacks/");
        System.out.println("Texture Packs: " + texturePacks.size());
    }
    
    
    
    @Override
    public boolean isPausingGame() {
        return true;
    }

    @Override
    public void render(Graphics g) {
        renderItems(g, game.getWidth() / 2 - 150, 0, 300, ALIGN_LEFT, selection, texturePacks);
    }

    @Override
    public void tick() {
        if (input.up.clicked) selection--;
        if (input.down.clicked) selection++;
        
        int len = texturePacks.size();
        if (selection<0)selection+=len;
        if (selection >= len) selection -= len;
        
        if(input.attack.clicked) {
            if (texturePacks.size() > 0)
                game.setTexturepack(texturePacks.get(selection));
            game.setScreen(new TitleScreen());
        }
        
    }
    
    
    
    
}
