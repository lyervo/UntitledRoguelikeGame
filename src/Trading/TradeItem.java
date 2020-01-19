/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trading;

import Item.Item;
import World.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class TradeItem
{
    private Item item;
    //if tradeAmount is negative meaning player is selling to the target
    //if tradeAmount is positive meaning player is buying from the target
    private int tradeAmount,playerAmount,targetAmount;
    
    private double sellValue,buyValue;
    
    private BuyAllButton buyAllButton;
    private BuyItemButton buyItemButton;
    private SellAllButton sellAllButton;
    private SellItemButton sellItemButton;
    private TradeXButton tradeXButton;
    
    private TradeItemTextField tradeItemTextfield;
    private TrueTypeFont font;
    
    private int index;
    
    private boolean bgColor;
    
    private int width;
    private int playerCount,pawnCount;
    
    public TradeItem(Item playerItem,Item pawnItem,GameContainer container,TrueTypeFont font,int index)
    {
        this.item = playerItem;
        
        this.width = container.getWidth();
        
        
        this.index = index;
        this.tradeAmount = 0;
        if(playerItem!=null)
        {
            this.playerAmount = playerItem.getStack();
            this.item = playerItem;
        }else
        {
            this.playerAmount = 0;
        }
        if(pawnItem!=null)
        {
            this.targetAmount = pawnItem.getStack();
            System.out.println("stack set to "+targetAmount);
            this.item = pawnItem;
        }else
        {
            this.targetAmount = 0;
        }
        this.tradeItemTextfield = new TradeItemTextField(container,font,0,0,100,100);
        this.sellValue = item.getValue();
        this.buyValue = item.getValue();
        
        this.font = font;
        
        buyAllButton = new BuyAllButton(width-415,index*64+128+17,100,30,"Buy All",Color.black,Color.gray,Color.darkGray,font,this);
        buyItemButton = new BuyItemButton(width-310,index*64+128+17,100,30,"Buy",Color.black,Color.gray,Color.darkGray,font,this);
        sellAllButton = new SellAllButton(width-730,index*64+128+17,100,30,"Sell All",Color.black,Color.gray,Color.darkGray,font,this);
        sellItemButton = new SellItemButton(width-625,index*64+128+17,100,30,"Sell",Color.black,Color.gray,Color.darkGray,font,this);
        tradeXButton = new TradeXButton(width-520,index*64+128+17,100,30,"0",Color.black,Color.gray,Color.darkGray,font,this);
        
//        if(targetAmount == 0)
//        {
//            buyAllButton.setDisplay(false);
//            buyItemButton.setDisplay(false);
//        }
//        
//        if(playerAmount == 0)
//        {
//            sellAllButton.setDisplay(false);
//            sellItemButton.setDisplay(false);
//        }
        
        if(index%2==0)
        {
            bgColor = true;
        }else
        {
            bgColor = false;
        }
        
        this.pawnCount = targetAmount;
        this.playerCount = playerAmount;
        
        setButtonDisplay();

    }
    
    public void render(Graphics g,Input input,int scrollIndex)
    {
        if(bgColor)
        {
            g.setColor(Color.decode("#34495E"));
        }else
        {
            g.setColor(Color.decode("#2C3E50"));
        }
        g.fillRect(0, index*64+128+(scrollIndex*64), width, 64);
        g.setFont(font);
        g.setColor(Color.white);
        item.getTexture().draw(5,index*64+128+(scrollIndex*64),64,64);
        g.drawString(item.getInGameName(),70, index*64+128+17+(scrollIndex*64));
        g.drawString(""+buyValue, width-100, index*64+128+17+(scrollIndex*64));
        g.drawString(""+targetAmount, width-205, index*64+128+17+(scrollIndex*64));
        
        
        g.drawString(""+sellValue, width-835, index*64+128+17+(scrollIndex*64));
        
        g.drawString(""+playerAmount, width-940, index*64+128+17+(scrollIndex*64));
        sellAllButton.render(g, 0, scrollIndex*64);
        sellItemButton.render(g, 0, scrollIndex*64);
        buyAllButton.render(g, 0, scrollIndex*64);
        buyItemButton.render(g, 0, scrollIndex*64);
        tradeXButton.render(g, 0, scrollIndex*64);
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world,int scrollIndex)
    {
        sellAllButton.tick(m, input, world, 0, scrollIndex*64, 99);
        sellItemButton.tick(m, input, world, 0, scrollIndex*64, 99);
        buyAllButton.tick(m, input, world, 0, scrollIndex*64, 99);
        buyItemButton.tick(m, input, world, 0, scrollIndex*64, 99);
        tradeXButton.tick(m, input, world, 0, scrollIndex*64, 99);
        
    }
    
    public void increaseTradeAmount()
    {
        
        tradeAmount++;

    }
    
    public void setButtonDisplay()
    {
        if(tradeAmount>=targetAmount)
        {
            buyAllButton.setDisplay(false);
            buyItemButton.setDisplay(false);
        }else
        {
            buyAllButton.setDisplay(true);
            buyItemButton.setDisplay(true);
        }
        
        if(-tradeAmount>=playerAmount)
        {
            sellAllButton.setDisplay(false);
            sellItemButton.setDisplay(false);
        }else
        {
            sellAllButton.setDisplay(true);
            sellItemButton.setDisplay(true);
        }
    }
    
    
    public void decreaseTradeAmount()
    {
        tradeAmount--;
        
    }

    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    public int getTradeAmount()
    {
        return tradeAmount;
    }

    public void setTradeAmount(int tradeAmount)
    {
        this.tradeAmount = tradeAmount;
    }

    public int getPlayerAmount()
    {
        return playerAmount;
    }

    public void setPlayerAmount(int playerAmount)
    {
        this.playerAmount = playerAmount;
    }

    public int getTargetAmount()
    {
        return targetAmount;
    }

    public void setTargetAmount(int targetAmount)
    {
        this.targetAmount = targetAmount;
    }

    public double getSellValue()
    {
        return sellValue;
    }

    public void setSellValue(double sellValue)
    {
        this.sellValue = sellValue;
    }

    public double getBuyValue()
    {
        return buyValue;
    }

    public void setBuyValue(double buyValue)
    {
        this.buyValue = buyValue;
    }

    public BuyAllButton getBuyAllButton()
    {
        return buyAllButton;
    }

    public void setBuyAllButton(BuyAllButton buyAllButton)
    {
        this.buyAllButton = buyAllButton;
    }

    public BuyItemButton getBuyItemButton()
    {
        return buyItemButton;
    }

    public void setBuyItemButton(BuyItemButton buyItemButton)
    {
        this.buyItemButton = buyItemButton;
    }

    public SellAllButton getSellAllButton()
    {
        return sellAllButton;
    }

    public void setSellAllButton(SellAllButton sellAllButton)
    {
        this.sellAllButton = sellAllButton;
    }

    public SellItemButton getSellItemButton()
    {
        return sellItemButton;
    }

    public void setSellItemButton(SellItemButton sellItemButton)
    {
        this.sellItemButton = sellItemButton;
    }

    public TradeXButton getTradeXButton()
    {
        return tradeXButton;
    }

    public void setTradeXButton(TradeXButton tradeXButton)
    {
        this.tradeXButton = tradeXButton;
    }

    public TradeItemTextField getTradeItemTextfield()
    {
        return tradeItemTextfield;
    }

    public void setTradeItemTextfield(TradeItemTextField tradeItemTextfield)
    {
        this.tradeItemTextfield = tradeItemTextfield;
    }

    public TrueTypeFont getFont()
    {
        return font;
    }

    public void setFont(TrueTypeFont font)
    {
        this.font = font;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public boolean isBgColor()
    {
        return bgColor;
    }

    public void setBgColor(boolean bgColor)
    {
        this.bgColor = bgColor;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
    
    
    
    
}
