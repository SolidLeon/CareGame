/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.crafting;

import caregame.Inventory;
import caregame.item.Item;
import caregame.item.ResourceItem;
import caregame.item.resource.Resource;
import caregame.screen.ListItem;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Markus
 */
public abstract class Recipe implements ListItem {
    public List<Item> costs = new ArrayList<Item>();
    public boolean canCraft = false;
    public Item resultTemplate;

    public Recipe(Item resultTemplate) {
        this.resultTemplate = resultTemplate;
    }
    
    abstract public void craft(Inventory inventory);
    
    public Recipe addCosts(Resource resource, int count) {
        costs.add(new ResourceItem(resource, count));
        return this;
    }
    
    public void checkCanCraft(Inventory inv) {
        for (int i = 0; i< costs.size(); i++) {
            Item item = costs.get(i);
            if (item instanceof ResourceItem) {
                ResourceItem ri = (ResourceItem) item;
                if (!inv.hasResources(ri.resource, ri.count)) {
                    canCraft = false;
                    return;
                }
            }
        }
        canCraft = true;
    }
    
    public void deductCosts(Inventory inv) {
        for (int i = 0; i< costs.size(); i++) {
            Item item = costs.get(i);
            if (item instanceof ResourceItem) {
                ResourceItem ri = (ResourceItem) item;
                inv.removeResource(ri.resource, ri.count);
            }
        }
    }

    @Override
    public void renderInventory(Graphics g, int x, int y) {
        resultTemplate.renderInventory(g, x, y);
    }

    @Override
    public int getWidth() {
        return resultTemplate.getWidth();
    }
    
    
    
}