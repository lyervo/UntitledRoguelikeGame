/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import Item.Inventory;
import Item.Item;
import Item.ItemLibrary;
import Res.Res;
import UI.UIComponent;
import UI.UIWindow;
import World.LocalMap;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;

/**
 *
 * @author Timot
 */
public class InventoryUI extends UIComponent 
{
    private Inventory player_inventory,interacting_inventory;
    
    private Image bg1,bg2;
    private Image bg_bar;
    //screen resolution
    private int width,height;
    
    private int xofs,yofs;
    private boolean display;
    
    
    private ArrayList<InventoryItemUI> primaryItemUI,secondaryItemUI;
    private int scroll1,scroll2;
    
    
    private InventoryPrimaryScrollUpButton primaryUp;
    private InventoryPrimaryScrollDownButton primaryDown;
    
    private InventorySecondaryScrollUpButton secondaryUp;
    private InventorySecondaryScrollDownButton secondaryDown;
    
    
    private ItemOptionTab itemOptionTab;
    
    private Res res;
    
    private int state;
    
    private Rectangle primaryBounds,secondaryBounds;
    
    private int secondaryMaxScroll,primaryMaxScroll,primary_height,secondary_height;
    
    private boolean mode;
    
    
    
    public InventoryUI(int x,int y,int xofs,int yofs,Inventory player_inventory,Res res,World world,UIWindow window)
    {
        super(x,y,xofs,yofs,window);
        this.player_inventory = player_inventory;
        
        this.display = false;
        
        state = 1;
        scroll1 = 0;
        scroll2 = 0;
        this.res = res;
        this.bg1 = res.inventory_bg_1;
        this.bg2 = res.inventory_bg_2;
        this.bg_bar = res.inventory_bg_bar;
        primaryItemUI = new ArrayList<InventoryItemUI>();
        secondaryItemUI = new ArrayList<InventoryItemUI>();
        
        mode = false;
        
        for(int i=0;i<player_inventory.getItems().size();i++)
        {
           primaryItemUI.add(new InventoryItemUI(player_inventory.getItems().get(i),i,res,this,state));
        }
        
        primaryBounds = new Rectangle(21,32,628,348);
        
        primaryUp = new InventoryPrimaryScrollUpButton(655,32,xofs,yofs,res.inventory_scroll_up,this);
        primaryDown = new InventoryPrimaryScrollDownButton(655,352,xofs,yofs,res.inventory_scroll_down,this);
        
        secondaryUp = new InventorySecondaryScrollUpButton(655,32,res.inventory_scroll_up,this);
        secondaryUp.setDisplay(false);
        secondaryDown = new InventorySecondaryScrollDownButton(655,352,res.inventory_scroll_down,this);
        secondaryDown.setDisplay(false);
        
        
    }
    
    @Override
    public void render(Graphics g,Input input,int x,int y)
    {
        if(!mode)
        {
            if(state == 1)
            {
                bg1.draw(x,y);
            }else if(state == 2)
            {
                bg2.draw(x,y);
            }
        }else
        {
            bg_bar.draw(x,y);
        }
        
        
           
        for(ItemUI i:primaryItemUI)
        {
            i.render(g,input,x,y);
        }
        
        if(state==2)
        {
            for(ItemUI i:secondaryItemUI)
            {
                i.render(g,input,x,y);
            }
        }
        primaryUp.render(g,x,y);
        primaryDown.render(g,x,y);
        
        g.setColor(Color.green);
        g.drawRect(primaryBounds.x+x, primaryBounds.y+y, primaryBounds.width,primaryBounds.height);
        
        if(secondaryBounds!=null)
        {
            g.drawRect(secondaryBounds.x+x,secondaryBounds.y+y,secondaryBounds.width,secondaryBounds.height);
        }
        
        secondaryUp.render(g,x,y);
        secondaryDown.render(g,x,y);

        
        if(itemOptionTab!=null)
        {
            itemOptionTab.render(g);
        }
//        
//        
//        
//        if(!drag)
//        {
//            for(ItemUI i:primaryItemUI)
//            {
//                if(i.isDisplay())
//                {
//                    i.renderDesc(g, input);
//                }
//            }
//            
//            for(ItemUI i:secondaryItemUI)
//            {
//                if(i.isDisplay())
//                {
//                    i.renderDesc(g, input);
//                }
//            }
//            
//        }else
//        {
//            for(ItemUI i:primaryItemUI)
//            {
//                if(i.isDrag())
//                {
//                    i.dragRender(g, input);
//                }
//            }
//
//            for(ItemUI i:secondaryItemUI)
//            {
//                if(i.isDrag())
//                {
//                    i.dragRender(g, input);
//                }
//            }
//        }
        

    }
    
