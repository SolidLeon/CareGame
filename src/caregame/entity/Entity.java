/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.entity;

import caregame.Game;
import caregame.field.GameField;
import caregame.field.Tile;
import caregame.item.Item;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Markus
 */
public class Entity {

    protected Random random = new Random();
    public int x, y;
    protected GameField field;
    public boolean removed;
    protected int xr = 3;
    protected int yr = 3;
    
    public void remove() {
        this.removed = true;
    }
    
    public void init(GameField field) {
        this.field = field;
    }

    public void tick() {
    }
    
    public void render(Graphics g) {
        
    }
    
    public boolean use(Player player, int attackDir) {
        return false;
    }

    public boolean move(int xa, int ya) {
        if (xa != 0 || ya != 0) {
            boolean stopped = true;
            if (xa != 0 && move2(xa,0)) stopped = false;
            if (ya != 0 && move2(0,ya)) stopped = false;
            if (!stopped) {
                int xt = x >> 5;
                int yt = y >> 5;
                field.getTile(xt, yt).steppedOn(field, xt, yt, this);
            }
            return !stopped;
        }
        return true;
    }
    
    protected boolean move2(int xa, int ya) {
        if (xa != 0 && ya != 0) throw new IllegalArgumentException("move2 only one axis at a time");
        
        int xto0 = (x - xr) >> 5;
        int yto0 = (y - yr) >> 5;
        int xto1 = (x + xr) >> 5;
        int yto1 = (y + yr) >> 5;
        
        int xt0 = ((x + xa) - xr) >> 5;
        int yt0 = ((y + ya) - yr) >> 5;
        int xt1 = ((x + xa) + xr) >> 5;
        int yt1 = ((y + ya) + yr) >> 5;
        
        for (int yt = yt0; yt <= yt1; yt++) {
            for (int xt = xt0; xt <= xt1; xt++) {
                if (xt >= xto0 && xt <= xto1 && yt0 >= yto0 && yt <= yto1) continue; //dont check within own tile
                field.getTile(xt, yt).bumpedInto(field, xt, yt, this);
                if (!field.getTile(xt, yt).mayPass(field, xt, yt, this)) {
                    return false;
                }
            }
        }
//        System.out.println(getClass().getSimpleName() + "CD");
        
        List<Entity> wasInside = field.getEntities(
                x - xr, 
                y - yr, 
                x + xr, 
                y + yr);
        List<Entity> isInside = field.getEntities(
                x + xa - xr,
                y + ya - yr, 
                x + xa + xr,
                y + ya + yr);
        
        for (int i = 0; i < isInside.size(); i++) {
            Entity e = isInside.get(i);
            if (e == this) continue;
            e.touchedBy(this);
        }
        isInside.removeAll(wasInside);
        for (int i = 0; i < isInside.size(); i++) {
            Entity e = isInside.get(i);
            if ( e == this ) continue;
            if (e.blocks(this)) {
                return false;
            }
        }
        
        x += xa;
        y += ya;
        return true;
    }
    
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(x);
        out.writeInt(y);
    }
    
    public void read(DataInputStream in) throws IOException {
        x = in.readInt();
        y = in.readInt();
    }

    public void hurt(Creature c, int dmg, int attackDir) {}
    
    public boolean intersects(int x0, int y0, int x1, int y1) {
//        if (x + xr < x0) return false; //a.right < b.left
//        if (x - xr > x1) return false; //a.left > b.right
//        if (y + yr < y0) return false; //a.bottom < b.top
//        if (y - yr > y1) return false; //a.top > b.bottom
//        return true;
        return !(x + xr < x0 || y + yr < y0 || x - xr > x1 || y - yr > y1);
    }

    public boolean interact(Player player, Item item, int attackDir) {
        return item.interact(player, this, attackDir);
    }

    public boolean canSwim() {
        return false;
    }
    
    public boolean isSwimming() {
        return field.getTile(x/Game.TILE_SIZE, y/Game.TILE_SIZE) == Tile.water;
    }
    
    public boolean blocks(Entity e) {
        return false;
    }

    protected void touchedBy(Entity e) {
        
    }
    public void touchItem(ItemEntity itemEntity) {}
    public boolean isBlockableBy(Creature creature) {
        return true;
    }
}
