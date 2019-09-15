/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import UI.Button;
import World.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class XItemTextFieldExitButton extends Button
{

    public XItemTextFieldExitButton(int x, int y, int width, int height, String text, Color border, Color fill, Color hoverFill, TrueTypeFont font) {
        super(x, y, width, height, text, border, fill, hoverFill, font);
    }

    @Override
    public void onClick(boolean[] m, World world)
    {
        world.deactivateXItemTextField();
    }
    
}
