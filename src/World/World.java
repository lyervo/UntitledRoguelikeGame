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
import InventoryUI.InventoryUIWindow;
import InventoryUI.QuickItemBarUI;
import InventoryUI.XItemTextField;
import UI.UIComponent;
import UI.UIWindow;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class World
{
    public WorldMap wm;
    
    private boolean hoveringWindow;
    
    //tells the rest of the entities that whether player has made a move
    public boolean moved;
    
    private Res res;
    
    private int turn;
    
    private GameContainer container;
    private Narrator ancestor;
    private double scale;
    private Input input;
    
    public Camera cam;
    
    private boolean mapTick;
    
    private int uiDisplay;
    
    private int mouse_z;
    
    private ItemLibrary itemLibrary;
    private EntityLibrary entityLibrary;
    
    
    private QuickItemBarUI quickItemBarUI;
    private boolean quickItemBarUIDisplay;

    
    private XItemTextField xItemTextField;
    private boolean xItemTextFieldActive;
    
    
    private ArrayList<UIWindow> uis;
    
    private InventoryUIWindow inventoryWindow;
    private InventoryUI inventory_ui;
    
    private boolean drag;
    
    private InventoryButton inventoryButton;
    
    private int z;
    
    
    public World(Res res,GameContainer container,Input input)
    {
        this.z = 0;
        hoveringWindow = false;
        
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
        cam = new Camera(100,100,container);
        
        xItemTextFieldActive = false;
        
        xItemTextField = new XItemTextField(container,res.disposableDroidBB,496-((res.disposableDroidBB.getWidth("00000")+5)/2),384-(res.disposableDroidBB.getHeight()/2),res.disposableDroidBB.getWidth("00000")+5,res.disposableDroidBB.getHeight(),res);

        
        inventory_ui = new InventoryUI(100,100,0,0,wm.getPlayerInventory(),res,quickItemBarUI,this,inventoryWindow);
        
        
        uiDisplay = 0;
        
        drag = false;
        
        
        ancestor.addText("Ruin has come to our family. You remember our venerable house,"
                + " opulent and imperial; gazing proudly from its stoic perch above the moor."
                + " I lived all my years in that ancient, rumour-shadowed manor. Fattened by"
                + " decadence and luxury... and yet, I began to tire of... conventional extravagance.");
        
        mapTick = false;
       
        inventoryWindow = new InventoryUIWindow(100,100,"Inventoy",inventory_ui,res.disposableDroidBB40f,res);
        
        uis = new ArrayList<UIWindow>();
        
        uis.add(inventoryWindow);
        
        for(int i=0;i<uis.size();i++)
        {
            uis.get(i).setZ(i+1);
        }
        
        inventoryButton = new InventoryButton(10,container.getHeight()-64,res.inventory_icon);
        
        
        
    }
    
    public void tick(boolean[] k,boolean[] m,Input input)
    {
        
        hoveringWindow = false;
        inventoryWindow.tick(k, m, input, this);
        
        if(!hoveringWindow)
        {
            z = 0;
        }
        
        inventoryButton.tick(m, input, this);
        
        if(k[Input.KEY_I])
        {
            deactivateXItemTextField();
            inventoryWindow.setDisplay();
        }else if(k[Input.KEY_TAB]&&uiDisplay == 0)
        {
//            deactivateXItemTextField();
//            quickItemBarUIDisplay = !quickItemBarUIDisplay;
        }
        
        
        
        if(xItemTextFieldActive)
        {
//            xItemTextField.tick(k,m, input, this,inventory_ui,quickItemBarUI);
        }
        

        
        switch(uiDisplay)
        {
            case 0:
                if(quickItemBarUIDisplay)
                {
//                    quickItemBarUI.tick(k, m, input, this);
                }
                break;
//            case 1:
//                inventory_ui.tick(k,m, input, this,uiDisplay);
//                break;
//            case 2:
//                inventory_ui.tick(k, m, input, this,uiDisplay);
//                break;
        }
        
        wm.tick(k,m,input,this);
        
        if(moved)
        {
           turn++;
           moved = false;
        }
        
    }
    
    public void render(Graphics g,boolean animate)
    {

        inventoryButton.render(g);
        ancestor.render(g,cam);
        switch(uiDisplay)
        {
            case 0:
                wm.render(g, animate);
//                if(quickItemBarUIDisplay)
//                {
//                    quickItemBarUI.render(g, input);
//                }
                
                break;
            
//            case 1:
//                inventory_ui.render(g,input,uiDisplay);
//                break;
//            case 2:
//                inventory_ui.render(g, input,uiDisplay);
//                break;
                
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
        
        inventoryWindow.render(g, input);
        
        if(inventoryWindow.isDrag())
        {
            inventoryWindow.dragRender(g, input);
        }
        
        for(UIWindow ui:uis)
        {
            if(ui.getUiComponent().isDrag())
            {
                ui.getUiComponent().dragRender(g,input);
            }
            
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
//            inventory_ui.getCraftingUI().getCrafting().clearAllIngridient();
        }
        
        if(uiDisplay!=ui)
        {
            uiDisplay = ui;
        }else
        {
            uiDisplay = 0;
        }
        
        
//        inventory_ui.refreshInventoryUI(wm.getCurrentLocalMap());
        
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

//    public InventoryUI getInventory_ui() {
//        return inventory_ui;
//    }
//
//    public void setInventory_ui(InventoryUI inventory_ui) {
//        this.inventory_ui = inventory_ui;
//    }

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

    public ArrayList<UIWindow> getUis() {
        return uis;
    }

    public void setUis(ArrayList<UIWindow> uis) {
        this.uis = uis;
    }

    public InventoryUIWindow getInventoryWindow() {
        return inventoryWindow;
    }

    public void setInventoryWindow(InventoryUIWindow inventoryWindow) {
        this.inventoryWindow = inventoryWindow;
    }

    public InventoryUI getInventory_ui() {
        return inventory_ui;
    }

    public void setInventory_ui(InventoryUI inventory_ui) {
        this.inventory_ui = inventory_ui;
    }

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

    

    public EntityLibrary getEntityLibrary() {
        return entityLibrary;
    }

    public void setEntityLibrary(EntityLibrary entityLibrary) {
        this.entityLibrary = entityLibrary;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public boolean isHoveringWindow() {
        return hoveringWindow;
    }

    public void setHoveringWindow(boolean hoveringWindow) {
        this.hoveringWindow = hoveringWindow;
    }
    
    
    
    
}
