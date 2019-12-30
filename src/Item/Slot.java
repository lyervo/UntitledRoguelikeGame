/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Item.Item;


/**
 *
 * @author Timot
 */
public class Slot
{
    private int type;
    private Item item;

    public Slot(int type, Item item)
    {
        this.type = type;
        this.item = item;
    }
    
    public boolean equipmentIsType(int type)
    {
        if(item==null)
        {
            return false;
        }
        return item.getProperties().contains(type);
    }
    
    public boolean isEmpty()
    {
        return item == null;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    
    
    
    
}
