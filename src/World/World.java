/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

import Camera.Camera;
import Entity.EntityLibrary;
import InventoryUI.CraftingButton;
import Item.ItemLibrary;
import Narrator.Narrator;
import Res.Res;
import InventoryUI.InventoryButton;
import InventoryUI.InventoryUI;
import InventoryUI.QuickItemBarUI;
import InventoryUI.XItemTextField;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;

/**
 *
 * @author Timot
 */
public class World
{
    public WorldMap wm;
    
    
    
    //tells the rest of the entities that whether player has made a move
    public boolean moved;
    
    private Res res;
    
    private int turn;
    
    private GameContainer container;
    private Narrator ancestor;
    private double scale;
    private Input input;
    
    private InventoryButton inventoryButton;
    private CraftingButton craftingButton;
    
    public Camera cam;
    private InventoryUI inventory_ui;
    
    private boolean mapTick;
    
    private int uiDisplay;
    
    private int mouse_z;
    
    private ItemLibrary itemLibrary;
    private EntityLibrary entityLibrary;
    
    
    private QuickItemBarUI quickItemBarUI;
    private boolean quickItemBarUIDisplay;

    
    private XItemTextField xItemTextField;
    private boolean xItemTextFieldActive;
    
    
    public World(Res res,GameContainer container,Input input)
    {
        
        itemLibrary = new ItemLibrary("test",res);
        entityLibrary = new EntityLibrary(res);
        
        mouse_z = 0;
        this.res = res;
        wm = new WorldMap(res,this,container);
        moved = false;
        turn = 0;
        this.container = container;
        this.input = input;
        
        this.scale = (double)container.getHeight()/768;
        
        ancestor = new Narrator(container,res.disposableDroidBB);
        cam = new Camera(container.getWidth(),container.getHeight(),100,100,scale);
        
        xItemTextFieldActive = false;
        
        xItemTextField = new XItemTextField(container,res.disposableDroidBB,496-((res.disposableDroidBB.getWidth("00000")+5)/2),384-(res.disposableDroidBB.getHeight()/2),res.disposableDroidBB.getWidth("00000")+5,res.disposableDroidBB.getHeight(),res);
        
        inventoryButton = new InventoryButton(994,100,res.inventory_icon);
        craftingButton = new CraftingButton(1078,100,res.crafting_icon);
        
        quickItemBarUI = new QuickItemBarUI(res,wm.getPlayerInventory(),null,64,640);
        quickItemBarUIDisplay = true;
        
        inventory_ui = new InventoryUI(wm.getPlayerInventory(),res,quickItemBarUI,this);
        
        quickItemBarUI.setInventoryUI(inventory_ui);
        
        uiDisplay = 0;
        
        
        
        ancestor.addText("Ruin has come to our family. You remember our venerable house,"
                + " opulent and imperial; gazing proudly from its stoic perch above the moor."
                + " I lived all my years in that ancient, rumour-shadowed manor. Fattened by"
                + " decadence and luxury... and yet, I began to tire of... conventional extravagance.");
        
        mapTick = false;
       
        
        
        
//        window1 = new Window(100,100,100,100);
        
    }
    
    public void tick(boolean[] k,boolean[] m,Input input)
    {
     
        
        
        if(k[Input.KEY_I])
        {
            deactivateXItemTextField();
            if(uiDisplay!=1)
            {
                uiDisplay = 1;
                inventory_ui.refreshInventoryUI(wm.getCurrentLocalMap());
            }else
            {
                uiDisplay = 0;
            }
        }else if(k[Input.KEY_TAB]&&uiDisplay == 0)
        {
            deactivateXItemTextField();
            quickItemBarUIDisplay = !quickItemBarUIDisplay;
        }
        
        
        
        if(xItemTextFieldActive)
        {
            xItemTextField.tick(k,m, input, this,inventory_ui,quickItemBarUI);
        }
        
        inventoryButton.tick(m, input, this);
        craftingButton.tick(m, input, this);
        
        switch(uiDisplay)
        {
            case 0:
                if(quickItemBarUIDisplay)
                {
                    quickItemBarUI.tick(k, m, input, this);
                }
                break;
            case 1:
                inventory_ui.tick(k,m, input, this,uiDisplay);
                break;
            case 2:
                inventory_ui.tick(k, m, input, this,uiDisplay);
                break;
        }
        
        wm.tick(k,m,input,this);
        
        if(moved)
        {
           turn++;
           moved = false;
        }
        
    }
    
