/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;


import Entity.Furniture;

import World.LocalMap;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Timot
 */
public class Crafting
{
    private ArrayList<Item> items;
    
    private Inventory inventory;
    
    private ItemLibrary itemLibrary;
    
    private int selectIndex;
    
    //turns take to finish crafting
    private int craftingTurns;
    
    private Recipe targetRecipe;
    
    
    //key == id of the furniture on the localmap, value == type of the station
    private ArrayList<Furniture> stations;
    
    private HashMap<String,String> previousMaterials;
    
    
    
    /**
    * Creates the crafting object, the crafting object is used to craft items base on the selected recipe
    *
    * @param inventory The inventory of the entity.
    * @param itemLibrary The item library contains information of items and recipes.
    * 
    * 
    */
    public Crafting(Inventory inventory,ItemLibrary itemLibrary)
    {
        this.inventory = inventory;
        this.itemLibrary = itemLibrary;
        this.items = inventory.getItems();
        this.selectIndex = 1;
        this.stations = new ArrayList<Furniture>();
        this.previousMaterials = new HashMap<String,String>();
    }
    
    
    /**
    * Get any adjacent crafting stations in the local map of the entity.
    * 
    * The station is refreshed every turn this function is called.
    *
    * @param lm the map which the entity currently resides in.
    * 
    * 
    */
    
    public void getNearbyStations(LocalMap lm)
    {
        if(lm != null)
        {
            stations.clear();
            ArrayList<Furniture> f = new ArrayList<Furniture>(lm.furnituresAround(lm.getPlayer().getX(), lm.getPlayer().getY()));
            for(int i=0;i<f.size();i++)
            {
                if(f.get(i).getStationType()!=-1)
                {
                    stations.add(f.get(i));
                }
            }
        }
        
    }
    
    /**
    * Initiate the crafting queue base on the selected crafting recipe.
    * 
    * 
    */
    public void setCraftingTarget()
    {
        targetRecipe = itemLibrary.getRecipeById(selectIndex);
        craftingTurns = targetRecipe.getTurns();
    }
    
    /**
    * <p>This function is called by the entity each time a turn has passed if it's task is set to crafting.</p>
    *
    * <p>It will check whether the inventory of the entity has all the requirement of the recipe met, 
    * if so it will decrease the crafting turn by 1, else it will clear the crafting queue and cancel the operation.</p>
    * 
    * <p>When crafting turn is less or equal to 0, ingredients(items) mark as consumed by the recipe will be removed from the inventory of the entity,
    * and the result and by product of the recipe will be added to the inventory of the entity, after finishing the operation it will clear the crafting queue.
    * </p>
    */
    public void craft()
    {
        if(selectIndex == -1)
        {
            return;
        }
        
        
        if(targetRecipe!=null)
        {
            
            if(checkCraftingRecipe(targetRecipe))
            {
                
                
                craftingTurns--;
                if(craftingTurns<=0&&targetRecipe!=null)
                {
                    
                    if(itemLibrary.getItemByTrueName(targetRecipe.getName()).getProperties().contains(7))//is a metal generic
                    {

                        Item a = new Item(itemLibrary.getItemByTrueName(targetRecipe.getName()));

                        for(int i=items.size()-1;i>=0;i--)
                        {

                            if(items.get(i).getProperties().contains(52)&&items.get(i).getName().equals(targetRecipe.getIngredientByGenericType(52).getItem()))//is a metal material
                            {

                                String[] splitter = items.get(i).getName().split(" ");
                                String template_name = "";
                                for(int z=1;z<splitter.length;z++)
                                {
                                    template_name+=splitter[z];
                                }
                                
                                a.setMetalMaterial(itemLibrary,itemLibrary.getMaterialByName(splitter[0]),targetRecipe.getTemplate_texture(),template_name);
                                inventory.addItem(a);
                                break;
                            }
                        }
                    }
                    else if(itemLibrary.getItemByTrueName(targetRecipe.getName()).isStackable())
                    {
                        Item a = itemLibrary.getItemByTrueName(targetRecipe.getName());
                        a.setStack(targetRecipe.getAmount());
                        inventory.addItem(new Item(a));
                    }else
                    {
                        for(int i=0;i<targetRecipe.getAmount();i++)
                        {
                            inventory.addItem(new Item(itemLibrary.getItemByTrueName(targetRecipe.getName())));
                        }
                    }

                    for(int i=0;i<targetRecipe.getByProducts().size();i++)
                    {
                        if(itemLibrary.getItemByTrueName(targetRecipe.getByProducts().get(i).getKey()).isStackable())
                        {
                            Item a = itemLibrary.getItemByTrueName(targetRecipe.getByProducts().get(i).getKey());
                            a.setStack(targetRecipe.getByProducts().get(i).getValue());
                            inventory.addItem(new Item(a));
                        }else
                        {
                            for(int j=0;j<targetRecipe.getByProducts().get(i).getValue();j++)
                            {
                                inventory.addItem(new Item(itemLibrary.getItemByTrueName(targetRecipe.getByProducts().get(i).getKey())));
                            }
                        }
                    }
                    removeIngredients(targetRecipe);
                    targetRecipe = null;
                }
                
            }else
            {
                clearCraftingTarget();
            }
            
        }
        
    }
    
