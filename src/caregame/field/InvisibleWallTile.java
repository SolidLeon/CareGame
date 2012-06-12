/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field;

import caregame.Game;
import caregame.OPCODES;
import caregame.entity.Entity;
import java.awt.Graphics;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Markus
 */
public class InvisibleWallTile extends Tile {

    public InvisibleWallTile(int id) {
        super(id);
    }
    
    @Override
    public boolean mayPass(GameField field, int x, int y, Entity e) {
        return false;
    }

    @Override
    public void render(Graphics g, GameField field, int xt, int yt) {

    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(OPCODES.OP_TILE_INVISIBLE_WALL);
    }
    
    
    
    
}
