/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.entity.particle;

import caregame.ImageCache;
import caregame.entity.Entity;
import java.awt.Graphics;

/**
 *
 * @author Markus
 */
public class SmashParticle extends Entity {
    private int time = 0;
    
    public SmashParticle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void tick() {
        time++;
        if (time > 10) {
            remove();
        }
    }

    @Override
    public void render(Graphics g) {
        ImageCache.get().get("blume_rot.png").render(g, x, y, 8, 8);
    }
    
}
