/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field;

import caregame.entity.Entity;

/**
 *
 * @author Markus
 */
public class ElevatorTile extends Tile {
    private boolean down;

    public ElevatorTile(int id, boolean down) {
        super(id);
        this.down = down;
    }

    @Override
    public void steppedOn(GameField field, int xt, int yt, Entity e) {
        if (down) {
            
        }
    }
    
    
    
}
