/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EquipmentUI;

import InventoryUI.ItemOptionTab;
import InventoryUI.ItemUI;
import Item.Equipment;
import Item.Slot;
import Res.Res;
import UI.UIComponent;
import UI.UIWindow;
import World.World;
import java.awt.Rectangle;
import java.util.ArrayList;
import javafx.util.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class EquipmentUI extends UIComponent
{
    private Equipment equipment;
    private Res res;
    private int state;
    
    private ArrayList<EquipmentItemUI> itemUI;
    
    private ItemOptionTab itemOptionTab;
    
    
    private ArrayList<Pair<Integer,Rectangle>> bounds;
    private Rectangle mainBounds;
    
    private Image bg;
    
    public EquipmentUI(Equipment equipment,Res res,int state,int x,int y,int xofs,int yofs,UIWindow window)
    {
        
        super(x,y,xofs,yofs,window);
        this.res = res;
        this.equipment = equipment;
        this.state = state;
        itemUI = new ArrayList<EquipmentItemUI>();
        for(Slot s:equipment.getEquipments())
        {
            if(s.getItem()!=null)
            {
                itemUI.add(new EquipmentItemUI(s.getItem(),s.getType()-21,res,this));
            }
        }
        
        int type = 21;
        
        mainBounds = new Rectangle(16,66,205,280);
        
        bounds = new ArrayList<Pair<Integer,Rectangle>>();
        
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<3;j++)
            {
                bounds.add(new Pair(type,new Rectangle((j * 64) + (j * 7) + 16, ((i) * 64) + ((i) * 7)+66, 64, 64)));
                type++;
            }
        }
        
        bg = res.equipment_bg;
        
        
    }
    
    private boolean firstRun = true;
    
    @Override
    public void tick(boolean[] k, boolean[] m, Input input, World world, int x, int y,UIWindow window) 
    {
        if(firstRun)
        {
            equipment.equip(world.getItemLibrary().getItemByTrueName("Wooden Shield"));
            refreshUI();
            firstRun = false;
        }
        for(int i=itemUI.size()-1;i>=0;i--)
        {
            itemUI.get(i).tick(k, m, input, world,x, y);
        }
    }

    @Override
    public void dragRender(Graphics g, Input input)
    {
        
    }

    @Override
    public int getUIWidth()
    {
        return bg.getWidth();
    }

    @Override
    public int getUIHeight()
    {
        return bg.getHeight();
    }
   
    
   
    
    public void render(Graphics g,Input input,int x,int y)
    {
        
        bg.draw(x,y);
        for(int i=0;i<itemUI.size();i++)
        {
            itemUI.get(i).render(g, input,x,y);
        }
        
        for(ItemUI i:itemUI)
        {
            if(i.isDrag())
            {
                i.dragRender(g, input);
            }
        }
        
//        g.setColor(Color.red);
//        for(Pair<Integer,Rectangle> p: bounds)
//        {
//            g.drawRect(p.getValue().x, p.getValue().y, p.getValue().width, p.getValue().height);
//            g.drawString(p.getKey()+"", p.getValue().x+10, p.getValue().y+10);
//        }
        
    }
    
    
    //Equipment properties, stats of an equipment is represented by it's effects
    //20 - Equipable item
    //21 - Neck
    //22 - Head
    //23 - Back
    //24 - Main-Hand
    //25 - Body
    //26 - Off-Hand weapon/shield
    //27 - Ring
    //28 - Leg
    //29 - Accessory
    //30 - Cloak
    //31 - Shoes
    //32 - Belt
    //33 - 2-Hand
    public void refreshUI()
    {
        itemUI.clear();
        for(Slot s:equipment.getEquipments())
        {
            if(s.getItem()!=null)
            {
                itemUI.add(new EquipmentItemUI(s.getItem(),s.getType()-21,res,this));
            }
        }
    }
    
    @Override
    public void checkDrop(boolean[] k, boolean[] m, Input input, World world) {
        for(int i=itemUI.size()-1;i>=0;i--)
        {
            if(itemUI.get(i).isDrag())
            {
                itemUI.get(i).checkDrop(input, world, 0, 0);
                itemUI.get(i).setDrag(false);
                drag = false;
                world.setDrag(false);
            }
        }
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ArrayList<EquipmentItemUI> getItemUI() {
        return itemUI;
    }

    public void setItemUI(ArrayList<EquipmentItemUI> itemUI) {
        this.itemUI = itemUI;
    }

    public ItemOptionTab getItemOptionTab() {
        return itemOptionTab;
    }

    public void setItemOptionTab(ItemOptionTab itemOptionTab) {
        this.itemOptionTab = itemOptionTab;
    }

    public ArrayList<Pair<Integer, Rectangle>> getBounds() {
        return bounds;
    }

    public void setBounds(ArrayList<Pair<Integer, Rectangle>> bounds) {
        this.bounds = bounds;
    }

    public Rectangle getMainBounds() {
        return mainBounds;
    }

    public void setMainBounds(Rectangle mainBounds) {
        this.mainBounds = mainBounds;
    }

    public Image getBg() {
        return bg;
    }

    public void setBg(Image bg) {
        this.bg = bg;
    }

    

    
    
    
    
    
    
    
}
