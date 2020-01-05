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
    
    
    
    public GameEvent(String type,String subFaction,Pawn p)
    {
        this.type = type;
        this.subFaction = subFaction;
        this.pawn = p;
    }
    
    public GameEvent(String type,String subFaction,int count)
    {
        this.type = type;
        this.subFaction = subFaction;
        this.count = count;
    }
    
    public GameEvent(String type,String subFaction,Plant p)
    {
        this.type = type;
        this.subFaction = subFaction;
        this.plant = p;
        this.count = 1;
    }
    
    public GameEvent(String type,Pawn p,int count)
    {
        this.type = type;
        this.pawn = p;
        this.count = count;
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

    
    

    public String getSubFaction()
    {
        return subFaction;
    }

    public void setSubFaction(String subFaction)
    {
        this.subFaction = subFaction;
    }
    
    
    
    
    
}
