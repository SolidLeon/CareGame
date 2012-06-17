/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame;

import caregame.entity.*;
import caregame.item.FurnitureItem;
import caregame.item.ResourceItem;
import caregame.item.ToolItem;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Markus
 */
public class OPCODES {
    public static final int OP_ITEM             = 0x00000001;
    public static final int OP_ITEM_FURNITURE   = 0x00000001; //sub ops
    public static final int OP_ITEM_RESOURCE    = 0x00000002;
    public static final int OP_ITEM_TOOL        = 0x00000003;
    
    public static final int OP_ENTITY           = 0x00010000;
    public static final int OP_ENTITY_FURNITURE = 0x00010001;
    public static final int OP_ENTITY_BAG       = 0x00010002;
    public static final int OP_ENTITY_WORKBENCH = 0x00010003;
    public static final int OP_ENTITY_PLAYER    = 0x00010004;
    public static final int OP_ENTITY_ITEM_ENTITY = 0x00010005;
    public static final int OP_ENTITY_PIG       = 0x00010006;
    public static final int OP_ENTITY_TORCH     = 0x00010007;
    
    public static final int OP_INVENTORY        = 0x00000030;
    
    public static final int OP_TILE             = 0x00020000;
    public static final int OP_TILE_FARM        = 0x00020001;
    public static final int OP_TILE_GRASS       = 0x00020002;
    public static final int OP_TILE_SEED        = 0x00020003;
    public static final int OP_TILE_TREE       = 0x00020004;
    public static final int OP_TILE_WATER       = 0x00020005;
    public static final int OP_TILE_WOOD_PLANKS = 0x00020006;
    public static final int OP_TILE_INVISIBLE_WALL = 0x00020007;
    
    public static final int OP_FIELD            = 0x00080000;
    
    
    public static Map<Class, Integer> opcodes = new HashMap<Class, Integer>();
    static {
        opcodes.put(Inventory.class, OP_INVENTORY);
        opcodes.put(Bag.class, OP_ENTITY_BAG);
        opcodes.put(ItemEntity.class, OP_ENTITY_ITEM_ENTITY);
        opcodes.put(Pig.class, OP_ENTITY_PIG);
        opcodes.put(Player.class, OP_ENTITY_PLAYER);
        opcodes.put(Torch.class, OP_ENTITY_TORCH);
        opcodes.put(Workbench.class, OP_ENTITY_WORKBENCH);
        opcodes.put(FurnitureItem.class, OP_ITEM_FURNITURE);
        opcodes.put(ResourceItem.class, OP_ITEM_RESOURCE);
        opcodes.put(ToolItem.class, OP_ITEM_TOOL);
    }
}
