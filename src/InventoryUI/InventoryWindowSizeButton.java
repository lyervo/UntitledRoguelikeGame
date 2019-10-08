/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import UI.Button;
import World.World;
import org.newdawn.slick.Image;

/**
 *
 * @author Timot
 */
public class InventoryWindowSizeButton extends Button
{

    private Image texture1,texture2;

    public InventoryWindowSizeButton(int x, int y, Image texture1,Image texture2) {
        super(x, y, texture1);
        this.texture1 = texture1;
        this.texture2 = texture2;
        
    }
    
    

    @Override
    public void onClick(boolean[] m, World world)
    {
        if(world.getInventoryWindow().isMode())
        {
            setTexture(texture2);
        }else
        {
            setTexture(texture1);
        }
        
        world.getInventoryWindow().setMode(world);
    }
    
}
