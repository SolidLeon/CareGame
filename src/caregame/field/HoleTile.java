/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field;

import caregame.ImageCache;
import caregame.entity.Entity;
import java.awt.Graphics;

/**
 *
 * @author Markus
 */
public class HoleTile extends Tile {

    private Tile digIn;
    
    public HoleTile(int id, Tile digIn) {
        super(id);
        this.digIn = digIn;
    }

    @Override
    public void render(Graphics g, GameField field, int xt, int yt) {
        digIn.render(g, field, xt, yt);
        ImageCache.get().get("texturen/loch.png").render(g, xt*32, yt*32, 32, 32);
    }

    @Override
    public boolean mayPass(GameField field, int x, int y, Entity e) {
        return true;
    }
    
    
    
    
}
