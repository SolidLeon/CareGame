/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.crafting;

import caregame.Inventory;
import caregame.item.ResourceItem;
import caregame.item.resource.Resource;

/**
 *
 * @author Markus
 */
public class ResourceRecipe extends Recipe {
    private Resource resource;

    public ResourceRecipe(Resource resource) {
        super(new ResourceItem(resource, 1));
        this.resource = resource;
    }
    
    public void craft(Inventory inventory) {
        inventory.add(0, new ResourceItem(resource, 1));
    }
    
}
