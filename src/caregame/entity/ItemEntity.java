/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.entity;

import caregame.item.Item;
import java.awt.Graphics;

/**
 *
 * @author Markus
 */
public class ItemEntity extends Entity {

    public Item item;
    public double xa, ya, za;
    public double xx, yy, zz;
    private int lifeTime;
    private int time = 0;

    public ItemEntity(Item item, int x, int y) {
        this.item = item;
        this.x = x;
        this.y = y;

        zz = 2;
        xa = random.nextGaussian() * 0.3;
        ya = random.nextGaussian() * 0.2;
        za = random.nextFloat() * 0.7 + 1;

        lifeTime = 60 * 10 + random.nextInt(60);
    }

    @Override
    public void tick() {
        time++;
        if (time >= lifeTime) {
            remove();
            return;
        }
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
        int ox = x;
        int oy = y;
        int nx = (int) xx;
        int ny = (int) yy;
        int expectedx = nx - x;
        int expectedy = ny - y;
        move(nx - x, ny - y);
        int gotx = x - ox;
        int goty = y - oy;
        xx += gotx - expectedx;
        yy += goty - expectedy;
    }
    

    @Override
    public void render(Graphics g) {
        item.getSprite().render(g, x, y, 16, 16);
    }

    public void take(Player player) {
        item.onTake(this);
        remove();
    }

    @Override
    protected void touchedBy(Entity e) {
        if (time > 30) e.touchItem(this);
    }
    
    
    
    
}
