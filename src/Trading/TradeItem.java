/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trading;

import Culture.TradingBehavior;
import Item.Item;
import UI.Button;
import World.World;
import org.newdawn.slick.Color;
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
    
    private Button buyAllButton;
    private Button buyItemButton;
    private Button sellAllButton;
    private Button sellItemButton;
    private Button tradeXButton;
    
    private TradeItemTextField tradeItemTextfield;
    private TrueTypeFont font;
    
    private int index;
    
    private boolean bgColor;
    
    private int width;
    private TradingWindow tradingWindow;
    
    private boolean buying,selling;
    private int buyLimit,sellLimit;
    
    
    private TradingBehavior tradingBehavior;
    
    private TradeXTextField tradeXTextField;
    
    private double multiplier;
    
    private int masterType;
    
    public TradeItem(Item playerItem,Item pawnItem,GameContainer container,TrueTypeFont font,int index,TradingBehavior tradingBehavior,TradingWindow tradingWindow)
    {
        this.item = playerItem;
        
        this.width = container.getWidth();
        
        this.tradingBehavior = tradingBehavior;
        
        
        
        
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
            this.item = pawnItem;
        }else
        {
            this.targetAmount = 0;
        }
        
        selling = true;
        buying = true;
        
        sellLimit = -1;
        buyLimit = -1;
        
        //the default buying value of items by the NPC
        //the end selling price multiplier will be (buy multiplier)*0.8
        //the price multiplier will never be applied to currency items (coins and cash)
        multiplier = 1.0;
        
        boolean setType = false;
        
        if(tradingBehavior!=null)
        {
            
            for(int i=0;i<tradingBehavior.getConditions().size();i++)
            {
                if(item.getProperties().contains(tradingBehavior.getConditions().get(i).getType()))
                {
                    if(!tradingBehavior.getConditions().get(i).isBuying())
                    {
                        buying = false;
                    }

                    if(!tradingBehavior.getConditions().get(i).isSelling())
                    {
                        selling = false;
                    }

                    multiplier *= tradingBehavior.getConditions().get(i).getPriceMultiplier();

                    if(sellLimit == -1||sellLimit > tradingBehavior.getConditions().get(i).getMax())
                    {
                        if(!setType)
                        {
                            masterType = tradingBehavior.getConditions().get(i).getType();
                            setType = true;
                        }
                        sellLimit = tradingBehavior.getConditions().get(i).getMax();
                    }

                    if(buyLimit == -1||buyLimit > tradingBehavior.getConditions().get(i).getMin())
                    {
                        buyLimit = tradingBehavior.getConditions().get(i).getMin();
                    }

                }
            }
        }
        
        if(!setType)
        {
            masterType = -1;
        }
        
        
        
        
     
        
        
        
        this.tradeItemTextfield = new TradeItemTextField(container,font,0,0,100,100);
        if(!item.isCurrency())
        {
            this.sellValue = item.getValue()*0.8*multiplier;
        }else
        {
            this.sellValue = item.getValue();
        }
        if(!item.isCurrency())
        {
            this.buyValue = item.getValue()*multiplier;
        }else
        {
            this.buyValue = item.getValue();
        }
        this.font = font;
        
        setButtons();
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
        
        this.tradingWindow = tradingWindow;
        setButtonDisplay();

    }
    
    public void setButtons()
    {
        buyAllButton = new Button(width-555,index*64+128+17,30,30,"<<",Color.black,Color.gray,Color.darkGray,font) {
            
            @Override
            public void onClick(boolean[] m, World world)
            {
                setTradeAmount(getTargetAmount());
                getTradeXButton().setText(""+getTradeAmount());
                setButtonDisplay();
                tradingWindow.refreshTotalTradeValue();
            }
        };
        buyItemButton = new Button(width-520,index*64+128+17,30,30,"<",Color.black,Color.gray,Color.darkGray,font) {
            @Override
            public void onClick(boolean[] m, World world)
            {
                if(getTradeAmount()<getTargetAmount())
                {
                    increaseTradeAmount();
                    getTradeXButton().setText(""+getTradeAmount());
                    setButtonDisplay();
                    tradingWindow.refreshTotalTradeValue();
                }
            }
        };
        sellAllButton = new Button(width-730,index*64+128+17,30,30,">>",Color.black,Color.gray,Color.darkGray,font) {
            @Override
            public void onClick(boolean[] m, World world)
            {
                setTradeAmount(-getPlayerAmount());
                getTradeXButton().setText(""+getTradeAmount());
                setButtonDisplay();
                tradingWindow.refreshTotalTradeValue();
            }
        };
        sellItemButton = new Button(width-695,index*64+128+17,30,30,">",Color.black,Color.gray,Color.darkGray,font) {
            @Override
            public void onClick(boolean[] m, World world)
            {
                if(-getTradeAmount()<getPlayerAmount())
                {
                    decreaseTradeAmount();
                    getTradeXButton().setText(""+getTradeAmount());
                    setButtonDisplay();
                    tradingWindow.refreshTotalTradeValue();

                }
            }
        };
        tradeXButton = new TradeXButton(width-660,index*64+128+17,100,30,"0",Color.black,Color.gray,Color.darkGray,font,this,tradingWindow);
        
    }
    
    public void initTradeXTextField(World world)
    {
        tradeXTextField = new TradeXTextField(world.getContainer(),font,width-660,index*64+128+17-(world.getTradingWindow().getScrollIndex()*64),100,30,this);
    }
    
    public void render(Graphics g,Input input,GameContainer container,int scrollIndex)
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
        if(multiplier==1)
        {
            g.setColor(Color.white);
        }
        else if(multiplier<1)
        {
            g.setColor(Color.green);
        }else if(multiplier>1)
        {
            g.setColor(Color.red);
        }
        
        g.drawString(""+buyValue, width-100, index*64+128+17+(scrollIndex*64));
        
        if(selling)
        {
            g.setColor(Color.white);
        }else
        {
            g.setColor(Color.red);
        }
        if(buyLimit==-1&&sellLimit==-1)
        {
            g.drawString(""+(targetAmount-tradeAmount), width-305-font.getWidth(""+(targetAmount-tradeAmount))/2, index*64+128+17+(scrollIndex*64));
        }else
        {
            if(targetAmount-tradeAmount<=buyLimit)
            {
                g.setColor(Color.red);
            }else if(targetAmount+(-tradeAmount)>=sellLimit)
            {
                g.setColor(Color.red);
            }else
            {
                g.setColor(Color.white);
            }
            if(tradingWindow.getTradeLimits().get(masterType)!=null)
            {
                g.drawString(tradingWindow.getTradeLimits().get(masterType).getCount()+"/"+sellLimit+"|"+(targetAmount-tradeAmount)+"|"+buyLimit, width-305-(font.getWidth(""+sellLimit+">="+(targetAmount-tradeAmount)+">="+buyLimit))/2, index*64+128+17+(scrollIndex*64));
            
            }else
            {
                g.drawString("/"+sellLimit+"|"+(targetAmount-tradeAmount)+"|"+buyLimit, width-305-(font.getWidth(""+sellLimit+">="+(targetAmount-tradeAmount)+">="+buyLimit))/2, index*64+128+17+(scrollIndex*64));
            }
        }
        
        if(multiplier==1)
        {
            g.setColor(Color.white);
        }else if(multiplier<1)
        {
            g.setColor(Color.red);
        }else if(multiplier>1)
        {
            g.setColor(Color.green);
        }
        g.drawString(""+sellValue, width-835, index*64+128+17+(scrollIndex*64));
        
        if(buying)
        {
            g.setColor(Color.white);
        }else
        {
            g.setColor(Color.red);
        }
        
        
        g.drawString(""+(playerAmount+tradeAmount), width-940, index*64+128+17+(scrollIndex*64));
        sellAllButton.render(g, 0, scrollIndex*64);
        sellItemButton.render(g, 0, scrollIndex*64);
        buyAllButton.render(g, 0, scrollIndex*64);
        buyItemButton.render(g, 0, scrollIndex*64);
        if(tradeXTextField!=null)
        {
            g.setColor(Color.white);
            tradeXTextField.render(container, g);
        }else
        {
            tradeXButton.render(g, 0, scrollIndex*64);
        }
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world,int scrollIndex)
    {
        sellAllButton.tick(m, input, world, 0, scrollIndex*64, 99);
        sellItemButton.tick(m, input, world, 0, scrollIndex*64, 99);
        buyAllButton.tick(m, input, world, 0, scrollIndex*64, 99);
        buyItemButton.tick(m, input, world, 0, scrollIndex*64, 99);
        
        if(tradeXTextField!=null)
        {
            tradeXTextField.tick(k, m, input, world);
            if(m[17]||m[16])
            {
                tradeXTextField = null;
            }else if(m[19]&&!tradeXTextField.isHover())
            {
                tradeXTextField.processInput();
                tradeXTextField = null;
            }else if(k[Input.KEY_ENTER])
            {
                tradeXTextField.processInput();
                tradeXTextField = null;
            }
            
        }else
        {
            tradeXButton.tick(m, input, world, 0, scrollIndex*64, 99);
        }
        
        
    }
    
    
    
    public void setButtonDisplay()
    {
        
        if(buyLimit!=-1&&targetAmount-tradeAmount<=buyLimit&&tradeAmount>=0)
        {
            buyAllButton.setDisplay(false);
            buyItemButton.setDisplay(false);
        }
        else if(!selling&&-tradeAmount>0)
        {
            buyAllButton.setDisplay(true);
            buyItemButton.setDisplay(true);
        }else if(!selling)
        {
            buyAllButton.setDisplay(false);
            buyItemButton.setDisplay(false);
        }
        else if(tradeAmount>=targetAmount)
        {
            buyAllButton.setDisplay(false);
            buyItemButton.setDisplay(false);
        }else
        {
            buyAllButton.setDisplay(true);
            buyItemButton.setDisplay(true);
        }
        
        if(sellLimit!=-1&&targetAmount+(-tradeAmount)>=sellLimit&&tradeAmount<0)
        {
            sellAllButton.setDisplay(false);
            sellItemButton.setDisplay(false);
        }else if(!buying&&tradeAmount>0)
        {
            sellAllButton.setDisplay(true);
            sellItemButton.setDisplay(true);
        }else if(!buying)
        {
            sellAllButton.setDisplay(false);
            sellItemButton.setDisplay(false);
        }else if(-tradeAmount>=playerAmount)
        {
            sellAllButton.setDisplay(false);
            sellItemButton.setDisplay(false);
        }else
        {
            sellAllButton.setDisplay(true);
            sellItemButton.setDisplay(true);
        }
        
        
        
        
    }
    
    
    public void increaseTradeAmount()
    {
        if(buyLimit!=-1)
        {
            
            if(targetAmount-(tradeAmount)<=buyLimit&&tradeAmount>0)
            {
                return;
            }
        }
        
        tradeAmount++;
    }
    
    public void decreaseTradeAmount()
    {
        if(sellLimit!=-1)
        {
            
            if(targetAmount+(-tradeAmount)>=sellLimit&&tradeAmount<0)
            {
                return;
            }
        }
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
        
        if(!buying&&-tradeAmount>0)
        {
            this.tradeAmount = 0;
            tradingWindow.refreshTotalTradeValue();
            tradeXButton.setText(""+this.tradeAmount);
            return;
        }
        
        if(!selling&&tradeAmount>0)
        {
            this.tradeAmount = 0;
            tradingWindow.refreshTotalTradeValue();
            tradeXButton.setText(""+this.tradeAmount);
            return;
        }
        
        if(sellLimit!=-1&&tradeAmount!=0&&targetAmount+(-tradeAmount)>sellLimit)
        {
            this.tradeAmount = (targetAmount-sellLimit);
            tradeXButton.setText(""+(-this.tradeAmount));
            tradingWindow.refreshTotalTradeValue();
            return;
        }
        
        if(buyLimit!=-1&&tradeAmount!=0&&targetAmount-tradeAmount<buyLimit&&tradeAmount>0)
        {
            if(targetAmount>buyLimit)
            {
                this.tradeAmount = targetAmount-buyLimit;
                tradeXButton.setText(""+this.tradeAmount);
                tradingWindow.refreshTotalTradeValue();
                return;
            }else
            {
                this.tradeAmount = 0;
                tradeXButton.setText(""+this.tradeAmount);
                tradingWindow.refreshTotalTradeValue();
                return;
            }
        }
        
        if(-tradeAmount>playerAmount)
        {
            this.tradeAmount = -playerAmount;
        }else if(tradeAmount>targetAmount)
        {
            this.tradeAmount  = targetAmount;
        }else
        {
            this.tradeAmount = tradeAmount;
        }
        tradingWindow.refreshTotalTradeValue();
            
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
        
        setButtons();
        
        if(index%2==0)
        {
            bgColor = true;
        }else
        {
            bgColor = false;
        }
        setButtonDisplay();
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
    
    public Button getTradeXButton()
    {
        return tradeXButton;
    }

    public Button getBuyAllButton()
    {
        return buyAllButton;
    }

    public void setBuyAllButton(Button buyAllButton)
    {
        this.buyAllButton = buyAllButton;
    }

    public Button getBuyItemButton()
    {
        return buyItemButton;
    }

    public void setBuyItemButton(Button buyItemButton)
    {
        this.buyItemButton = buyItemButton;
    }

    public Button getSellAllButton()
    {
        return sellAllButton;
    }

    public void setSellAllButton(Button sellAllButton)
    {
        this.sellAllButton = sellAllButton;
    }

    public Button getSellItemButton()
    {
        return sellItemButton;
    }

    public void setSellItemButton(Button sellItemButton)
    {
        this.sellItemButton = sellItemButton;
    }

    public TradingWindow getTradingWindow()
    {
        return tradingWindow;
    }

    public void setTradingWindow(TradingWindow tradingWindow)
    {
        this.tradingWindow = tradingWindow;
    }

    public boolean isBuying()
    {
        return buying;
    }

    public void setBuying(boolean buying)
    {
        this.buying = buying;
    }

    public boolean isSelling()
    {
        return selling;
    }

    public void setSelling(boolean selling)
    {
        this.selling = selling;
    }

    public int getBuyLimit()
    {
        return buyLimit;
    }

    public void setBuyLimit(int buyLimit)
    {
        this.buyLimit = buyLimit;
    }

    public int getSellLimit()
    {
        return sellLimit;
    }

    public void setSellLimit(int sellLimit)
    {
        this.sellLimit = sellLimit;
    }

    public TradeXTextField getTradeXTextField()
    {
        return tradeXTextField;
    }

    public void setTradeXTextField(TradeXTextField tradeXTextField)
    {
        this.tradeXTextField = tradeXTextField;
    }

    public TradingBehavior getTradingBehavior()
    {
        return tradingBehavior;
    }

    public void setTradingBehavior(TradingBehavior tradingBehavior)
    {
        this.tradingBehavior = tradingBehavior;
    }

    public double getMultiplier()
    {
        return multiplier;
    }

    public void setMultiplier(double multiplier)
    {
        this.multiplier = multiplier;
    }

    public int getMasterType()
    {
        return masterType;
    }

    public void setMasterType(int masterType)
    {
        this.masterType = masterType;
    }
    
    
}
