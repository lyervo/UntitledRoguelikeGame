/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Item.Inventory;
import Item.Item;
import Res.Res;
import InventoryUI.ItemUI;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Timot
 */
public class Equipment
{
    
    private ArrayList<Slot> equipments;
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
    
    
    private Inventory inventory;
    
    private ArrayList<ItemUI> itemUI;
    
    public Equipment(Inventory inventory)
    {
        this.inventory = inventory;
        this.equipments = new ArrayList<Slot>();
        for(int i=21;i<33;i++)
        {
            equipments.add(new Slot(i,null));
        }
    }
    
    public void equip(Item item)
    {
        if(item.getProperties().contains(33))//if item is a two hand weapon
        {
            if(equipments.get(3).getItem()!=null)
            {
                inventory.addItem(equipments.get(3).getItem());
                equipments.get(3).setItem(null);
            }
            
            if(equipments.get(5).getItem()!=null)
            {
                inventory.addItem(equipments.get(5).getItem());
                equipments.get(5).setItem(null);;
            }
            
            equipments.get(3).setItem(item);
            inventory.removeItem(item);
            return;
        }else if(item.getType()==26)//if item is an off-hand equipment
        {
            if(equipments.get(3).getItem()!=null)//if there is a main hand weapon
            {
                if(equipments.get(3).getItem().getType()==33)//if main hand weapon is a two hand weapon
                {
                    inventory.addItem(equipments.get(3).getItem());
                    equipments.get(3).setItem(null);
                }
            }
            
            if(!equipments.get(5).isEmpty())
            {
                inventory.addItem(equipments.get(5).getItem());
            }
            
            equipments.get(5).setItem(item);
            inventory.removeItem(item);
            return;
        }
        
        for(int i=0;i<equipments.size();i++)
        {
            if(item.getProperties().contains(equipments.get(i).getType()))
            {
                if(equipments.get(i).isEmpty())
                {
                    equipments.get(i).setItem(new Item(item));
                    inventory.removeItem(item);
                }else
                {
                    inventory.addItem(equipments.get(i).getItem());
                    equipments.get(i).setItem(new Item(item));
                    inventory.removeItem(item);
                }
            }
        }
        
    }
    
    public void unequip(int type)
    {
        if(type==33)
        {
            inventory.addItem(equipments.get(3).getItem());
            equipments.get(3).setItem(null);
            return;
        }
        
        for(int i=0;i<equipments.size();i++)
        {
            if(equipments.get(i).getType() == type)
            {
                inventory.addItem(equipments.get(i).getItem());
                equipments.get(i).setItem(null);
            }
        }
    }

    public ArrayList<Slot> getEquipments() {
        return equipments;
    }

    public void setEquipments(ArrayList<Slot> equipments) {
        this.equipments = equipments;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ArrayList<ItemUI> getItemUI() {
        return itemUI;
    }

    public void setItemUI(ArrayList<ItemUI> itemUI) {
        this.itemUI = itemUI;
    }
    
    
    
}
