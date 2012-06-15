/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.entity;

import caregame.entity.particle.TextParticle;

/**
 *
 * @author Markus
 */
public class Creature extends Entity {
    public static final int DIR_NORTH = 0;
    public static final int DIR_EAST = 1;
    public static final int DIR_SOUTH = 2;
    public static final int DIR_WEST = 3;
    protected int dir = 0;
    public int maxHealth = 10;
    public int health = maxHealth;
    public int tickTime = 0;
    public int swimTimer = 0;
    public int walkDist = 0;
    private int xKnockback;
    private int yKnockback;
    
    @Override
    public void tick() {
        tickTime++;
        if (health <= 0) {
            remove();
        }
    }
    
    

    @Override
    public boolean move(int xa, int ya) {
        if (isSwimming()) {
            if (swimTimer++ % 2 == 0) return true;
        }
        if (xKnockback < 0) {
            move2(-1, 0);
            xKnockback++;
        }
        if (xKnockback > 0) {
            move2(+1, 0);
            xKnockback--;
        }
        if (yKnockback < 0) {
            move2(0, -1);
            yKnockback++;
        }
        if (yKnockback > 0) {
            move2(0, 1);
            yKnockback--;
        }
        if (xa != 0 || ya != 0) {
            walkDist++;
            if (xa < 0) dir = DIR_WEST;
            if (xa > 0) dir = DIR_EAST;
            if (ya < 0) dir = DIR_NORTH;
            if (ya > 0) dir = DIR_SOUTH;
        }
        return super.move(xa, ya);
    }
    
    public void heal(int heal) {
        field.add(new TextParticle("H" + Integer.toString(heal), x, y));
        health += heal;
        if (health > maxHealth) health = maxHealth;
    }

    @Override
    public boolean blocks(Entity e) {
        return e.isBlockableBy(this);
    }
    
    
}
