/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CraftingUI;

import Item.Inventory;
import Item.Item;
import Item.ItemLibrary;
import Item.ItemType;
import Item.Recipe;
import Res.Res;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class MaterialUI 
{
    
    private Recipe recipe;
    
    private int type,column,index;
    
    private boolean hover;
    
    private ArrayList<MaterialItemUI> itemUI;
    
    private Rectangle bounds;
    
    private int materialIndex;
    
    private ItemType itemType;
    
    public MaterialUI(Recipe recipe,Inventory inventory,Res res,int column,int type,int index,ItemLibrary itemLibrary,RecipeUI ui)
    {
        
        this.recipe = recipe;
        this.type = type;
        
        itemUI = new ArrayList<MaterialItemUI>();
        
        this.itemType = itemLibrary.getItemTypeByType(type);
        
        for(int i=0;i<inventory.getItems().size();i++)
        {
            if(inventory.getItems().get(i).getProperties().contains(type))
            {
                itemUI.add(new MaterialItemUI(inventory.getItems().get(i),i,res,column,this));
            }
        }
        
        this.column = column;
        this.index = index;
        
        this.bounds = new Rectangle(700-(column*48),66+(index*48),48,48);
        
        materialIndex = 0;
        if(itemUI.size()>=1)
        {
            this.recipe.setRecipeGeneric(itemUI.get(0).getItem(),itemLibrary );
            ui.refreshRequirement();
        }else
        {
            
        }
        
        
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world,int x,int y,int scroll,RecipeUI ui)
    {
        if(bounds.contains(new Point(input.getMouseX()-x,input.getMouseY()-y)))
        {
            hover = true;
        }else
        {
            hover = false;
        }
        
        
        if(m[0]&&hover&&world.getZ()==world.getCraftingWindow().getZ()&&itemUI.size()>=1)
        {
            materialIndex++;
            if(materialIndex>=itemUI.size())
            {
                materialIndex = 0;
            }
            recipe.setRecipeGeneric(itemUI.get(materialIndex).getItem(), world.getItemLibrary());
            
        
            ui.refreshRequirement();
            
        }
        
        
        
    }
    
    public void render(Graphics g,Input input,int x,int y,int scroll)
    {
        
        if(itemUI.size()>0)
        {
            itemUI.get(materialIndex).render(g, input, 700+x-(column*48), 66+y+((index-scroll)*48));
        }else
        {
            itemType.getTexture().draw( 700+x-(column*48), 66+y+((index-scroll)*48),48,48);
        }
        g.setColor(Color.red);
        g.drawRect(bounds.x+x, bounds.y+y+(index-scroll*48), bounds.width, bounds.height);
        
    }
    
}
