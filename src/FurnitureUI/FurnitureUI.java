/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FurnitureUI;

import Entity.Furniture;
import Res.Res;
import UI.UIComponent;
import UI.UIWindow;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class FurnitureUI extends UIComponent{

    private Furniture furniture;
    private Image bg;
  
    private ArrayList<FurnitureItemUI> itemUI;
    
    private Rectangle bounds;
    
    private Rectangle fuelBounds;
    
    private int scroll;

    private Res res;
    
    public FurnitureUI(int x, int y,Furniture furniture,Res res)
    {
        super(x, y);
        this.furniture = furniture;
        this.res = res;
        if(furniture.isFuelable())
        {
            this.bg = res.furniture_inventory_fuelable;
            this.bounds = new Rectangle(16,32,206,206);
            this.fuelBounds = new Rectangle(87,213);
        }else
        {
            this.bg = res.furniture_inventory;
            this.bounds = new Rectangle(16,32,206,277);
        }
        this.scroll = 0;
        
        itemUI = new ArrayList<FurnitureItemUI>();
        
        
        for(int i=0;i<furniture.getItems().size();i++)
        {
            itemUI.add(new FurnitureItemUI(furniture.getItems().get(i),i,res,this));
        }
        
        
        
    }
    
    public void refreshUI()
    {
        
        itemUI.clear();
        
        for(int i=0;i<furniture.getItems().size();i++)
        {
            itemUI.add(new FurnitureItemUI(furniture.getItems().get(i),i,res,this));
        }
    }

    @Override
    public void checkDrop(boolean[] k, boolean[] m, Input input, World world)
    {
        for(int i=itemUI.size()-1;i>=0;i--)
        {
            if(itemUI.get(i).isDrag())
            {
                itemUI.get(i).setDrag(false);
                drag = false;
                world.setDrag(false);
                itemUI.get(i).checkDrop(input, world, x, y);
            }
        }
    }

    @Override
    public void render(Graphics g, Input input, int x, int y)
    {
        bg.draw(x,y);
        
        for(int i=0;i<itemUI.size();i++)
        {
            itemUI.get(i).render(g, input, x, y);
        }
        
    }
    
    public void renderDesc(Graphics g,Input input)
    {
        
    }

    @Override
    public void tick(boolean[] k, boolean[] m, Input input, World world, int x, int y, UIWindow window)
    {
        if(window.getZ()==world.getZ())
        {
            if(m[16])
            {
                if(bounds.contains(new Point(input.getMouseX()-x,input.getMouseY()-y)))
                {
                    scrollUp();
                }
            }else if(m[17])
            {
                if(bounds.contains(new Point(input.getMouseX()-x,input.getMouseY()-y)))
                {
                    scrollDown();
                }
            }

            for(int i=itemUI.size()-1;i>=0;i--)
            {
                itemUI.get(i).tick(k, m, input, world,x,y);
            }
            
            
        }
    }

    @Override
    public void dragRender(Graphics g, Input input)
    {
        for(FurnitureItemUI i:itemUI)
        {
            if(i.isDrag())
            {
                i.dragRender(g, input);
            }
        }
    }
    
    public void scrollUp()
    {
        if(scroll == 0)
        {
            return;
        }else
        {
            scroll--;
        }
    }
    
    public void scrollDown()
    {
            
        if(itemUI.size()<=9||2==(itemUI.size()/3)-scroll)
        {

            return;
        }else
        {

            scroll++;
        }
        
        
    }

    @Override
    public int getUIWidth()
    {
        return bg.getWidth();
    }

    @Override
    public int getUIHeight() {
        return bg.getHeight();
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
    }

    public Image getBg() {
        return bg;
    }

    public void setBg(Image bg) {
        this.bg = bg;
    }

    public ArrayList<FurnitureItemUI> getItemUI() {
        return itemUI;
    }

    public void setItemUI(ArrayList<FurnitureItemUI> itemUI) {
        this.itemUI = itemUI;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Rectangle getFuelBounds() {
        return fuelBounds;
    }

    public void setFuelBounds(Rectangle fuelBounds) {
        this.fuelBounds = fuelBounds;
    }

    public int getScroll() {
        return scroll;
    }

    public void setScroll(int scroll) {
        this.scroll = scroll;
    }

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }
    
    
    
}
