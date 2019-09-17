/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import World.World;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class Furniture extends Entity
{

    private int fuel;
    private String name;
    private String desc;
    private ArrayList<Integer> properties;
    //works the same way as item properties, each integer represents a property
    //1 - fuelable
    //2 - is a fire source
    //3 - flammable
    //4 - woodworking station
    
    public Furniture(int id,int x,int y,FurnitureTemplate ft)
    {
        super(id,x,y,ft.getTexture());
        this.properties = new ArrayList<Integer>(ft.getProperties());
        this.name = ft.getName();
        this.desc = ft.getDesc();
    }

    @Override
    public void tick(boolean[] k, boolean[] m, Input input, World world)
    {
        
    }
    
    public int getStationType()
    {
        for(int i=0;i<properties.size();i++)
        {
            if(properties.get(i)>20&&properties.get(i)<50)
            {
                return properties.get(i);
            }
        }
        return -1;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<Integer> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Integer> properties) {
        this.properties = properties;
    }
    
    

}
