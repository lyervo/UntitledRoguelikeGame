/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

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
public class InventoryItemUI extends ItemUI
{
    
    private InventoryUI ui;
    private int state;
    
    
    public InventoryItemUI(Item item, int index,Res res,InventoryUI ui,int state)
    {
        super(item, index, res);
        this.ui = ui;
        this.state = state;
        if(state == 1)
        {
            if(index>8)
            {
                int columnIndex = index % 9;
                int rowIndex = index / 9;
                bounds = new Rectangle((columnIndex*71) + 16, 32 + (rowIndex*71), 64, 64);
            } else {
                bounds = new Rectangle((index * (64)) + (index * 7) + 16, 32, 64, 64);
            }
        }else if(state==2)
        {
            if(index>3)
            {
                int columnIndex = index % 4;
                int rowIndex = index / 4;
                bounds = new Rectangle((columnIndex*71) + 16, 32 + (rowIndex*71), 64, 64);
            } else {
                bounds = new Rectangle((index * (64)) + (index * 7) + 16, 32, 64, 64);
            }
        }else if(state==4)
        {
            if(index>3)
            {
                int columnIndex = index % 4;
                int rowIndex = index / 4;
                bounds = new Rectangle((columnIndex*71) + 371, 32 + (rowIndex*71), 64, 64);
            } else {
                bounds = new Rectangle((index * (64)) + (index * 7) + 371, 32, 64, 64);
            }
        }
        
    }
    
    @Override
    public void tick(boolean[] k,boolean[] m,Input input,World world,int x,int y)
    {
        
        if(!world.isDrag())
        {
   
            if(state<=2)
            {
                if(bounds.contains(new Point(input.getMouseX()-x,input.getMouseY()+(ui.getScroll1()*71)-y))&&ui.getPrimaryBounds().contains(new Point(input.getMouseX()-x,input.getMouseY()-y)))
                {
                    hover = true;
                }else
                {
                    hover = false;
                }
            }else if(state == 4)
            {
                if(bounds.contains(new Point(input.getMouseX()-x,input.getMouseY()+(ui.getScroll2()*71)-y)))
                {
                    
                    hover = true;
                }else
                {
                    hover = false;
                }
            }
        }
        
        if (m[1] && hover)
        {
            world.spawnItemOptionTab(input.getMouseX(), input.getMouseY(), index, state,item);
        } else if (input.isMouseButtonDown(0) && hover && !ui.isDrag() && !world.isDrag()) {
            xofs = input.getMouseX() - x - bounds.x;
            if (state <= 2) {
                yofs = input.getMouseY() - y - bounds.y + (ui.getScroll1() * 64 + ui.getScroll1() * 7);
            } else if (state == 4) {
                yofs = input.getMouseY() - y - bounds.y + (ui.getScroll2() * 64 + ui.getScroll2() * 7);
            }
            drag = true;
            ui.setDrag(true);
            world.setDrag(true);
        }
    }
    
