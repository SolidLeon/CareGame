/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field;

import caregame.entity.Creature;
import caregame.entity.Entity;
import caregame.entity.ItemEntity;
import caregame.entity.Player;
import caregame.item.Item;
import caregame.item.ResourceItem;
import caregame.item.ToolItem;
import caregame.item.ToolType;
import caregame.item.resource.Resource;
import java.awt.Graphics;

/**
 *
 * @author Markus
 */
public class WheatTile extends Tile {

    public WheatTile(int id) {
        super(id);
    }

    @Override
    public int getWetness(GameField field, int xt, int yt) {
        return ((field.getData(xt, yt)>>4)&0xf);
    }

    @Override
    public void render(Graphics g, GameField field, int xt, int yt) {
        int age = field.getData(xt, yt)&0x7;
//        int progress = age / 10;
        
        Tile.farmland.render(g, field, xt, yt);
        if (age == 0) {
            getSprite("texturen/seeds.png").render(g, xt*32, yt*32);
        } else if (age == 1) {
            getSprite("phase1.png").render(g, xt*32, yt*32);
        } else if (age == 2) {
            getSprite("phase2.png").render(g, xt*32, yt*32);
        } else if (age == 3) {
            getSprite("phase3.png").render(g, xt*32, yt*32);
        } else if (age == 4) {
            getSprite("weizen_phase1.png").render(g, xt*32, yt*32);
        } else if (age == 5) {
            getSprite("weizen_phase2.png").render(g, xt*32, yt*32);
        }
    }

    @Override
    public void tick(GameField field, int xt, int yt) {
        int age = (field.getData(xt, yt)&0x7);
        if (age < 5 && random.nextInt(200) == 0) {
            age += 1;
        }
        int wetness = getWetness(field, xt, yt);
        if (wetness < 8 && random.nextInt(60) != 0 ) {
            if (isWaterAround(field, xt, yt)) wetness += 1;
        }
        field.setData(xt, yt, (wetness<<4) | age);
    }

    
    
    @Override
    public boolean interact(GameField field, int xt, int yt, Player player, Item item, int attackDir) {
        if (item instanceof ToolItem) {
            ToolItem tool = (ToolItem) item;
            if (tool.type == ToolType.shovel) {
                if (player.payStamina(4 - tool.level)) {
                    field.setTile(xt, yt, Tile.grass, 0);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void steppedOn(GameField field, int xt, int yt, Entity e) {
        if (random.nextInt(60) != 0) return;
        if (field.getData(xt, yt) < 2) return;
        harvest(field, xt, yt);
    }

    @Override
    public void hurt(GameField field, int xt, int yt, Creature attacker, int dmg, int dir) {
        harvest(field, xt, yt);
    }
    
    private void harvest(GameField field, int xt, int yt) {
        int age = field.getData(xt, yt);
        int count = random.nextInt(2);
        for (int i = 0; i < count; i++) {
            field.add(
                    new ItemEntity(
                    new ResourceItem(Resource.wheatSeeds),
                    xt*32+random.nextInt(10) + 3, 
                    yt*32+random.nextInt(10) + 3));
        }
        count = 0;
        if (age == 50) {
            count = random.nextInt(3) + 2;
        } else if (age >= 40) {
            count = random.nextInt(2) + 1;
        }
        for (int i = 0; i < count; i++) {
            field.add(
                    new ItemEntity(
                    new ResourceItem(Resource.wheat),
                    xt*32+random.nextInt(10) + 3, 
                    yt*32+random.nextInt(10) + 3));
        }
        field.setTile(xt, yt, Tile.grass, 0);
    }
    
    
    
    
    
    
    
}
