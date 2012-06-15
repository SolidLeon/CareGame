/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field;

import caregame.Game;
import caregame.ImageCache;
import caregame.OPCODES;
import caregame.entity.Entity;
import caregame.entity.Player;
import caregame.item.Item;
import caregame.item.ResourceItem;
import java.awt.Graphics;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Markus
 */
public class FarmTile extends Tile {

//    private boolean watered;

    public FarmTile(int id) {
        super(id);
    }
    
    private boolean isWatered(GameField field, int xt, int yt) {
        return ((field.getData(xt, yt)>>4)&0xf) == 8;
    }

    @Override
    public int getWetness(GameField field, int xt, int yt) {
        return ((field.getData(xt, yt)>>4)&0xf);
    }
    
    
    
    @Override
    public void tick(GameField field, int xt, int yt) {
        int age = field.getData(xt, yt)&0xf;
        if (age < 5) age++;
        int wetness = getWetness(field, xt, yt);
        if (wetness < 8) {
            if (field.weather.isRaining()) {
                wetness++;
            }
            for (int y = yt - 1; y <= yt + 1; y++) {
                for (int x = xt - 1; x <= xt + 1; x++) {
                    Tile t = field.getTile(x,y);
                    if (t == Tile.water || t == Tile.waterHole) {
                        wetness++;
                    }
                }
            }
            field.setData(xt, yt, (wetness<<4) | age);
        }
    }

    @Override
    public void steppedOn(GameField field, int xt, int yt, Entity e) {
        if (isWatered(field, xt, yt)) return;
        if (random.nextInt(60) != 0) return;
        if ((field.getData(xt, yt)&0xf) < 5) return;
        field.setTile(xt, yt, Tile.grass, 0);
    }

    @Override
    public boolean interact(GameField field, int xt, int yt, Player player, Item item, int attackDir) {
        if (item instanceof ResourceItem) {
            ResourceItem ri = (ResourceItem) item;
            return ri.interactOn(this, field, xt, yt, player, attackDir);
        }
        return false;
    }
    
    
    @Override
    public void render(Graphics g, GameField field, int tx, int ty) {
        if (isWatered(field, tx, ty)){
            ImageCache.get().get("texturen/feld_wasser.png").render(g, tx*Game.TILE_SIZE, ty*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
        } else {
            ImageCache.get().get("texturen/feld.png").render(g, tx*Game.TILE_SIZE, ty*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
        }
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(OPCODES.OP_TILE_FARM);
    }
    
    
    
}