    /**
    * Clear the crafting queue by setting the target recipe to null and crafting turn to 0
    *
    * 
    * 
    * 
    */
    public void clearCraftingTarget()
    {
        targetRecipe = null;
        craftingTurns = 0;
        
    }
    
    
    /**
    * Check if a crafting queue is finished by checking whether the number of crafting turn left is equal or less than 0.
    *
    * @return craftingTurn <= 0
    */
    public boolean finishedCrafting()
    {
        return craftingTurns <= 0;
    }
    
    
    /**
    * Remove items from the inventory base on the ingredient requirements from the provided recipe
    *
    * @param recipe selected recipe which contains a list of ingredient requirements
    * 
    * 
    */
    public void removeIngredients(Recipe recipe)
    {
        for(int i=0;i<recipe.getIngredients().size();i++)
        {
            for(int j=items.size()-1;j>=0;j--)
            {
                
                if(recipe.getIngredients().get(i).getItem().startsWith("<"))
                {
                    
                    
                    if(items.get(j).getProperties().contains(itemLibrary.getItemTypeByName(recipe.getIngredients().get(i).getItem()).getType())&&recipe.getIngredients().get(i).getConsume()==1)
                    {
                        if(items.get(j).isStackable())
                        {
                        
                            items.get(j).addStack(-1);
                            
                            if(items.get(j).getStack()<=0)
                            {
       
                                items.remove(j);
                            }
                        
                        }else
                        {
                            items.remove(j);
                        }
                        
                        
                        
                        break;
                    }
                }else
                {
                    
                    if(items.get(j).getTrueName().equals(recipe.getIngredients().get(i).getItem())&&recipe.getIngredients().get(i).getConsume()==1)
                    {
                        
                        
                        
                        
                        if(items.get(j).isStackable())
                        {
                        
                            items.get(j).addStack(-1);
                            
                            if(items.get(j).getStack()<=0)
                            {
                                items.remove(j);
                                
                            }
                        
                        }else
                        {
                            items.remove(j);
                        }
                        
                        
                        
                        
                        break;
                    }else if(items.get(j).getTrueName().equals(recipe.getIngredients().get(i).getItem()))
                    {
                        
                        break;
                    }
                }
                
                
                
                
            }
        }
    }
    
    
    /**
    * <p>Use for the crafting UI to remember material selection so that it won't get reset every time the UI is refreshed.</p>
    * 
    * <p>This function is called whenever a generic material is used to craft the respective generic item.<p/>
    * 
    *
    * @param recipe Selected recipe which uses a generic material.
    * @param item The selected generic material.
    * 
    * 
    */
    public void rememberGeneric(Recipe recipe,Item item)
    {
        
        previousMaterials.put(recipe.getName(), item.getTrueName());
    }
    
    
    /**
    * Check items in the inventory base on the ingredient requirements from the provided recipe
    *
    * @param recipe selected recipe which contains a list of ingredient requirements
    * 
    * @return Whether all the requirements of the recipe is met.
    * 
    * 
    */
    public boolean checkCraftingRecipe(Recipe recipe)
    {
        
        ArrayList<Item> recipeItems = new ArrayList<Item>();
        for(int i=0;i<items.size();i++)
        {
            recipeItems.add(new Item(items.get(i)));
        }

        boolean notFound;
        
        for(int i=0;i<recipe.getIngredients().size();i++)
        {
 
            notFound = true;
            for(int j=recipeItems.size()-1;j>=0;j--)
            {
                
                if(recipe.getIngredients().get(i).getItem().startsWith("<"))
                {
                    if(recipeItems.get(j).getProperties().contains(itemLibrary.getItemTypeByName(recipe.getIngredients().get(i).getItem()).getType())&&notFound)
                    {
                   
                        if(recipeItems.get(j).isStackable())
                        {
                        
                            recipeItems.get(j).addStack(-1);
                            
                            if(recipeItems.get(j).getStack()==0)
                            {
                                recipeItems.remove(j);
                            }
                        
                        }else
                        {
                            recipeItems.remove(j);
                        }
                        notFound = false;
                    }
                }else
                {
                    if(recipeItems.get(j).getTrueName().equals(recipe.getIngredients().get(i).getItem())&&notFound)
                    {
                        
                        if(recipeItems.get(j).isStackable())
                        {
                            
                                recipeItems.get(j).addStack(-1);
                                if(recipeItems.get(j).getStack()==0)
                                {
                                    recipeItems.remove(j);
                                }
                        
                        }else
                        {
                            recipeItems.remove(j);
                        }
                        notFound = false;
                    }
                }
            }
            if(notFound)
            {
                
                return false;
            }
        }
        
        for(int i=0;i<recipe.getStations().size();i++)
        {
 
            notFound = true;
            for(int j=stations.size()-1;j>=0;j--)
            {
                
                if(recipe.getStations().get(i)==stations.get(j).getStationType())
                {
                    notFound = false;
                }
                        
            }
            if(notFound)
            {
              
                return false;
            }
        }
        
        return true;
        
    }
    
    /**
    * Check if there is currently a crafting queue
    *
    * @return whether there is a target recipe and crafting turns is not less or equal to 0
    * 
    * 
    */
    public boolean isCrafting()
    {
        return (targetRecipe!=null&&craftingTurns>=1);
    }
    
    

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ItemLibrary getItemLibrary() {
        return itemLibrary;
    }

    public void setItemLibrary(ItemLibrary itemLibrary) {
        this.itemLibrary = itemLibrary;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    public ArrayList<Furniture> getStations() {
        return stations;
    }

    public void setStations(ArrayList<Furniture> stations) {
        this.stations = stations;
    }

    public HashMap<String, String> getPreviousMaterials() {
        return previousMaterials;
    }

    public void setPreviousMaterials(HashMap<String, String> previousMaterials) {
        this.previousMaterials = previousMaterials;
    }

    public int getCraftingTurns() {
        return craftingTurns;
    }

    public void setCraftingTurns(int craftingTurns) {
        this.craftingTurns = craftingTurns;
    }

    public Recipe getTargetRecipe() {
        return targetRecipe;
    }

    public void setTargetRecipe(Recipe targetRecipe) {
        this.targetRecipe = targetRecipe;
    }

    
    

    
    
}
