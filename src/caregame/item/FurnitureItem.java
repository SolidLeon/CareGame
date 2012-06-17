/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.item;

import caregame.*;
import caregame.entity.Bag;
import caregame.entity.Furniture;
import caregame.entity.Player;
import caregame.entity.Workbench;
import caregame.field.GameField;
import caregame.field.Tile;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Markus
 */
public class FurnitureItem extends Item{

    public Furniture furniture;
    protected boolean placed;
    
    public FurnitureItem(Furniture furniture) {
        this.furniture = furniture;
    }

    @Override
    public Sprite getSprite() {
        return ImageCache.get().get(furniture.sprite);
    }
        
    @Override
    public boolean interactOn(Tile tile, GameField field, int xt, int yt, Player player, int attackDir) {
        System.out.println("InteractOn: " + tile.getClass().getSimpleName() + ", " + xt + ", " + yt);
        if (tile.mayPass(field, xt, yt, furniture)) {
            furniture.x = (xt*32) +16;
            furniture.y = (yt*32) +16;
            field.add(furniture);
            placed = true;
            return true;
        }
        return false;
    }

    @Override
    public void renderInventory(Graphics g, int x, int y) {
        getSprite().render(g, x, y, 16, 16);
        Font.render(g, "001 " + furniture.name, x + 24, y);
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        furniture.write(out);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        int opc = in.readInt();
        
        for (Class clazz : OPCODES.opcodes.keySet()) {
            if (OPCODES.opcodes.get(clazz) == opc) {
                try {
                    furniture = (Furniture) clazz.newInstance();
                    break;
                } catch (InstantiationException ex) {
                    Logger.getLogger(FurnitureItem.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(FurnitureItem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
//        switch (opc) {
//            case OPCODES.OP_ENTITY_WORKBENCH: furniture = new Workbench(); 
//                break;
//            case OPCODES.OP_ENTITY_BAG: furniture = new Bag();
//                break;
//            default:    
//                throw new RuntimeException("Error reading Furniture Item, expected Furniture opc, " + opc);
//        }
        furniture.read(in);
    }

    @Override
    public boolean isDepleted() {
        return placed;
    }
    
}