    @Override
    public void checkDrop(boolean[] k, boolean[] m, Input input, World world)
    {
        for(int i=primaryItemUI.size()-1;i>=0;i--)
        {
            if(primaryItemUI.get(i).isDrag())
            {
                drag = false;
                world.setDrag(false);
                primaryItemUI.get(i).setDrag(false);
                primaryItemUI.get(i).checkDrop(input, world, x, y);
                
                
            }
        }
        
        for(int i=secondaryItemUI.size()-1;i>=0;i--)
        {
            if(secondaryItemUI.get(i).isDrag())
            {
                drag = false;
                world.setDrag(false);
                secondaryItemUI.get(i).setDrag(false);
                secondaryItemUI.get(i).checkDrop(input, world, x, y);
                
                
            }
        }
    }
    
    
    public void renderScrollBars(Graphics g)
    {
        
        if(state == 1&&player_inventory.getItems().size()>65)
        {
            g.setColor(Color.decode("#757161"));
            
            
            g.fillRect(940, 431+(scroll1*primary_height), 32, primary_height);
            g.setColor(Color.black);
            g.drawRect(940,431+(scroll1*primary_height), 32, primary_height);
        }else if(state == 2)
        {
            
            if(player_inventory.getItems().size()>30)
            {
                g.setColor(Color.decode("#757161"));

                
                g.fillRect(442, 431+(scroll1*primary_height), 32, primary_height);
                g.setColor(Color.black);
                g.drawRect(442,431+(scroll1*primary_height), 32, primary_height);
            }
            if(interacting_inventory.getItems().size()>30)
            {
                g.setColor(Color.decode("#757161"));

                
                g.fillRect(940, 431+(scroll2*secondary_height), 32, secondary_height);
                g.setColor(Color.black);
                g.drawRect(940,431+(scroll2*secondary_height), 32, secondary_height);
        
            }
        }
    }
    

    @Override
    public void tick(boolean[] k,boolean[] m,Input input,World world,int x,int y,UIWindow window)
    {
        if(window.getZ()==world.getZ())
        {
            primaryUp.tick(m, input, world,x,y);
            primaryDown.tick(m, input, world,x,y);
            secondaryUp.tick(m, input, world,x,y);
            secondaryDown.tick(m, input, world,x,y);


        
        
            if(m[16])
            {
                if(primaryBounds.contains(new Point(input.getMouseX()-x,input.getMouseY()-y)))
                {
                    primaryScrollUp();
                }else if(secondaryBounds!=null)
                {
                    if(secondaryBounds.contains(new Point(input.getMouseX()-x,input.getMouseY()-y)))
                    {
                        secondaryScrollUp();
                    }
                }
            }else if(m[17])
            {
                if(primaryBounds.contains(new Point(input.getMouseX()-x,input.getMouseY()-y)))
                {
                    primaryScrollDown();
                }else if(secondaryBounds!=null)
                {
                    if(secondaryBounds.contains(new Point(input.getMouseX()-x,input.getMouseY()-y)))
                    {
                        secondaryScrollDown();
                    }
                }
            }

            for(int i=primaryItemUI.size()-1;i>=0;i--)
            {
                primaryItemUI.get(i).tick(k, m, input, world,x,y);
            }
            
            if(!mode)
            {
                for(int i=secondaryItemUI.size()-1;i>=0;i--)
                {
                    secondaryItemUI.get(i).tick(k, m, input, world,x,y);
                }
            }
        }
        
//        
//        
//        if(m[0]&&itemOptionTab!=null)
//        {
//            itemOptionTab.runOption(world.getWm().getCurrentLocalMap());
//            itemOptionTab = null;
//            refreshInventoryUI(world.getWm().getCurrentLocalMap());
//        }else if(k[255])
//        {
//            itemOptionTab = null;
//        }
//        
//        if(itemOptionTab!=null)
//        {
//            itemOptionTab.tick(k, m, input, world.getWm().getCurrentLocalMap());
//        }
//        
//        if(m[0])
//        {
//            refreshInventoryUI(world.getWm().getCurrentLocalMap());
//        }

        
    }
    
