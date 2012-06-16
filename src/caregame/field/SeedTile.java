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
    
    
    @Override
    public void tick(GameField field, int xt, int yt) {
        int growth = 1;
        if (isWaterAround(field, xt, yt)) {
            growth = 3;
        } else {
            if (random.nextInt(100) == 0) {
                field.setTile(xt, yt, Tile.grass, 0);
                return;
            }
        }
        int age = field.getData(xt, yt) + growth;
        if (age >= 100) { //4th age
            field.setTile(xt, yt, growsTo, 0);
        } else {
            field.setData(xt, yt, age);
        }
    }
    
    @Override
    public void render(Graphics g, GameField field, int xt, int yt) {
        growsOn.render(g, field, xt, yt);
        int age = field.getData(xt, yt);
        if (age >= 0 && age < 25) ImageCache.get().get("phase1.png").render(g, xt*32, yt*32);
        if (age >= 25 && age < 50) ImageCache.get().get("phase1.png").render(g, xt*32, yt*32);
        if (age >= 50 && age < 75) ImageCache.get().get("phase2.png").render(g, xt*32, yt*32);
        if (age >= 75 && age < 100) ImageCache.get().get("phase3.png").render(g, xt*32, yt*32);
        
    }

    @Override
    public void hurt(GameField field, int xt, int yt, Creature attacker, int dmg, int dir) {
        field.setTile(xt, yt, growsOn, 0);
    }
    
    

    
    
}
