/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.screen;

import caregame.Inventory;
import caregame.entity.Player;
import caregame.item.Item;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author Markus
 */
public class ContainerScreen extends Screen{
    private Player player;
    private String title;
    private Inventory container;
    
    private int window = 0;
    private int selection=0;
    
    public ContainerScreen(Player player, String title, Inventory container) {
        this.player = player;
        this.title = title;
        this.container = container;
    }

    @Override
    public void render(Graphics g) {
        int ox = game.getWidth() / 2 - 250;
        int oy = 20;
        
        renderFrameText(g, ox, oy, 500, ALIGN_CENTER, title);
        renderFrameText(g, ox, oy + getFrameHeight(1), 250, ALIGN_CENTER, "Container");
        renderFrameText(g, ox + 250, oy + getFrameHeight(1), 250, ALIGN_CENTER, "Inventory");
        
        if (window == 0) {
            renderItems(g, ox, oy + getFrameHeight(1)*2, 250, ALIGN_LEFT, selection, container.items);
            renderItems(g, ox+250, oy + getFrameHeight(1)*2, 250, ALIGN_LEFT, player.inventory.items);
        }else {
            renderItems(g, ox, oy + getFrameHeight(1)*2, 250, ALIGN_LEFT, container.items);
            renderItems(g, ox+250, oy + getFrameHeight(1)*2, 250, ALIGN_LEFT, selection, player.inventory.items);
        }
    }

    @Override
    public void tick() {
        if (input.menu.clicked) {
            game.setScreen(null);
        }
        if (input.left.clicked) {window--;selection=0;}
        if (input.right.clicked) {window++;selection=0;}
        if (input.up.clicked) selection--;
        if (input.down.clicked) selection++;
        
        if (input.attack.clicked) {
            if (window == 0 && container.items.size() > 0) {
                Item item = container.items.remove(selection);
                player.inventory.add(0, item);
            }
            if (window == 1 && player.inventory.items.size() > 0){
                Item item = player.inventory.items.remove(selection);
                container.add(0, item);
            }
        }
        
        if (window < 0) window = 1;
        if (window > 1) window = 0;
        
        int len;
        if (window == 0) len = container.items.size();
        else len = player.inventory.items.size();
        
        if (selection < 0) selection += len;
        if (selection >= len) selection -= len;
    }

    
}