    @Override
    public void dragRender(Graphics g, Input input)
    {
        for(ItemUI i:primaryItemUI)
        {
            if(i.isDrag())
            {
                i.dragRender(g, input);
                return;
            }
        }
        
        if(!mode)
        {
            for(ItemUI i:secondaryItemUI)
            {
                if(i.isDrag())
                {
                    i.dragRender(g, input);
                    return;
                }
            }
        }

           
    }

    
    public void spawnOptionTab(int x,int y,LocalMap lm,Item item,int index,int state,ItemLibrary itemLibrary)
    {

    }
    
    public void setMode(World world)
    {
        if(mode)
        {
            mode = false;
           
        }else
        {
            mode = true;
            
        }
        refreshInventoryUI(world.getWm().getCurrentLocalMap());
    }
    

    
    public void refreshInventoryUI(LocalMap lm)
    {
        
        refreshSecondaryInventoryUI(lm);
        
        refreshPrimaryInventoryUI();
        
        
        if(state == 1&&!mode)
        {
            primaryMaxScroll = (player_inventory.getItems().size()/9)+1-4;
            primary_height = 284/primaryMaxScroll;
            if(primaryItemUI.size()<=45)
            {
                scroll1 = 0;
            }else if(scroll1>(primaryItemUI.size()/8))
            {
                scroll1 = (primaryItemUI.size()/8)-4;
            }
        }else if(mode)
        {
            primaryMaxScroll = (player_inventory.getItems().size()/9)+1;
            primary_height = 284/primaryMaxScroll;
            scroll1 = 0;
            
        }else if(state == 2)
        {
            primaryMaxScroll = (player_inventory.getItems().size()/6)+1-4;
                
            if(primaryMaxScroll>0)
            {
                primary_height = 284/primaryMaxScroll;
            
                if(primaryItemUI.size()<=30)
                {
                    scroll1 = 0;
                }else if(scroll1>(primaryItemUI.size()/6)-4)
                {
                    scroll1 = (primaryItemUI.size()/6)-4;
                }
            }

            
            
            secondaryMaxScroll = (interacting_inventory.getItems().size()/6)+1-4;
            
            
            
            if(secondaryMaxScroll>0)
            {
                secondary_height = 284/secondaryMaxScroll;
                
                if(secondaryItemUI.size()<=30)
                {
                    scroll2 = 0;
                }else if(scroll2>(secondaryItemUI.size()/6)-4)
                {
                    scroll2 = (secondaryItemUI.size()/6)-4;
                }
            }
            
            
        }
        
    }
    
