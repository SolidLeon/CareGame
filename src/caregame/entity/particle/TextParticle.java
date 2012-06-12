/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.entity.particle;

import caregame.Font;
import caregame.entity.Entity;
import java.awt.Graphics;

/**
 *
 * @author Markus
 */
public class TextParticle extends Entity {
    private String text;
    private int time = 0;
    public double xa, ya, za;
    public double xx,yy,zz;
    
    public TextParticle(String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;
        xx = x;
        yy = y;
        zz = 2;
        xa = random.nextGaussian() * 0.3;
        ya = random.nextGaussian() * 0.2;
        za = random.nextFloat() * 0.7 + 2;
    }

    @Override
    public void tick() {
        time++;
        if (time > 60) remove();
        xx += xa;
        yy += ya;
        zz += za;
        if (zz < 0) {
            zz = 0;
            za *= -0.5;
            xa *= 0.6;
            ya *= 0.6;
        }
        za -= 0.15;
        x = (int) xx;
        y = (int) yy;
    }

    @Override
    public void render(Graphics g) {
        Font.render(g, text, x - text.length() * 4, y - (int) zz);
    }
    
    
    
    
}
