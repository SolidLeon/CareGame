/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.item.resource;

import caregame.entity.Player;
import caregame.field.GameField;
import caregame.field.Tile;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Markus
 */
public class PlantableResource extends Resource {

    private Tile targetTile;
    private List<Tile> sourceTiles;
    
    public PlantableResource(int id, String name, String sprite, Tile targetTile, Tile ...sourceTiles) {
        this(id, name, sprite, targetTile, Arrays.asList(sourceTiles));
    }
    
    public PlantableResource(int id, String name, String sprite, Tile targetTile, List<Tile> sourceTiles) {
        super(id, name, sprite);
        this.targetTile = targetTile;
        this.sourceTiles = sourceTiles;
    }

    @Override
    public boolean interactOn(Tile tile, GameField field, int xt, int yt, Player player, int attackDir) {
        if (sourceTiles.contains(tile)){
            field.setTile(xt, yt, targetTile, 0);
            return true;
        }
        return false;
    }
    
    
    
}
