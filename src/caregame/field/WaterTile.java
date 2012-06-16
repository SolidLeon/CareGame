/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.field;

import caregame.Game;
import caregame.ImageCache;
import caregame.OPCODES;
import caregame.entity.Entity;
import caregame.entity.Player;
import caregame.item.Item;
import caregame.item.ResourceItem;
import caregame.item.resource.Resource;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Markus
 */
public class WaterTile extends Tile {

    public WaterTile(int id) {
        super(id);
    }
    
    @Override
    public void render(Graphics g, GameField field, int xt, int yt) {
        Tile tl = field.getTile(xt-1, yt);
        Tile tr = field.getTile(xt+1, yt);
        Tile tu = field.getTile(xt, yt-1);
        Tile td = field.getTile(xt, yt+1);
        Tile tru = field.getTile(xt+1, yt-1);
        Tile trd = field.getTile(xt+1, yt+1);
        Tile tlu = field.getTile(xt-1, yt-1);
        Tile tld = field.getTile(xt-1, yt+1);
        
        boolean wl = tl == null || tl instanceof WaterTile;
        boolean wr = tr == null || tr instanceof WaterTile;
        boolean wu = tu == null || tu instanceof WaterTile;
        boolean wd = td == null || td instanceof WaterTile;
        
        boolean wru = tru == null || tru instanceof WaterTile;
        boolean wrd = trd == null || trd instanceof WaterTile;
        boolean wlu = tlu == null || tlu instanceof WaterTile;
        boolean wld = tld == null || tld instanceof WaterTile;
        
        if (wl && wr && wu && wd & wru && wlu && wrd && wld) { //wasser rund herum
            ImageCache.get().get("texturen/wasser.png").render(g, xt*Game.TILE_SIZE, yt*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
        } else {
            //außen ecken
            if (wrd && !wl && !wu && !wlu) //links oben
                ImageCache.get().get("texturen/wasserrand_eck.png").render(g, xt*Game.TILE_SIZE, yt*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
            else if (wld && !wu && !wru && !wr) //rechts oben
                ImageCache.get().get("texturen/wasserrand_eck_ro.png").render(g, xt*Game.TILE_SIZE, yt*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
            else if(wru && !wl && !wld && !wd) //links unten    
                ImageCache.get().get("texturen/wasserrand_eck_unten.png").render(g, xt*Game.TILE_SIZE, yt*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
            else if (wlu && !wd && !wrd && !wr)
                ImageCache.get().get("texturen/wasserrand_eck_rechts_unten.png").render(g, xt*Game.TILE_SIZE, yt*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
            //innen ecken
            else if (wlu && wl && wu && !wrd && wd && wr)   
                ImageCache.get().get("texturen/wasserrand_eck2_lo.png").render(g, xt*Game.TILE_SIZE, yt*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
            else if (wl && wu && wd && wld && wr && !wru)
                ImageCache.get().get("texturen/wasserrand_eck2_lu.png").render(g, xt*Game.TILE_SIZE, yt*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
            else if (wlu && wru && wrd && wl && wu && wr && wd && !wld)
               ImageCache.get().get("texturen/wasserrand_eck2_ro.png").render(g, xt*Game.TILE_SIZE, yt*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
            else if(!wlu && wru && wld && wrd && wl && wr && wu && wd) 
               ImageCache.get().get("texturen/wasserrand_eck2_ru.png").render(g, xt*Game.TILE_SIZE, yt*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
            //ränder
            //links rects
            else if (!wl && wr && wru && wrd && wu && wd)
                ImageCache.get().get("texturen/wasserrand_seite.png").render(g, xt*Game.TILE_SIZE, yt*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
            else if (!wr && wl && wlu && wld && wu && wd)
                ImageCache.get().get("texturen/wasserrand_seite_rechts.png").render(g, xt*Game.TILE_SIZE, yt*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
            //oben unten
            else if (!wu && wl && wr && wrd && wld && wd)
                ImageCache.get().get("texturen/wasserrand_oben.png").render(g, xt*Game.TILE_SIZE, yt*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
            else if (!wd && wl && wr && wru && wlu && wu)
                ImageCache.get().get("texturen/wasserrand_unten.png").render(g, xt*Game.TILE_SIZE, yt*Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
            else field.setTile(xt, yt, Tile.grass,0);
        }
    }

    @Override
    public boolean mayPass(GameField field, int x, int y, Entity e) {
        return e.canSwim();
    }

    @Override
    public boolean interact(GameField field, int xt, int yt, Player player, Item item, int attackDir) {
        if (item instanceof ResourceItem) {
            ResourceItem ri = (ResourceItem) item;
            if (ri.resource == Resource.bottle) {
                if(player.inventory.add(0, new ResourceItem(Resource.waterBottle))) {
                    ri.count--;
                    return true;
                }
            }
        }
        return false;
    }
    
    
    
}
