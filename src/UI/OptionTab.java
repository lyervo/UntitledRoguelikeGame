/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Item.Item;
import Item.ItemPile;
import Res.Res;
import World.LocalMap;
import World.Tile;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import javafx.util.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public abstract class OptionTab
{
    
    private int x,y;
    private int w,h;
    protected LocalMap lm;
    protected Rectangle bounds;
    protected TrueTypeFont optionFont;
    
    private int scroll;
    
    protected int hoveringIndex;
    
    protected ArrayList<Pair<String,Integer>> options;
    protected ArrayList<Rectangle> optionBounds;
    protected int longestIndex;
  
    
    protected Image up_indicator,down_indicator;
    
    public OptionTab(int x,int y,LocalMap lm,TrueTypeFont font,Res res)
    {
        hoveringIndex = -1;
        optionFont = font;
        this.x = (int)(x);
        this.y = (int)(y);
        
        this.up_indicator = res.up_indicator;
        this.down_indicator = res.down_indicator;
        
        
        this.lm = lm;
        
        
            
        
        
    }
    
    public abstract void initOptions();
    
    public void initOptionTab()
    {
        int xoffset = 1360-(x+optionFont.getWidth(options.get(longestIndex).getKey()));
        if(xoffset>0)
        {
            
            xoffset = 0;
        }
        int yoffset = 768-(y+(options.size()*optionFont.getHeight()));
       
        if(yoffset>0)
        {
            yoffset = 0;
        }
        this.x = x+xoffset;
        if(options.size()*optionFont.getHeight()>768)
        {
            
        }else
        {
            this.y = y+yoffset;
            bounds.y = this.y;
            for(Rectangle r:optionBounds)
            {
                r.y = r.y+yoffset;
            }
        }
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,LocalMap lm)
    {
        if(bounds!=null)
        {
            if(!bounds.contains(new Point((int)(input.getMouseX()),(int)(input.getMouseY()))))
            {
                hoveringIndex = -1;
            }else
            {
                if(optionBounds!=null)
                {
                    for(int i=0;i<optionBounds.size();i++)
                    {
                        if(optionBounds.get(i).contains(new Point((int)(input.getMouseX()),(int)(input.getMouseY()+(scroll*optionFont.getHeight())))))
                        {
                            hoveringIndex = i;
                        }
                    }
                }
            }
        }
        
        if(hoveringIndex!=-1)
        {
            if(m[11])
            {
                optionTabScrollUp();
            }else if(m[12])
            {
                optionTabScrollDown();
            }
        }
        
        
        
    }
    
    public void optionTabScrollUp()
    {
        if(scroll==0)
        {
            return;
        }else
        {
            scroll--;
            hoveringIndex--;
        }
    }
    
    public void optionTabScrollDown()
    {
        if(scroll==options.size()-5||options.size()<=5)
        {
            return;
        }else
        {
            scroll++;
            hoveringIndex++;
        }
        
    }
    
    public void checkClick(boolean[] m,LocalMap lm)
    {
        if(m[0]&&hoveringIndex>=0)
        {
            runOption(lm);
            
        }
    }
    
    public abstract void runOption(LocalMap lm);
    
    public void render(Graphics g)
    {
        g.setFont(optionFont);
        
        g.setColor(Color.decode("#60a3bc"));
        if(options.size()<5)
        {
            g.fillRect(x, y, (int)bounds.getWidth(), (int)bounds.getHeight());
        }else
        {
            g.fillRect(x, y, (int)bounds.getWidth(), optionFont.getHeight()*5);
        }
        if(hoveringIndex>=0)
        {
            if(optionBounds.get(hoveringIndex).y-(scroll*optionFont.getHeight())>=0)
            {
                g.setColor(Color.decode("#82ccdd"));
                g.fillRect(x, optionBounds.get(hoveringIndex).y-(scroll*optionFont.getHeight()), w, optionFont.getHeight());
        
            }
        }
        g.setColor(Color.black);
        g.drawRect(x, y, w, h);
        for(int i=0;i<options.size();i++)
        {
            if(i*optionFont.getHeight()-scroll*optionFont.getHeight()<145&&i*optionFont.getHeight()-scroll*optionFont.getHeight()>=0)
            {
               
                optionFont.drawString(this.x+5, this.y+(i*optionFont.getHeight()-(scroll*optionFont.getHeight())), options.get(i).getKey());
            }
            if(i!=0&&i*optionFont.getHeight()-scroll*optionFont.getHeight()<145&&i*optionFont.getHeight()-scroll*optionFont.getHeight()>=0)
            {
                g.drawLine(x, this.y+(i*optionFont.getHeight()-scroll*optionFont.getHeight()), x+w, this.y+(i*optionFont.getHeight()-scroll*optionFont.getHeight()));
            }
        }
        
        if(options.size()>5&&scroll!=options.size()-5)
        {
            down_indicator.draw(x, y+optionFont.getHeight()*4);
        }
        
        if(options.size()>5&&scroll>0)
        {
            up_indicator.draw(x,y);
        }
        
        
        
    }
    
    
    public void setLongestIndex()
    {
        int i;
        int max = 0;
        for(i=0;i<options.size();i++)
        {
            if(optionFont.getWidth(options.get(i).getKey())>max)
            {
                max = optionFont.getWidth(options.get(i).getKey());
                longestIndex = i;
            }
        }
        w = optionFont.getWidth(options.get(longestIndex).getKey())+10;
           
            h = options.size()*optionFont.getHeight();
            if(h>optionFont.getHeight()*5)
            {
                h = optionFont.getHeight()*5;
            }
            bounds = new Rectangle(x,y,w,h);
            for(int j=0;j<options.size();j++)
            {
                optionBounds.add(new Rectangle(x,y+(optionFont.getHeight()*j),w,optionFont.getHeight()));
            }
    }
    
    public boolean isHover(int x,int y)
    {
        if(bounds.contains(new Point(x,y)))
        {
            return true;
        }else
        {
            return false;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public LocalMap getLm() {
        return lm;
    }

    public void setLm(LocalMap lm) {
        this.lm = lm;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public TrueTypeFont getOptionFont() {
        return optionFont;
    }

    public void setOptionFont(TrueTypeFont optionFont) {
        this.optionFont = optionFont;
    }

    public int getScroll() {
        return scroll;
    }

    public void setScroll(int scroll) {
        this.scroll = scroll;
    }

    public int getHoveringIndex() {
        return hoveringIndex;
    }

    public void setHoveringIndex(int hoveringIndex) {
        this.hoveringIndex = hoveringIndex;
    }

    public ArrayList<Pair<String, Integer>> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Pair<String, Integer>> options) {
        this.options = options;
    }

    public ArrayList<Rectangle> getOptionBounds() {
        return optionBounds;
    }

    public void setOptionBounds(ArrayList<Rectangle> optionBounds) {
        this.optionBounds = optionBounds;
    }

    public int getLongestIndex() {
        return longestIndex;
    }

    public void setLongestIndex(int longestIndex) {
        this.longestIndex = longestIndex;
    }

    public Image getUp_indicator() {
        return up_indicator;
    }

    public void setUp_indicator(Image up_indicator) {
        this.up_indicator = up_indicator;
    }

    public Image getDown_indicator() {
        return down_indicator;
    }

    public void setDown_indicator(Image down_indicator) {
        this.down_indicator = down_indicator;
    }
    
    
    
}
