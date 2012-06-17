/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.item.resource;

import caregame.entity.Player;
import caregame.field.GameField;
import caregame.field.Tile;

/**
 *
 * @author Markus
 */
public class Resource {
    
    public final static Resource[] resources = new Resource[256];
    public static final Resource wood = new PlaceableResource(0, "WOOD", "holz.png", Tile.wood, Tile.grass);
    public static final Resource fertilizer = new Resource(1, "FERTILIZER");
    public final static Resource treeSapling = new PlantableResource(2, "TREE SAPLING", "baum.png", Tile.treeSapling, Tile.grass);
    public final static Resource pineSapling = new PlantableResource(3, "PINE SAPLING", "tanne.png", Tile.pineSapling, Tile.grass);
    public final static Resource flowerSeed = new PlantableResource(4, "FLOWER SEEDS", "blume_gelb.png", Tile.flowerSeed, Tile.farmland);
    public final static Resource cloth = new Resource(5, "CLOTH", "blume_gelb.png");
    public final static Resource bread = new FoodResource(6, "BREAD", "brot.png", 40, 0);
    public final static Resource wheatSeeds = new PlantableResource(7, "WHEAT SEEDS", "weizen_phase1.png", Tile.wheat, Tile.farmland);
    public final static Resource wheat = new Resource(8, "WHEAT", "wheat.png");
    public final static Resource flour = new Resource(9, "FLOUR", "flour.png");
    public final static Resource bottle = new Resource(10, "BOTTLE", "waterBottle.png");
    public final static Resource waterBottle = new Resource(11, "WATER BOTTLE", "waterBottle.png");
    public final static Resource rawPorkchop = new FoodResource(12, "RAW PORKCHOP", "porkchop.png", 8, 6);
    public final static Resource cookedPorkchop = new FoodResource(13, "COOKED PORKCHOP", "cookedporkchop.png", 16, 8);
    
    public final byte id;
    public String name;
    public String sprite;
    
    public Resource(int id, String name, String sprite) {
        this.id = (byte) id;
        if (resources[id] != null) throw new RuntimeException("Duplicate resource ids, " + id);
        resources[id] = this;
        this.name = name;
        this.sprite = sprite;
    }
    
    public Resource(int id, String name) {
        this(id, name, "sack.png");
    }

    public boolean interactOn(Tile tile, GameField field, int xt, int yt, Player player, int attackDir) {
        return false;
    }
}
