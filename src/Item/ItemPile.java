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
    
    public ItemPile(int id, int x, int y, Item item,int stack)
    {
        super(id, x, y, item.getTexture());
        items = new ArrayList<Item>();
        if(item.isStackable())
        {
            item.setStack(stack);
        }
        items.add(item);
    }
    
    
    
    

    @Override
    public void tick(boolean[] k, boolean[] m, Input input, World world)
    {
        if(world.isMoved())
        {
            for(int i=items.size()-1;i>=0;i--)
            {
                items.get(i).tick(k, m, input, world);
                if(items.get(i).getDurability()<=0)
                {
                    if(!items.get(i).getExpire().isEmpty())
                    {
                        for(int j=0;j<items.get(i).getExpire().size();j++)
                        {
                            addItem(world.getItemLibrary().getItemByTrueName(items.get(i).getExpire().get(j)));
                        }
                    }
                    items.remove(i);
                    if(items.isEmpty())
                    {
                        world.getWm().getCurrentLocalMap().getItemPiles().remove(this);
                    }
                }
            }
        }
    }
    
    @Override
    public void render(Camera cam,LocalMap map,boolean animate)
    {
        if(map.getTiles()[y][x].isVisit())
        {
            if(texture!=null)
            {
                texture.draw(x*cam.getTile_size()+cam.getXofs(),y*cam.getTile_size()+cam.getYofs(),cam.getTile_size(),cam.getTile_size());
            }
        }
    }
    
    public void takeFrom(Inventory inventory,String name,LocalMap lm,int stack)
    {

        for(int index = items.size()-1;index>=0;index--)
        {
            if(items.get(index).getTrueName().equals(name))
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
                    break;
                }
            }
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

    public Item getItemByType(int type)
    {
        for(Item i:items)
        {
            if(i.getProperties().contains(type))
            {
                return i;
            }
        }
        return null;
    }
    
    
    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public boolean hasItem(String name)
    {
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).getTrueName().equals(name))
            {
                return true;
            }
        }
        return false;
    }
    
    
    @Override
    public boolean hasItem(int type)
    {
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).getProperties().contains(type))
            {
                return true;
            }
        }
        return false;
    }
    
    
    
    
    
}
