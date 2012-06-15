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
    private int hungerRegain;
    private int staminaCost;

    public FoodResource(int id, String name, String sprite, int hungerRegain, int staminaCost) {
        super(id, name, sprite);
        this.hungerRegain = hungerRegain;
        this.staminaCost = staminaCost;
    }

    @Override
    public boolean interactOn(Tile tile, GameField field, int xt, int yt, Player player, int attackDir) {
        System.out.println("EAT");
        if (player.hunger < player.maxHunger && player.payStamina(staminaCost)) {
            player.eat(hungerRegain);
            return true;
        }
        return false;
    }
    
    
}