    public void refreshPrimaryInventoryUI()
    {
        
        primaryItemUI.clear();
        if(!mode)
        {
            for(int i=0;i<player_inventory.getItems().size();i++)
            {

                primaryItemUI.add(new InventoryItemUI(player_inventory.getItems().get(i),i,res,this,state));
            }
        }else
        {
            for(int i=0;i<player_inventory.getItems().size();i++)
            {

                primaryItemUI.add(new InventoryItemUI(player_inventory.getItems().get(i),i,res,this,1));
            }
        }
        
    }
    
    
    public void refreshSecondaryInventoryUI(LocalMap lm)
    {
        secondaryItemUI.clear();
        
        if(!mode)
        {
            if(lm.getItemPileAt(lm.getPlayer().getX(), lm.getPlayer().getY())!=null)
            {

                state = 2;
                interacting_inventory =
                        new Inventory(lm.getItemPileAt(lm.getPlayer().getX(), lm.getPlayer().getY()),
                                      lm.getItemPileAt(lm.getPlayer().getX(), lm.getPlayer().getY()).getItems());

                for (int i = 0; i < interacting_inventory.getItems().size(); i++)
                {
                    secondaryItemUI.add(new InventoryItemUI(interacting_inventory.getItems().get(i),i,res,this,4));
                }

                primaryBounds = new Rectangle(16,32,277,348);
                secondaryBounds = new Rectangle(371,32,277,348);

                primaryUp.setX(300);
                primaryDown.setX(300);
                primaryDown.setY(352);
                secondaryUp.setDisplay(true);
                secondaryDown.setDisplay(true);



            }else
            {

                state = 1;
                primaryBounds = new Rectangle(21,32,628,348);
                secondaryBounds = null;
                primaryUp.setX(655);
                primaryDown.setX(655);
                primaryDown.setY(352);
                secondaryUp.setDisplay(false);
                secondaryDown.setDisplay(false);    
            }
        }else
        {
            primaryBounds = new Rectangle(21,32,628,64);
            secondaryBounds = null;
            primaryUp.setX(655);
            primaryDown.setX(655);
            primaryDown.setY(64);
            secondaryUp.setDisplay(false);
            secondaryDown.setDisplay(false);  
        }
    }
    
    public void primaryScrollUp()
    {
        if(scroll1 == 0)
        {
            return;
        }else
        {
            scroll1--;
        }
    }
    
    public void primaryScrollDown()
    {
        
        if(state == 1&&!mode)
        {
            
            if(primaryItemUI.size()<=45||4==(primaryItemUI.size()/9)-scroll1)
            {

                return;
            }else
            {

                scroll1++;
            }
        }else if(mode)
        {
            
            if(primaryItemUI.size()<=9||0==(primaryItemUI.size()/9)-scroll1)
            {

                return;
            }else
            {

                scroll1++;
            }
        }else
        {
            if(primaryItemUI.size()<=30||4==(primaryItemUI.size()/6)-scroll1)
            {

                return;
            }else
            {

                scroll1++;
            }
        }
        
    }
    
    public void secondaryScrollUp()
    {
        if(scroll2 == 0)
        {
            return;
        }else
        {
            scroll2--;
        }
    }
    
    public void secondaryScrollDown()
    {
        if(secondaryItemUI.size()<=30||4==(secondaryItemUI.size()/6)-scroll2)
        {
            return;
        }else
        {
 
            scroll2++;
        }
    }

    public Inventory getPlayer_inventory() {
        return player_inventory;
    }

    public void setPlayer_inventory(Inventory player_inventory) {
        this.player_inventory = player_inventory;
    }

    public Inventory getInteracting_inventory() {
        return interacting_inventory;
    }

    public void setInteracting_inventory(Inventory interacting_inventory) {
        this.interacting_inventory = interacting_inventory;
    }

    public int getUIWidth() {
        if(mode)
        {
            return bg_bar.getWidth();
        }else
        {
            return bg1.getWidth();
        }
    }
    
    public int getUIHeight()
    {
        if(mode)
        {
            return bg_bar.getHeight();
        }else
        {
            return bg1.getHeight();
        }
    }

    public Image getBg1() {
        return bg1;
    }

