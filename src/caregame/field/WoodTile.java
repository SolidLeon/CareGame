/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field;

import caregame.ImageCache;
import caregame.Sprite;
import caregame.entity.Creature;
import caregame.entity.Entity;
import caregame.entity.ItemEntity;
import caregame.entity.Player;
import caregame.item.Item;
import caregame.item.ResourceItem;
import caregame.item.resource.Resource;
import java.awt.Graphics;

/**
 *
 * @author Markus
 */
public class WoodTile extends Tile {

    public WoodTile(int id) {
        super(id);
    }

    @Override
    public void render(Graphics g, GameField field, int xt, int yt) {
        Sprite s = ImageCache.get().get("holz.png");
        int id = field.getData(xt, yt);
        Tile.tiles[id].render(g, field, xt, yt);
        s.render(g, xt*32, yt*32, 32, 32);
    }

    @Override
    public boolean mayPass(GameField field, int x, int y, Entity e) {
        return false;
    }

    @Override
    public boolean interact(GameField field, int xt, int yt, Player player, Item item, int attackDir) {
        return super.interact(field, xt, yt, player, item, attackDir);
    }

    @Override
    public void hurt(GameField field, int xt, int yt, Creature attacker, int dmg, int dir) {
        if (attacker instanceof Player) {
            int id = field.getData(xt, yt);
            field.setTile(xt, yt, Tile.tiles[id], 0);
            field.add(new ItemEntity(new ResourceItem(Resource.wood), xt*32+random.nextInt(10)+3, yt*32+random.nextInt(10)+3));
        }
    }
    
}
