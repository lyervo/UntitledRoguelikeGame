/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CraftingUI;

import InventoryUI.InventoryUI;
import Item.Crafting;
import Item.ItemLibrary;
import Res.Res;
import UI.UIComponent;
import UI.UIWindow;
import World.LocalMap;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class CraftingUI extends UIComponent
{
    private Rectangle recipeBounds;
//    private ArrayList<CraftingItemUI> itemUI;
    
    private Crafting crafting;
    
    private InventoryUI inventoryUI;
    private Res res;
    
//    private CraftingClearAllButton craftingClearAllButton;
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
    
    private Image texture;
    
    private HashMap<String,String> previousMaterials;
    
    public CraftingUI(int x,int y,Crafting crafting,Res res,InventoryUI inventoryUI,ItemLibrary itemLibrary,World world)
    {
        super(x,y);
        
        this.previousMaterials = crafting.getPreviousMaterials();
        
        this.texture = res.crafting_bg_1;
        
        this.crafting = crafting;
        this.res = res;
        this.inventoryUI = inventoryUI;

//        itemUI = new ArrayList<CraftingItemUI>();
//        craftingClearAllButton = new CraftingClearAllButton(16,279,res.crafting_clear_all,crafting,inventoryUI);
        craftingCraftButton = new CraftingCraftButton(85,279,res.crafting_craft,crafting,inventoryUI);
        craftingFilterButton = new CraftingFilterButton(154,279,res.crafting_filter_by_learnt,res.crafting_filter_by_learnt_and_craftable,this);
        
        recipeBounds = new Rectangle(231,66,700,284);
        
        
        recipeScrollUpButton = new RecipeScrollUpButton(860,66,res.inventory_scroll_up,this);
        recipeScrollDownButton = new RecipeScrollDownButton(860,300,res.inventory_scroll_down,this);
        
        recipes = new ArrayList<RecipeUI>();
        
       
        
        stations = new ArrayList<StationUI>();
        crafting.getNearbyStations(world.getWm().getCurrentLocalMap());
        for(int i=0;i<crafting.getStations().size();i++)
        {
            stations.add(new StationUI(crafting.getStations().get(i),i,res));
        }
        
        
        
        filter = 1;
               
    }
    
    @Override
    public void render(Graphics g,Input input,int x,int y)
    {
        texture.draw(x,y);
//        craftingClearAllButton.render(g,x,y);
        craftingCraftButton.render(g,x,y);
        craftingFilterButton.render(g,x,y);
        recipeScrollUpButton.render(g,x,y);
        recipeScrollDownButton.render(g,x,y);
        
//        for(CraftingItemUI i:itemUI)
//        {
//            i.render(g, input, x,y);
//        }
        
        for(int i=0;i<recipes.size();i++)
        {
            recipes.get(i).render(g, input, i, scroll,crafting,x,y);
        }
        
//        for(CraftingItemUI i:itemUI)
//        {
//            if(i.isDrag())
//            {
//                i.dragRender(g, input);
//            }
//        }
        
        
        
        for(StationUI s:stations)
        {
            s.render(g, input,x,y);
        }
        
            
        
    }
    
    public void renderDesc(Graphics g,Input input)
    {
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
            for(StationUI s:stations)
            {
                if(s.isDisplay())
                {
                    s.renderDesc(g, input);
                }
            }
        }
    }
    
    @Override
    public void tick(boolean[] k,boolean[] m,Input input,World world,int x,int y,UIWindow window)
    {
//        craftingClearAllButton.tick(m, input, world,x,y,window.getZ());
        craftingCraftButton.tick(m, input, world,x,y,window.getZ());
        craftingFilterButton.tick(m, input, world,x,y,window.getZ());
        recipeScrollUpButton.tick(m, input, world,x,y,window.getZ());
        recipeScrollDownButton.tick(m, input, world,x,y,window.getZ());
        if(recipeBounds.contains(new Point(input.getMouseX()-x,input.getMouseY()-y)))
        {
            if(m[16])
            {
                scrollUp();
            }else if(m[17])
            {
                scrollDown();
            }
        }
        
//        for(int i=itemUI.size()-1;i>=0;i--)
//        {
//            itemUI.get(i).tick(k, m, input, world, x, y);
//        }
        for(int i=0;i<recipes.size();i++)
        {
            recipes.get(i).tick(k,m, input,world, i,scroll, crafting,x,y);
        }
        for(StationUI s:stations)
        {
            s.tick(k, m, input, world,x,y);
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
            if(recipes.size()-scroll>5)
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
        
        
        recipes.clear();
        
        int index = 0;
        
        
        
        for(int i=0;i<crafting.getItemLibrary().getRecipes().size();i++)
        {
            
            if(filter==1&&crafting.getItemLibrary().getLearntRecipe()[i])
            {
                recipes.add(new RecipeUI(crafting.getItemLibrary().getRecipes().get(i),crafting.getItemLibrary(),lm.getWorld().getEntityLibrary(),index,res.disposableDroidBB,res.disposableDroidBB40f,inventoryUI.getPlayer_inventory(),res,previousMaterials));
                index++;
            }
            if(filter==2&&crafting.checkCraftingRecipe(crafting.getItemLibrary().getRecipes().get(i))&&crafting.getItemLibrary().getLearntRecipe()[i])
            {

                recipes.add(new RecipeUI(crafting.getItemLibrary().getRecipes().get(i),crafting.getItemLibrary(),lm.getWorld().getEntityLibrary(),index,res.disposableDroidBB,res.disposableDroidBB40f,inventoryUI.getPlayer_inventory(),res,previousMaterials));
                index++;
                
            }else if(filter==0)
            {
                recipes.add(new RecipeUI(crafting.getItemLibrary().getRecipes().get(i),crafting.getItemLibrary(),lm.getWorld().getEntityLibrary(),i,res.disposableDroidBB,res.disposableDroidBB40f,inventoryUI.getPlayer_inventory(),res,previousMaterials));
            }
        }
    }



//    public ArrayList<CraftingItemUI> getItemUI() {
//        return itemUI;
//    }
//
//    public void setItemUI(ArrayList<CraftingItemUI> itemUI) {
//        this.itemUI = itemUI;
//    }

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

//    public CraftingClearAllButton getCraftingClearAllButton() {
//        return craftingClearAllButton;
//    }
//
//    public void setCraftingClearAllButton(CraftingClearAllButton craftingClearAllButton) {
//        this.craftingClearAllButton = craftingClearAllButton;
//    }

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

    @Override
    public int getUIWidth()
    {
        return texture.getWidth();
    }

    @Override
    public int getUIHeight()
    {
        return texture.getHeight();
    }

    @Override
    public void checkDrop(boolean[] k, boolean[] m, Input input, World world) {
//        for(int i=itemUI.size()-1;i>=0;i--)
//        {
//            if(itemUI.get(i).isDrag())
//            {
//                itemUI.get(i).setDrag(false);
//                drag = false;
//                world.setDrag(false);
//                itemUI.get(i).checkDrop(input, world, x, y);
//            }
//        }
    }

    @Override
    public void dragRender(Graphics g, Input input) {
      
    }

    public CraftingFilterButton getCraftingFilterButton() {
        return craftingFilterButton;
    }

    public void setCraftingFilterButton(CraftingFilterButton craftingFilterButton) {
        this.craftingFilterButton = craftingFilterButton;
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }
    
    
    
    
    
    
    
}
