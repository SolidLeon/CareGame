/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.screen;

import caregame.entity.ItemEntity;
import caregame.entity.Player;
import caregame.item.Item;
import java.awt.Graphics;

/**
 *
 * @author Markus
 */
public class InventoryScreen extends Screen {

    private int selection = 0;
    private Player player;
    
    public InventoryScreen(Player player) {
        this.player = player;
        if (player.activeItem != null) {
            if (player.inventory.add(0, player.activeItem))
                player.activeItem = null;
        }
    }
    
    @Override
    public void render(Graphics g) {
        int ox = game.getWidth() / 2 - 150;
        int oy = 20; //game.getHeight() / 2 - (getFrameHeight(1) + getFrameHeight(game.inventory.items.size())) / 2;
        renderFrameText(g, ox, oy, 300, ALIGN_CENTER, "INVENTORY");
        renderItems(g, ox, oy+getFrameHeight(1), 300, ALIGN_LEFT, selection, player.inventory.items);
    }
    

    @Override
    public void tick() {
        if (input.menu.clicked) game.setScreen(null);
        if (input.attack.clicked && selection >= 0 && player.inventory.items.size() > 0) {
            if (player.activeItem == player.inventory.items.get(selection))
                player.activeItem = null;
            else {
                player.activeItem = player.inventory.items.remove(selection);
                game.setScreen(null);
            }
//            game.setScreen(null);
        }
        if (input.up.clicked) selection--;
        if (input.down.clicked) selection++;
        int len = player.inventory.items.size();
        if (selection < 0) selection += len;
        if (selection >= player.inventory.items.size()) selection -= len;
        if (input.drop.clicked) {
            player.drop(player.inventory.items.get(selection));
        }
    }
    
    
}
