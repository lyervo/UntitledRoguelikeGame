/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trading;

import World.World;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

/**
 *
 * @author Timot
 */
public class TradeXTextField extends TextField
{
    
    private Rectangle bounds;
    private boolean hover;
    private TradeItem ti;
    private TradingWindow tradingWindow;
    private int index;
    private boolean remove;
    public TradeXTextField(GUIContext container, Font font, int x, int y, int width, int height,TradeItem ti)
    {
        super(container, font, x, y, width, height);
        this.ti = ti;
        bounds = new Rectangle(x,y,width,height);
        hover = true;
        this.index = ti.getIndex();
        setBorderColor(Color.black);
        setBackgroundColor(Color.gray);
        setText("");
        setCursorVisible(false);
        remove = false;
        
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        if(bounds.contains(new Point2D(input.getMouseX(),input.getMouseY())))
        {
            hover = true;
        }else
        {
            hover = false;
        }
    }
    
    @Override
    public void keyReleased(int key,char c)
    {   
        if(getText().length()>=1&&key==Input.KEY_BACK)
        {
            setText(getText().substring(0, getText().length()-1));
        }
    }
    
    @Override
    public void keyPressed(int key,char c)
    {
        if(getText().length()<=5)
        {
            if(Character.isDigit(c))
            {
                setText(getText()+c);
            }else if(getText().length()==0&&(c+"").equals("-"))
            {
                setText(getText()+c);
            }
        }
    }
    
    
    public void processInput()
    {
        
        if(getText().length()==0)
        {
            return;
        }
        
        try
        {
            
            int amount = Integer.parseInt(getText());
            ti.setTradeAmount(amount);
            ti.setButtonDisplay();
            setText("");
            
            
        }catch(NumberFormatException e)
        {
            e.printStackTrace();
        }
    }
    
    

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void setBounds(Rectangle bounds)
    {
        this.bounds = bounds;
    }

    public boolean isHover()
    {
        return hover;
    }

    public void setHover(boolean hover)
    {
        this.hover = hover;
    }

    public TradeItem getTi()
    {
        return ti;
    }

    public void setTi(TradeItem ti)
    {
        this.ti = ti;
    }

    public TradingWindow getTradingWindow()
    {
        return tradingWindow;
    }

    public void setTradingWindow(TradingWindow tradingWindow)
    {
        this.tradingWindow = tradingWindow;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }
    
    
    
}
