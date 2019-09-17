/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Entity.Furniture;
import Res.Res;
import World.LocalMap;
import java.util.ArrayList;
import javafx.util.Pair;

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
    
    //key == id of the furniture on the localmap, value == type of the station
    private ArrayList<Furniture> stations;
    
    public Crafting(Inventory inventory,ItemLibrary itemLibrary)
    {
        this.inventory = inventory;
        this.itemLibrary = itemLibrary;
        this.items = new ArrayList<Item>();
        this.selectIndex = -1;
        this.stations = new ArrayList<Furniture>();
       
    }
    
    public void clearAllIngridient()
    {
        if(!items.isEmpty())
        {
            for(int i=items.size()-1;i>=0;i--)
            {
                inventory.addItem(new Item(items.get(i)));
                items.remove(i);
            }
        }
    }
    
    public void addIngridient(int index)
    {
        if(inventory.getItems().get(index).isStackable()&&inventory.getItems().get(index).getStack()>1)
        {
            Item a = new Item(inventory.getItems().get(index));
            a.setStack(1);
            items.add(a);
            inventory.getItems().get(index).addStack(-1);
        }else
        {
            items.add(new Item(inventory.getItems().get(index)));
            inventory.removeItem(inventory.getItems().get(index));
        }
        
    }
    
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
    
    public void craft()
    {
        if(selectIndex == -1)
        {
            return;
        }
        
        if(itemLibrary.getRecipeById(selectIndex)!=null)
        {
            if(checkCraftingRecipe(itemLibrary.getRecipeById(selectIndex)))
            {
                removeIngredients(itemLibrary.getRecipeById(selectIndex));
                if(itemLibrary.getItemByTrueName(itemLibrary.getRecipeById(selectIndex).getName()).isStackable())
                {
                    Item a = itemLibrary.getItemByTrueName(itemLibrary.getRecipeById(selectIndex).getName());
                    a.setStack(itemLibrary.getRecipeById(selectIndex).getAmount());
                    inventory.addItem(new Item(a));
                }else
                {
                    for(int i=0;i<itemLibrary.getRecipeById(selectIndex).getAmount();i++)
                    {
                        inventory.addItem(new Item(itemLibrary.getItemByTrueName(itemLibrary.getRecipeById(selectIndex).getName())));
                    }
                }
            }
            
        }
        
    }
    
    public void removeIngredients(Recipe recipe)
    {
        for(int i=0;i<recipe.getIngredients().size();i++)
        {
            for(int j=items.size()-1;j>=0;j--)
            {
                
                if(recipe.getIngredients().get(i).getKey().startsWith("<"))
                {
                    if(items.get(j).getProperties().contains(itemLibrary.getItemTypeByName(recipe.getIngredients().get(i).getKey()).getType())&&recipe.getIngredients().get(i).getValue()==1)
                    {
           
                        items.remove(j);
                        break;
                    }
                }else
                {
                    if(items.get(j).getTrueName().equals(recipe.getIngredients().get(i).getKey())&&recipe.getIngredients().get(i).getValue()==1)
                    {
                
                        items.remove(j);
                        break;
                    }
                }
            }
        }
    }
    
    public boolean checkCraftingRecipe(Recipe recipe)
    {
        ArrayList<Item> recipeItems = new ArrayList<Item>(items);

        for(int i=0;i<recipe.getIngredients().size();i++)
        {
 
            boolean notFound = true;
            for(int j=recipeItems.size()-1;j>=0;j--)
            {
                
                if(recipe.getIngredients().get(i).getKey().startsWith("<"))
                {
                    if(recipeItems.get(j).getProperties().contains(itemLibrary.getItemTypeByName(recipe.getIngredients().get(i).getKey()).getType())&&notFound)
                    {
                   
                        recipeItems.remove(j);
                        notFound = false;
                    }
                }else
                {
                    if(recipeItems.get(j).getTrueName().equals(recipe.getIngredients().get(i).getKey())&&notFound)
                    {
                        
                        recipeItems.remove(j);
                        notFound = false;
                    }
                }
            }
            if(notFound)
            {
              
                return false;
            }
        }
        
        return true;
        
    }
    
    
    public void removeIngridient(int index)
    {
        inventory.addItem(new Item(items.get(index)));
        items.remove(index);
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

    
    

    
    
}
