/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import World.World;
import org.newdawn.slick.Image;

/**
 *
 * @author Timot
 */
public class CloseWindowButton extends Button
{

    UIWindow window;
    
    public CloseWindowButton(int x, int y, Image texture,UIWindow window)
    {
        super(x, y, texture);
        this.window = window;
    }

    @Override
    public void onClick(boolean[] m, World world)
    {
        window.setDisplay();
    }
    
}
