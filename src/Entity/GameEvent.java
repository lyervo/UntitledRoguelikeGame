/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Culture.SubFaction;
import Entity.Plant.Plant;
import Item.Item;
import Item.ItemPile;
import java.util.Objects;

/**
 *
 * @author Timot
 */
public class GameEvent
{
    //the game event will be use to record state of a pawn
    //such as: being attack by another pawn, attacked a pawn, witness an attack
    //of a pawn, doing something illegal 
    private String type;
    private String subFaction;
    private int coolDown,count;
    
    private Plant plant;
    private Pawn pawn;
    private ItemPile itemPile;
    private Item item;
    
    private boolean remove;
    
    public GameEvent(String type,String subFaction,int coolDown,boolean remove)
    {
        this.type = type;
        this.subFaction = subFaction;
        this.coolDown = coolDown;
        this.remove = remove;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }


    public int getCoolDown()
    {
        return coolDown;
    }

    public void setCoolDown(int coolDown)
    {
        this.coolDown = coolDown;
    }

    public Plant getPlant()
    {
        return plant;
    }

    public void setPlant(Plant plant)
    {
        this.plant = plant;
    }

    public Pawn getPawn()
    {
        return pawn;
    }

    public void setPawn(Pawn pawn)
    {
        this.pawn = pawn;
    }

    public ItemPile getItemPile()
    {
        return itemPile;
    }

    public void setItemPile(ItemPile itemPile)
    {
        this.itemPile = itemPile;
    }

    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }


    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }
    
    public void increaseCount()
    {
        count++;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.type);
        hash = 37 * hash + Objects.hashCode(this.subFaction);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final GameEvent other = (GameEvent) obj;
        if (!Objects.equals(this.type, other.type))
        {
            return false;
        }
        if (!Objects.equals(this.subFaction, other.subFaction))
        {
            return false;
        }
        return true;
    }

    public void coolDown()
    {
        coolDown--;
    }
    
    public boolean isExpired()
    {
        return count <= 0;
    }

    public String getSubFaction()
    {
        return subFaction;
    }

    public void setSubFaction(String subFaction)
    {
        this.subFaction = subFaction;
    }

    public boolean isRemove()
    {
        return remove;
    }

    public void setRemove(boolean remove)
    {
        this.remove = remove;
    }
    
    
    
    
    
}
