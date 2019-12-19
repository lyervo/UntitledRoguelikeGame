/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CraftingUI;

import InventoryUI.ItemUI;
import Item.Item;
import Res.Res;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class CraftingItemUI extends ItemUI
{
    
    private CraftingUI ui;
    
    public CraftingItemUI(Item item, int index, Res res,CraftingUI ui)
    {
        super(item, index, res);
        if (index < 3)
        {
            bounds = new Rectangle(16 + (index * (71)), 66, 64, 64);
        } else
        {
            int colIndex = index % 3;
            int rowIndex = index / 3;
            bounds = new Rectangle((colIndex * 64) + (colIndex * 7) + 16, (rowIndex * 71) + 66, 64, 64);
        }
        
        this.ui = ui;
        
    }
    
    @Override
    public void render(Graphics g,Input input,int x,int y)
    {
        if(!drag)
        {
            int colIndex = index % 3;
            int rowIndex = index / 3;

            item.getTexture().draw((colIndex * 64) + (colIndex * 7) + 16+x, (rowIndex * 71) + 66+y, 64, 64);

            if (item.isStackable())
            {
                font.drawString((colIndex * 64) + (colIndex * 7) + 16+x, (rowIndex * 71) + 66+y, item.getStack() + "");
            }
        }       
            
        
    }
    
    @Override
    public void tick(boolean[] k,boolean[] m,Input input,World world,int x,int y)
    {
        
        
        if(!world.isDrag())
        {
            if (bounds.contains(new Point(input.getMouseX()-x, input.getMouseY()-y)))
            {
                hover = true;

            } else {
                hover = false;
            }

            if (m[1] && hover)
            {
                world.spawnItemOptionTab(input.getMouseX(), input.getMouseY(), index, 9, item);
            } else if (input.isMouseButtonDown(0) && hover && !ui.isDrag() && !world.isDrag()) {
                xofs = input.getMouseX() - x - bounds.x;
                yofs = input.getMouseY() - y - bounds.y;
                drag = true;
                world.setDrag(true);
                ui.setDrag(true);
            }

        }
            
    }
    
    @Override
    public void checkDrop(Input input,World world,int x,int y)
    {
        
        
        
        
    }
    
}
