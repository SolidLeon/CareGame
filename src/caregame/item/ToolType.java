/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.item;

/**
 *
 * @author Markus
 */
public class ToolType {

    public static ToolType types[] = new ToolType[7];
    
    public static ToolType shovel = new ToolType(0, "Shvl", 0);
    public static ToolType hoe = new ToolType(1, "Hoe", 1);
    public static ToolType sword = new ToolType(2, "Swrd", 2);
    public static ToolType pickaxe = new ToolType(3, "Pick", 3);
    public static ToolType axe = new ToolType(4, "Axe", 4);
    public static ToolType can = new ToolType(5, "Can", 5);
    public static ToolType waterCan = new ToolType(6, "Water Can", 6);
    
    public final byte id;
    public final String name;
    public final int sprite;

    private ToolType(int id, String name, int sprite) {
        this.id = (byte) id;
        types[id] = this;
        this.name = name;
        this.sprite = sprite;
    }
}
