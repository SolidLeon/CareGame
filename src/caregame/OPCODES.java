/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame;

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
    
    
}
