/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EquipmentUI;

import InventoryUI.ItemUI;
import Item.Item;
import Res.Res;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class EquipmentItemUI extends ItemUI
{
    private EquipmentUI ui;
    
    public EquipmentItemUI(Item item, int index,Res res,EquipmentUI ui)
    {
        super(item, index, res);
        this.ui = ui;
       
            if(item.getEquipmentType()!=33)
            {
                if(index<3)
                {
                    bounds = new Rectangle(16+(index*(71)),66,64,64);
                }else
                {
                    int colIndex = index%3;
                    int rowIndex = index/3;
                    bounds = new Rectangle((colIndex * 64) + (colIndex * 7) + 16, (rowIndex*71)+66, 64, 64);
                }
            }else
            {
                bounds = new Rectangle(16,137,64,64);
            }
        
    }
    
    @Override
    public void render(Graphics g,Input input,int x,int y)
    {
        if(!drag)
        {
            
            
            if(item.getEquipmentType()!=33)
            {
                int colIndex = index%3;
                int rowIndex = index/3;
                if(hover)
                {
                    item.getTexture().draw((colIndex * 64) + (colIndex * 7) + 16+x, (rowIndex*71)+66+y, 64, 64);
                }else
                {
                    item.getTexture().draw((colIndex * 64) + (colIndex * 7) + 16+x, (rowIndex*71)+66+y, 64, 64,Color.gray);
                }
            }else
            {
                if(hover)
                {
                    item.getTexture().draw(16+x,137+y, 64, 64);
                }else
                {
                    item.getTexture().draw(16+x,137+y, 64, 64,Color.gray);
                }
                    
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
            
            
            tickDesc((hover&&world.getZ()==world.getEquipmentWindow().getZ()));

            if (m[1] && hover)
            {
                world.spawnItemOptionTab(input.getMouseX(), input.getMouseY(), index, 6, item);
            } else if (input.isMouseButtonDown(0) && hover && !ui.isDrag() && !world.isDrag()) {
                xofs = input.getMouseX() - x - bounds.x;
                yofs = input.getMouseY() - y - bounds.y;
                drag = true;
                world.setDrag(true);
                ui.setDrag(true);
            }


            if(hover&&ui.getItemOptionTab()==null)
            {
                tickDesc(true);

            }else
            {
                tickDesc(false);
            }
        }
            
    }
    
    @Override
    public void checkDrop(Input input,World world,int x,int y)
    {
        int max = 0;
        int maxIndex = -1;
        
        
        dropRect = new Rectangle(input.getMouseX()-xofs-x,input.getMouseY()-yofs-y,64,64);
        
        Rectangle dropRect2 = new Rectangle(input.getMouseX()-xofs,input.getMouseY()-yofs,64,64);
        
        if(dropRect2.intersects(world.getInventoryWindow().getBounds())&&world.getZ()==world.getInventoryWindow().getZ())
        {
            ui.getEquipment().unequip(item.getEquipmentType());
            ui.refreshUI();
            world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
            return;
        }else if(dropRect.intersects(ui.getMainBounds()))
        {
            
        }
        
        
        
    }
    
}