    public void setBg1(Image bg1) {
        this.bg1 = bg1;
    }

    public Image getBg2() {
        return bg2;
    }

    public void setBg2(Image bg2) {
        this.bg2 = bg2;
    }

    public Image getBg_bar() {
        return bg_bar;
    }

    public void setBg_bar(Image bg_bar) {
        this.bg_bar = bg_bar;
    }

    public boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }
    
    

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getXofs() {
        return xofs;
    }

    public void setXofs(int xofs) {
        this.xofs = xofs;
    }

    public int getYofs() {
        return yofs;
    }

    public void setYofs(int yofs) {
        this.yofs = yofs;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public ArrayList<InventoryItemUI> getPrimaryItemUI() {
        return primaryItemUI;
    }

    public void setPrimaryItemUI(ArrayList<InventoryItemUI> primaryItemUI) {
        this.primaryItemUI = primaryItemUI;
    }

    public ArrayList<InventoryItemUI> getSecondaryItemUI() {
        return secondaryItemUI;
    }

    public void setSecondaryItemUI(ArrayList<InventoryItemUI> secondaryItemUI) {
        this.secondaryItemUI = secondaryItemUI;
    }

    public int getScroll1() {
        return scroll1;
    }

    public void setScroll1(int scroll1) {
        this.scroll1 = scroll1;
    }

    public int getScroll2() {
        return scroll2;
    }

    public void setScroll2(int scroll2) {
        this.scroll2 = scroll2;
    }

    public InventoryPrimaryScrollUpButton getPrimaryUp() {
        return primaryUp;
    }

    public void setPrimaryUp(InventoryPrimaryScrollUpButton primaryUp) {
        this.primaryUp = primaryUp;
    }

    public InventoryPrimaryScrollDownButton getPrimaryDown() {
        return primaryDown;
    }

    public void setPrimaryDown(InventoryPrimaryScrollDownButton primaryDown) {
        this.primaryDown = primaryDown;
    }

    public InventorySecondaryScrollUpButton getSecondaryUp() {
        return secondaryUp;
    }

    public void setSecondaryUp(InventorySecondaryScrollUpButton secondaryUp) {
        this.secondaryUp = secondaryUp;
    }

    public InventorySecondaryScrollDownButton getSecondaryDown() {
        return secondaryDown;
    }

    public void setSecondaryDown(InventorySecondaryScrollDownButton secondaryDown) {
        this.secondaryDown = secondaryDown;
    }

    public ItemOptionTab getItemOptionTab() {
        return itemOptionTab;
    }

    public void setItemOptionTab(ItemOptionTab itemOptionTab) {
        this.itemOptionTab = itemOptionTab;
    }

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Rectangle getPrimaryBounds() {
        return primaryBounds;
    }

    public void setPrimaryBounds(Rectangle primaryBounds) {
        this.primaryBounds = primaryBounds;
    }

    public Rectangle getSecondaryBounds() {
        return secondaryBounds;
    }

    public void setSecondaryBounds(Rectangle secondaryBounds) {
        this.secondaryBounds = secondaryBounds;
    }

    public int getSecondaryMaxScroll() {
        return secondaryMaxScroll;
    }

    public void setSecondaryMaxScroll(int secondaryMaxScroll) {
        this.secondaryMaxScroll = secondaryMaxScroll;
    }

    public int getPrimaryMaxScroll() {
        return primaryMaxScroll;
    }

    public void setPrimaryMaxScroll(int primaryMaxScroll) {
        this.primaryMaxScroll = primaryMaxScroll;
    }

    public int getPrimary_height() {
        return primary_height;
    }

    public void setPrimary_height(int primary_height) {
        this.primary_height = primary_height;
    }

    public int getSecondary_height() {
        return secondary_height;
    }

    public void setSecondary_height(int secondary_height) {
        this.secondary_height = secondary_height;
    }




    
}
