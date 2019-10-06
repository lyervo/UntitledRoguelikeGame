/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import Res.Res;
import UI.UIComponent;
import UI.UIWindow;
import World.World;
import java.awt.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class InventoryUIWindow extends UIWindow
{
    
    private InventoryUI inventoryUI;
    private TrueTypeFont font;
    
    
    public InventoryUIWindow(int x, int y,String name,InventoryUI inventoryUI,TrueTypeFont font,Res res) 
    {
        super(x, y, name, inventoryUI,res);
        this.inventoryUI = inventoryUI;
        this.font = font;
    }
    
    
    @Override
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        
        
        if(display)
        {
            if(bounds.contains(new Point(input.getMouseX(),input.getMouseY())))
            {

                hover = true;
            }else
            {
                hover = false;
            }
            
            


            if(dragBounds.contains(new Point(input.getMouseX(),input.getMouseY())))
            {
                dragHover = true;

            }else
            {
                dragHover = false;
            }
            

            
            
            if((dragHover||hover)&&m[10]&&world.getZ()==z)
            {
                world.moveWindowToTop(this);
            }
            
            if(world.getZ()<=z&&(hover||dragHover))
            {
                world.setZ(z);
                world.setHoveringWindow(true);
            }
            
            closeWindowButton.tick(m, input, world,x,y);
            
            
            

            if(dragHover&&input.isMouseButtonDown(0)&&!drag&&!world.isDrag())
            {
                xofs = input.getMouseX()-dragBounds.x;
                yofs = input.getMouseY()-dragBounds.y;
                drag = true;
                world.setDrag(true);
            }

            if(m[0]&&drag&&world.isDrag())
            {

                drag = false;
                setWindowLocation(input,world.getContainer());
                world.setDrag(false);
            }

            if(!drag)
            {
                inventoryUI.tick(k, m, input, world,x,y,this);
            }
        }
    }
    
    @Override
    public void itemUICheckDrop(boolean[] k, boolean[] m, Input input, World world)
    {
        inventoryUI.checkDrop(k, m, input, world);
    }
    
    
    @Override
    public void render(Graphics g,Input input)
    {
        if(!drag&&display)
        {
            
            inventoryUI.render(g, input, bounds.x, bounds.y);
            g.setColor(Color.blue);
            g.drawRect(dragBounds.x, dragBounds.y, dragBounds.width, dragBounds.height);
            g.setColor(Color.yellow);
            g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString(name, dragBounds.x+32, dragBounds.y-5);
            closeWindowButton.render(g,x,y);
        }
        
    }
    
    @Override
    public void dragRender(Graphics g,Input input)
    {
        if(display)
        {
            g.setColor(Color.blue);
            g.drawRect(input.getMouseX()-xofs, input.getMouseY()-yofs, dragBounds.width, dragBounds.height);
            g.setColor(Color.yellow);
            g.drawRect(input.getMouseX()-xofs, input.getMouseY()-yofs+32, width, height);
            inventoryUI.render(g, input, input.getMouseX()-xofs, input.getMouseY()-yofs+32);
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString(name, input.getMouseX()-xofs+32, input.getMouseY()-yofs-5);
            closeWindowButton.render(g,input.getMouseX()-xofs, input.getMouseY()-yofs+32);
        }
    }

    
    
}
