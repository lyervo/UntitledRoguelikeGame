/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

import Camera.Camera;
import Entity.Entity;
import Entity.Plant.Plant;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


/**
 *
 * @author Timot
 */
public class Zone
{
    
    private int x,y,width,height,mapId;
    
    private static int ID = 0;
    private int zoneId;
    
    //plants in this zone
    private ArrayList<Plant> plants;
    
    private String plantName;
    
    private String subFaction;
    
    private Color color;
    
    private int type;
    //1 - House
    //2 - Shop
    //3 - 
    //4 - Farm
    
    
    public Zone(int x,int y,int width,int height,int mapId,int type)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mapId = mapId;
        zoneId = ID;
        ID++;
        plants = new ArrayList<Plant>();
        this.type = type;
        color = Color.black;
        if(type == 4)
        {
            color = Color.decode("#a0d6b4");
            color.a = 0.3f;
        }else if(type == 1)
        {
            color = Color.decode("#90c1d7");
            color.a = 0.3f;
        }
       
    }
    
    public Zone(int x,int y,int width,int height,int mapId,String plantName)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mapId = mapId;
        this.type = 4;
        this.plantName = plantName;
        zoneId = ID;
        ID++;
        plants = new ArrayList<Plant>();
        color = Color.black;
        if(type == 4)
        {
            color = Color.decode("#a0d6b4");
            color.a = 0.3f;
        }else if(type == 1)
        {
            color = Color.decode("#90c1d7");
            color.a = 0.3f;
        }
    }
    
    public void render(Graphics g,Camera cam)
    {
        
        g.setColor(color);
        g.fillRect(cam.getTile_size()*x+cam.getXofs(), cam.getTile_size()*y+cam.getYofs(), cam.getTile_size()*width, cam.getTile_size()*height);
        
    }
    
    
    
    public void setSubFaction(String subFaction)
    {
        this.subFaction = subFaction;
    }
    
    public boolean notEnoughPlants(String name)
    {
        if(plants.size()<((width*height)/2))
        {
            return true;
        }else
        {
            int count = 0;
            for(Plant p:plants)
            {
                if(p.getPlantTemplate().getName().equals(name))
                {
                    count++;
                }
            }
            return count < (width*height)/2;
        }
    }
    
    public boolean notEnoughPlants()
    {
        if(plants.size()<((width*height)/2))
        {
            return true;
        }else
        {
            int count = 0;
            for(Plant p:plants)
            {
                if(p.getPlantTemplate().getName().equals(plantName))
                {
                    count++;
                }
            }
            return count < (width*height)/2;
        }
    }

    public boolean isWithinZone(Entity entity)
    {
        return x<=entity.getX()&&(x+width)>entity.getX()&&y<=entity.getY()&&(y+height)>entity.getY();
    }
    
    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getMapId()
    {
        return mapId;
    }

    public void setMapId(int id)
    {
        this.mapId = id;
    }

    public static int getID()
    {
        return ID;
    }

    public static void setID(int ID)
    {
        Zone.ID = ID;
    }

    public int getZoneId()
    {
        return zoneId;
    }

    public void setZoneId(int zoneId)
    {
        this.zoneId = zoneId;
    }

    public ArrayList<Plant> getPlants()
    {
        return plants;
    }

    public void setPlants(ArrayList<Plant> plants)
    {
        this.plants = plants;
    }

    public String getPlantName()
    {
        return plantName;
    }

    public void setPlantName(String plantName)
    {
        this.plantName = plantName;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getSubFaction()
    {
        return subFaction;
    }
    
    
    
    
    
}
