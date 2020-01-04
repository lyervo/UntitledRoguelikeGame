/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

import Camera.Camera;
import Entity.EntityLibrary;
import CraftingUI.CraftingButton;
import CraftingUI.CraftingUI;
import CraftingUI.CraftingUIWindow;
import Culture.CultureManager;
import Culture.SubFaction;
import Dialogue.DialogueLibrary;
import Dialogue.DialogueWindow;
import Entity.Furniture;
import EquipmentUI.EquipmentButton;
import EquipmentUI.EquipmentUI;
import EquipmentUI.EquipmentUIWindow;
import FurnitureUI.FurnitureUI;
import FurnitureUI.FurnitureUIWindow;
import GameConsole.GameConsole;
import Item.ItemLibrary;
import Narrator.Narrator;
import Res.Res;
import InventoryUI.InventoryButton;
import InventoryUI.InventoryUI;
import InventoryUI.InventoryUIWindow;
import InventoryUI.ItemOptionTab;
import InventoryUI.XItemTextField;
import Item.Item;
import Narrator.NarratorButton;
import Narrator.NarratorUIWindow;
import UI.OptionTab;

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
    private NarratorUIWindow narratorWindow;
    
    
    private double scale;
    private Input input;
    
    public Camera cam;
    
    private boolean mapTick;
    
    private int uiDisplay;
    
    private int mouse_z;
    
    private ItemLibrary itemLibrary;
    private EntityLibrary entityLibrary;
    
    
    private boolean quickItemBarUIDisplay;

    
    private XItemTextField xItemTextField;
    private boolean xItemTextFieldActive;
    
    
    private ArrayList<UIWindow> uis;
    
    private InventoryUIWindow inventoryWindow;
    private EquipmentUIWindow equipmentWindow;
    private CraftingUIWindow craftingWindow;
    
    
    private InventoryUI inventory_ui;
    private EquipmentUI equipment_ui;
    private CraftingUI crafting_ui;
    
    private boolean drag;
    
    private InventoryButton inventoryButton;
    private EquipmentButton equipmentButton;
    private CraftingButton craftingButton;
    private NarratorButton narratorButton;
    
    
    private int z;
    
    private OptionTab optionTab;
    
    private DialogueWindow dialogue;
    private DialogueLibrary dialogueLibrary;
    
    
    private CultureManager cm;
    
    
    
    private boolean consoleActive;
    private GameConsole gameConsole;
   
    
    
    
    
    public World(Res res,GameContainer container,Input input)
    {
        
        cm = new CultureManager();
        
        this.z = 0;
        hoveringWindow = false;
        entityLibrary = new EntityLibrary(res);
        itemLibrary = new ItemLibrary("test",res,entityLibrary);
        
        
        mouse_z = 0;
        this.res = res;
        wm = new WorldMap(res,this,container);
        moved = false;
        turn = 0;
        this.container = container;
        this.input = input;
        
        this.scale = (double)container.getHeight()/768;
        
        ancestor = new Narrator(0,0,res.disposableDroidBB);
        narratorWindow = new NarratorUIWindow(50,100,"Log",ancestor,res);
        
        
        cam = new Camera(100,100,container);
        
        xItemTextFieldActive = false;
        
        xItemTextField = new XItemTextField(container,res.disposableDroidBB,(container.getWidth()/2)-((res.disposableDroidBB.getWidth("00000")+5)/2),(container.getHeight()/2)-(res.disposableDroidBB.getHeight()/2),res.disposableDroidBB.getWidth("00000")+5,res.disposableDroidBB.getHeight(),this,res);

        dialogueLibrary = new DialogueLibrary(this);
        dialogue = new DialogueWindow(dialogueLibrary,this);
        
        
        
        uiDisplay = 0;
        
        drag = false;
        
        
        ancestor.addText("Ruin has come to our family. You remember our venerable house,"
                + " opulent and imperial; gazing proudly from its stoic perch above the moor."
                + " I lived all my years in that ancient, rumour-shadowed manor. Fattened by"
                + " decadence and luxury... and yet, I began to tire of... conventional extravagance."
                + " There is a house in new orleans, they call the rising sun, and its been of ruin of "
                + "many poor boy, and god I know I'm one, My mother was a taylor, sew my new blue jeans,"
                + " my father was a gambling man, down in new orleans");
        ancestor.addText("ewdifjvkglti jkrhnlfgrweijdgnb mwinjr ewfrgdijmkleoiwdgf jbokm werfgtioj mkleko 3ed 3edrfdfrgb");
        
        mapTick = false;
        
        
        inventory_ui = new InventoryUI(100,100,wm.getPlayerInventory(),res,this);
        inventoryWindow = new InventoryUIWindow(100,100,"Inventoy",inventory_ui,res.disposableDroidBB40f,res);
        
        equipment_ui = new EquipmentUI(100,100,wm.getPlayerInventory().getEquipment(),res,9);
        equipmentWindow = new EquipmentUIWindow(100,100,"Equipment",equipment_ui,res);
        
        
        
        crafting_ui = new CraftingUI(200,200,wm.getPlayerInventory().getCrafting(),res,inventory_ui,itemLibrary,this);
        craftingWindow = new CraftingUIWindow(200,200,"Crafting",crafting_ui,res);
        
        uis = new ArrayList<UIWindow>();
        uis.add(equipmentWindow);
        uis.add(inventoryWindow);
        uis.add(craftingWindow);
        uis.add(narratorWindow);
        
        for(int i=0;i<uis.size();i++)
        {
            uis.get(i).setZ(i+1);
        }
        
        inventoryButton = new InventoryButton(10,container.getHeight()-64,res.inventory_icon);
        equipmentButton = new EquipmentButton(84,container.getHeight()-64,res.inventory_icon);
        craftingButton = new CraftingButton(158,container.getHeight()-64,res.inventory_icon);
        narratorButton = new NarratorButton(232,container.getHeight()-64,res.inventory_icon);
        
        
        
        gameConsole = new GameConsole(this);
        gameConsole.getTextfield().setFocus(false);
        consoleActive = false;
    }
    
    public void tick(boolean[] k,boolean[] m,Input input)
    {
        
        if(k[Input.KEY_F1])
        {
            consoleActive = !consoleActive;
            if(consoleActive)
            {
                gameConsole.startState();
            }else
            {
                gameConsole.getTextfield().setFocus(false);
            }
        }
        
        
        if(consoleActive)
        {
            gameConsole.tick(k, m, input, this);
            return;
        }
        
        if(xItemTextFieldActive)
        {
            xItemTextField.tick(k,m, input, this,inventory_ui);
        }
        hoveringWindow = false;
        
        
        if(!dialogue.isDisplay())
        {
            for(int i=uis.size()-1;i>=0;i--)
            {
                uis.get(i).tick(k, m, input, this);
            }
        }
        if(optionTab!=null)
        {
            optionTab.tick(k, m, input, wm.getCurrentLocalMap());
            if(m[10]&&optionTab.getHoveringIndex()!=-1)
            {
                optionTab.runOption(wm.getCurrentLocalMap());
                optionTab = null;
                drag = false;
                for(UIWindow ui:uis)
                {
                    ui.setDrag(false);
                    ui.getUiComponent().setDrag(false);
                }
            }else if((m[10]&&optionTab.getHoveringIndex()==-1)||k[255])
            {
                optionTab = null;
                drag = false;
                for(UIWindow ui:uis)
                {
                    ui.setDrag(false);
                    ui.getUiComponent().setDrag(false);
                }
                
            }
        }
        
        if(m[0]&&drag)
        {
            for(UIWindow ui:uis)
            {
                ui.itemUICheckDrop(k, m, input, this);
            }
        }
        
        if(!hoveringWindow)
        {
            z = 0;
        }
        
        inventoryButton.tick(m, input, this);
        equipmentButton.tick(m, input, this);
        craftingButton.tick(m, input, this);
        narratorButton.tick(m, input, this);
        
        if(!dialogue.isDisplay())
        {
        
            if(k[Input.KEY_I])
            {
                deactivateXItemTextField();
                inventoryWindow.setDisplay();
            }
        }
        
        
        
        
        

        
        
        
        if(!dialogue.isDisplay())
        {
            wm.tick(k,m,input,this);
        }
        dialogue.tick(k, m, input, this);
        if(moved)
        {
            equipment_ui.refreshUI();
            inventory_ui.refreshInventoryUI(wm.getCurrentLocalMap());
            crafting_ui.refreshUI(wm.getCurrentLocalMap());
            for(int i=uis.size()-1;i>=0;i--)
            {
                if(uis.get(i) instanceof FurnitureUIWindow)
                {
                    if(!((FurnitureUI)uis.get(i).getUiComponent()).getFurniture().withinDistance(1, wm.getPlayer()))
                    {
                        uis.remove(i);
                    }
                }
            }
        }
        
        if(moved)
        {
           turn++;
           moved = false;
        }
        
        
        
    }
    
   
    
    public void render(Graphics g,boolean animate)
    {

        inventoryButton.render(g);
        equipmentButton.render(g);
        craftingButton.render(g);
        narratorButton.render(g);
        wm.render(g, animate);

        
//        if(wm.getPlayer()!=null)
//        {
//            g.setColor(Color.red);
//            g.fillRect(1018, 50, (int)wm.getPlayer().getHp().getValue(), 50);
//        }
        
        
        for(int i=0;i<uis.size();i++)
        {
            if(uis.get(i).isDisplay())
            {
                uis.get(i).render(g, input);
            }
        }
      
        
        for(UIWindow ui:uis)
        {
            if(ui.isDrag())
            {
                ui.dragRender(g, input);
            }else if(ui.getUiComponent().isDrag())
            {
                ui.getUiComponent().dragRender(g, input);
            }
            
            
        }
        
        
        
        if(xItemTextFieldActive)
        {
            xItemTextField.renderBackground(g);
            g.setColor(Color.white);
            xItemTextField.render(container, g);
        }
        
        for(UIWindow ui:uis)
        {
            ui.renderDesc(g, input);
        }
        
        if(optionTab!=null)
        {
            optionTab.render(g);
        }
        dialogue.render(g);
        if(consoleActive)
        {
            gameConsole.render(g);
        }
    }
    
    
    
    public void moveWindowToTop(UIWindow window)
    {
        if(uis.indexOf(window)==uis.size()-1)
        {
            return;
        }
        
        for(int i=uis.size()-1;i>=0;i--)
        {
            if(uis.get(i).equals(window))
            {
                uis.remove(i);
                uis.add(window);
                resetZ();
                return;
                
            }
        }
    }
    
    public void resetZ()
    {
        for(int i=0;i<uis.size();i++)
        {
            uis.get(i).setZ(i+1);
        }
    }
    
    
   
    
    public void activateXItemTextField(String itemName,int state)
    {
        xItemTextFieldActive = true;
        xItemTextField.setItemName(itemName);
        xItemTextField.setState(state);
    }
    
    public void deactivateXItemTextField()
    {
        xItemTextFieldActive = false;
        xItemTextField.setText("");
    
    }

    public void spawnFurnitureInventoryWindow(Furniture f,int x,int y)
    {
        uis.add(new FurnitureUIWindow(x,y,f.getName(),new FurnitureUI(x,y,f,res),res));
        uis.get(uis.size()-1).setWindowLocation(input, container);
        uis.get(uis.size()-1).setDisplay(true);
        drag = false;
        resetZ();
    }
    
    
    public void spawnItemOptionTab(int x,int y,int index,int state,Item item)
    {
        optionTab = new ItemOptionTab(x,y,wm.getCurrentLocalMap(),container,res,wm.getPlayerInventory(),inventory_ui,item, index,state,itemLibrary);
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

    public EquipmentUIWindow getEquipmentWindow() {
        return equipmentWindow;
    }

    public void setEquipmentWindow(EquipmentUIWindow equipmentWindow) {
        this.equipmentWindow = equipmentWindow;
    }

    public EquipmentUI getEquipment_ui() {
        return equipment_ui;
    }

    public void setEquipment_ui(EquipmentUI equipment_ui) {
        this.equipment_ui = equipment_ui;
    }

    public InventoryButton getInventoryButton() {
        return inventoryButton;
    }

    public void setInventoryButton(InventoryButton inventoryButton) {
        this.inventoryButton = inventoryButton;
    }

    public EquipmentButton getEquipmentButton() {
        return equipmentButton;
    }

    public void setEquipmentButton(EquipmentButton equipmentButton) {
        this.equipmentButton = equipmentButton;
    }

    public CraftingUIWindow getCraftingWindow() {
        return craftingWindow;
    }

    public void setCraftingWindow(CraftingUIWindow craftingWindow) {
        this.craftingWindow = craftingWindow;
    }

    public CraftingUI getCrafting_ui() {
        return crafting_ui;
    }

    public void setCrafting_ui(CraftingUI crafting_ui) {
        this.crafting_ui = crafting_ui;
    }

    public CraftingButton getCraftingButton() {
        return craftingButton;
    }

    public void setCraftingButton(CraftingButton craftingButton) {
        this.craftingButton = craftingButton;
    }

    public OptionTab getOptionTab() {
        return optionTab;
    }

    public void setOptionTab(OptionTab optionTab) {
        this.optionTab = optionTab;
    }

    public DialogueWindow getDialogue() {
        return dialogue;
    }

    public void setDialogue(DialogueWindow dialogue) {
        this.dialogue = dialogue;
    }

    public DialogueLibrary getDialogueueLibrary() {
        return dialogueLibrary;
    }

    public void setDialogueueLibrary(DialogueLibrary dialogueLibrary) {
        this.dialogueLibrary = dialogueLibrary;
    }

    public NarratorUIWindow getNarratorWindow() {
        return narratorWindow;
    }

    public void setNarratorWindow(NarratorUIWindow narratorWindow) {
        this.narratorWindow = narratorWindow;
    }

    public DialogueLibrary getDialogueLibrary() {
        return dialogueLibrary;
    }

    public void setDialogueLibrary(DialogueLibrary dialogueLibrary) {
        this.dialogueLibrary = dialogueLibrary;
    }

    public NarratorButton getNarratorButton()
    {
        return narratorButton;
    }

    public void setNarratorButton(NarratorButton narratorButton)
    {
        this.narratorButton = narratorButton;
    }

    public boolean isConsoleActive()
    {
        return consoleActive;
    }

    public void setConsoleActive(boolean consoleActive)
    {
        this.consoleActive = consoleActive;
    }

    public GameConsole getGameConsole()
    {
        return gameConsole;
    }

    public void setGameConsole(GameConsole gameConsole)
    {
        this.gameConsole = gameConsole;
    }

    public CultureManager getCm()
    {
        return cm;
    }

    public void setCm(CultureManager cm)
    {
        this.cm = cm;
    }
    
    
    
    
}
