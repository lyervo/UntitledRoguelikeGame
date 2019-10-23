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
public class MaterialUIDropDownButton extends Button
{

    private MaterialUI ui;
    public MaterialUIDropDownButton(int x, int y, Image texture,MaterialUI ui)
    {
        super(x, y, texture);
        this.ui = ui;
    }

    @Override
    public void onClick(boolean[] m, World world)
    {
        
    }
    
}
