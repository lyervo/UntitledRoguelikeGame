/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import UI.Button;
import World.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;



/**
 *
 * @author Timot
 */
public class InventoryButton extends Button
{

    public InventoryButton(int x, int y, int width, int height, String text, Color border, Color fill, Color hoverFill,TrueTypeFont font) {
        super(x, y, width,height, text, border, fill, hoverFill,font);
    }
    
    public InventoryButton(int x,int y,Image texture)
    {
        super(x,y,texture);
    }

    @Override
    public void onClick(boolean[] m, World world)
    {
        if(!world.getDialogue().isDisplay())
        {
            world.getInventoryWindow().setDisplay();
            world.deactivateXItemTextField();
            world.setDrag(false);
        }
    }
    
    
    
    
}
