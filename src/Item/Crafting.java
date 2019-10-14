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
    
    public void addIngredient(int index)
    {
        
        if(items.size()>=9)
        {
            return;
        }
        
        if(inventory.getItems().get(index).isMetalMaterial())
        {
            for(int i=items.size()-1;i>=0;i--)
            {
                if(items.get(i).isMetalMaterial())
                {
                    inventory.addItem(items.get(i));
                    items.remove(i);
                }
            }
            items.add(new Item(inventory.getItems().get(index)));
        }else
        {
            items.add(new Item(inventory.getItems().get(index)));
        }
            
        inventory.removeItem(inventory.getItems().get(index),-1);
        
        
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
            Recipe recipe = itemLibrary.getRecipeById(selectIndex);
            if(checkCraftingRecipe(recipe))
            {
                if(itemLibrary.getItemByTrueName(recipe.getName()).getProperties().contains(7))//is a metal generic
                {
                    
                    Item a = new Item(itemLibrary.getItemByTrueName(recipe.getName()));
                    
                    for(int i=items.size()-1;i>=0;i--)
                    {
                        if(items.get(i).getProperties().contains(52))//is a metal material
                        {
                            String[] splitter = items.get(i).getName().split(" ");
                            String template_name = "";
                            for(int z=1;z<splitter.length;z++)
                            {
                                template_name+=splitter[z];
                            }
                            a.setMetalMaterial(itemLibrary,itemLibrary.getMaterialByName(splitter[0]),recipe.getTemplate_texture(),template_name);
                            inventory.addItem(a);
                            break;
                        }
                    }
                }
                else if(itemLibrary.getItemByTrueName(recipe.getName()).isStackable())
                {
                    Item a = itemLibrary.getItemByTrueName(recipe.getName());
                    a.setStack(recipe.getAmount());
                    inventory.addItem(new Item(a));
                }else
                {
                    for(int i=0;i<recipe.getAmount();i++)
                    {
                        inventory.addItem(new Item(itemLibrary.getItemByTrueName(recipe.getName())));
                    }
                }
                
                for(int i=0;i<recipe.getByProducts().size();i++)
                {
                    if(itemLibrary.getItemByTrueName(recipe.getByProducts().get(i).getKey()).isStackable())
                    {
                        Item a = itemLibrary.getItemByTrueName(recipe.getByProducts().get(i).getKey());
                        a.setStack(recipe.getByProducts().get(i).getValue());
                        inventory.addItem(new Item(a));
                    }else
                    {
                        for(int j=0;j<recipe.getByProducts().get(i).getValue();j++)
                        {
                            inventory.addItem(new Item(itemLibrary.getItemByTrueName(recipe.getByProducts().get(i).getKey())));
                        }
                    }
                }
                
                removeIngredients(recipe);
                
                
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
                    if(items.get(j).getTrueName().equals(recipe.getIngredients().get(i).getKey())&&recipe.getIngredients().get(i).getValue()==1)
                    {
                
                        if(items.get(j).isStackable())
                        {
                        
                            items.get(j).addStack(-1);
                            System.out.println("remove "+j);
                            if(items.get(j).getStack()<=0)
                            {
                                items.remove(j);
                                System.out.println("remove "+j);
                            }
                        
                        }else
                        {
                            items.remove(j);
                        }
                        break;
                    }
                }
                
                
                
                
            }
        }
    }
    
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
                
                if(recipe.getIngredients().get(i).getKey().startsWith("<"))
                {
                    if(recipeItems.get(j).getProperties().contains(itemLibrary.getItemTypeByName(recipe.getIngredients().get(i).getKey()).getType())&&notFound)
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
                    if(recipeItems.get(j).getTrueName().equals(recipe.getIngredients().get(i).getKey())&&notFound)
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
