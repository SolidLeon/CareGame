/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.crafting;

import caregame.Inventory;
import caregame.entity.Furniture;
import caregame.item.FurnitureItem;

/**
 *
 * @author Markus
 */
public class FurnitureRecipe extends Recipe {
    private Class<? extends Furniture> clazz;
    
    public FurnitureRecipe(Class<? extends Furniture> clazz) throws InstantiationException, IllegalAccessException {
        super(new FurnitureItem(clazz.newInstance()));
        this.clazz = clazz;
    }

    @Override
    public void craft(Inventory inventory) {
        try {
            inventory.add(0, new FurnitureItem(clazz.newInstance()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
