/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Entity.Entity;
import World.World;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;

/**
 *
 * @author Timot
 */
public class Traveler extends Entity
{

    //The traveler class represent entities that is traveling on the world map
    //such as player, random travelers, enemy and monsters, animal packs, army
    //works sorta like how armies moves in mount & blade : warband
    
    public Traveler(int id, int x, int y, Image texture)
    {
        super(id, x, y, texture);
    }

    
    
    @Override
    public void tick(boolean[] k,boolean[] m, Input input, World world)
    {
        
    }

    @Override
    public boolean hasItem(String name) {
        return true;
    }
   
    
}
