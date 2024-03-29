/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.entity;

import caregame.ImageCache;
import caregame.Sprite;
import caregame.item.ResourceItem;
import caregame.item.resource.Resource;
import java.awt.Graphics;

/**
 *
 * @author Markus
 */
public class Pig extends Creature {

    private int xa;
    private int ya;
    private int randomWalkTime;

    
    public Pig() {
        health = maxHealth = 10;
        xr = 16;
        yr = 16;
    }
    
    @Override
    public void tick() {
        super.tick();
        
        int speed = tickTime & 1;
        if (!move(xa * speed, ya * speed) || random.nextInt(100) == 0){
            randomWalkTime = 60;
            xa = (random.nextInt(3) - 1) * random.nextInt(2);
            ya = (random.nextInt(3) - 1) * random.nextInt(2);
        }
        if (randomWalkTime > 0) randomWalkTime--;
    }

    @Override
    public void render(Graphics g) {
        Sprite s = ImageCache.get().get("pig.png");
        s.render(g, x-xr, y-yr, 32, 32);
    }

    @Override
    protected void die() {
        int n = random.nextInt(1) + 1;
        for (int i = 0; i < n; i++) {
            field.add(new ItemEntity(new ResourceItem(Resource.rawPorkchop), x+random.nextInt(10) + 3, y + random.nextInt(10) + 3));
        }
        super.die();
    }

    
    
    
}
