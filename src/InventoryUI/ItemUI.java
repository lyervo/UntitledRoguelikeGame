/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import UI.DescBox;
import Item.Item;
import Res.Res;
import World.LocalMap;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import javafx.util.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class ItemUI extends DescBox
{
    protected Item item;

    protected int index;
    
    protected Rectangle bounds;
    
    protected Rectangle dropRect;
    
    protected boolean hover;
    
    
    protected boolean drag;
    protected int xofs,yofs;
    protected int posX,posY;
    
    private long last_desc_hover;
    private long desc_hover;
    private boolean desc_display;
    private ArrayList<String> desc_lines;
    
    protected TrueTypeFont font,fontSmall;
    
    public ItemUI(Item item,int index,Res res)
    {
        super(item.getName(),item.getDesc(),res.disposableDroidBB);
        this.drag = false;
        this.item = item;
        this.index = index;
        
        desc_hover = 0;
        hover = false;
        font = res.disposableDroidBB;
        last_desc_hover = 0;
//        if(state == 1)
//        {
//            if(index>8)
//            {
//                int columnIndex = index % 9;
//                int rowIndex = index / 9;
//                bounds = new Rectangle((columnIndex*71) + 16, 32 + (rowIndex*71), 64, 64);
//            } else {
//                bounds = new Rectangle((index * (64)) + (index * 7) + 16, 32, 64, 64);
//            }
//        }else if(state == 2)
//        {
//            if(index>3)
//            {
//                int columnIndex = index % 4;
//                int rowIndex = index / 4;
//                
//                bounds = new Rectangle((columnIndex*71) + 16, 32 + (rowIndex*71), 64, 64);
//            } else {
//                
//                bounds = new Rectangle((index * (64)) + (index * 7) + 16, 32, 64, 64);
//            }
//        }else if(state == 4)
//        {
//            if(index>5)
//            {
//                int columnIndex = index % 6;
//                int rowIndex = index / 6;
//                bounds = new Rectangle((columnIndex*71) + 371, 32 + (rowIndex*71), 64, 64);
//            } else {
//                bounds = new Rectangle((index * (64)) + (index * 7) + 371, 32, 64, 64);
//            }
//        }else if(state == 5)
//        {
//            bounds = new Rectangle(76+(index*(64+7)),666,64,64);
//        }else if(state == 6)
//        {
//            if(item.getType()!=33)
//            {
//                if(index<3)
//                {
//                    bounds = new Rectangle(16+(index*(71)),66,64,64);
//                }else
//                {
//                    int colIndex = index%3;
//                    int rowIndex = index/3;
//                    bounds = new Rectangle((colIndex * 64) + (colIndex * 7) + 16, (rowIndex*71)+66, 64, 64);
//                }
//            }else
//            {
//                bounds = new Rectangle(16,137,64,64);
//            }
//        }else if(state == 9)
//        {
//            
//            if(index<3)
//            {
//                bounds = new Rectangle(16+(index*(71)),66,64,64);
//            }else
//            {
//                int colIndex = index%3;
//                int rowIndex = index/3;
//                bounds = new Rectangle((colIndex * 64) + (colIndex * 7) + 16, (rowIndex*71)+66, 64, 64);
//            }
//            
//        }
        
        fontSmall = res.disposableDroidBB20f;
        
        desc_lines = new ArrayList<String>();
        
        
        
        
    }
    
    public void render(Graphics g,Input input,int x,int y)
    {
        if(!drag)
        {
            if(state == 5)
            {
               
                item.getTexture().draw(76+(index*(64+7)),666,64,64);
                if(item.isStackable())
                {
                    fontSmall.drawString(76+(index*(64+7)),666,""+item.getStack());
                }
            }else if(state == 6)
            {
                if(item.getType()!=33)
                {
                    int colIndex = index%3;
                    int rowIndex = index/3;
                    item.getTexture().draw((colIndex * 64) + (colIndex * 7) + 16, (rowIndex*71)+66, 64, 64);
                }else
                {
                    item.getTexture().draw(16,137, 64, 64);
                }
            }else if(state == 9)
            {
                int colIndex = index%3;
                int rowIndex = index/3;
                
                item.getTexture().draw((colIndex * 64) + (colIndex * 7) + 16, (rowIndex*71)+66, 64, 64);
                
                if(item.isStackable())
                {
                    font.drawString((colIndex * 64) + (colIndex * 7) + 16, (rowIndex*71)+66,item.getStack()+"");
                }
                
            }
        }
        
    }
    
    
    
    
    public void dragRender(Graphics g,Input input)
    {
        
        item.getTexture().draw(input.getMouseX()-xofs,input.getMouseY()-yofs,64,64);
        g.setColor(Color.black);
        g.drawRect(input.getMouseX()-xofs, input.getMouseY()-yofs, 64, 64);
        if(item.isStackable())
        {
            g.setColor(Color.white);
            g.drawString(""+item.getStack(),input.getMouseX()-xofs,input.getMouseY()-yofs);
        }
    }
    
    
    public void tick(boolean[] k,boolean[] m,Input input,World world,int x,int y)
    {
        
//        if(!world.isDrag())
//   
//            if(state<=2)
//            {
//                if(bounds.contains(new Point(input.getMouseX()-x,input.getMouseY()+(scroll1*71)-y))&&ui.getPrimaryBounds().contains(new Point(input.getMouseX()-x,input.getMouseY()-y)))
//                {
//                    hover = true;
//                }else
//                {
//                    hover = false;
//                }
//            }else if(state == 4)
//            {
//                if(bounds.contains(new Point(input.getMouseX()-x,input.getMouseY()+(scroll2*71)-y)))
//                {
//                    
//                    hover = true;
//                }else
//                {
//                    hover = false;
//                }
//            }else if(state==5||state==6||state==9)
//            {
//                if(bounds.contains(new Point(input.getMouseX(),input.getMouseY())))
//                {
//                    hover = true;
//                    
//                }else
//                {
//                    hover = false;
//                }
//            }
//
//            if(state!=5&&state!=6)
//            {
//                if(m[1]&&hover)
//                {
//                    ui.spawnOptionTab(input.getMouseX(), input.getMouseY(), world.getWm().getCurrentLocalMap(), item,index,this.state,world.getItemLibrary());
//                }else if(input.isMouseButtonDown(0)&&hover&&!ui.isDrag()&&!world.isDrag())
//                {
//                    xofs = input.getMouseX()-x-bounds.x;
//                    if(state<=2)
//                    {
//                        yofs = input.getMouseY()-y-bounds.y+(scroll1*64+scroll1*7);
//                    }else if(state == 4)
//                    {
//                        yofs = input.getMouseY()-y-bounds.y+(scroll2*64+scroll2*7);
//                    }
//                    drag = true;
//                    ui.setDrag(true);
//                    world.setDrag(true);
//                }
//                else if(m[0]&&drag)
//                {
//                    checkDrop(input,ui,world.getWm().getCurrentLocalMap(),scroll1,scroll2,x,y);
//                    drag = false;
//                    ui.setDrag(false);
//                    world.setDrag(false);
//
//                }
//            }else if(state == 5)
//            {
//                if(m[1]&&hover)
//                {
//                    quickItemBarUI.spawnOptionTab(input.getMouseX(), input.getMouseY(), world.getWm().getCurrentLocalMap(), item,index,this.state,world.getItemLibrary());
//                }else if(input.isMouseButtonDown(0)&&hover&&!quickItemBarUI.isDrag()&&!world.isDrag())
//                {
//                    xofs = input.getMouseX()-bounds.x;
//                    if(state<=2)
//                    {
//                        yofs = input.getMouseY()-bounds.y+(scroll1*64+scroll1*7);
//                    }else if(state == 4)
//                    {
//                        yofs = input.getMouseY()-bounds.y+(scroll2*64+scroll2*7);
//                    }
//                    drag = true;
//                    world.setDrag(true);
//                    quickItemBarUI.setDrag(true);
//                }else if(m[0]&&drag)
//                {
//                    checkQuickItemBarDrop(input,quickItemBarUI,world.getWm().getCurrentLocalMap());
//                    drag = false;
//                    quickItemBarUI.setDrag(false);
//                    world.setDrag(false);
//
//                }
//
//            }else if(state == 6 || state == 9)
//            {
//                if(m[1]&&hover)
//                {
//                    ui.spawnOptionTab(input.getMouseX(), input.getMouseY(), world.getWm().getCurrentLocalMap(), item,index,this.state,world.getItemLibrary());
//                }else if(input.isMouseButtonDown(0)&&hover&&!ui.isDrag()&&!world.isDrag())
//                {
//                    xofs = input.getMouseX()-bounds.x;
//                    if(state<=2)
//                    {
//                        yofs = input.getMouseY()-bounds.y+(scroll1*64+scroll1*7);
//                    }else if(state == 4)
//                    {
//                        yofs = input.getMouseY()-bounds.y+(scroll2*64+scroll2*7);
//                    }
//                    drag = true;
//                    world.setDrag(true);
//                    ui.setDrag(true);
//                }else if(m[0]&&drag)
//                {
//                    checkDrop(input,ui,world.getWm().getCurrentLocalMap(),scroll1,scroll2,x,y);
//                    drag = false;
//                    ui.setDrag(false);
//                    world.setDrag(false);
//
//                }
//            }
//            
//        if(hover&&state==5)
//        {
//            
//        }else if(hover&&ui.getItemOptionTab()==null)
//        {
//            tickDesc(true);
//            
//        }else
//        {
//            tickDesc(false);
//        }
            
    }
    
    public void checkQuickItemBarDrop(Input input,QuickItemBarUI ui,LocalMap lm)
    {
        if(drag)
        {
            dropRect = new Rectangle(input.getMouseX()-xofs,input.getMouseY()-yofs,64,64);
            if(!dropRect.intersects(ui.getBounds()))
            {
                lm.getWm().getPlayerInventory().dropItem(lm.getPlayer().getX(),lm.getPlayer().getY(), index, lm, -1);
                lm.getWorld().moved();
            }else
            {
                for(int i=0;i<ui.getItemUI().size();i++)
                    {
                        if(ui.getItemUI().get(i).getBounds().intersects(dropRect))
                        {
                            
                            Collections.swap(lm.getWm().getPlayerInventory().getItems(), i, index);
                            ui.refreshUI();
                            ui.setLastDrag(5);
                            return;
                        }
                    }
            }
            ui.setLastDrag(5);
            ui.refreshUI();
        }
    }
    
    public void checkDrop(Input input,World world,int x,int y)
    {
        ArrayList<Pair<Integer,Integer>> pixelIntersection = new ArrayList<Pair<Integer,Integer>>();
        int max = 0;
        int maxIndex = -1;
        
        if(drag)
        {
            dropRect = new Rectangle(input.getMouseX()-xofs-x,input.getMouseY()-yofs+(64+7)-y,64,64);

            

//            if(state<=2)
//            {
//                if(dropRect.intersects(ui.getPrimaryBounds()))
//                {
//                    for(int i=0;i<ui.getPrimaryItemUI().size();i++)
//                    {
//                        if(ui.getPrimaryItemUI().get(i).getBounds().intersects(dropRect))
//                        {
//                            
//                            Collections.swap(ui.getPlayer_inventory().getItems(), i, index);
//                            ui.refreshInventoryUI(lm);
//                            return;
//                        }
//                    }
//                    
//                    
//                }else if(ui.getSecondaryBounds()!=null&&dropRect.intersects(ui.getSecondaryBounds()))
//                {
//                    if(dropRect.intersects(ui.getSecondaryBounds()))
//                    {
//                        ui.getPlayer_inventory().dropItem(lm.getPlayer().getX(), lm.getPlayer().getY(), index, lm,-1);
//                        ui.refreshInventoryUI(lm);
//                        return;
//                    }
//                }else
//                {
//                    ui.getPlayer_inventory().dropItem(lm.getPlayer().getX(), lm.getPlayer().getY(), index, lm,-1);
//                    ui.refreshInventoryUI(lm);
//                    return;
//                }
                
////                if(dropRect.intersects(ui.getPlayerEquipment().getMainBounds())&&lm.getWorld().getUiDisplay()==1)
////                {
////                    if(item.getType()==33&&dropRect.intersects(ui.getPlayerEquipment().getBounds().get(3).getValue()))
////                    {
////                        ui.getPlayerEquipment().getEquipment().equip(item);
////                        lm.getWorld().moved();
////                        return;
////                    }
////                    
////                    for(Pair<Integer,Rectangle> p:ui.getPlayerEquipment().getBounds())
////                    {
////                        if(p.getKey()==item.getType()&&p.getValue().intersects(dropRect))
////                        {
////                            ui.getPlayerEquipment().getEquipment().equip(item);
////                            lm.getWorld().moved();
////                            return;
////                        }
////                    }
////                }
////                
////                if(dropRect.intersects(ui.getCraftingUI().getBounds())&&lm.getWorld().getUiDisplay()==2)
////                {
////                    if(ui.getCraftingUI().getCrafting().getItems().size()<9)
////                    {
////                        
////                        ui.getCraftingUI().getCrafting().addIngredient(index);
////                        ui.getCraftingUI().refreshUI(lm);
////                        ui.refreshInventoryUI(lm);
////                        return;
////                    }
////                }
                
              
                
                
//            }else if(state==4)
//            {
//                if(dropRect.intersects(ui.getPrimaryBounds()))
//                {
//                    lm.getItemPileAt(lm.getPlayer().getX(), lm.getPlayer().getY()).takeFrom(lm.getWm().getPlayerInventory(), index, lm,-1);
//                    
//                }else if(dropRect.intersects(ui.getSecondaryBounds()))
//                {
//                    
//                    for(int i=0;i<ui.getPrimaryItemUI().size();i++)
//                    {
//                        if(ui.getPrimaryItemUI().get(i).getBounds().intersects(dropRect))
//                        {
//                            
//                            Collections.swap(ui.getPlayer_inventory().getItems(), i, index);
//                            return;
//                        }
//                    }
//                }
//            }else if(state==6)
//            {
//                if(dropRect.intersects(ui.getPrimaryBounds()))
//                {
//                    ui.getPlayer_inventory().getEquipment().unequip(item.getType());
//                    lm.getWorld().moved();
//                }
//            }else if(state==9)
//            {
//                if(dropRect.intersects(ui.getPrimaryBounds()))
//                {
////                    ui.getCraftingUI().getCrafting().removeIngridient(index);
////                    ui.getCraftingUI().refreshUI(lm);
//                    ui.refreshInventoryUI(lm);
//                }
//            }
//            
//            ui.refreshInventoryUI(lm);
//        }   
            
//    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

    public int getXofs() {
        return xofs;
    }

    public void setXofs(int xofs) {
        this.xofs = xofs;
    }

    public int getYofs() {
        return yofs;
    }

    public void setYofs(int yofs) {
        this.yofs = yofs;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Rectangle getDropRect() {
        return dropRect;
    }

    public void setDropRect(Rectangle dropRect) {
        this.dropRect = dropRect;
    }

    public long getLast_desc_hover() {
        return last_desc_hover;
    }

    public void setLast_desc_hover(long last_desc_hover) {
        this.last_desc_hover = last_desc_hover;
    }

    public long getDesc_hover() {
        return desc_hover;
    }

    public void setDesc_hover(long desc_hover) {
        this.desc_hover = desc_hover;
    }

    public boolean isDesc_display() {
        return desc_display;
    }

    public void setDesc_display(boolean desc_display) {
        this.desc_display = desc_display;
    }

    public TrueTypeFont getFont() {
        return font;
    }

    public void setFont(TrueTypeFont font) {
        this.font = font;
    }
    
    
    
    
    
        

}
