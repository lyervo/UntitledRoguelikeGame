/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Narrator;

import Camera.Camera;
import UI.UIComponent;
import UI.UIWindow;
import World.World;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class Narrator extends UIComponent
{
    private int maxRow;
    private int maxWidth;
    private TrueTypeFont font;
    private ArrayList<String> lines;
    private int h,w;
    private int textWidth;
    private int scrollIndex;

    public Narrator(int x, int y,TrueTypeFont font) {
        super(x, y);
        
        //how many rows of text the window should fits
        maxRow = 15;
        scrollIndex = 0;
        this.font = font;
        h = font.getHeight()*15;
        lines = new ArrayList<String>();
        maxWidth = 500;
        w = maxWidth + 50;
    }

    
    
    public void addText(String text)
    {
        text = "> "+text;
        if(font.getWidth(text)>(maxWidth))
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
                    lines.add(appendText);
                   
                    scrollDown();
                    appendText = splitter[i];
                    if(i == splitter.length-1)
                    {
                        lines.add(appendText);
                        
                        scrollDown();
                    }
                }
            }
            
        }else
        {
            lines.add(" "+text);
            
            scrollDown();
        }
        
    }
    
    public void scrollDown()
    {
        if(lines.size()-scrollIndex<=15)
        {
            return;
        }
        scrollIndex++;
           
    }
    
    public void scrollUp()
    {
     
        if(lines.size()<=15||scrollIndex==0)
        {
            return;
        }
        
        
        scrollIndex--;
            
    }
    
    
    @Override
    public void checkDrop(boolean[] k, boolean[] m, Input input, World world) {
        
    }

    @Override
    public void render(Graphics g, Input input, int x, int y)
    {
        
        g.setColor(Color.black);
        g.fillRect(x, y, w, h);
        g.setColor(Color.yellow);
        g.drawRect(x, y, w, h);
        for(int i=scrollIndex;i<scrollIndex+15;i++)
        {
            if(!outOfBounds((i*font.getHeight())-(scrollIndex*font.getHeight())))
            {
                font.drawString(x+5,y+(i*font.getHeight())-(scrollIndex*font.getHeight()), lines.get(i));
            }
        }
    }
    
    public boolean outOfBounds(int y)
    {
        if(y>=0&&y<h)
        {
            return false;
        }
        return true;
    }

    @Override
    public void tick(boolean[] k, boolean[] m, Input input, World world, int x, int y, UIWindow window)
    {
        if(window.getZ()==world.getZ())
        {
            if(m[16])
            {
                System.out.println("up");
                scrollUp();
            }else if(m[17])
            {
                System.out.println("down");
                scrollDown();
            }
        }
    }

    @Override
    public void dragRender(Graphics g, Input input)
    {
        
    }

    @Override
    public int getUIWidth() {
        return w;
    }

    @Override
    public int getUIHeight() {
        return h;
    }

    @Override
    public void renderDesc(Graphics g, Input input) {
        
    }
    
    
    
}
