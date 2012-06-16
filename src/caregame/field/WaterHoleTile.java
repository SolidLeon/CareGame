/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field;

import caregame.ImageCache;
import caregame.entity.Entity;
import caregame.entity.Player;
import caregame.item.Item;
import caregame.item.ResourceItem;
import caregame.item.resource.Resource;
import java.awt.Graphics;

/**
 *
 * @author Markus
 */
public class WaterHoleTile extends Tile {

    private Tile digIn;
    
    public WaterHoleTile(int id, Tile digIn) {
        super(id);
        this.digIn = digIn;
    }

    @Override
    public boolean mayPass(GameField field, int x, int y, Entity e) {
        return false;
    }
    @Override
    public void render(Graphics g, GameField field, int xt, int yt) {
        digIn.render(g, field, xt, yt);
        ImageCache.get().get("texturen/wasserloch.png").render(g, xt*32, yt*32, 32, 32);
    }

    @Override
    public boolean interact(GameField field, int xt, int yt, Player player, Item item, int attackDir) {
        if (item instanceof ResourceItem) {
            ResourceItem ri = (ResourceItem) item;
            if (ri.resource == Resource.bottle) {
                if(player.inventory.add(0, new ResourceItem(Resource.waterBottle))) {
                    ri.count--;
                    int fills = field.getData(xt, yt) - 1;
                    if (fills == 0) {
                        field.setTile(xt, yt, Tile.hole, 0);
                    } else {
                        field.setData(xt, yt, fills);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    

    
}
