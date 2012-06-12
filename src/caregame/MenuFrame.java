/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Markus
 */
public class MenuFrame {
    private Sprite s;

    public MenuFrame(Sprite s) {
        this.s = s;
    }
    
    public void render(Graphics g, int x, int y, int width, int lines) {
        //corners 11x11 pixel
        int lh = g.getFontMetrics().getHeight();
        g.drawImage(null, 0, 0, 11, 11, x, y, x+11, y+11, null); //left top corner
        g.drawImage(null, 11, 0, 53, 11, x+11, y, x+width-11, y+11, null); //top
        g.drawImage(null, 53, 0, 64, 11, x+width-11, y, x+width, y+11, null); //right top corner
        
        g.drawImage(null, 0, 11, 11, 53, x, y+11, x+11, y+11+lines*lh, null); //left side
        g.drawImage(null, 11, 11, 27, 27, x+11, y+11, x+width-11, y+11+lines*lh, null); //center
        g.drawImage(null, 53, 11, 64, 27, x+width-11, y+11, x+width, y+11+lines*lh, null); //right side
        
        
        g.drawImage(null, 0, 53, 11, 64, x, y+11+lh*lines, x+11, y+11+lh*lines+11, null); //left bottom corner
        g.drawImage(null, 11, 53, 53, 64, x+11, y+11+lh*lines, x+width-11, y+11+lh*lines+11, null); //bottom
        g.drawImage(null, 53, 53, 64, 64, x+width-11, y+11+lh*lines, x+width, y+11+lh*lines+11, null); //right bottom corner
        
        
    }
    
}
