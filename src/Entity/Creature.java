/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import World.World;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class Creature extends Entity
{

    private String name;
    private Meter hp;
    
    public Creature(int id, int x, int y, Image texture,String name,Meter hp)
    {
        super(id, x, y, texture);
        this.name = name;
        this.hp = hp;
        
    }

    @Override
    public void tick(boolean[] k, boolean[] m, Input input, World world)
    {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Meter getHp() {
        return hp;
    }

    public void setHp(Meter hp) {
        this.hp = hp;
    }
    
    
    
    
}
