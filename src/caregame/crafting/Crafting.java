/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caregame.crafting;

import caregame.entity.Bag;
import caregame.entity.Torch;
import caregame.entity.Workbench;
import caregame.item.ToolType;
import caregame.item.resource.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Markus
 */
public class Crafting {
    public static final List<Recipe> freeRecipes = new ArrayList<Recipe>();
    public static final List<Recipe> workbenchRecipes = new ArrayList<Recipe>();
    public static final List<Recipe> smeltRecipes = new ArrayList<Recipe>();
    
    static {
//        freeRecipes.add(new FurnitureRecipe(new ResourceItem(Resource.treeSapling)).addCosts(Resource.wood, 2));
//        freeRecipes.add(new ResourceRecipe(new ResourceItem(Resource.treeSapling)).addCosts(Resource.wood, 2))
        try {
//            workbenchRecipes.add(new ResourceRecipe(Resource.treeSapling).addCosts(Resource.wood, 2));
            freeRecipes.add(new FurnitureRecipe(Workbench.class).addCosts(Resource.wood, 5));
            workbenchRecipes.add(new FurnitureRecipe(Bag.class).addCosts(Resource.cloth, 9));
            workbenchRecipes.add(new FurnitureRecipe(Workbench.class).addCosts(Resource.wood, 5));
            workbenchRecipes.add(new FurnitureRecipe(Torch.class).addCosts(Resource.wood, 3)); //add coal
            workbenchRecipes.add(new ToolRecipe(ToolType.hoe, 0).addCosts(Resource.wood, 5));
            workbenchRecipes.add(new ToolRecipe(ToolType.axe, 0).addCosts(Resource.wood, 5));
            workbenchRecipes.add(new ToolRecipe(ToolType.pickaxe, 0).addCosts(Resource.wood, 5));
            workbenchRecipes.add(new ToolRecipe(ToolType.shovel, 0).addCosts(Resource.wood, 5));
            workbenchRecipes.add(new ToolRecipe(ToolType.sword, 0).addCosts(Resource.wood, 5));
            workbenchRecipes.add(new ToolRecipe(ToolType.can, 0).addCosts(Resource.wood, 5));
//            workbenchRecipes.add(new ResourceRecipe(Resource.waterBottle).addCosts(Resource.glass, 5));
            workbenchRecipes.add(new ResourceRecipe(Resource.flour).addCosts(Resource.wheat, 5));
            workbenchRecipes.add(new ResourceRecipe(Resource.bread).addCosts(Resource.flour, 2).addCosts(Resource.waterBottle, 2));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        smeltRecipes.add(new ResourceRecipe(Resource.ironbar).addCosts(Resource.ironore, 3));
    }
    
}
