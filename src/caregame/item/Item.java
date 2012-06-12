/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.item;

import caregame.Font;
import caregame.OPCODES;
import caregame.Sprite;
import caregame.entity.Entity;
import caregame.entity.ItemEntity;
import caregame.entity.Player;
import caregame.field.GameField;
import caregame.field.Tile;
import caregame.screen.ListItem;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Markus
 */
public class Item implements ListItem {

    public String getName() {
        return "";
    }

    public boolean matches(Item item) {
        return item.getClass() == getClass();
    }
    
    public Sprite getSprite() {
        return null;
    }

    @Override
    public void renderInventory(Graphics g, int x, int y) {
//        if (getInventoryIcon() != null)
//                getInventoryIcon().render(g, x, y, 8, 8);
//        Font.render(g, getName(), x + 16, y);
    }

    @Override
    public int getWidth() {
        return Font.getWidth(getName());
    }
    
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(OPCODES.OP_ITEM);
    }
    
    public void read(DataInputStream in) throws IOException {
    }

    public boolean interactOn(Tile tile, GameField field, int xt, int yt, Player player, int attackDir) {
        return false;
    }
    
    public boolean isDepleted() {
        return false;
    }

    public void onTake(ItemEntity aThis) {
        
    }

    public void renderIcon(Graphics g, int x, int y) {
    }

    public boolean interact(Player player, Entity entity, int attackDir) {
        return false;
    }
    
    public boolean canAttack() {
        return false;
    }
    
}
