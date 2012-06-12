/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.item;

import caregame.entity.Player;
import caregame.field.GameField;
import caregame.field.Tile;

/**
 *
 * @author Markus
 */
public class WaterCan extends ToolItem {

    private boolean empty;
    
    public WaterCan(int level) {
        super(ToolType.waterCan, level);
    }
    
    @Override
    public boolean interactOn(Tile tile, GameField field, int xt, int yt, Player player, int attackDir) {
        if (tile == Tile.hole) {
            field.setTile(xt, yt, Tile.waterHole, 0);
            player.inventory.add(new Can(level));
            empty = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean isDepleted() {
        return empty;
    }
    
}
