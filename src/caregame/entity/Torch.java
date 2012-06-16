/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.entity;

/**
 *
 * @author Markus
 */
public class Torch extends Furniture {

    public Torch() {
        super("Torch");
        sprite = "torch.png";
    }

    
    @Override
    public int getLightRadius() {
        return 8;
    }
    
}
