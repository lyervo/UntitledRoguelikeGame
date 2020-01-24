/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trading;

import Culture.TradingBehavior;
import Entity.Pawn;
import Item.Item;
import UI.Button;
import World.World;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
    private ArrayList<TradeItem> coins;
    
    private int scrollIndex;
    
    private boolean display;
    
    private Button confirmTradeButton;
    private Button cancelTradeButton;
    private Button resetTradeButton;
    
    private Button sortNameButton;
    private Button sortBuyValueButton;
    private Button sortSellValueButton;
    private Button sortPlayerAmountButton;
    private Button sortPawnAmountButton;
    
    
    private Rectangle bounds;
    
    private int width,height;
    private int centerX;
    
    
    //how many trade items can the screen fits in
    private int maxDisplay;
    
    private TrueTypeFont font;
    
    private TradeXTextField tradeXTextField;
    
    //the total values of all the traded items
    //negative means the player is losing profit
    //the trader will not agree to trade if the number is not zero or less
    private double totalTradeValue;
    private GameContainer container;
    
    //remembering sort preference
    private Comparator lastComparator;
    private boolean lastReverse;
    
    private TradingBehavior tradingBehavior;
    
    private HashMap<Integer,TradeLimit> tradeLimits;
    
    
    public TradingWindow(GameContainer container,TrueTypeFont font)
    {
        tradeItems = new ArrayList<TradeItem>();
        coins = new ArrayList<TradeItem>();
        scrollIndex = 0;
        display = false;
        bounds = new Rectangle(0,0,container.getWidth(),container.getHeight());
        this.height = container.getHeight();
        this.width = container.getWidth();
        this.centerX = width/2;
        this.font = font;
        
        tradeLimits = new HashMap<Integer,TradeLimit>();
        
        this.maxDisplay = (height-256)/64;
        
        
        cancelTradeButton = new Button(centerX-155,height-80,100,30,"Leave",Color.black,Color.gray,Color.darkGray,font) {
            @Override
            public void onClick(boolean[] m, World world)
            {
                world.getTradingWindow().setDisplay(false);
                world.setHoveringWindow(false);
            }
        };
        confirmTradeButton = new Button(centerX-50,height-80,100,30,"Trade",Color.black,Color.gray,Color.darkGray,font) {
            @Override
            public void onClick(boolean[] m, World world)
            {
                world.getTradingWindow().trade(world);
            }
        };
        resetTradeButton = new Button(centerX+55,height-80,100,30,"Reset",Color.black,Color.gray,Color.darkGray,font) {
            @Override
            public void onClick(boolean[] m, World world)
            {
                world.getTradingWindow().resetTrade();
            }
        };
        
        
        sortNameButton = new Button(5,98,100,30,"Name",Color.black,Color.gray,Color.darkGray,font) {
            boolean buttonToggle = false;
            @Override
            public void onClick(boolean[] m, World world)
            {
                buttonToggle = !buttonToggle;
                world.getTradingWindow().sortTradeItems(world, new TradeItemNameComparator(), buttonToggle);
            }
        };
        
        sortBuyValueButton = new Button(width-135,98,130,30,"Buy Price",Color.black,Color.gray,Color.darkGray,font) {
            boolean buttonToggle = false;
            @Override
            public void onClick(boolean[] m, World world)
            {
                buttonToggle = !buttonToggle;
                world.getTradingWindow().sortTradeItems(world, new TradeItemBuyValueComparator(), buttonToggle);
            }
        };
        sortSellValueButton = new Button(width-870,98,130,30,"Sell Price",Color.black,Color.gray,Color.darkGray,font) {
            boolean buttonToggle = false;
            @Override
            public void onClick(boolean[] m, World world)
            {
                buttonToggle = !buttonToggle;
                world.getTradingWindow().sortTradeItems(world, new TradeItemSellValueComparator(), buttonToggle);
            }
        };
        sortPlayerAmountButton = new Button(width-980,98,100,30,"Owned",Color.black,Color.gray,Color.darkGray,font) {
            boolean buttonToggle = false;
            @Override
            public void onClick(boolean[] m, World world)
            {
                buttonToggle = !buttonToggle;
                world.getTradingWindow().sortTradeItems(world, new TradeItemPlayerAmountComparator(), buttonToggle);
            }
        };
        sortPawnAmountButton = new Button(width-345,98,100,30,"Trader",Color.black,Color.gray,Color.darkGray,font) {
            boolean buttonToggle = false;
            @Override
            public void onClick(boolean[] m, World world)
            {
                buttonToggle = !buttonToggle;
                world.getTradingWindow().sortTradeItems(world, new TradeItemPawnAmountComparator(), buttonToggle);
            }
        };
        
        
        this.container = container;
        totalTradeValue = 0;
        lastComparator = new TradeItemNameComparator();
        lastReverse = false;
        
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
        if(scrollIndex >= (tradeItems.size()+coins.size())-maxDisplay||(tradeItems.size()+coins.size())<=maxDisplay)
        {
            return;
        }
        scrollIndex++;
    }
    
    public void initTrade(Pawn player,Pawn target,World world)
    {
        this.player = player;
        this.target = target;
        
        tradingBehavior = null;
        
        tradingBehavior = world.getCm().getTradingBehaviorByJob(target.getJobTitle());
        
        
        totalTradeValue = 0;
        
        ArrayList<Item> playerItem = new ArrayList<Item>(world.getWm().getPlayerInventory().getItems());
        ArrayList<Item> pawnItem = new ArrayList<Item>(target.getInventory().getItems());
        tradeItems.clear();
        
        coins.clear();
        
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
                        if(pawnItem.get(j).isCurrency())
                        {
                            coins.add(new TradeItem(playerItem.get(i),pawnItem.get(j),world.getContainer(),world.getRes().disposableDroidBB,i,tradingBehavior,this));
                            pawnItem.remove(j);
                            found = true;
                        }
                        else
                        {
                            tradeItems.add(new TradeItem(playerItem.get(i),pawnItem.get(j),world.getContainer(),world.getRes().disposableDroidBB,i,tradingBehavior,this));
                            pawnItem.remove(j);
                            found = true;
                        }
                    }
                }
                if(!found)
                {
                    if(playerItem.get(i).isCurrency())
                    {
                        coins.add(new TradeItem(playerItem.get(i),null,world.getContainer(),world.getRes().disposableDroidBB,i,tradingBehavior,this));
                    }else
                    {
                        tradeItems.add(new TradeItem(playerItem.get(i),null,world.getContainer(),world.getRes().disposableDroidBB,i,tradingBehavior,this));
                    }
                }
                
                
            }else
            {
                tradeItems.add(new TradeItem(playerItem.get(i),null,world.getContainer(),world.getRes().disposableDroidBB,i,tradingBehavior,this));
            }
        }
        for(int i=0;i<pawnItem.size();i++)
        {
            if(pawnItem.get(i).isCurrency())
            {
                coins.add(new TradeItem(null,pawnItem.get(i),world.getContainer(),world.getRes().disposableDroidBB,i,tradingBehavior,this));
            }else
            {
                tradeItems.add(new TradeItem(null,pawnItem.get(i),world.getContainer(),world.getRes().disposableDroidBB,i+playerItem.size(),tradingBehavior,this));
            }
        }
        
        
        
        for(int i=0;i<tradingBehavior.getConditions().size();i++)
        {
            tradeLimits.put(tradingBehavior.getConditions().get(i).getType(),new TradeLimit(tradingBehavior.getConditions().get(i)));
        }
        
        
        
        scrollIndex = 0;
        
        
        tradeItems.sort(new TradeItemNameComparator());
            
       
        
        coins.sort(new TradeItemBuyValueComparator());
        
        for(int i=coins.size()-1;i>=0;i--)
        {
            coins.get(i).setIndex(i);
        }
        
        for(int i=0;i<tradeItems.size();i++)
        {
            tradeItems.get(i).setIndex(i+coins.size());
        }
        
        
        
    }
    
    public void updateTradeLimits()
    {
        for(int i=0;i<tradeItems.size();i++)
        {
            if(tradeLimits.get(tradeItems.get(i).getMasterType())!=null)
            {
                tradeLimits.get(tradeItems.get(i).getMasterType()).addCount(tradeItems.get(i).getTradeAmount());
            }
        }
    }
    
    public void refreshTradeItems(World world)
    {
        totalTradeValue = 0;
        
        ArrayList<Item> playerItem = new ArrayList<Item>(world.getWm().getPlayerInventory().getItems());
        ArrayList<Item> pawnItem = new ArrayList<Item>(target.getInventory().getItems());
        tradeItems.clear();
        
        coins = new ArrayList<TradeItem>();
        
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
                        if(pawnItem.get(j).isCurrency())
                        {
                            coins.add(new TradeItem(playerItem.get(i),pawnItem.get(j),world.getContainer(),world.getRes().disposableDroidBB,i,tradingBehavior,this));
                            pawnItem.remove(j);
                            found = true;
                        }
                        else
                        {
                            tradeItems.add(new TradeItem(playerItem.get(i),pawnItem.get(j),world.getContainer(),world.getRes().disposableDroidBB,i,tradingBehavior,this));
                            pawnItem.remove(j);
                            found = true;
                        }
                    }
                }
                if(!found)
                {
                    if(playerItem.get(i).isCurrency())
                    {
                        coins.add(new TradeItem(playerItem.get(i),null,world.getContainer(),world.getRes().disposableDroidBB,i,tradingBehavior,this));
                    }else
                    {
                        tradeItems.add(new TradeItem(playerItem.get(i),null,world.getContainer(),world.getRes().disposableDroidBB,i,tradingBehavior,this));
                    }
                }
                
                
            }else
            {
                tradeItems.add(new TradeItem(playerItem.get(i),null,world.getContainer(),world.getRes().disposableDroidBB,i,tradingBehavior,this));
            }
        }
        for(int i=0;i<pawnItem.size();i++)
        {
            if(pawnItem.get(i).isCurrency())
            {
                coins.add(new TradeItem(null,pawnItem.get(i),world.getContainer(),world.getRes().disposableDroidBB,i,tradingBehavior,this));
            }else
            {
                tradeItems.add(new TradeItem(null,pawnItem.get(i),world.getContainer(),world.getRes().disposableDroidBB,i+playerItem.size(),tradingBehavior,this));
            }
        }
        scrollIndex = 0;
        
        if(lastComparator!=null)
        {
            tradeItems.sort(lastComparator);
            if(lastReverse)
            {
                Collections.reverse(tradeItems);
            }
        }
        
        coins.sort(new TradeItemBuyValueComparator());
        
        for(int i=coins.size()-1;i>=0;i--)
        {
            coins.get(i).setIndex(i);
        }
        
        for(int i=0;i<tradeItems.size();i++)
        {
            tradeItems.get(i).setIndex(i+coins.size());
        }
        
        
    }
    
    public void sortTradeItems(World world,Comparator comparator,boolean reverse)
    {
        totalTradeValue = 0;
        if(comparator!=null)
        {
            tradeItems.sort(comparator);
            if(reverse)
            {
                Collections.reverse(tradeItems);
            }
            lastComparator = comparator;
            lastReverse = reverse;
        }
        
        coins.sort(new TradeItemBuyValueComparator());
        
        for(int i=coins.size()-1;i>=0;i--)
        {
            coins.get(i).setIndex(i);
        }
        
        for(int i=0;i<tradeItems.size();i++)
        {
            tradeItems.get(i).setIndex(i+coins.size());
        }
        
        
    }
    
    public void display()
    {
        display = true;
    }
    
    public void refreshTotalTradeValue()
    {
        totalTradeValue = 0;
        for(int i=0;i<coins.size();i++)
        {
            totalTradeValue += (coins.get(i).getTradeAmount()*coins.get(i).getBuyValue());
        }
        for(int i=0;i<tradeItems.size();i++)
        {
            
            if(tradeItems.get(i).getTradeAmount()>=0)
            {
                totalTradeValue += (tradeItems.get(i).getTradeAmount()*tradeItems.get(i).getBuyValue());
            }else
            {
                totalTradeValue += (tradeItems.get(i).getTradeAmount()*tradeItems.get(i).getSellValue());
            }
        }
        updateTradeLimits();
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
            g.drawString("Total Trade Value:"+String.format("%.2f", totalTradeValue), centerX-(font.getWidth("Total Trade Value:"+String.format("%.2f", totalTradeValue))/2), 70);
            
//            g.drawString(item.getInGameName(),70, index*64+128+17+(scrollIndex*64));
//        g.drawString(""+buyValue, centerX+260, index*64+128+17+(scrollIndex*64));
//        g.drawString(""+sellValue, centerX-360, index*64+128+17+(scrollIndex*64));
//        g.drawString(""+targetAmount, centerX+360, index*64+128+17+(scrollIndex*64));
//        g.drawString(""+playerAmount, centerX-460, index*64+128+17+(scrollIndex*64));
            
            
            

    //        confirmTradeButton.render(g);
            
            
    
            for(int i=0;i<tradeItems.size()+coins.size();i++)
            {
                if(i+1<=coins.size())
                {
                    if(i*64+128+(-scrollIndex*64)+64<=height-128&&i*64+128+(-scrollIndex*64)>=128)
                    {
                        coins.get(i).render(g, input,container, -scrollIndex);
                    }
                }else
                {
                    if(i*64+128+(-scrollIndex*64)+64<=height-128&&i*64+128+(-scrollIndex*64)>=128)
                    {
                        tradeItems.get(i-coins.size()).render(g, input,container, -scrollIndex);
                    }
                }
                
            }
            if(tradeXTextField!=null)
            {
                g.setColor(Color.white);
                tradeXTextField.render(container, g);
            }
            sortNameButton.render(g);
            sortBuyValueButton.render(g);
            cancelTradeButton.render(g);
            confirmTradeButton.render(g);
            resetTradeButton.render(g);
            sortSellValueButton.render(g);
            sortPlayerAmountButton.render(g);
            sortPawnAmountButton.render(g);
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
                tradeXTextField = null;
            }else if(m[17])
            {
                scrollDown();
                tradeXTextField = null;
            }
            
            if(tradeXTextField!=null)
            {
                tradeXTextField.tick(k, m, input, world);
            }
            
            
            cancelTradeButton.tick(m, input, world);
            confirmTradeButton.tick(m, input, world);
            resetTradeButton.tick(m, input, world);
            sortNameButton.tick(m, input, world);
            sortBuyValueButton.tick(m, input, world);
            sortSellValueButton.tick(m, input, world);
            sortPlayerAmountButton.tick(m, input, world);
            sortPawnAmountButton.tick(m, input, world);
            for(int i=0;i<tradeItems.size()+coins.size()-1;i++)
            {
                if(i+1<=coins.size())
                {
                    if(i*64+128+(-scrollIndex*64)+64<=height-128&&i*64+128+(-scrollIndex*64)>=128)
                    {
                        coins.get(i).tick(k, m, input, world, -scrollIndex);
                    }
                }else
                {
                    if(i*64+128+(-scrollIndex*64)+64<=height-128&&i*64+128+(-scrollIndex*64)>=128)
                    {
                        tradeItems.get(i).tick(k, m, input, world, -scrollIndex);
                    }
                }
                
            }
        }
    }
    
    public void trade(World world)
    {
        if(totalTradeValue>0)
        {
            return;
        }
        for(int i=0;i<coins.size();i++)
        {
            if(coins.get(i).getTradeAmount()>=1)
            {
                target.getInventory().transferItem(world.getWm().getPlayerInventory(), coins.get(i).getItem(), coins.get(i).getTradeAmount());
            }else if(coins.get(i).getTradeAmount()<=-1)
            {
                world.getWm().getPlayerInventory().transferItem(target.getInventory(), coins.get(i).getItem(), (-coins.get(i).getTradeAmount()));
            }
        }
        
        for(int i=0;i<tradeItems.size();i++)
        {
            if(tradeItems.get(i).getTradeAmount()>=1)
            {
                target.getInventory().transferItem(world.getWm().getPlayerInventory(), tradeItems.get(i).getItem(), tradeItems.get(i).getTradeAmount());
            }else if(tradeItems.get(i).getTradeAmount()<=-1)
            {
                world.getWm().getPlayerInventory().transferItem(target.getInventory(), tradeItems.get(i).getItem(), (-tradeItems.get(i).getTradeAmount()));
            }
        }
        totalTradeValue = 0;
        refreshTradeItems(world);
        world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
        
    }
    
    
    
    
    public void resetTrade()
    {
        for(int i=0;i<coins.size();i++)
        {
            coins.get(i).setTradeAmount(0);
            coins.get(i).setButtonDisplay();
        }
        for(int i=0;i<tradeItems.size();i++)
        {
            tradeItems.get(i).setTradeAmount(0);
            tradeItems.get(i).setButtonDisplay();
        }
        totalTradeValue = 0;
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


    public Rectangle getBounds()
    {
        return bounds;
    }

    public void setBounds(Rectangle bounds)
    {
        this.bounds = bounds;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getCenterX()
    {
        return centerX;
    }

    public void setCenterX(int centerX)
    {
        this.centerX = centerX;
    }

    public int getMaxDisplay()
    {
        return maxDisplay;
    }

    public void setMaxDisplay(int maxDisplay)
    {
        this.maxDisplay = maxDisplay;
    }

    public TrueTypeFont getFont()
    {
        return font;
    }

    public void setFont(TrueTypeFont font)
    {
        this.font = font;
    }

    public TradeXTextField getTradeXTextField()
    {
        return tradeXTextField;
    }

    public void setTradeXTextField(TradeXTextField tradeXTextField)
    {
        this.tradeXTextField = tradeXTextField;
    }

    public double getTotalTradeValue()
    {
        return totalTradeValue;
    }

    public void setTotalTradeValue(double totalTradeValue)
    {
        this.totalTradeValue = totalTradeValue;
    }

    public GameContainer getContainer()
    {
        return container;
    }

    public void setContainer(GameContainer container)
    {
        this.container = container;
    }

    public ArrayList<TradeItem> getCoins()
    {
        return coins;
    }

    public void setCoins(ArrayList<TradeItem> coins)
    {
        this.coins = coins;
    }

    public Button getConfirmTradeButton()
    {
        return confirmTradeButton;
    }

    public void setConfirmTradeButton(Button confirmTradeButton)
    {
        this.confirmTradeButton = confirmTradeButton;
    }

    public Button getCancelTradeButton()
    {
        return cancelTradeButton;
    }

    public void setCancelTradeButton(Button cancelTradeButton)
    {
        this.cancelTradeButton = cancelTradeButton;
    }

    public Button getResetTradeButton()
    {
        return resetTradeButton;
    }

    public void setResetTradeButton(Button resetTradeButton)
    {
        this.resetTradeButton = resetTradeButton;
    }

    public Button getSortNameButton()
    {
        return sortNameButton;
    }

    public void setSortNameButton(Button sortNameButton)
    {
        this.sortNameButton = sortNameButton;
    }

    public Button getSortBuyValueButton()
    {
        return sortBuyValueButton;
    }

    public void setSortBuyValueButton(Button sortBuyValueButton)
    {
        this.sortBuyValueButton = sortBuyValueButton;
    }

    public Button getSortSellValueButton()
    {
        return sortSellValueButton;
    }

    public void setSortSellValueButton(Button sortSellValueButton)
    {
        this.sortSellValueButton = sortSellValueButton;
    }

    public Button getSortPlayerAmountButton()
    {
        return sortPlayerAmountButton;
    }

    public void setSortPlayerAmountButton(Button sortPlayerAmountButton)
    {
        this.sortPlayerAmountButton = sortPlayerAmountButton;
    }

    public Button getSortPawnAmountButton()
    {
        return sortPawnAmountButton;
    }

    public void setSortPawnAmountButton(Button sortPawnAmountButton)
    {
        this.sortPawnAmountButton = sortPawnAmountButton;
    }

    public Comparator getLastComparator()
    {
        return lastComparator;
    }

    public void setLastComparator(Comparator lastComparator)
    {
        this.lastComparator = lastComparator;
    }

    public boolean isLastReverse()
    {
        return lastReverse;
    }

    public void setLastReverse(boolean lastReverse)
    {
        this.lastReverse = lastReverse;
    }

    public TradingBehavior getTradingBehavior()
    {
        return tradingBehavior;
    }

    public void setTradingBehavior(TradingBehavior tradingBehavior)
    {
        this.tradingBehavior = tradingBehavior;
    }

    public HashMap<Integer, TradeLimit> getTradeLimits()
    {
        return tradeLimits;
    }

    public void setTradeLimits(HashMap<Integer, TradeLimit> tradeLimits)
    {
        this.tradeLimits = tradeLimits;
    }
    
    
    
    
}
