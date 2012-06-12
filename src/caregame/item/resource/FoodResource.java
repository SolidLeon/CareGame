/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.item.resource;

import caregame.entity.Player;
import caregame.field.GameField;
import caregame.field.Tile;

/**
 *
 * @author Markus
 */
public class FoodResource extends Resource {
    private int heal;
    private int staminaCost;

    public FoodResource(int id, String name, String sprite, int heal, int staminaCost) {
        super(id, name, sprite);
        this.heal = heal;
        this.staminaCost = staminaCost;
    }

    @Override
    public boolean interactOn(Tile tile, GameField field, int xt, int yt, Player player, int attackDir) {
        if (player.health < player.maxHealth && player.payStamina(staminaCost)) {
            player.heal(heal);
            return true;
        }
        return false;
    }
    
    
}
