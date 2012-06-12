/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame;

import caregame.screen.ListItem;
import java.awt.Graphics;
import java.io.File;

/**
 *
 * @author Markus
 */
public class Texturepack implements ListItem {
    public File file;

    public Texturepack(File file) {
        this.file = file;
    }

    @Override
    public int getWidth() {
        return Font.getWidth(file.getName());
    }

    
    @Override
    public void renderInventory(Graphics g, int x, int y) {
        Font.render(g, file.getName(), x, y);
    }
    
    
}
