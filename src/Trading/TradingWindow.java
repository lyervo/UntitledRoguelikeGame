/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trading;

import Entity.Pawn;
import Item.Item;
import World.World;
import java.awt.Point;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class TradingWindow
{
    
    private Pawn player,target;
    
    private ArrayList<TradeItem> tradeItems;
    
    private int scrollIndex;
    
    private boolean display;
    
    private ConfirmTradeButton confirmTradeButton;
    private CancelTradeButton cancelTradeButton;
    
    private Rectangle bounds;
    
    private int width,height;
    private int centerX;
    
    
    //how many trade items can the screen fits in
    private int maxDisplay;
    
    private TrueTypeFont font;
    

    
    public TradingWindow(GameContainer container,TrueTypeFont font)
    {
        tradeItems = new ArrayList<TradeItem>();
        scrollIndex = 0;
        display = false;
        bounds = new Rectangle(0,0,container.getWidth(),container.getHeight());
        this.height = container.getHeight();
        this.width = container.getWidth();
        this.centerX = width/2;
        this.font = font;
        
        this.maxDisplay = (height-256)/64;
        
        confirmTradeButton = new ConfirmTradeButton(centerX+5,height-80,100,30,"Confirm",Color.black,Color.gray,Color.darkGray,font);
        cancelTradeButton = new CancelTradeButton(centerX-105,height-80,100,30,"Cancel",Color.black,Color.gray,Color.darkGray,font);
        
        
    }
    
    
    public void scrollUp()
    {
        if(scrollIndex == 0)
        {
            return;
        }
        
        scrollIndex--;
        
    }
    
    public void scrollDown()
    {
        if(scrollIndex >= tradeItems.size()-maxDisplay||tradeItems.size()<=maxDisplay)
        {
            return;
        }
        scrollIndex++;
    }
    
    public void initTrade(Pawn player,Pawn target,World world)
    {
        this.player = player;
        this.target = target;
        ArrayList<Item> playerItem = new ArrayList<Item>(world.getWm().getPlayerInventory().getItems());
        ArrayList<Item> pawnItem = new ArrayList<Item>(target.getInventory().getItems());
        tradeItems.clear();
        boolean found = false;
        for(int i=0;i<playerItem.size();i++)
        {
            found = false;
            if(playerItem.get(i).isStackable())
            {
                for(int j=0;j<pawnItem.size();j++)
                {
                    if(pawnItem.get(j).equals(playerItem.get(i)))
                    {
                        tradeItems.add(new TradeItem(playerItem.get(i),pawnItem.get(j),world.getContainer(),world.getRes().disposableDroidBB,i));
                        pawnItem.remove(j);
                        found = true;
                        
                    }
                }
                if(!found)
                {
                    tradeItems.add(new TradeItem(playerItem.get(i),null,world.getContainer(),world.getRes().disposableDroidBB,i));
                }
                
                
            }else
            {
                tradeItems.add(new TradeItem(playerItem.get(i),null,world.getContainer(),world.getRes().disposableDroidBB,i));
            }
        }
        for(int i=0;i<pawnItem.size();i++)
        {
            tradeItems.add(new TradeItem(null,pawnItem.get(i),world.getContainer(),world.getRes().disposableDroidBB,i+playerItem.size()));
        }
        scrollIndex = 0;
        
        
        
    }
    
    public void display()
    {
        display = true;
    }
    
    public void render(Graphics g,Input input)
    {
        if(display)
        {
            g.setColor(Color.decode("#4d4949"));
            g.fillRect(0, 0, (int)bounds.getWidth(), (int)bounds.getHeight());
            g.setColor(Color.decode("#2C3E50"));
            g.fillRect(0,64,width, 64);
            
            g.setFont(font);
            g.setColor(Color.white);
            g.drawString("Trade", centerX-(font.getWidth("Trade")/2), 70);
            g.drawString("Amount", centerX-(font.getWidth("Amount")/2), 64+font.getHeight());
            
            g.drawString("Buying", centerX+260-(font.getWidth("Buying")/2), 70);
            g.drawString("Price", centerX+260-(font.getWidth("Price")/2), 64+font.getHeight());
            
            g.drawString("Trader", centerX+360-(font.getWidth("Trader")/2), 70);
            g.drawString("Amount", centerX+360-(font.getWidth("Amount")/2), 64+font.getHeight());
            
            g.drawString("Selling",centerX-360-(font.getWidth("Selling")/2), 70);
            g.drawString("Price",centerX-360-(font.getWidth("Price")/2), 64+font.getHeight());
            
//            g.drawString(item.getInGameName(),70, index*64+128+17+(scrollIndex*64));
//        g.drawString(""+buyValue, centerX+260, index*64+128+17+(scrollIndex*64));
//        g.drawString(""+sellValue, centerX-360, index*64+128+17+(scrollIndex*64));
//        g.drawString(""+targetAmount, centerX+360, index*64+128+17+(scrollIndex*64));
//        g.drawString(""+playerAmount, centerX-460, index*64+128+17+(scrollIndex*64));
            
            
            

    //        confirmTradeButton.render(g);
            
            for(int i=0;i<tradeItems.size();i++)
            {
                if(i*64+128+(-scrollIndex*64)+64<=height-128&&i*64+128+(-scrollIndex*64)>=128)
                {
                    tradeItems.get(i).render(g, input, -scrollIndex);
                }
            }
            cancelTradeButton.render(g);
            confirmTradeButton.render(g);
            g.setColor(Color.black);
            g.drawRect(0, 64, width, 64);
        }
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        if(display)
        {
            if(bounds.contains(new Point2D(input.getMouseX(),input.getMouseY())))
            {
                world.setZ(99);
                world.setHoveringWindow(true);
            }else
            {
                world.setZ(0);
            }
            
            if(m[16])
            {
                scrollUp();
            }else if(m[17])
            {
                scrollDown();
            }
            cancelTradeButton.tick(m, input, world);
            confirmTradeButton.tick(m, input, world);
            for(int i=0;i<tradeItems.size();i++)
            {
                if(i*64+128+(-scrollIndex*64)+64<=height-128&&i*64+128+(-scrollIndex*64)>=128)
                {
                    tradeItems.get(i).tick(k, m, input, world, -scrollIndex);
                }
            }
        }
    }

    public Pawn getPlayer()
    {
        return player;
    }

    public void setPlayer(Pawn player)
    {
        this.player = player;
    }

    public Pawn getTarget()
    {
        return target;
    }

    public void setTarget(Pawn target)
    {
        this.target = target;
    }

    public ArrayList<TradeItem> getTradeItems()
    {
        return tradeItems;
    }

    public void setTradeItems(ArrayList<TradeItem> tradeItems)
    {
        this.tradeItems = tradeItems;
    }

    public int getScrollIndex()
    {
        return scrollIndex;
    }

    public void setScrollIndex(int scrollIndex)
    {
        this.scrollIndex = scrollIndex;
    }

    public boolean isDisplay()
    {
        return display;
    }

    public void setDisplay(boolean display)
    {
        this.display = display;
    }

    public ConfirmTradeButton getConfirmTradeButton()
    {
        return confirmTradeButton;
    }

    public void setConfirmTradeButton(ConfirmTradeButton confirmTradeButton)
    {
        this.confirmTradeButton = confirmTradeButton;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void setBounds(Rectangle bounds)
    {
        this.bounds = bounds;
    }
    
    
}
