/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trading;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

/**
 *
 * @author Timot
 */
public class TradeItemTextField extends TextField
{
    
    public TradeItemTextField(GUIContext container, Font font, int x, int y, int width, int height)
    {
        super(container, font, x, y, width, height);
        setBackgroundColor(Color.decode("#757161"));
        setBorderColor(Color.decode("#757161"));
    }
    
    
    @Override
    public void keyPressed(int key,char c)
    {
        if(getText().length()<=5)
        {
            if(Character.isDigit(c))
            {
                setText(getText()+c);
            }
        }
    }
    
}
