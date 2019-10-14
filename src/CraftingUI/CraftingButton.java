/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CraftingUI;

import UI.Button;
import World.World;
import org.newdawn.slick.Image;

/**
 *
 * @author Timot
 */
public class CraftingButton extends Button
{

    public CraftingButton(int x, int y, Image texture)
    {
        super(x, y, texture);
    }

    @Override
    public void onClick(boolean[] m, World world)
    {
        world.getCraftingWindow().setDisplay();
        world.getCrafting_ui().refreshUI(world.getWm().getCurrentLocalMap());
    }
    
}
