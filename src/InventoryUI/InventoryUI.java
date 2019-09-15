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
public class InventoryUI 
{
    private Inventory player_inventory,interacting_inventory;
    
    
    //screen resolution
    private int width,height;
    //center the UI
    private int xofs,yofs;
    private boolean display;
    
    private Image bg1,bg2;
    
    private Image crafting_bg1,crafting_bg2;
    
    private ArrayList<ItemUI> primaryItemUI,secondaryItemUI;
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
    
    private boolean drag;
    
    private EquipmentUI playerEquipment,interactingEquipment;
    
    
    private QuickItemBarUI quickItemBarUI;
    
    private CraftingUI craftingUI;
    
    public InventoryUI(Inventory player_inventory,Res res,QuickItemBarUI quickItemBarUI,World world)
    {
        this.player_inventory = player_inventory;
        this.quickItemBarUI = quickItemBarUI;
        
        this.display = false;
        
        state = 1;
        scroll1 = 0;
        scroll2 = 0;
        this.res = res;
        this.bg1 = res.inventory_bg_1;
        this.bg2 = res.inventory_bg_2;
        
        primaryItemUI = new ArrayList<ItemUI>();
        secondaryItemUI = new ArrayList<ItemUI>();
        
        playerEquipment = new EquipmentUI(player_inventory.getEquipment(),res,this,6);
        
        
        for(int i=0;i<player_inventory.getItems().size();i++)
        {
           primaryItemUI.add(new ItemUI(player_inventory.getItems().get(i),i,state,res));
        }
        
        primaryBounds = new Rectangle(16,397,916,348);
        
        primaryUp = new InventoryPrimaryScrollUpButton(940,397,res.inventory_scroll_up,this);
        primaryDown = new InventoryPrimaryScrollDownButton(940,718,res.inventory_scroll_down,this);
        
        secondaryUp = new InventorySecondaryScrollUpButton(940,397,res.inventory_scroll_up,this);
        secondaryUp.setDisplay(false);
        secondaryDown = new InventorySecondaryScrollDownButton(940,718,res.inventory_scroll_down,this);
        secondaryDown.setDisplay(false);
        drag = false;
        
        
        crafting_bg1 = res.crafting_bg_1;
        crafting_bg2 = res.crafting_bg_2;
        
        craftingUI = new CraftingUI(player_inventory.getCrafting(),res,this,world.getItemLibrary());
        
    }
    
