/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.crafting;

import caregame.Inventory;
import caregame.item.Can;
import caregame.item.ToolItem;
import caregame.item.ToolType;
import caregame.item.WaterCan;

/**
 *
 * @author Markus
 */
public class ToolRecipe extends Recipe {
    private ToolType type;
    private int level;

    public ToolRecipe(ToolType type, int level) {
        super(new ToolItem(type, level));
        this.type = type;
        this.level = level;
    }

    @Override
    public void craft(Inventory inventory) {
        if (type == ToolType.can) {
            inventory.add(0, new Can(level));
        } else if (type == ToolType.waterCan) {
            inventory.add(0, new WaterCan(level));
        } else {
            inventory.add(0, new ToolItem(type, level));
        }
    }
    
    
    
}
