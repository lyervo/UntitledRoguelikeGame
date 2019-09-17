/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import Item.Item;
import Item.ItemType;
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
    
    public RecipeRequirementUI(Item item, TrueTypeFont font,int index,int reqIndex)
    {
        super(item.getTrueName(),item.getDesc(),font);
        this.name = item.getTrueName();
        this.texture = item.getTexture();
        this.description = item.getDesc();
        this.bounds = new Rectangle(311+(reqIndex*71),64+(index*71),64,64);
        this.renderDesc = false;
        this.desc_lines = new ArrayList<String>();
        this.font = font;
    }
    
    public RecipeRequirementUI(ItemType itemType, TrueTypeFont font,int index,int reqIndex)
    {
        super(itemType.getName(),itemType.getDesc(),font);
        this.name = itemType.getName();
        this.description = itemType.getDesc();
        this.texture = itemType.getTexture();
        this.type = itemType.getType();
        this.bounds = new Rectangle(311+(reqIndex*71),64+(index*71),64,64);
        this.renderDesc = false;
        this.desc_lines = new ArrayList<String>();
        this.font = font;
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world,int scroll)
    {
        tickDesc(bounds.contains(new Point(input.getMouseX(),input.getMouseY()+(scroll*71))));
        
    }
    
    public void render(Graphics g, Input input, int index, int reqIndex , int scroll)
    {
        texture.draw(311+(reqIndex*71),64+(index*71)-(scroll*71),64,64);
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