    public void render(Graphics g,Input input,int uiDisplay)
    {
        if(uiDisplay == 1)
        {
            if(state == 1)
            {
                bg1.draw(0,0);
            }else if(state == 2)
            {
                bg2.draw(0,0);
            }
        }else if(uiDisplay == 2)
        {
            if(state == 1)
            {
                crafting_bg1.draw(0,0);
            }else if(state == 2)
            {
                crafting_bg2.draw(0,0);
            }
        }
            
        for(ItemUI i:primaryItemUI)
        {
            i.render(g,input,state,scroll1,scroll2);
        }
        for(ItemUI i:secondaryItemUI)
        {
            i.render(g,input,4,scroll1,scroll2);
        }
        
        primaryUp.render(g);
        primaryDown.render(g);
        secondaryUp.render(g);
        secondaryDown.render(g);
        renderScrollBars(g);
        
        if(uiDisplay == 1)
        {
            playerEquipment.render(g, input);
            if(interactingEquipment!=null)
            {
                interactingEquipment.render(g, input);
            }
        }else if(uiDisplay == 2)
        {
            craftingUI.render(g,input);
        }
        
        if(itemOptionTab!=null)
        {
            itemOptionTab.render(g);
        }
        
        
        
        if(!drag)
        {
            for(ItemUI i:primaryItemUI)
            {
                if(i.isDesc_display())
                {
                    i.displayDesc(g, input);
                }
            }
            
            for(ItemUI i:secondaryItemUI)
            {
                if(i.isDesc_display())
                {
                    i.displayDesc(g, input);
                }
            }
            
        }else
        {
            for(ItemUI i:primaryItemUI)
            {
                if(i.isDrag())
                {
                    i.dragRender(g, input);
                }
            }

            for(ItemUI i:secondaryItemUI)
            {
                if(i.isDrag())
                {
                    i.dragRender(g, input);
                }
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
    

    public void tick(boolean[] k,boolean[] m,Input input,World world,int uiDisplay)
    {
        primaryUp.tick(m, input, world);
        primaryDown.tick(m, input, world);
        secondaryUp.tick(m, input, world);
        secondaryDown.tick(m, input, world);

        
        
        
        if(m[11])
        {
            if(primaryBounds.contains(new Point(input.getMouseX(),input.getMouseY())))
            {
                primaryScrollUp();
            }else if(secondaryBounds!=null)
            {
                if(secondaryBounds.contains(new Point(input.getMouseX(),input.getMouseY())))
                {
                    secondaryScrollUp();
                }
            }
        }else if(m[12])
        {
            if(primaryBounds.contains(new Point(input.getMouseX(),input.getMouseY())))
            {
                primaryScrollDown();
            }else if(secondaryBounds!=null)
            {
                if(secondaryBounds.contains(new Point(input.getMouseX(),input.getMouseY())))
                {
                    secondaryScrollDown();
                }
            }
        }
        
        if(uiDisplay==1)
        {
            playerEquipment.tick(k, m, input, world,this);

            if(interactingEquipment!=null)
            {
                interactingEquipment.tick(k, m, input, world,this);
            }
        }else if(uiDisplay==2)
        {
            craftingUI.tick(k, m, input, world);
        }
        
        for(int i=primaryItemUI.size()-1;i>=0;i--)
        {
            primaryItemUI.get(i).tick(k, m, input, world, state, scroll1,scroll2,this,null);
        }
        
        for(ItemUI i:secondaryItemUI)
        {
            i.tick(k, m, input, world, 4, scroll1, scroll2,this,null);
        }
        
        
        if(m[0]&&itemOptionTab!=null)
        {
            itemOptionTab.runOption(world.getWm().getCurrentLocalMap());
            itemOptionTab = null;
            refreshInventoryUI(world.getWm().getCurrentLocalMap());
        }else if(k[255])
        {
            itemOptionTab = null;
        }
        
        if(itemOptionTab!=null)
        {
            itemOptionTab.tick(k, m, input, world.getWm().getCurrentLocalMap());
        }
        
        if(m[0])
        {
            refreshInventoryUI(world.getWm().getCurrentLocalMap());
        }
        
        
        
    }
    
    public void spawnOptionTab(int x,int y,LocalMap lm,Item item,int index,int state,ItemLibrary itemLibrary)
    {
        if(state<=2)
        {
            itemOptionTab = new ItemOptionTab(x,y,lm,res.disposableDroidBB,res,this.player_inventory,this,item, index,state,itemLibrary);
        }else if(state==4)
        {
            itemOptionTab = new ItemOptionTab(x,y,lm,res.disposableDroidBB,res,this.interacting_inventory,this,item, index,state,itemLibrary);
        }else if(state==6)
        {
            itemOptionTab = new ItemOptionTab(x,y,lm,res.disposableDroidBB,res,this.player_inventory,this,item, index,state,itemLibrary);
        }
    }
    

    
    public void refreshInventoryUI(LocalMap lm)
    {
        refreshSecondaryInventoryUI(lm);
        refreshPrimaryInventoryUI();
        playerEquipment.refreshUI();
        craftingUI.refreshUI();
        if(interactingEquipment!=null)
        {
            interactingEquipment.refreshUI();
        }
        quickItemBarUI.refreshUI();

        
        if(state == 1)
        {
            primaryMaxScroll = (player_inventory.getItems().size()/13)+1-4;
            primary_height = 284/primaryMaxScroll;
            if(primaryItemUI.size()<=65)
            {
                scroll1 = 0;
            }else if(scroll1>(primaryItemUI.size()/13))
            {
                scroll1 = (primaryItemUI.size()/13)-4;
            }
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
        for(int i=0;i<player_inventory.getItems().size();i++)
        {
            
            primaryItemUI.add(new ItemUI(player_inventory.getItems().get(i),i,state,res));
        }
        
    }
    
    
    public void refreshSecondaryInventoryUI(LocalMap lm)
    {
        secondaryItemUI.clear();
        
        if(lm.getItemPileAt(lm.getPlayer().getX(), lm.getPlayer().getY())!=null)
        {
            
            state = 2;
            interacting_inventory =
                    new Inventory(lm.getItemPileAt(lm.getPlayer().getX(), lm.getPlayer().getY()),
                                  lm.getItemPileAt(lm.getPlayer().getX(), lm.getPlayer().getY()).getItems());
            
            for (int i = 0; i < interacting_inventory.getItems().size(); i++)
            {
                secondaryItemUI.add(new ItemUI(interacting_inventory.getItems().get(i), i, 4,res));
            }
            
            primaryBounds = new Rectangle(16,397,426,348);
            secondaryBounds = new Rectangle(514,397,426,348);
            
            primaryUp.setX(442);
            primaryDown.setX(442);
            secondaryUp.setDisplay(true);
            secondaryDown.setDisplay(true);
            
            
            
        }else
        {
            
            state = 1;
            primaryBounds = new Rectangle(16,397,916,348);
            secondaryBounds = null;
            primaryUp.setX(940);
            primaryDown.setX(940);
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
        
        if(state == 1)
        {
            
            if(primaryItemUI.size()<=65||4==(primaryItemUI.size()/13)-scroll1)
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

    public int getWidth() {
        return width;
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

    public ArrayList<ItemUI> getPrimaryItemUI() {
        return primaryItemUI;
    }

    public void setPrimaryItemUI(ArrayList<ItemUI> primaryItemUI) {
        this.primaryItemUI = primaryItemUI;
    }

    public ArrayList<ItemUI> getSecondaryItemUI() {
        return secondaryItemUI;
    }

    public void setSecondaryItemUI(ArrayList<ItemUI> secondaryItemUI) {
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

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }


    public QuickItemBarUI getQuickItemBarUI() {
        return quickItemBarUI;
    }

    public void setQuickItemBarUI(QuickItemBarUI quickItemBarUI) {
        this.quickItemBarUI = quickItemBarUI;
    }

    public EquipmentUI getPlayerEquipment() {
        return playerEquipment;
    }

    public void setPlayerEquipment(EquipmentUI playerEquipment) {
        this.playerEquipment = playerEquipment;
    }

    public EquipmentUI getInteractingEquipment() {
        return interactingEquipment;
    }

    public void setInteractingEquipment(EquipmentUI interactingEquipment) {
        this.interactingEquipment = interactingEquipment;
    }

    public Image getCrafting_bg1() {
        return crafting_bg1;
    }

    public void setCrafting_bg1(Image crafting_bg1) {
        this.crafting_bg1 = crafting_bg1;
    }

    public Image getCrafting_bg2() {
        return crafting_bg2;
    }

    public void setCrafting_bg2(Image crafting_bg2) {
        this.crafting_bg2 = crafting_bg2;
    }

    public CraftingUI getCraftingUI() {
        return craftingUI;
    }

    public void setCraftingUI(CraftingUI craftingUI) {
        this.craftingUI = craftingUI;
    }
    
    
    
    
}