    public void render(Graphics g)
    {
        inventoryButton.render(g);
        craftingButton.render(g);
        ancestor.render(g,cam);
        switch(uiDisplay)
        {
            case 0:
                wm.render(g);
                if(quickItemBarUIDisplay)
                {
                    quickItemBarUI.render(g, input);
                }
                
                break;
            
            case 1:
                inventory_ui.render(g,input,uiDisplay);
                break;
            case 2:
                inventory_ui.render(g, input,uiDisplay);
                break;
                
        }
        
        if(wm.getPlayer()!=null)
        {
            g.setColor(Color.red);
            g.fillRect(1018, 50, (int)wm.getPlayer().getHp().getValue(), 50);
        }
        
        if(xItemTextFieldActive)
        {
            xItemTextField.renderBackground(g);
            g.setColor(Color.white);
            xItemTextField.render(container, g);
        }

        
    }
    
    public void activateXItemTextField(int index,int state)
    {
        xItemTextFieldActive = true;
        xItemTextField.setIndex(index);
        xItemTextField.setState(state);
    }
    
    public void deactivateXItemTextField()
    {
        xItemTextFieldActive = false;
        xItemTextField.setText("");
    }
    
    public void setUIDisplay(int ui)
    {
        if(uiDisplay==2)
        {
            inventory_ui.getCraftingUI().getCrafting().clearAllIngridient();
        }
        
        if(uiDisplay!=ui)
        {
            uiDisplay = ui;
        }else
        {
            uiDisplay = 0;
        }
        
        
        inventory_ui.refreshInventoryUI(wm.getCurrentLocalMap());
        
    }
    
    public void callMapTick()
    {
        //ask the world to tick the map once
        mapTick = true;
    }
    
    public void moved()
    {
        moved = true;
    }

    public WorldMap getWm() {
        return wm;
    }

    public void setWm(WorldMap wm) {
        this.wm = wm;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public Res getRes() {
        return res;
    }

    public void setRes(Res res) {
        this.res = res;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public GameContainer getContainer() {
        return container;
    }

    public void setContainer(GameContainer container) {
        this.container = container;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public Narrator getAncestor() {
        return ancestor;
    }

    public void setAncestor(Narrator ancestor) {
        this.ancestor = ancestor;
    }

    public InventoryButton getInventoryButton() {
        return inventoryButton;
    }

    public void setInventoryButton(InventoryButton inventoryButton) {
        this.inventoryButton = inventoryButton;
    }

    public Camera getCam() {
        return cam;
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }

    public int getMouse_z() {
        return mouse_z;
    }

    public void setMouse_z(int mouse_z) {
        this.mouse_z = mouse_z;
    }

    public InventoryUI getInventory_ui() {
        return inventory_ui;
    }

    public void setInventory_ui(InventoryUI inventory_ui) {
        this.inventory_ui = inventory_ui;
    }

    public int getUiDisplay() {
        return uiDisplay;
    }

    public void setUiDisplay(int uiDisplay) {
        this.uiDisplay = uiDisplay;
    }

    public boolean isMapTick() {
        return mapTick;
    }

    public void setMapTick(boolean mapTick) {
        this.mapTick = mapTick;
    }

    public ItemLibrary getItemLibrary() {
        return itemLibrary;
    }

    public void setItemLibrary(ItemLibrary itemLibrary) {
        this.itemLibrary = itemLibrary;
    }

    public QuickItemBarUI getQuickItemBarUI() {
        return quickItemBarUI;
    }

    public void setQuickItemBarUI(QuickItemBarUI quickItemBarUI) {
        this.quickItemBarUI = quickItemBarUI;
    }

    public boolean isQuickItemBarUIDisplay() {
        return quickItemBarUIDisplay;
    }

    public void setQuickItemBarUIDisplay(boolean quickItemBarUIDisplay) {
        this.quickItemBarUIDisplay = quickItemBarUIDisplay;
    }

    public XItemTextField getxItemTextField() {
        return xItemTextField;
    }

    public void setxItemTextField(XItemTextField xItemTextField) {
        this.xItemTextField = xItemTextField;
    }

    public boolean isxItemTextFieldActive() {
        return xItemTextFieldActive;
    }

    public void setxItemTextFieldActive(boolean xItemTextFieldActive) {
        this.xItemTextFieldActive = xItemTextFieldActive;
    }

    public CraftingButton getCraftingButton() {
        return craftingButton;
    }

    public void setCraftingButton(CraftingButton craftingButton) {
        this.craftingButton = craftingButton;
    }

    public EntityLibrary getEntityLibrary() {
        return entityLibrary;
    }

    public void setEntityLibrary(EntityLibrary entityLibrary) {
        this.entityLibrary = entityLibrary;
    }
    
    
    
    
}
