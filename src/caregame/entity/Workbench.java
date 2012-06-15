/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.entity;

import caregame.Game;
import caregame.ImageCache;
import caregame.OPCODES;
import caregame.crafting.Crafting;
import caregame.item.FurnitureItem;
import caregame.item.Item;
import caregame.screen.CraftingScreen;
import java.awt.Graphics;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Markus
 */
public class Workbench extends Furniture{

    public Workbench() {
        super("Workbench");
        sprite = "workbench.png";
        xr = 16;
        yr = 8;
    }

    
    @Override
    public boolean use(Player player, int attackDir) {
        player.game.setScreen(new CraftingScreen(player, Crafting.workbenchRecipes));
        return true;
    }

//    @Override
//    public boolean interact(Player player, Item item, int attackDir) {
//        remove();
//        player.inventory.add(new FurnitureItem(this));
//        return true;
//    }
    
    
    
    
}
