/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Camera.Camera;
import Entity.Entity;
import Item.Item;
import World.LocalMap;
import World.World;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class ItemPile extends Entity
{

    private ArrayList<Item> items;
    
    public ItemPile(int id, int x, int y, Item item)
    {
        super(id, x, y, item.getTexture());
        items = new ArrayList<Item>();
        items.add(item);
    }

    @Override
    public void tick(boolean[] k, boolean[] m, Input input, World world)
    {
        
    }
    
    @Override
    public void render(Camera cam,LocalMap map)
    {
        if(map.getTiles()[y][x].isVisit())
        {
            if(texture!=null)
            {
                texture.draw(x*32+cam.getXofs(),y*32+cam.getYofs());
            }
        }
    }
    
    public void takeFrom(Inventory inventory,int index,LocalMap lm,int stack)
    {

        if(items.get(index).isStackable()&&stack>0&&items.get(index).getStack()>=stack)
        {
            Item a = new Item(items.get(index));
            a.setStack(stack);
            inventory.addItem(a);
            items.get(index).addStack(-stack);
            if(items.get(index).getStack()<=0)
            {
                items.remove(index);
            }
            
        }else
        {
            inventory.addItem(new Item(items.get(index)));
            items.remove(index);
        }
            
        
        
        
        if(items.isEmpty())
        {
            lm.getItemPiles().remove(this);
            return;
        }
        
        //once an item is removed, refresh the first item to be printed on the map
        texture = items.get(0).getTexture();
        
        
    }
    
    public void addItem(Item i)
    {
        Item a = new Item(i);
        if(a.isStackable())
        {
            boolean unique = true;
            for(Item s:items)
            {
                if(s.getTrueName().equals(a.getTrueName()))
                {
                    s.addStack(a.getStack());
                    unique = false;
                }
            }
            if(unique)
            {
                items.add(a);
            }
        }else
        {
            items.add(a);
        }
    }


    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
    
    
    
    
}