    @Override
    public void render(Graphics g,Input input,int x,int y)
    {
        if(!drag)
        {
            if(state == 1)
            {
                if((index/9)-ui.getScroll1()>=0&&(index/9)-ui.getScroll1()<=4)
                {
                    if(index>8)
                    {
                        int columnIndex = index%9;
                        int rowIndex = index/9;
                        if(hover)
                        {
                            item.getTexture().draw((columnIndex*64)+(columnIndex*7)+16+x,32+((rowIndex-ui.getScroll1())*64)+((rowIndex-ui.getScroll1())*7)+y,64,64);
                            if(item.isStackable())
                            {
                                fontSmall.drawString((columnIndex*64)+(columnIndex*7)+16+x,32+((rowIndex-ui.getScroll1())*64)+((rowIndex-ui.getScroll1())*7)+y,""+item.getStack());
                            }
                        }else
                        {
                            item.getTexture().draw((columnIndex*64)+(columnIndex*7)+16+x,32+((rowIndex-ui.getScroll1())*64)+((rowIndex-ui.getScroll1())*7)+y,64,64,Color.gray);
                            if(item.isStackable())
                            {
                                fontSmall.drawString((columnIndex*64)+(columnIndex*7)+16+x,32+((rowIndex-ui.getScroll1())*64)+((rowIndex-ui.getScroll1())*7)+y,""+item.getStack());
                            }
                        }
                    }else
                    {
                        if(hover)
                        {
                            item.getTexture().draw((index*(64))+(index*7)+16+x,32-(ui.getScroll1()*64)-(ui.getScroll1()*7)+y,64,64);
                            if(item.isStackable())
                            {
                                fontSmall.drawString((index*(64))+(index*7)+16+x,32-(ui.getScroll1()*64)-(ui.getScroll1()*7)+y,""+item.getStack());
                            }
                        }else
                        {
                         
                            item.getTexture().draw((index*(64))+(index*7)+16+x,32-(ui.getScroll1()*64)-(ui.getScroll1()*7)+y,64,64,Color.gray);
                            if(item.isStackable())
                            {
                                fontSmall.drawString((index*(64))+(index*7)+16+x,32-(ui.getScroll1()*64)-(ui.getScroll1()*7)+y,""+item.getStack());
                            }
                        }
                    }
                }
            }else if(state == 2)
            {
                if((index/4)-ui.getScroll1()>=0&&(index/4)-ui.getScroll1()<=4)
                {
                    if(index>3)
                    {
                        int columnIndex = index%4;
                        int rowIndex = index/4;
                        if(hover)
                        {
                            item.getTexture().draw((columnIndex*64)+(columnIndex*7)+16+x,32+((rowIndex-ui.getScroll1())*64)+((rowIndex-ui.getScroll1())*7)+y,64,64);
                            if(item.isStackable())
                            {
                                
                                fontSmall.drawString((columnIndex*64)+(columnIndex*7)+16+x,32+((rowIndex-ui.getScroll1())*64)+((rowIndex-ui.getScroll1())*7)+y,""+item.getStack());
                            }
                            
                        }else
                        {
                            item.getTexture().draw((columnIndex*71)+16+x,32+((rowIndex-ui.getScroll1())*64)+((rowIndex-ui.getScroll1())*7)+y,64,64,Color.gray);
                            if(item.isStackable())
                            {
                                
                                fontSmall.drawString((columnIndex*64)+(columnIndex*7)+16+x,32+((rowIndex-ui.getScroll1())*64)+((rowIndex-ui.getScroll1())*7)+y,""+item.getStack());
                            }
                        }
                    }else
                    {
                        if(hover)
                        {
                            item.getTexture().draw((index*(64))+(index*7)+16+x,32-(ui.getScroll1()*64)-(ui.getScroll1()*7)+y,64,64);
                            if(item.isStackable())
                            {
                              
                                fontSmall.drawString((index*(64))+(index*7)+16+x,32-(ui.getScroll1()*64)-(ui.getScroll1()*7)+y,""+item.getStack());
                            }
                        }else
                        {
                            item.getTexture().draw((index*(64))+(index*7)+16+x,32-(ui.getScroll1()*64)-(ui.getScroll1()*7)+y,64,64,Color.gray);
                            if(item.isStackable())
                            {
                                
                                fontSmall.drawString((index*(64))+(index*7)+16+x,32-(ui.getScroll1()*64)-(ui.getScroll1()*7)+y,""+item.getStack());
                            }
                        }
                    }
                }
            }else if(state == 4)
            {
                if((index/4)-ui.getScroll2()>=0&&(index/4)-ui.getScroll2()<=4)
                {
                    if(index>3)
                    {
                        int columnIndex = index%4;
                        int rowIndex = index/4;
                        if(hover)
                        {
                            item.getTexture().draw((columnIndex*71)+371+x,32+((rowIndex-ui.getScroll2())*71)+y,64,64);
                            if(item.isStackable())
                            {
                                fontSmall.drawString((columnIndex*71)+371+x,32+((rowIndex-ui.getScroll2())*71)+y,""+item.getStack());
                            }
                        }else
                        {
                            item.getTexture().draw((columnIndex*71)+371+x,32+((rowIndex-ui.getScroll2())*71)+y,64,64,Color.gray);
                            if(item.isStackable())
                            {
                               
                                fontSmall.drawString((columnIndex*71)+371+x,32+((rowIndex-ui.getScroll2())*71)+y,""+item.getStack());
                            }
                        }
                    }else
                    {
                        if(hover)
                        {
                            item.getTexture().draw((index*(71))+371+x,32-(ui.getScroll2()*71)+y,64,64);
                            if(item.isStackable())
                            {
                                fontSmall.drawString((index*(71))+371+x,32-(ui.getScroll2()*71)+y,""+item.getStack());
                            }
                        }else
                        {
                            item.getTexture().draw((index*(71))+371+x,32-(ui.getScroll2()*71)+y,64,64,Color.gray);
                            if(item.isStackable())
                            {
                                g.setColor(Color.white);
                                fontSmall.drawString((index*(71))+371+x,32-(ui.getScroll2()*71)+y,""+item.getStack());
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void checkDrop(Input input,World world,int x,int y)
    {
        int max = 0;
        int maxIndex = -1;
        
        
            dropRect = new Rectangle(input.getMouseX()-xofs-x,input.getMouseY()-yofs-y,64,64);
            
            if(state<=2)
            {
                if(!dropRect.intersects(world.getInventory_ui().getPrimaryBounds()))
                {
                    System.out.println("index"+index);
                    ui.getPlayer_inventory().dropItem(world.getWm().getPlayer().getX(), world.getWm().getPlayer().getY(), index, world.getWm().getCurrentLocalMap(), -1);
                    ui.refreshInventoryUI(world.getWm().getCurrentLocalMap());
                    return;
                }

                if(ui.getSecondaryBounds()!=null)
                {
                    if(dropRect.intersects(ui.getSecondaryBounds()))
                    {
                        System.out.println("index"+index);
                        ui.getPlayer_inventory().dropItem(world.getWm().getPlayer().getX(), world.getWm().getPlayer().getY(), index, world.getWm().getCurrentLocalMap(), -1);
                        ui.refreshInventoryUI(world.getWm().getCurrentLocalMap());
                        return;
                    }
                }
            }else if(state==4)
            {
                if(dropRect.intersects(ui.getPrimaryBounds()))
                {
                    world.getWm().getCurrentLocalMap().getItemPileAt(world.getWm().getCurrentLocalMap().getPlayer().getX(), world.getWm().getCurrentLocalMap().getPlayer().getY()).takeFrom(world.getWm().getPlayerInventory(), index, world.getWm().getCurrentLocalMap(),-1);
                    ui.refreshInventoryUI(world.getWm().getCurrentLocalMap());
                    return;
                }
            }
            
            
        
    }
    
}
