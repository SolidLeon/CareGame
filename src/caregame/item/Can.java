/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.item;

import caregame.ImageCache;
import caregame.Sprite;
import caregame.entity.Player;
import caregame.field.GameField;
import caregame.field.Tile;

/**
 *
 * @author Markus
 */
public class Can extends ToolItem {

    private boolean filled;

    public Can(int level) {
        super(ToolType.can, level);
    }

    @Override
    public boolean interactOn(Tile tile, GameField field, int xt, int yt, Player player, int attackDir) {
        if (tile == Tile.waterHole) {
            field.setTile(xt, yt, Tile.hole, 0);
            player.activeItem = new WaterCan(level);
//            player.inventory.add(0, new WaterCan(level));
//            filled = true; we remove the item already here so no need to let it remove by isDepleted()
            return true;
        }
        if (tile == Tile.water) {
//            player.inventory.add(0, new WaterCan(level));
            player.activeItem = new WaterCan(level);
//            filled = true; we remove the item already here so no need to let it remove by isDepleted()
            return true;
        }
        return false;
    }

    @Override
    public boolean isDepleted() {
        return false;
    }
    
}
