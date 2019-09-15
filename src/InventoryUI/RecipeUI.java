/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import Item.Crafting;
import Item.ItemLibrary;
import Item.Recipe;
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
public class RecipeUI
{
    private Recipe recipe;
    
    private ArrayList<RecipeRequirementUI> req;
    
    private long descHover,lastDescHover;
    
    private boolean hover;
    
    private Image texture;
    
    private String desc;
    
    private Rectangle textureBounds,bounds;
    
    private boolean renderDesc;
    
    private TrueTypeFont font;
    
    private ArrayList<String> desc_lines;
    
    public RecipeUI(Recipe recipe,ItemLibrary itemLibrary,int index,TrueTypeFont font)
    {
        this.recipe = recipe;
        this.font = font;
        this.texture = recipe.getTexture();
        
        this.desc = itemLibrary.getItemByTrueName(recipe.getName()).getDescTrue();
        
        req = new ArrayList<RecipeRequirementUI>();

        
        bounds = new Rectangle(231,66+(index*71),700,64);
        
        textureBounds = new Rectangle(241,66+(index*71),64,64);
        
        descHover = 0;
        lastDescHover = 0;
        
        desc_lines = new ArrayList<String>();
        
        for(int i=0;i<recipe.getIngredients().size();i++)
        {
            if(recipe.getIngredients().get(i).getKey().startsWith("<"))
            {
                req.add(new RecipeRequirementUI(itemLibrary.getItemTypeByName(recipe.getIngredients().get(i).getKey()),font,index,i));
            }
            else
            {
                req.add(new RecipeRequirementUI(itemLibrary.getItemByTrueName(recipe.getIngredients().get(i).getKey()),font,index,i));
            }
        }
        
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world,int index,int scroll,Crafting crafting)
    {
        if(bounds.y-(scroll*71)>=66&&bounds.y-(scroll*71)<=344)
        {
            if(bounds.contains(new Point(input.getMouseX(),input.getMouseY()+(scroll*71))))
            {
                hover = true;
            }else
            {
                hover = false;
            }

            if(hover&&textureBounds.contains(new Point(input.getMouseX(),input.getMouseY()+(scroll*71))))
            {
                if(descHover<500)
                {
                    if(lastDescHover==0)
                    {
                        lastDescHover = System.currentTimeMillis();
                    }else
                    {
                        descHover += System.currentTimeMillis()-lastDescHover;
                        lastDescHover = System.currentTimeMillis();
                    }
                }else
                {
                    renderDesc = true;
                    if(desc_lines.isEmpty())
                    {
                        if(font.getWidth(desc)>300)
                        {
                            String[] desc_arr = desc.split(" ");
                            String placeholder = "";
                            for(int i=0;i<desc_arr.length;i++)
                            {
                                if(font.getWidth(placeholder+" "+desc_arr[i])<300)
                                {
                                    placeholder += " "+desc_arr[i];
                                }else
                                {
                                    desc_lines.add(placeholder);
                                    placeholder = desc_arr[i];

                                }
                            }
                            desc_lines.add(placeholder);

                        }else
                        {
                            desc_lines.add(desc);
                        }
                    }
                }
            }else
            {
                renderDesc = false;
                descHover = 0;
                lastDescHover = 0;
            }

            if(hover&&m[0])
            {
                crafting.setSelectIndex(recipe.getId());
                
            }

            for(int i=0;i<req.size();i++)
            {
                req.get(i).tick(k,m,input,world,scroll);
            }
        }
    }
    
    public void render(Graphics g,Input input,int index,int scroll,Crafting crafting)
    {
        if(bounds.y-(scroll*71)>=66&&bounds.y-(scroll*71)<=344)
        {
            if(crafting.getSelectIndex()==recipe.getId())
            {
                g.setColor(Color.decode("#b2b6b2"));
            }else
            {
                g.setColor(Color.decode("#757161"));
            }
            g.fillRect(bounds.x, bounds.y-(scroll*71), bounds.width, bounds.height);
            g.setColor(Color.black);
            g.drawRect(bounds.x, bounds.y-(scroll*71), bounds.width, bounds.height);



            texture.draw(241,66+(index*71)-(scroll*71),64,64);
            for(int i=0;i<req.size();i++)
            {
                req.get(i).render(g,input,index,i,scroll);
            }
        }
        
        
        
    }
    
    public void renderDesc(Graphics g,Input input)
    {
        g.setColor(Color.decode("#60a3bc"));
        g.fillRect(input.getMouseX(), input.getMouseY(), 320, 29+(desc_lines.size()*29)+5);
        font.drawString(input.getMouseX()+20, input.getMouseY(), recipe.getName());
        
        for(int i=0;i<desc_lines.size();i++)
        {
            if(i==0)
            {
                font.drawString(input.getMouseX(), input.getMouseY()+29+(i*29), desc_lines.get(i));
            }else
            {
                font.drawString(input.getMouseX()+10, input.getMouseY()+29+(i*29), desc_lines.get(i));
            }
        }
        
        g.setColor(Color.black);
        g.drawRect(input.getMouseX(), input.getMouseY(), 320, 29+(desc_lines.size()*29)+5);
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
