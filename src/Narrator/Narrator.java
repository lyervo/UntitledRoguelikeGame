/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Narrator;

import Camera.Camera;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class Narrator
{
    private int maxRow;
    private int maxWidth;
    private TrueTypeFont font;
    private ArrayList<String> lines;
    private int h;
    private int textWidth;
    
    public Narrator(GameContainer gc,TrueTypeFont font)
    {
        maxRow = 7;
        this.font = font;
        h = gc.getHeight();
        lines = new ArrayList<String>();
        maxWidth = 250;
        
    }
    
    public void addText(String text)
    {
        if(font.getWidth(text)>maxWidth)
        {
            String[] splitter = text.split(" ");
            String appendText = "";
            for(int i=0;i<splitter.length;i++)
            {
                if(font.getWidth(appendText+" "+splitter[i])<maxWidth)
                {
                    appendText+=" "+splitter[i];
                }else
                {
                    lines.add(0,appendText);
                    appendText = splitter[i];
                }
            }
        }else
        {
            lines.add(0,text);
        }
    }
    
    public void render(Graphics g,Camera cam)
    {
        for(int i=0;i<lines.size();i++)
        {
            font.drawString(cam.getWidth()+5,h-(i*25)-35, ">"+lines.get(i));
        }
    }
    
    
    
}
