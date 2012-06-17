/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.entity;

import caregame.Game;
import caregame.ImageCache;
import caregame.Inventory;
import caregame.OPCODES;
import caregame.screen.ContainerScreen;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Markus
 */
public class Bag extends Furniture {

    public Inventory inventory = new Inventory(4);

    public Bag() {
        super("Bag");
        sprite = "sack.png";
    }
    
    @Override
    public boolean use(Player player, int attackDir) {
        player.game.setScreen(new ContainerScreen(player, "Bag", inventory));
        return true;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        super.write(out);
        inventory.write(out);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        super.read(in);
        inventory = new Inventory();
        inventory.read(in);
    }
    
    
    
    
}
