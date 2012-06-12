/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Markus
 */
public class Sprite {
    public BufferedImage img;

    public Sprite(BufferedImage img) {
        this.img = img;
    }

    public void render(Graphics g, int dx0, int dy0, int dx1, int dy1, int sx0, int sy0, int sx1, int sy1) {
        g.drawImage(img, dx0, dy0, dx1, dy1, sx0, sy0, sx1, sy1, null);
    }
    public void render(Graphics g, int x, int y, int w, int h) {
        g.drawImage(img, x, y, x+w, y+h, 0, 0, getWidth(), getHeight(), null);
    }
    public void render(Graphics g, int x, int y) {
        g.drawImage(img, x, y, null);
    }
    
    public void render8(Graphics g, int x, int y, int sprite) {
        int tx = (sprite % 32)*8;
        int ty = (sprite / 32)*8;
        g.drawImage(img, x, y, x+16, y+16, tx, ty, tx+8, ty+8, null);
    }
    
    public int getWidth() {
        return img.getWidth();
    }
    
    public int getHeight() {
        return img.getHeight();
    }
    
}
