/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CraftingUI;

import UI.DescBox;
import Entity.EntityLibrary;
import Item.Crafting;
import Item.Inventory;
import Item.ItemLibrary;
import Item.Recipe;
import Res.Res;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class RecipeUI extends DescBox
{
    private Recipe recipe;
    
    private ArrayList<RecipeRequirementUI> req;
    
    private long descHover,lastDescHover;
    
    private boolean hover;
    
    private Image texture;
    
    private String desc;
    
    private Rectangle textureBounds,bounds;
    
    private boolean renderDesc;
    
    private TrueTypeFont font,nameFont;
    
    private ArrayList<String> desc_lines;
    
    private int index;
    
    private ArrayList<MaterialUI> materials;
    
    private EntityLibrary entityLibrary;
    private ItemLibrary itemLibrary;
    
    public RecipeUI(Recipe recipe,ItemLibrary itemLibrary,EntityLibrary entityLibrary,int index,TrueTypeFont font,TrueTypeFont nameFont,Inventory inventory,Res res)
    {
        super(recipe.getName(),itemLibrary.getItemByTrueName(recipe.getName()).getDescTrue(),font);
        this.recipe = recipe;
        this.font = font;
        this.nameFont = nameFont;
        this.texture = recipe.getTexture();
        
        this.desc = itemLibrary.getItemByTrueName(recipe.getName()).getDescTrue();
        
        req = new ArrayList<RecipeRequirementUI>();

        
        bounds = new Rectangle(231,66+(index*48),500,48);
        
        textureBounds = new Rectangle(241,66+(index*48),48,48);
        
        descHover = 0;
        lastDescHover = 0;
        
        desc_lines = new ArrayList<String>();
        
        materials = new ArrayList<MaterialUI>();
        
        int column = 0;
        this.index = index;
        this.itemLibrary = itemLibrary;
        
        this.entityLibrary = entityLibrary;
        
        for(int i=0;i<recipe.getIngredients().size();i++)
        {
            if(recipe.getIngredients().get(i).getItem().startsWith("<"))
            {
                req.add(new RecipeRequirementUI(itemLibrary.getItemTypeByName(recipe.getIngredients().get(i).getItem()),font,index,i));
                materials.add(new MaterialUI(recipe,inventory,res,column,itemLibrary.getItemTypeByName(recipe.getIngredients().get(i).getItem()).getType(),index,itemLibrary,this));
                column++;
            }
            else
            {
                req.add(new RecipeRequirementUI(itemLibrary.getItemByTrueName(recipe.getIngredients().get(i).getItem()),font,index,i));
                if(itemLibrary.getItemByTrueName(recipe.getIngredients().get(i).getItem()).getGenericType()!=-1)
                {
                    materials.add(new MaterialUI(recipe,inventory,res,column,itemLibrary.getItemByTrueName(recipe.getIngredients().get(i).getItem()).getGenericType(),index,itemLibrary,this));
                    column++;
                }
            }
        }
        
        for(int i=0;i<recipe.getStations().size();i++)
        {
            req.add(new RecipeRequirementUI(entityLibrary.getFurnitureTemplateByStationType(recipe.getStations().get(i)),font,index,i+recipe.getIngredients().size()));
        }
        

    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world,int index,int scroll,Crafting crafting,int x,int y)
    {
        if(bounds.y-(scroll*48)>=66&&bounds.y-(scroll*48)<=344)
        {
            if(bounds.contains(new Point(input.getMouseX()-x,input.getMouseY()+(scroll*48)-y)))
            {
                hover = true;
            }else
            {
                hover = false;
            }

            tickDesc((hover&&textureBounds.contains(new Point(input.getMouseX()-x,input.getMouseY()+(scroll*48)-y))&&world.getZ()==world.getCraftingWindow().getZ()));
            
            
            for(MaterialUI ui:materials)
            {
                ui.tick(k, m, input, world, x, y, scroll,this);
            }
                
                

            if(hover&&m[10]&&world.getZ()==world.getCraftingWindow().getZ())
            {
                crafting.setSelectIndex(recipe.getId());
                
            }

            if(crafting.getSelectIndex()==index)
            {
                for(int i=0;i<req.size();i++)
                {
                    req.get(i).tick(k,m,input,world,scroll,x,y);
                }
            }
        }
    }
    
    public void refreshRequirement()
    {
        req.clear();
        for(int i=0;i<recipe.getIngredients().size();i++)
        {
           
            if(recipe.getIngredients().get(i).getItem().startsWith("<"))
            {
                req.add(new RecipeRequirementUI(itemLibrary.getItemTypeByName(recipe.getIngredients().get(i).getItem()),font,index,i));
                
            }
            else
            {
                req.add(new RecipeRequirementUI(itemLibrary.getItemByTrueName(recipe.getIngredients().get(i).getItem()),font,index,i));
                
            }
        }
        for(int i=0;i<recipe.getStations().size();i++)
        {
            System.out.println("loop a loop");
            req.add(new RecipeRequirementUI(entityLibrary.getFurnitureTemplateByStationType(recipe.getStations().get(i)),font,index,i+recipe.getIngredients().size()));
        }
    }
    
    
    public void render(Graphics g,Input input,int index,int scroll,Crafting crafting,int x,int y)
    {
        if(bounds.y-(scroll*48)>=66&&bounds.y-(scroll*48)<=344)
        {
            if(crafting.getSelectIndex()==recipe.getId())
            {
                g.setColor(Color.decode("#b2b6b2"));
            }else
            {
                g.setColor(Color.decode("#757161"));
            }
            g.fillRect(bounds.x+x, bounds.y-(scroll*48)+y, bounds.width, bounds.height);
            g.setColor(Color.black);
            g.drawRect(bounds.x+x, bounds.y-(scroll*48)+y, bounds.width, bounds.height);
            g.setFont(font);
            g.setColor(Color.white);
            g.drawString(name, bounds.x+x+75, bounds.y+y-(scroll*48));


            texture.draw(241+x,63+(index*48)-(scroll*48)+y,48,48);
            
            for(MaterialUI ui :materials)
            {
                ui.render(g, input, x, y,scroll);
            }
            
            
        }
        if(crafting.getSelectIndex()==index)
            {
                
            
                for(int i=0;i<req.size();i++)
                {
                    req.get(i).render(g,input,index,i,scroll,x,y);
                }
            }
        
        
        
    }
    

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public ArrayList<RecipeRequirementUI> getReq() {
        return req;
    }

    public void setReq(ArrayList<RecipeRequirementUI> req) {
        this.req = req;
    }

    public long getDescHover() {
        return descHover;
    }

    public void setDescHover(long descHover) {
        this.descHover = descHover;
    }

    public long getLastDescHover() {
        return lastDescHover;
    }

    public void setLastDescHover(long lastDescHover) {
        this.lastDescHover = lastDescHover;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Rectangle getTextureBounds() {
        return textureBounds;
    }

    public void setTextureBounds(Rectangle textureBounds) {
        this.textureBounds = textureBounds;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean isRenderDesc() {
        return renderDesc;
    }

    public void setRenderDesc(boolean renderDesc) {
        this.renderDesc = renderDesc;
    }
    
    
    
}
