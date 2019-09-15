/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import org.newdawn.slick.Font;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

/**
 *
 * @author Timot
 */
public class NumericTextField extends TextField
{
    
    public NumericTextField(GUIContext container, Font font, int x, int y, int width, int height, ComponentListener listener)
    {
        super(container, font, x, y, width, height, listener);
    }
    
}
