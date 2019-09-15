/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import Item.Inventory;
import Item.Item;
import Item.ItemLibrary;
import Res.Res;
import World.LocalMap;
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
public class QuickItemBarUI
{
    private TrueTypeFont font,fontSmall;
    
    private Inventory playerInventory;
    
    private Image texture;
    
    private Rectangle bounds;
    
    private Res res;
    
    private int x,y;
    
    private ArrayList<ItemUI> itemUI;
    
    private ItemOptionTab itemOptionTab;

    private InventoryUI inventoryUI;
    
    private boolean drag;
    
    private int lastDrag;
    
    public QuickItemBarUI(Res res,Inventory inventory,InventoryUI inventoryUI,int x,int y)
    {
        this.inventoryUI = inventoryUI;
        this.res = res;
        this.playerInventory = inventory;
        this.texture = res.quick_item_bg;
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x,y,texture.getWidth(),texture.getHeight());
        this.font = res.disposableDroidBB;
        this.fontSmall = res.disposableDroidBB20f;
        this.drag = false;
        this.itemUI = new ArrayList<ItemUI>();
        
        if(inventory.getItems().size()<12)
        {
            for(int i=0;i<inventory.getItems().size();i++)
            {
                itemUI.add(new ItemUI(inventory.getItems().get(i),i,5,res));
            }
        }else
        {
            for(int i=0;i<12;i++)
            {
                itemUI.add(new ItemUI(inventory.getItems().get(i),i,5,res));
            }
        }
        this.lastDrag = 0;
    }
    
    public void render(Graphics g,Input input)
    {
        texture.draw(x,y);
        if(itemUI.size()<12)
        {
            for(int i=0;i<itemUI.size();i++)
            {
                itemUI.get(i).render(g, input,5,0,0);
            }
        }else
        {
            for(int i=0;i<12;i++)
            {
                itemUI.get(i).render(g, input, 5, 0, 0);
            }
        }
        
        for(ItemUI i:itemUI)
        {
            if(i.isDesc_display())
            {
                i.displayDesc(g, input);
            }
        }
        if(itemOptionTab!=null)
        {
            itemOptionTab.render(g);
        }
        
        for(ItemUI i:itemUI)
        {
            if(i.isDrag())
            {
                i.dragRender(g, input);
            }
        }
    }
    
    public void refreshUI()
    {
   
        itemUI.clear();
        if(playerInventory.getItems().size()<12)
        {
            for(int i=0;i<playerInventory.getItems().size();i++)
            {
                itemUI.add(new ItemUI(playerInventory.getItems().get(i),i,5,res));
            }
        }else
        {
            for(int i=0;i<12;i++)
            {
                itemUI.add(new ItemUI(playerInventory.getItems().get(i),i,5,res));
            }
        }
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        if(lastDrag!=0)
        {
            lastDrag--;
        }
        if(bounds.contains(new Point(input.getMouseX(),input.getMouseY())))
        {
            world.setMouse_z(1);
        }else
        {
            world.setMouse_z(0);
        }
        
        for(int i=itemUI.size()-1;i>=0;i--)
        {
            itemUI.get(i).tick(k, m, input, world, 5, 0, 0, inventoryUI,this);
        }
        
        if(itemOptionTab!=null)
        {
            itemOptionTab.tick(k, m, input, world.getWm().getCurrentLocalMap());
        }
        
        if(m[0]&&itemOptionTab!=null)
        {
            itemOptionTab.runOption(world.getWm().getCurrentLocalMap());
            itemOptionTab = null;
            refreshUI();
        }else if(k[255])
        {
            itemOptionTab = null;
        }
        
    }
    
    public void spawnOptionTab(int x,int y,LocalMap lm,Item item,int index,int state,ItemLibrary itemLibrary)
    {
        itemOptionTab = new ItemOptionTab(x,y,lm,res.disposableDroidBB,res,playerInventory,inventoryUI,item, index,state,itemLibrary);
        
    }

    public TrueTypeFont getFont() {
        return font;
    }

    public void setFont(TrueTypeFont font) {
        this.font = font;
    }

    public TrueTypeFont getFontSmall() {
        return fontSmall;
    }

    public void setFontSmall(TrueTypeFont fontSmall) {
        this.fontSmall = fontSmall;
    }

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

    public void setPlayerInventory(Inventory playerInventory) {
        this.playerInventory = playerInventory;
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<ItemUI> getItemUI() {
        return itemUI;
    }

    public void setItemUI(ArrayList<ItemUI> itemUI) {
        this.itemUI = itemUI;
    }

    public ItemOptionTab getItemOptionTab() {
        return itemOptionTab;
    }

    public void setItemOptionTab(ItemOptionTab itemOptionTab) {
        this.itemOptionTab = itemOptionTab;
    }

    public InventoryUI getInventoryUI() {
        return inventoryUI;
    }

    public void setInventoryUI(InventoryUI inventoryUI) {
        this.inventoryUI = inventoryUI;
    }

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

    public int getLastDrag() {
        return lastDrag;
    }

    public void setLastDrag(int lastDrag) {
        this.lastDrag = lastDrag;
    }
    
    
    
}
