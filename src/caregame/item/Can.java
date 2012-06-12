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
            player.inventory.add(0, new WaterCan(level));
            filled = true;
            return true;
        }
        if (tile == Tile.water) {
            player.inventory.add(0, new WaterCan(level));
            filled = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean isDepleted() {
        return filled;
    }
    
}
