/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameConsole;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

/**
 *
 * @author Timot
 */
public class ConsoleTextField extends TextField
{
    
    private GameConsole gameConsole;
    
    public ConsoleTextField(GUIContext container, Font font, int x, int y, int width, int height,GameConsole gameConsole)
    {
        super(container, font, x, y, width, height);
        setBackgroundColor(Color.decode("#757161"));
        setBorderColor(Color.decode("#757161"));
        this.gameConsole = gameConsole;
    }
    
//    @Override
//    public void keyPressed(int key,char c)
//    {
//        if(key == Input.KEY_ENTER)
//        {
//            gameConsole.runCommand(getText());
//            setText("");
//        }else if(key == Input.KEY_BACK)
//        {
//            setText(getText().substring(0, getText().length()-1));
//        }else
//        {
//            setText(getText()+c);
//        }
//    }


    @Override
    public void keyReleased(int key,char c)
    {
        if(key == Input.KEY_ENTER)
        {
            if(getText().length() > 0)
            {
                gameConsole.runCommand(getText());
                setText("");
            }
        }
    }
    
    
}
