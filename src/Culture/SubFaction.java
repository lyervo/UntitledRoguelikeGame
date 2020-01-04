/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Culture;

import Entity.Pawn;
import Entity.Plant.Plant;
import Entity.Task;
import World.World;
import World.Zone;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Timot
 */
public class SubFaction
{
    private String subFactionName;
    
    private Faction faction;
    
    private ArrayList<Zone> zones;
    
    private int taskCoolDown;
    
    public SubFaction(String subFactionName,Faction faction)
    {
        this.subFactionName = subFactionName;
        this.faction = faction;
        zones = new ArrayList<Zone>();
        taskCoolDown = 0;
    }
    
    public void tick(World world)
    {
        
        if(world.isMoved())
        {
            if(taskCoolDown == 0)
            {
                for(Zone z:zones)
                {

                    if(z.getMapId() == world.getWm().getCurrentLocalMap().getId())
                    {
                        //if zone is a farming zone
                        if(z.getType() == 4)
                        {
                            giveFarmingTask(world,z);
                        }
                    }
                }
                taskCoolDown = 10;
            }else
            {
                taskCoolDown--;
            }
        }
    }
    
    public void giveFarmingTask(World world,Zone zone)
    {
        ArrayList<Pawn> pawns = world.getWm().getCurrentLocalMap().getPawnsBySubFactionAndJobTitle(this,"Farmer");
        if(pawns.isEmpty())
        {
            return;
        }
        
       
        ArrayList<Task> farmingTasks = new ArrayList<Task>();
        
        for(Plant p:zone.getPlants())
            {
                Task t;
                if(p.isFinishedGrowing())
                {
                    t = new Task(p.getX(),p.getY(),p.getId(),p.getCurrentHarvest().getTool()[0],"harvest_plant");
                    t.setTarget(p);
                    t.setInfo(p.getCurrentName());
                    
                    farmingTasks.add(t);
                    
                }
            }
            
            if(zone.notEnoughPlants())
            {
                String seedName = zone.getPlantName()+" Seed";
                for(int i=0;i<(zone.getWidth()/2);i++)
                {
                    for(int j=0;j<(zone.getHeight()/2);j++)
                    {
                        if(world.getWm().getCurrentLocalMap().countAccessibleTiles(zone.getX()+i, zone.getY()+j)>=2
                            &&world.getWm().getCurrentLocalMap().getTiles()[j][i].getPlant() == null)
                        {
                            Task newPlantingTask = new Task(zone.getX()+(i*2),zone.getY()+(j*2),0,0,"plant_seed");
                            newPlantingTask.setInfo(seedName);
                            farmingTasks.add(newPlantingTask);


                        }
                    }
                }
            }
            
        
        
        
        
        
        int pawnIndex = 0;
        
        for(Task t:farmingTasks)
        {
            pawns.get(pawnIndex).addTask(t);
            pawnIndex++;
            if(pawnIndex == pawns.size())
            {
                pawnIndex = 0;
            }
        }
        
        for(Pawn p:pawns)
        {
            p.sortTask();
        }
        
        
        
        
        
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.subFactionName);
        hash = 59 * hash + Objects.hashCode(this.faction);
        hash = 59 * hash + Objects.hashCode(this.zones);
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
        final SubFaction other = (SubFaction) obj;
        if (!Objects.equals(this.subFactionName, other.subFactionName))
        {
            return false;
        }
        return true;
    }
    
    
    

    public String getSubFactionName()
    {
        return subFactionName;
    }

    public void setSubFactionName(String subFactionName)
    {
        this.subFactionName = subFactionName;
    }

    public Faction getFaction()
    {
        return faction;
    }

    public void setFaction(Faction faction)
    {
        this.faction = faction;
    }

    public ArrayList<Zone> getZones()
    {
        return zones;
    }

    public void setZones(ArrayList<Zone> zones)
    {
        this.zones = zones;
    }
    
    
    
}
