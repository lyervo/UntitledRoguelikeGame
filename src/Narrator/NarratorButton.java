/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Narrator;

import UI.Button;
import World.World;
import org.newdawn.slick.Image;

/**
 *
 * @author Timot
 */
public class NarratorButton extends Button
{

    public NarratorButton(int x, int y, Image texture) {
        super(x, y, texture);
    }

    @Override
    public void onClick(boolean[] m, World world)
    {
        world.getNarratorWindow().setDisplay();
    }
    
}
