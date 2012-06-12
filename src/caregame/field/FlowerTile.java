/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field;

import caregame.ImageCache;
import caregame.entity.Creature;
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
public class FlowerTile extends Tile {

    public FlowerTile(int id) {
        super(id);
    }

    @Override
    public void render(Graphics g, GameField field, int xt, int yt) {
        Tile.grass.render(g, field, xt, yt);
        ImageCache.get().get("blume_gelb_klein.png").render(g, xt*32, yt*32);
    }

    @Override
    public void hurt(GameField field, int xt, int yt, Creature attacker, int dmg, int dir) {
        interact(field, xt, yt, null, null, dir);
    }

    
    
    @Override
    public boolean interact(GameField field, int xt, int yt, Player player, Item item, int attackDir) {
        int n = random.nextInt(3)+1;
        for (int i = 0; i < n; i++)
            field.add(new ItemEntity(new ResourceItem(Resource.flowerSeed),  xt * 32 + random.nextInt(10) + 3, yt * 32 + random.nextInt(10) + 3));
        field.setTile(xt, yt, Tile.grass, 0);
        return true;
    }
    
    
}
