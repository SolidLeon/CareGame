/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field;

import caregame.Game;
import caregame.ImageCache;
import caregame.OPCODES;
import caregame.entity.Creature;
import caregame.entity.ItemEntity;
import caregame.item.*;
import caregame.item.resource.PlantableResource;
import caregame.item.resource.Resource;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Markus
 */
public class SeedTile extends Tile {
    
    private Tile growsOn;
    private Tile growsTo;
    
    public SeedTile(int id, Tile growsOn, Tile growsTo) {
        super(id);
        this.growsOn = growsOn;
        this.growsTo = growsTo;
    }
    
    private boolean isWatered(GameField field, int xt, int yt) {
        return ((field.getData(xt, yt)>>4)&0xf) == 8;
    }
    
    
    @Override
    public void tick(GameField field, int xt, int yt) {
        int growth = 60;
        if (isWatered(field, xt, yt)) {
            growth = 90;
        } else {
            if (random.nextInt(100) == 0) {
                field.setTile(xt, yt, Tile.grass, 0);
                return;
            }
        }
        if (random.nextInt(growth) != 0) return;
        int age = (field.getData(xt, yt)&0x7) + 1;
        if (age >= 3) { //4th age
            field.setTile(xt, yt, growsTo, 0);
        } else {
            field.setData(xt, yt, (field.getData(xt, yt)&0xf0) | age);
        }
    }
    
    @Override
    public void render(Graphics g, GameField field, int xt, int yt) {
        growsOn.render(g, field, xt, yt);
        int age = field.getData(xt, yt)&0x7;
        if (age == 0) ImageCache.get().get("phase1.png").render(g, xt*32, yt*32);
        if (age == 1) ImageCache.get().get("phase1.png").render(g, xt*32, yt*32);
        if (age == 2) ImageCache.get().get("phase2.png").render(g, xt*32, yt*32);
        if (age == 3) ImageCache.get().get("phase3.png").render(g, xt*32, yt*32);
        
    }

    @Override
    public void hurt(GameField field, int xt, int yt, Creature attacker, int dmg, int dir) {
        field.setTile(xt, yt, growsOn, 0);
    }
    
    

    
    
}
