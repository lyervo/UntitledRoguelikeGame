/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import World.Zone;
import java.util.Objects;

/**
 *
 * @author Timot
 */
public class Task
{
    private int x,y,id,index;
    private String type;
    
    private Entity target;
    private String info;
    private int priority;
    
    private Zone zone;
    
    private String subFaction;
    
    private String additionalInfo;
    
    //type
    //nothing
    //grab_item
    //follow_target
    //craft
    //attack
    
    
    public Task(int x, int y, int id, int index,String type) 
    {
        this.x = x;
        this.y = y;
        this.id = id;
        this.index = index;
        this.type = type;
        determinePriority();
    }
    
    public Task(int x, int y, int id, int index,String type,int priority) 
    {
        this.x = x;
        this.y = y;
        this.id = id;
        this.index = index;
        this.type = type;
    }
    
    public void setTask(Task task)
    {
        this.x = task.getX();
        this.y = task.getY();
        this.id = task.getId();
        this.index = task.getIndex();
        this.type = task.getType();
        this.priority = task.getPriority();
    }
    
    public void determinePriority()
    {
        switch(type)
        {
            case "nothing":
                priority = 0;
                break;
            case "plant_seed":
                priority = 5;
                break;
            case "harvest_plant":
                priority = 7;
                break;
            case "grab_item":
                priority = 3;
                break;
            case "craft":
                priority = 4;
                break;
            case "follow_target":
                priority = 5;
                break;
            case "walk_to":
                priority = 5;
            case "maintain":
                priority = 1;
                break;
            case "plant_seed_in_zone":
                priority = 2;
                break;
            case "find":
                priority = 3;
                break;
            case "search_item":
                priority = 6;
                break;
            case "search_item_seed":
                priority = 6;
                break;
            case "search_item_type":
                priority = 8;
                break;
            case "buy_item":
                priority = 4;
                break;
            case "manage_farm":
                priority = 8;
                break;
            case "protect_land":
                priority = 11;
                break;
            case "call_guards":
                priority = 12;
                break;
            case "fufill_need":
                priority = 7;
                break;
            case "beg_or_buy":
                priority = 8;
                break;
            case "rest":
                priority = 6;
                break;
            case "ask_for_item":
                priority = 10;
                break;
            case "wait":
                priority = 10;
                break;
            case "talk_to_target":
                priority = 11;
                break;
            
        }
    }
    
    @Override
    public boolean equals(Object object)
    {

        if (object != null && object instanceof Task)
        {
            Task t = (Task)object;
            if(!(x==t.getX()&&y==t.getY()&&id==t.getId()&&index==t.getIndex()&&type.equals(t.getType())))
            {
                return false;
            }
            
            if(t.getTarget()!=null&&target!=null)
            {
                if(!target.equals(t.getTarget()))
                {
                    System.out.println("target equal false called");
                    return false;
                }
            }else if(t.getTarget()!=null||target!=null)
            {
                return false;
            }
            
            if(t.getInfo()!=null&&info!=null)
            {
                if(!info.equals(t.getInfo()))
                {
                    System.out.println(type+"+"+info+"  vs  "+t.getType()+"+"+t.getInfo());
                    System.out.println("info equal false called");
                    return false;
                }
            }else if(t.getInfo()!=null||info!=null)
            {
                return false;
            }
            
            
            
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y,id,index,target,type,priority);
    }
    
    public void clearTask()
    {
        this.x = 0;
        this.y = 0;
        this.id = 0;
        this.index = 0;
        this.target = null;
        this.type = "nothing";
        this.priority = 0;
    }
    
    public void setCordsToTarget()
    {
        x = target.getX();
        y = target.getY();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public Zone getZone()
    {
        return zone;
    }

    public void setZone(Zone zone)
    {
        this.zone = zone;
    }

    public String getSubFaction()
    {
        return subFaction;
    }

    public void setSubFaction(String subFaction)
    {
        this.subFaction = subFaction;
    }

    public String getAdditionalInfo()
    {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo)
    {
        this.additionalInfo = additionalInfo;
    }
    
    
    
    
}
