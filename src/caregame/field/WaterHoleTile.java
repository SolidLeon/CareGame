/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field;

import caregame.ImageCache;
import caregame.entity.Entity;
import caregame.entity.Player;
import caregame.item.Can;
import caregame.item.Item;
import java.awt.Graphics;

/**
 *
 * @author Markus
 */
public class WaterHoleTile extends Tile {

    private Tile digIn;
    
    public WaterHoleTile(int id, Tile digIn) {
        super(id);
        this.digIn = digIn;
    }

    @Override
    public boolean mayPass(GameField field, int x, int y, Entity e) {
        return false;
    }
    @Override
    public void render(Graphics g, GameField field, int xt, int yt) {
        digIn.render(g, field, xt, yt);
        ImageCache.get().get("texturen/wasserloch.png").render(g, xt*32, yt*32, 32, 32);
    }

    
}
