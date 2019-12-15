/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import UI.OptionTab;
import Entity.Status;
import Item.Inventory;
import Item.Item;
import Item.ItemLibrary;
import Item.ItemPile;
import static Item.ItemType.*;
import Res.Res;
import World.LocalMap;
import java.awt.Rectangle;
import java.util.ArrayList;
import javafx.util.Pair;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class ItemOptionTab extends OptionTab
{

    private Item item;
    private Inventory inventory;
    private int index;

    private InventoryUI inventoryUI;
    
    private ItemLibrary itemLibrary;
    
    private int state;
    private Res res;
    
    public ItemOptionTab(int x, int y,LocalMap lm,GameContainer container,Res res, Inventory inventory,InventoryUI ui,Item item,int index,int state,ItemLibrary itemLibrary)
    {
        super(x, y,lm,container, res.disposableDroidBB,res);
        this.item = item;
        this.index = index;
        this.inventory = inventory;
        this.inventoryUI = ui;
        this.state = state;
        this.itemLibrary = itemLibrary;
        this.res = res;
        initOptions();
        initOptionTab();
    
    }

    @Override
    public void runOption(LocalMap lm)
    {
        if(hoveringIndex==-1)
        {
            return;
        }
        
        switch (options.get(hoveringIndex).getValue())
        {
            case -4:
                lm.getWorld().activateXItemTextField(index, state);
                break;
            case -3:
                lm.getItemPileAt(lm.getPlayer().getX(), lm.getPlayer().getY()).takeFrom(lm.getWm().getPlayerInventory(), index, lm,item.getStack()/2);
                lm.getWorld().moved();
                break;
            case -2:
                lm.getItemPileAt(lm.getPlayer().getX(), lm.getPlayer().getY()).takeFrom(lm.getWm().getPlayerInventory(), index, lm,-1);
                lm.getWorld().moved();
                break;
            case -1:
                lm.getItemPileAt(lm.getPlayer().getX(), lm.getPlayer().getY()).takeFrom(lm.getWm().getPlayerInventory(), index, lm,1);
                lm.getWorld().moved();
                break;
            case 0:
                inventory.dropItem(lm.getPlayer().getX(), lm.getPlayer().getY(), index, lm,1);
                lm.getWorld().moved();
                break;
            case 1:
                inventory.dropItem(lm.getPlayer().getX(), lm.getPlayer().getY(), index, lm,-1);
                lm.getWorld().moved();
                break;
            case 2:
                inventory.dropItem(lm.getPlayer().getX(), lm.getPlayer().getY(), index, lm,item.getStack()/2);
                lm.getWorld().moved();
                break;
            case 3:
                
                lm.getWorld().activateXItemTextField(index,state);
                
                break;
            case 4:
//                res.potion_pop.play();
                for(int i=0;i<item.getEffects().size();i++)
                {
                    lm.getPlayer().getStatus().add(new Status(item.getEffects().get(i)));
                }
                if(!item.getPostConsume().isEmpty())
                {
                    for(String s:item.getPostConsume())
                    {
                        inventory.addItem(itemLibrary.getItemByTrueName(s));
                    }
                }
                itemLibrary.identify(item.getTrueName());
                inventory.removeItem(item);
                
                lm.getWorld().moved();
                break;
            case 6:
                for(int i=0;i<item.getEffects().size();i++)
                {

                    lm.getPlayer().getStatus().add(new Status(item.getEffects().get(i)));
                }
                lm.getWorld().moved();
                break;
            case 20:
                inventory.getEquipment().equip(item);
                inventoryUI.refreshInventoryUI(lm);
                lm.getWorld().moved();
                break;
            case 21:
                inventory.getEquipment().unequip(item.getEquipmentType());
                lm.getWorld().moved();
                break;
                
        }
        
        if(inventoryUI!=null)
        {
            inventoryUI.refreshInventoryUI(lm);
        }
    }

    @Override
    public void initOptions()
    {
        optionBounds = new ArrayList<Rectangle>();
        options = new ArrayList<Pair<String,Integer>>();
        if(state!=4&&state!=6)
        {
            options.add(new Pair("Drop",0));
            
            
            
            if(item.getStack()>1)
            {
                options.add(new Pair("Drop All",1));
                options.add(new Pair("Drop Half",2));
                options.add(new Pair("Drop X",3));
            }
            
                
            for(Integer i:item.getProperties())
            {
                switch(i)
                {
                    case 0:
                        options.add(new Pair("Eat",3));
                        break;
                    case 1:
                        options.add(new Pair("Drink",4));
                        break;
                    case 2:
                        options.add(new Pair("Empty",5));
                        break;
                    case 3:
                        break;
                    case 5:
     
                        options.add(new Pair("Read",6));
                        break;
                    case 20:
                        options.add(new Pair("Equip",20));
                        break;
                }
            }
        }else if(state == 4)
        {
            options.add(new Pair("Take",-1));
            if(item.getStack()>1)
            {
                options.add(new Pair("Take All",-2));
                options.add(new Pair("Take Half",-3));
                options.add(new Pair("Take X",-4));
        
            }
        }else if(state == 6)
        {
            options.add(new Pair("Unequip",21));
        }
        
        setLongestIndex();
       
        
        
    }
    
}
