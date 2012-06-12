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
    public void render(Graphics g, GameField field, int xt, int yt) {
        int age = field.getData(xt, yt);
        int progress = age / 10;
        
        Tile.farmland.render(g, field, xt, yt);
        if (progress == 0) {
            getSprite("texturen/seeds.png").render(g, xt*32, yt*32);
        } else if (progress == 1) {
            getSprite("phase1.png").render(g, xt*32, yt*32);
        } else if (progress == 2) {
            getSprite("phase2.png").render(g, xt*32, yt*32);
        } else if (progress == 3) {
            getSprite("phase3.png").render(g, xt*32, yt*32);
        } else if (progress == 4) {
            getSprite("weizen_phase1.png").render(g, xt*32, yt*32);
        } else if (progress == 5) {
            getSprite("weizen_phase2.png").render(g, xt*32, yt*32);
        }
    }

    @Override
    public void tick(GameField field, int xt, int yt) {
        if (random.nextInt(2) == 0) return;
        int age = field.getData(xt, yt) + 1;
        if (age <= 50) field.setData(xt, yt, age);
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
