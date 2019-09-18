/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import Entity.Furniture;
import Item.Crafting;
import Item.ItemLibrary;
import Item.Recipe;
import Res.Res;
import World.LocalMap;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class CraftingUI
{
    private Rectangle bounds,recipeBounds;
    private ArrayList<ItemUI> itemUI;
    
    private Crafting crafting;
    
    private InventoryUI inventoryUI;
    private Res res;
    
    private CraftingClearAllButton craftingClearAllButton;
    private CraftingCraftButton craftingCraftButton;
    private CraftingFilterButton craftingFilterButton;
    private RecipeScrollUpButton recipeScrollUpButton;
    private RecipeScrollDownButton recipeScrollDownButton;
  
    
    private int scroll;
    
    private ArrayList<RecipeUI> recipes;
    private ArrayList<StationUI> stations;
    
    private int filter;
    //0 = show all
    //1 = show learnt
    //2 = show learnt and craftable
    //3 = show learnt and relevant name
    
    
    public CraftingUI(Crafting crafting,Res res,InventoryUI inventoryUI,ItemLibrary itemLibrary,World world)
    {
        
        this.crafting = crafting;
        this.res = res;
        this.inventoryUI = inventoryUI;
        bounds = new Rectangle(16,66,205,205);
        itemUI = new ArrayList<ItemUI>();
        craftingClearAllButton = new CraftingClearAllButton(16,279,res.crafting_clear_all,crafting,inventoryUI);
        craftingCraftButton = new CraftingCraftButton(85,279,res.crafting_craft,crafting,inventoryUI);
        craftingFilterButton = new CraftingFilterButton(154,279,res.crafting_filter_by_learnt,res.crafting_filter_by_learnt_and_craftable,this);
        
        recipeBounds = new Rectangle(231,66,700,284);
        
        
        recipeScrollUpButton = new RecipeScrollUpButton(940,66,res.inventory_scroll_up,this);
        recipeScrollDownButton = new RecipeScrollDownButton(940,312,res.inventory_scroll_down,this);
        
        recipes = new ArrayList<RecipeUI>();
        
        for(int i=0;i<itemLibrary.getRecipes().size();i++)
        {
            recipes.add(new RecipeUI(itemLibrary.getRecipes().get(i),itemLibrary,world.getEntityLibrary(),i,res.disposableDroidBB));
        }
        
        stations = new ArrayList<StationUI>();
        crafting.getNearbyStations(world.getWm().getCurrentLocalMap());
        for(int i=0;i<crafting.getStations().size();i++)
        {
            stations.add(new StationUI(crafting.getStations().get(i),i,res));
        }
        
        filter = 0;
        
    }
    
    public void render(Graphics g,Input input)
    {
        craftingClearAllButton.render(g);
        craftingCraftButton.render(g);
        craftingFilterButton.render(g);
        recipeScrollUpButton.render(g);
        recipeScrollDownButton.render(g);
        
        for(ItemUI i:itemUI)
        {
            i.render(g, input, 9, 0, 0);
        }
        
        for(int i=0;i<recipes.size();i++)
        {
            recipes.get(i).render(g, input, i, scroll,crafting);
        }
        
        for(ItemUI i:itemUI)
        {
            if(i.isDrag())
            {
                i.dragRender(g, input);
            }
        }
        
        
        
        for(StationUI s:stations)
        {
            s.render(g, input);
        }
        
        for(int i=0;i<recipes.size();i++)
        {
            if(recipes.get(i).isDisplay())
            {
                recipes.get(i).renderDesc(g, input);
            }
            for(RecipeRequirementUI r:recipes.get(i).getReq())
            {
                if(r.isDisplay())
                {
                    r.renderDesc(g, input);
                }
            }
        }
        
        for(StationUI s:stations)
        {
            if(s.isDisplay())
            {
                s.renderDesc(g, input);
            }
        }
        
        for(ItemUI i:itemUI)
        {
            if(i.isDisplay())
            {
                i.renderDesc(g, input);
            }
        }
        
        
        
    }
    
    
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        craftingClearAllButton.tick(m, input, world);
        craftingCraftButton.tick(m, input, world);
        craftingFilterButton.tick(m, input, world);
        recipeScrollUpButton.tick(m, input, world);
        recipeScrollDownButton.tick(m, input, world);
        if(recipeBounds.contains(new Point(input.getMouseX(),input.getMouseY())))
        {
            if(m[11])
            {
                scrollUp();
            }else if(m[12])
            {
                scrollDown();
            }
        }
        
        for(int i=itemUI.size()-1;i>=0;i--)
        {
            itemUI.get(i).tick(k, m, input, world, 9, 0, 0, inventoryUI,null);
        }
        for(int i=0;i<recipes.size();i++)
        {
            recipes.get(i).tick(k,m, input,world, i,scroll, crafting);
        }
        for(StationUI s:stations)
        {
            s.tick(k, m, input, world);
        }
    }
    
    public void scrollUp()
    {
        if(recipes.size()>4)
        {
            if(scroll>0)
            {
                scroll--;
            }
        }
    }
    
    public void scrollDown()
    {
        if(recipes.size()>4)
        {
            if(recipes.size()-scroll>4)
            {
                scroll++;
            }
            
        }
    }
    
    
    public void refreshUI(LocalMap lm)
    {
        
        crafting.getNearbyStations(lm);
        stations.clear();
        
        for(int i=0;i<crafting.getStations().size();i++)
        {
            stations.add(new StationUI(crafting.getStations().get(i),i,res));
        }
        
        
        itemUI.clear();
        
        for(int i=0;i<crafting.getItems().size();i++)
        {
            itemUI.add(new ItemUI(crafting.getItems().get(i),i,9,res));
        }
        
        recipes.clear();
        
        int index = 0;
        for(int i=0;i<crafting.getItemLibrary().getRecipes().size();i++)
        {
            
            if(filter==1&&crafting.getItemLibrary().getLearntRecipe()[i])
            {
                recipes.add(new RecipeUI(crafting.getItemLibrary().getRecipes().get(i),crafting.getItemLibrary(),lm.getWorld().getEntityLibrary(),index,res.disposableDroidBB));
                index++;
            }
            if(filter==2&&crafting.checkCraftingRecipe(crafting.getItemLibrary().getRecipes().get(i))&&crafting.getItemLibrary().getLearntRecipe()[i])
            {

                recipes.add(new RecipeUI(crafting.getItemLibrary().getRecipes().get(i),crafting.getItemLibrary(),lm.getWorld().getEntityLibrary(),index,res.disposableDroidBB));
                index++;
                
            }else if(filter==0)
            {
                recipes.add(new RecipeUI(crafting.getItemLibrary().getRecipes().get(i),crafting.getItemLibrary(),lm.getWorld().getEntityLibrary(),i,res.disposableDroidBB));
            }
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public ArrayList<ItemUI> getItemUI() {
        return itemUI;
    }

    public void setItemUI(ArrayList<ItemUI> itemUI) {
        this.itemUI = itemUI;
    }

    public Crafting getCrafting() {
        return crafting;
    }

    public void setCrafting(Crafting crafting) {
        this.crafting = crafting;
    }

    public InventoryUI getInventoryUI() {
        return inventoryUI;
    }

    public void setInventoryUI(InventoryUI inventoryUI) {
        this.inventoryUI = inventoryUI;
    }

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    public Rectangle getRecipeBounds() {
        return recipeBounds;
    }

    public void setRecipeBounds(Rectangle recipeBounds) {
        this.recipeBounds = recipeBounds;
    }

    public CraftingClearAllButton getCraftingClearAllButton() {
        return craftingClearAllButton;
    }

    public void setCraftingClearAllButton(CraftingClearAllButton craftingClearAllButton) {
        this.craftingClearAllButton = craftingClearAllButton;
    }

    public CraftingCraftButton getCraftingCraftButton() {
        return craftingCraftButton;
    }

    public void setCraftingCraftButton(CraftingCraftButton craftingCraftButton) {
        this.craftingCraftButton = craftingCraftButton;
    }

    public RecipeScrollUpButton getRecipeScrollUpButton() {
        return recipeScrollUpButton;
    }

    public void setRecipeScrollUpButton(RecipeScrollUpButton recipeScrollUpButton) {
        this.recipeScrollUpButton = recipeScrollUpButton;
    }

    public RecipeScrollDownButton getRecipeScrollDownButton() {
        return recipeScrollDownButton;
    }

    public void setRecipeScrollDownButton(RecipeScrollDownButton recipeScrollDownButton) {
        this.recipeScrollDownButton = recipeScrollDownButton;
    }

    public int getScroll() {
        return scroll;
    }

    public void setScroll(int scroll) {
        this.scroll = scroll;
    }

    public ArrayList<RecipeUI> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<RecipeUI> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<StationUI> getStations() {
        return stations;
    }

    public void setStations(ArrayList<StationUI> stations) {
        this.stations = stations;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }
    
    
    
    
    
    
    
}
