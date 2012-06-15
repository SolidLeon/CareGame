/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field;

import caregame.ImageCache;
import caregame.Sprite;
import caregame.entity.Creature;
import caregame.entity.Entity;
import caregame.entity.Player;
import caregame.item.Item;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Markus
 */
public class Tile {
    
    public static final Tile []tiles = new Tile[256];
    public static final Tile grass = new GrassTile(0);
    public static final Tile farmland = new FarmTile(1);
    public static final Tile invisibleWall = new InvisibleWallTile(2);
    public static final Tile tree = new TreeTile(3, 0);
    public static final Tile pine = new TreeTile(4, 1);
    public static final Tile water = new WaterTile(5);
    public static final Tile flower = new FlowerTile(6);
    public static final Tile treeSapling = new SeedTile(7, grass, tree);
    public static final Tile pineSapling = new SeedTile(8, grass, pine);
    public static final Tile flowerSeed = new SeedTile(9, farmland, flower);
    public static final Tile hole = new HoleTile(10, grass);
    public static final Tile waterHole = new WaterHoleTile(11, grass);
    public static final Tile wheat = new WheatTile(12);
    public static final Tile wheatSeed = new SeedTile(13, farmland, wheat);
    public static final Tile elevatorUpTile = new ElevatorTile(14, false);
    public static final Tile elevatorDownTile = new ElevatorTile(15, true);
    
    protected Random random = new Random();
    public final byte id;
    
    public Tile(int id) {
        this.id = (byte) id;
        if (tiles[id] != null) throw new RuntimeException("Duplicate tile ids, " + id);
        tiles[id] = this;
    }
    
    protected Sprite getSprite(String ref) {
        return ImageCache.get().get(ref);
    }
    
    protected boolean isWaterAround(GameField field, int xt, int yt) {
        if (field.weather.isRaining()) {
            return true;
        }
        for (int y = yt - 1; y <= yt + 1; y++) {
            for (int x = xt - 1; x <= xt + 1; x++) {
                Tile t = field.getTile(x,y);
                if (t == Tile.water || t == Tile.waterHole) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void tick( GameField field, int xt, int yt) {
        
    }
    
    public void render(Graphics g, GameField field, int xt, int yt) {
        
    }
    
    public boolean interact(GameField field, int xt, int yt, Player player, Item item, int attackDir) {
        return true;
    }
    
    public boolean mayPass(GameField field, int x, int y, Entity e) {
        return true;
    }
    
    public void write(DataOutputStream out) throws IOException {}
    public void read(DataInputStream in) throws IOException {}

    public void steppedOn(GameField field, int xt, int yt, Entity e) {
        
    }

    public void bumpedInto(GameField field, int xt, int yt, Entity e) {
        
    }

    public boolean use(GameField field, int xt, int yt, Player player, int attackDir) {
        return false;
    }

    public void hurt(GameField field, int xt, int yt, Creature attacker, int dmg, int dir) {
        
    }

}
