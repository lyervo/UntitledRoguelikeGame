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
public class PinWindowButton extends Button
{
    
    private Image texture1,texture2;
    private UIWindow window;

    public PinWindowButton(int x, int y,UIWindow window, Image texture1,Image texture2)
    {
        super(x, y, texture1);
        this.texture1 = texture1;
        this.texture2 = texture2;
        this.window = window;
        
    }

    @Override
    public void onClick(boolean[] m, World world)
    {
        window.setPin();
        if(window.isPin())
        {
            setTexture(texture1);
        }else
        {
            setTexture(texture2);
        }
    }
    
}
