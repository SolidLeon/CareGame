/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field;

import caregame.Game;
import caregame.entity.Entity;
import caregame.entity.Player;
import caregame.field.biome.Biome;
import caregame.field.fieldgen.FieldGen;
import caregame.weather.Weather;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Markus
 */
public class GameField {
    public int width = 256;
    public int height = 256;
    public byte[] field;
    public byte[] data;
    public byte[] light;
    private Random random = new Random();
    
    public Player player;
    
    public List<Entity> entities = new ArrayList<Entity>();
    public List<Entity>[] entitiesInTiles;
    public int level;
    public Biome biome;
    public Weather weather = Weather.sunny;
    
    /**
     * game time in seconds (so each 60ticks, +1)
     * one day takes 16 minutes, so 8 minutes day, 8 minutes night
     * as we have 16 light levels, we run through them as the day passes
     * 
     * night                day              night
     * 0 1 2 3 4  5  6  7  8 | 9 10 11 12 13 14 15
     * 
     */
    public int gameTime = 0;
    private int ticks = 0;
    private byte []ll = { 0, 2, 4, 6, 8, 12, 14, 15, 14, 12, 8, 6, 4, 2, 0 };
    
    public GameField(Biome biome, int level) {
        this.biome = biome;
        this.level = level;
        byte [][]map = FieldGen.generateField(width, height, level, biome);
        field = map[0];
        data = map[1];
        light = new byte[width*height];
        entitiesInTiles = new ArrayList[width*height];
        for (int i = 0; i < entitiesInTiles.length; i++) {
            entitiesInTiles[i] = new ArrayList<Entity>();
        }
    }
    
    public int getLightLevel(int xt, int yt) {
        return light[xt + yt * width];
    }

    public void add(Entity e) {
        
        e.removed = false;
        entities.add(e);
        e.init(this);
        
        insertEntity(e.x / Game.TILE_SIZE, e.y / Game.TILE_SIZE, e);
        
        if (e instanceof Player) {
            this.player = (Player) e;
        }
    }
    
    public void remove(Entity e) {
        entities.remove(e);
        int xt = e.x / Game.TILE_SIZE;
        int yt = e.y / Game.TILE_SIZE;
        removeEntity(xt, yt, e);
    }
    
    private void insertEntity(int x, int y, Entity e) {
        if (x < 0 || y < 0 || x >= width || y >= height) return;
        entitiesInTiles[x + y * width].add(e);
    }
    
    private void removeEntity(int xt, int yt, Entity e) {
        if (xt < 0 || yt < 0 || xt >= width || yt >= height) return;
        entitiesInTiles[xt + yt * width].remove(e);
    }
    
    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width ||y >= height) return Tile.invisibleWall;
        return Tile.tiles[field[x+y*width]];
    }
    public void setTile(int x, int y, Tile t, int dataValue) {
        if (x < 0 || y < 0 || x >= width ||y >= height) return;
        field[x+y*width] = t.id;
        data[x+y*width] = (byte) dataValue;
    }
    public byte getData(int x, int y) {
        if (x < 0 || y < 0 || x >= width ||y >= height) return 0;
        return data[x+y*width];
    }
    public void setData(int x, int y, int dataValue) {
        if (x < 0 || y < 0 || x >= width ||y >= height) return;
        data[x+y*width] = (byte) dataValue;
    }
    public void setLightLevel(int xt, int yt, int ll) {
        light[xt+yt*width] = (byte) ll;
    }

    public void tick() {
        ticks++;
        if (ticks % 60 == 0) gameTime++;
        if (gameTime > 8*60) gameTime = 0;
        int tm = (gameTime/60);
        for (int i = 0; i < light.length; i++) light[i] = ll[tm]; //set to daytime depending value
        
        for (int i = 0; i < width * height / 50; i++) {
            int xt = random.nextInt(width);
            int yt = random.nextInt(height);
            getTile(xt, yt).tick(this, xt, yt);
        }
        
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            int xto = e.x / Game.TILE_SIZE;
            int yto = e.y / Game.TILE_SIZE;
            entities.get(i).tick();
            if (e.removed) {
                entities.remove(i--);
                removeEntity(xto, yto, e);
            } else {
                int xt = e.x / Game.TILE_SIZE;
                int yt = e.y / Game.TILE_SIZE;
                if (xt != xto || yt != yto) {
                    removeEntity(xto, yto, e);
                    insertEntity(xt, yt, e);
                }
            }
        }
        
    }
    
    public List<Entity> getEntities(int x0, int y0, int x1, int y1) {
        List<Entity> lst = new ArrayList<Entity>();
        
        int xt0 = (x0 >> 5) - 1;
        int yt0 = (y0 >> 5) - 1;
        int xt1 = (x1 >> 5) + 1;
        int yt1 = (y1 >> 5) + 1;
        
        for (int y = yt0; y <= yt1; y++) {
            for (int x = xt0; x <= xt1; x++) {
                if (x < 0 || y < 0 || x >= width || y >= height) continue;
                List<Entity> entities = entitiesInTiles[x+y*width];
                for (int i = 0; i < entities.size(); i++) {
                    Entity e = entities.get(i);
//                    lst.add(e);
                    if (e.intersects(x0, y0, x1, y1)) lst.add(e);
                }
            }
        }
        return lst;
    }
}
