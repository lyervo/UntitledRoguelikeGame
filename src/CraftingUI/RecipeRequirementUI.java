/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CraftingUI;

import UI.DescBox;

import Entity.FurnitureTemplate;
import Item.Item;
import Item.ItemType;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class RecipeRequirementUI extends DescBox
{
 
    private String name;
    private Image texture;
    private String description;
    private int type;
    
    private Rectangle bounds;
    
    private long descHover,lastDescHover;
    
    private boolean renderDesc;
    
    private ArrayList<String> desc_lines;
    private TrueTypeFont font;
    
    private int columnIndex,rowIndex;
    
    public RecipeRequirementUI(Item item, TrueTypeFont font,int index,int reqIndex)
    {
        super(item.getTrueName(),item.getDesc(),font);
        this.name = item.getTrueName();
        this.texture = item.getTexture();
        this.description = item.getDesc();
        if(reqIndex<3)
        {
            columnIndex = reqIndex;
            rowIndex = 0;
            this.bounds = new Rectangle(16+(columnIndex*71),66,64,64);
        }else
        {
            columnIndex = reqIndex%3;
            rowIndex = reqIndex/3;
            this.bounds = new Rectangle(16+(columnIndex*71),66+(rowIndex*71),64,64);
        }
        this.renderDesc = false;
        this.desc_lines = new ArrayList<String>();
        this.font = font;
    }
    
    public RecipeRequirementUI(ItemType itemType, TrueTypeFont font,int index,int reqIndex)
    {
        super(itemType.getName(),itemType.getDesc(),font);
        this.name = itemType.getName();
        this.description = itemType.getDesc();
        if(reqIndex<3)
        {
            columnIndex = reqIndex;
            rowIndex = 0;
            this.bounds = new Rectangle(16+(columnIndex*71),66,64,64);
        }else
        {
            columnIndex = reqIndex%3;
            rowIndex = reqIndex/3;
            this.bounds = new Rectangle(16+(columnIndex*71),66+(rowIndex*71),64,64);
        }
        this.texture = itemType.getTexture();
        this.type = itemType.getType();
        this.renderDesc = false;
        this.desc_lines = new ArrayList<String>();
        this.font = font;
    }
    
    public RecipeRequirementUI(FurnitureTemplate furniture,TrueTypeFont font,int index,int reqIndex)
    {
        super(furniture.getName(),furniture.getDesc(),font);
        this.name = furniture.getName();
        this.description = furniture.getDesc();
        if(reqIndex<3)
        {
            columnIndex = reqIndex;
            rowIndex = 0;
            this.bounds = new Rectangle(16+(columnIndex*71),66,64,64);
        }else
        {
            columnIndex = reqIndex%3;
            rowIndex = reqIndex/3;
            this.bounds = new Rectangle(16+(columnIndex*71),66+(rowIndex*71),64,64);
        }
        this.texture = furniture.getTexture();
        this.type = furniture.getStationType();
        this.renderDesc = false;
        this.desc_lines = new ArrayList<String>();
        this.font = font;
    }
    
  
    
    public void tick(boolean[] k,boolean[] m,Input input,World world,int scroll,int x,int y)
    {
        tickDesc(bounds.contains(new Point(input.getMouseX()-x,input.getMouseY()-y))&&world.getZ()==world.getCraftingWindow().getZ());
        
    }
    
    public void render(Graphics g, Input input, int index, int reqIndex , int scroll, int x,int y)
    {
        texture.draw(16+(columnIndex*71)+x,66+(rowIndex*71)+y,64,64);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
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

    public boolean isRenderDesc() {
        return renderDesc;
    }

    public void setRenderDesc(boolean renderDesc) {
        this.renderDesc = renderDesc;
    }

    public ArrayList<String> getDesc_lines() {
        return desc_lines;
    }

    public void setDesc_lines(ArrayList<String> desc_lines) {
        this.desc_lines = desc_lines;
    }

    public TrueTypeFont getFont() {
        return font;
    }

    public void setFont(TrueTypeFont font) {
        this.font = font;
    }
    
    
    
}
