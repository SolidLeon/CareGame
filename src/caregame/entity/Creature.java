/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.entity;

import caregame.entity.particle.TextParticle;
import caregame.field.GameField;

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
    public int hurtTime = 0;
    
    @Override
    public void tick() {
        tickTime++;
        if (health <= 0) {
            die();
        }
    }
    
    protected void die() {
        remove();
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
        if (hurtTime > 0) return;
        field.add(new TextParticle("H" + Integer.toString(heal), x, y));
        health += heal;
        if (health > maxHealth) health = maxHealth;
    }

    @Override
    public boolean blocks(Entity e) {
        return e.isBlockableBy(this);
    }

    @Override
    public void hurt(Creature c, int dmg, int attackDir) {
        doHurt(dmg, attackDir);
    }
    
    protected void doHurt(int dmg, int attackDir) {
        if (hurtTime > 0) return;
        
        if (field.player != null) {
            int xd = field.player.x  - x;
            int yd = field.player.y  - y;
            if (xd * xd + yd*yd < 80*80) {
//                Sound.creatureHurt.play();
            }
        }
        field.add(
                new TextParticle("" + dmg, x, y));
        health -= dmg;
        if (attackDir == DIR_SOUTH) yKnockback = +6;
        if (attackDir == DIR_NORTH) yKnockback = -6;
        if (attackDir == DIR_WEST) xKnockback = -6;
        if (attackDir == DIR_EAST) xKnockback = +6;
        hurtTime = 10;
    }
    
    public boolean findStartPos(GameField field) {
        int x = random.nextInt(field.width);
        int y = random.nextInt(field.height);
        int xx = x * 32 + 16;
        int yy = y * 32 + 16;
        
        if (field.player != null) {
            int xd = field.player.x - xx;
            int yd = field.player.y - yy;
            if (xd*xd + yd*yd < 80*80) return false;
        }
        int r = field.creatureDensity * 32;
        if (field.getEntities(xx-r, yy-r, xx+r, yy+r).size() > 0) return false;
        
        if (field.getTile(x, y).mayPass(field, x, y, this)) {
            this.x = xx;
            this.y = yy;
            return true;
        }
        return false;
    }
    
}
