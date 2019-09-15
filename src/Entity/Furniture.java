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
    
    private ArrayList<Integer> properties;
    //works the same way as item properties, each integer represents a property
    //1 - fuelable
    //2 - is a fire source
    
    public Furniture(int id, int x, int y, Image texture) {
        super(id, x, y, texture);
    }

    @Override
    public void tick(boolean[] k, boolean[] m, Input input, World world)
    {
        
    }

}
