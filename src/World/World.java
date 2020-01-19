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
import Entity.Pawn;
import Entity.Plant.Plant;
import Entity.Status;
import Entity.Task;
import EquipmentUI.EquipmentButton;
import EquipmentUI.EquipmentItemUI;
import EquipmentUI.EquipmentUI;
import EquipmentUI.EquipmentUIWindow;
import FurnitureUI.FurnitureUI;
import FurnitureUI.FurnitureUIWindow;
import Game.CanvasGame;
import UI.JMenuItemOption;
import GameConsole.GameConsole;
import Item.ItemLibrary;
import Narrator.Narrator;
import Res.Res;
import InventoryUI.InventoryButton;
import InventoryUI.InventoryItemUI;
import InventoryUI.InventoryUI;
import InventoryUI.InventoryUIWindow;
import InventoryUI.ItemOptionTab;
import InventoryUI.XItemTextField;
import Item.Item;
import Item.ItemPile;
import Narrator.NarratorButton;
import Narrator.NarratorUIWindow;
import Trading.TradingWindow;
import UI.OptionTab;

import UI.UIWindow;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class World implements ActionListener
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
   
    
    private CanvasGame game;
    
    private JPopupMenu popupMenu;
    
    
    private boolean popupDisplay;
    
    private boolean zoneDisplay,nameDisplay;
    
    private boolean autoMove;
    private long lastMove,currentMove;
    private long moveSpeed;
    
    private TradingWindow tradingWindow;
    
    public World(Res res,GameContainer container,CanvasGame game,Input input) 
    {
        this.game = game;
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
        
        popupDisplay = false;
        popupMenu = new JPopupMenu("menu_option");
        
        
        zoneDisplay = false;
        nameDisplay = false;
        
        autoMove = false;
        currentMove = 0;
        lastMove = System.currentTimeMillis();
        moveSpeed = 250;
        

        tradingWindow = new TradingWindow(container,res.disposableDroidBB);
        
        
    }
    
    public void tick(boolean[] k,boolean[] m,Input input)
    {
        
        if(k[Input.KEY_F1])
        {
            consoleActive = !consoleActive;
            gameConsole.getTextfield().setAcceptingInput(consoleActive);
            gameConsole.getTextfield().setFocus(consoleActive);
        }
        
        
        if(consoleActive)
        {
            gameConsole.tick(k, m, input, this);
            return;
        }

        if(autoMove)
        {
            currentMove += System.currentTimeMillis()-lastMove;
            lastMove = System.currentTimeMillis();
            if(currentMove >= moveSpeed)
            {
                currentMove = 0;
                moved();
            }
        }
        
        if(k[Input.KEY_1])
        {
            zoneDisplay = !zoneDisplay;
        }
        if(k[Input.KEY_2])
        {
            nameDisplay = !nameDisplay;
        }
        
        
        if(popupDisplay)
        {
            if(k[255]||m[19]||m[16]||m[17])
            {
                popupDisplay = false;
                popupMenu.setVisible(false);
                return;
            }
            
        }
        
        if(xItemTextFieldActive)
        {
            xItemTextField.tick(k,m, input, this,inventory_ui);
        }
        hoveringWindow = false;
        
        
        if(!dialogue.isDisplay()&&!tradingWindow.isDisplay())
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
        
        if(!dialogue.isDisplay()&&!tradingWindow.isDisplay())
        {
        
            if(k[Input.KEY_I])
            {
                deactivateXItemTextField();
                inventoryWindow.setDisplay();
            }
        }
        
        
        
        
        

        
        
        
        if(!dialogue.isDisplay()&&!tradingWindow.isDisplay())
        {
            wm.tick(k,m,input,this);
        }
        dialogue.tick(k, m, input, this);
        tradingWindow.tick(k, m, input, this);
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
        
        if(!popupDisplay&&!consoleActive)
        {
            for(UIWindow ui:uis)
            {
                if(ui.isDisplay())
                {
                    ui.renderDesc(g, input);
                }
            }
        }
        if(optionTab!=null)
        {
            optionTab.render(g);
        }
        dialogue.render(g);
        tradingWindow.render(g, input);
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
    
    
   
    
    public void activateXItemTextField(String itemName,int index,int state,int itemPileId)
    {
        xItemTextFieldActive = true;
        xItemTextField.setItemName(itemName);
        xItemTextField.setIndex(index);
        xItemTextField.setState(state);
        xItemTextField.setItemPileId(itemPileId);
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
    
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() instanceof JMenuItemOption)
        {
            JMenuItemOption option = (JMenuItemOption)e.getSource();
            
            processAction((JMenuItemOption)e.getSource());
              
            
        }
        popupDisplay = false;
    }
    
    
    public void processAction(JMenuItemOption option)
    {
        int amount;
        switch (option.getCommand())
        {
            case "walk_to":
                Tile t = ((Tile)option.getTarget());
                if(t.getX()!=wm.getPlayer().getX()||t.getY()!=wm.getPlayer().getY())
                {
                    wm.getCurrentLocalMap().getPlayer().walkTo(t.getX(), t.getY());
                }
                break;
            case "look":
                
                break;
            case "grab_item":
                int id = option.getId();
                int index = option.getIndex();
                amount = 1;
                if(((Item)option.getTarget()).isStackable())
                {
                    switch(option.getInfo())
                    {
                        case "half":
                            amount =  ((Item)option.getTarget()).getStack()/2;
                            break;
                        case "all":
                            amount = -1;
                            break;
                        case "x":
                            activateXItemTextField(((Item)option.getTarget()).getTrueName(),option.getIndex(),4,id);
                            return;
                    }
                }
                wm.getCurrentLocalMap().getPlayer().grabItemAt(0,0, id, index,((Item)option.getTarget()).getTrueName(),amount);
                break;
            
            case "talk_to":
                wm.getCurrentLocalMap().getPlayer().addTask(new Task(0,0,option.getId(),0,"talk_to_target"));
                break;
            case "harvest_plant":
                Task task = new Task(((Tile)option.getTarget()).getX(),((Tile)option.getTarget()).getY(),((Tile)option.getTarget()).getPlant().getId(),((Tile)option.getTarget()).getPlant().getId(),"harvest_plant");
                task.setIndex(option.getIndex());
                task.setTarget(((Tile)option.getTarget()).getPlant());
                task.setInfo(((Tile)option.getTarget()).getPlant().getCurrentName());
                wm.getCurrentLocalMap().getPlayer().addTask(task);
                break;
            case "drop_item":
                amount = 1;
                if(((Item)option.getTarget()).isStackable())
                {
                    switch(option.getInfo())
                    {
                        case "all":
                            amount = -1;
                            break;
                        case "half":
                            amount = ((Item)option.getTarget()).getStack()/2;
                            break;
                        case "x":
                            activateXItemTextField(((Item)option.getTarget()).getTrueName(),option.getIndex(),2,-1);
                            return;
                    }
                }
                wm.getPlayerInventory().dropItem(wm.getPlayer().getX(), wm.getPlayer().getY(), option.getIndex(),wm.getCurrentLocalMap(), amount);
                moved();
                break;
            case "drink_item":
                wm.getPlayerInventory().consumeItem(option.getIndex(), this,true);
                break;
            case "eat_item":
                wm.getPlayerInventory().consumeItem(option.getIndex(), this,true);
                break;
            case "equip_item":
                wm.getPlayerInventory().getEquipment().equip(((Item)option.getTarget()));
                inventory_ui.refreshInventoryUI(wm.getCurrentLocalMap());
                moved();
                break;
            case "unequip_item":
                wm.getPlayerInventory().getEquipment().unequip(((EquipmentItemUI)option.getTarget()).getItem().getEquipmentType());
                moved();
                break;
            case "read_item":
                Item book = ((Item)option.getTarget());
                for(int i=0;i<book.getEffects().size();i++)
                {
                    wm.getCurrentLocalMap().getPlayer().getStatus().add(new Status(book.getEffects().get(i)));
                }
                wm.getCurrentLocalMap().getWorld().moved();
                break;
            case "empty_item":
                
                break;
            case "trade":
                Pawn tradeTarget = (Pawn)option.getTarget();
                Task tradeTask = new Task(tradeTarget.getX(),tradeTarget.getY(),tradeTarget.getId(),0,"trade_with_target");
                tradeTask.setTarget(tradeTarget);
                wm.getCurrentLocalMap().getPlayer().addTask(tradeTask);
                break;
            
                
        }
        
    }
    
    public void createPopUpMenu(Object o,Input input)
    {
        popupDisplay = true;
        
        
        if(o instanceof Tile)
        {
            popupMenu = createTilePopupMenu((Tile)o);
            
        }else if(o instanceof InventoryItemUI)
        {
            popupMenu = createInventoryItemPopupMenu((InventoryItemUI)o);
        }else if(o instanceof EquipmentItemUI)
        {
            popupMenu = createEquipmentItemPopupMenu((EquipmentItemUI)o);
        }
        popupDisplay = true;
        popupMenu.show(game.getCanvasGameContainer(), input.getMouseX(), input.getMouseY());
    }
    
    public JPopupMenu createEquipmentItemPopupMenu(EquipmentItemUI itemUI)
    {
        JPopupMenu menu = new JPopupMenu("menu_option");
        menu.add(createItem("Unequip",itemUI.getItem().getTrueName(),"unequip_item",0,0,itemUI));
        if(itemUI.getItem().isStackable())
        {
            JLabel label = new JLabel(itemUI.getItem().getStack()+" x "+itemUI.getItem().getInGameName());
            label.setFont(new Font("TimesRoman",Font.BOLD,16));
            label.setAlignmentX(0.2f);
            menu.add(label,0);
        }else
        {
            JLabel label = new JLabel(itemUI.getItem().getInGameName());
            label.setFont(new Font("TimesRoman",Font.BOLD,16));
            label.setAlignmentX(0.2f);
            menu.add(label,0);
        }
        return menu;
        
    }
    
    public JPopupMenu createInventoryItemPopupMenu(InventoryItemUI itemUI)
    {
        JPopupMenu menu = new JPopupMenu("menu_option");
        if(itemUI.getState()!=4&&itemUI.getState()!=6)
        {
            boolean otherOption = false;
            for(Integer i:itemUI.getItem().getProperties())
            {
                switch(i)
                {
                    case 0:
                        menu.add(createItem("Eat "+itemUI.getName(),itemUI.getItem().getTrueName(),"eat_item",0,itemUI.getIndex(),itemUI.getItem()));
                        otherOption = true;
                        break;
                    case 1:
                        menu.add(createItem("Drink "+itemUI.getName(),itemUI.getItem().getTrueName(),"drink_item",0,itemUI.getIndex(),itemUI.getItem()));
                        otherOption = true;
                        break;
                    case 2:
                        menu.add(createItem("Empty "+itemUI.getName(),itemUI.getItem().getTrueName(),"empty_item",0,itemUI.getIndex(),itemUI.getItem()));
                        otherOption = true;
                        break;
                    case 3:
                        break;
                    case 5:
     
                        menu.add(createItem("Read "+itemUI.getName(),itemUI.getItem().getTrueName(),"read_item",0,itemUI.getIndex(),itemUI.getItem()));
                        otherOption = true;
                        break;
                    case 20:
                        menu.add(createItem("Equip "+itemUI.getName(),itemUI.getItem().getTrueName(),"equip_item",0,itemUI.getIndex(),itemUI.getItem()));
                        otherOption = true;
                        break;
                }
            }
            if(itemUI.getItem().isStackable())
            {
                if(otherOption)
                {
                    menu.add(createDropItemSubMenu(itemUI.getItem(),0,itemUI.getIndex()));
                }else
                {
                   menu.add(createItem("Drop","1","drop_item",0,itemUI.getIndex(),itemUI.getItem()));
                   menu.add(createItem("Drop all","all","drop_item",0,itemUI.getIndex(),itemUI.getItem()));
                   menu.add(createItem("Drop half","half","drop_item",0,itemUI.getIndex(),itemUI.getItem()));
                   menu.add(createItem("Drop X","x","drop_item",0,itemUI.getIndex(),itemUI.getItem()));
                }
            }else
            {
                menu.add(createItem("Drop "+itemUI.getItem().getInGameName(),"1","drop_item",0,itemUI.getIndex(),itemUI.getItem()));
            }
        }else if(itemUI.getState() == 4)
        {
            int id = wm.getCurrentLocalMap().getItemPileAt(wm.getPlayer().getX(), wm.getPlayer().getY()).getId();
            if(itemUI.getItem().isStackable())
            {
                menu.add(createItemSubMenu(itemUI.getItem(),id,itemUI.getIndex()));
            }else if(itemUI.getItem().getStack()==1)
            {
                menu.add(createItem("Take "+itemUI.getItem().getInGameName(),"1","grab_item",id,itemUI.getIndex(),itemUI.getItem()));
            }else
            {
                menu.add(createItem("Take "+itemUI.getItem().getInGameName(),"1","grab_item",id,itemUI.getIndex(),itemUI.getItem()));
            }
            
        }else if(itemUI.getState() == 6)
        {
//            options.add(new Option("Unequip",21));
        }
        
        if(itemUI.getItem().isStackable())
        {
            JLabel label = new JLabel(itemUI.getItem().getStack()+" x "+itemUI.getItem().getInGameName());
            label.setFont(new Font("TimesRoman",Font.BOLD,16));
            label.setAlignmentX(0.2f);
            menu.add(label,0);
        }else
        {
            JLabel label = new JLabel(itemUI.getItem().getInGameName());
            label.setFont(new Font("TimesRoman",Font.BOLD,16));
            label.setAlignmentX(0.2f);
            menu.add(label,0);
        }
        return menu;
    }
    
    
    
    public JMenuItemOption createItem(String label,String info,String command,int id,int index,Object o)
    {
        JMenuItemOption item = new JMenuItemOption(label, info, command, id, index, o);
        item.addActionListener(this);
        item.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        
        
     
        return item;
    }
    
    public JMenu createPawnSubMenu(Pawn pawn)
    {
        JMenu subMenu = new JMenu(pawn.getName()+","+pawn.getJobTitle());
        subMenu.setFont(new Font("TimesRoman",Font.PLAIN,16));
        subMenu.setOpaque(true);
        subMenu.setBackground(java.awt.Color.decode("#82ccdd"));
        subMenu.add(createItem("Talk to","none","talk_to",pawn.getId(),0,pawn));
        subMenu.add(createItem("Trade","none","trade",pawn.getId(),0,pawn));
        subMenu.add(createItem("Pickpocket","none","steal",pawn.getId(),0,pawn));
        subMenu.add(createItem("Attack","none","trade",pawn.getId(),0,pawn));
        
        
        return subMenu;
    }
    
    public JMenu createItemSubMenu(Item item,int id,int index)
    {
        
        JMenu subMenu = new JMenu(item.getStack()+" x "+item.getInGameName());
        subMenu.setOpaque(true);
        subMenu.setBackground(java.awt.Color.decode("#82ccdd"));
        subMenu.setFont(new Font("TimesRoman",Font.PLAIN,16));
        if(item.isStackable())
        {
            subMenu.add(createItem("Take","1","grab_item",id,index,item));
            if(item.getStack()>1)
            {
                subMenu.add(createItem("Take all","all","grab_item",id,index,item));
                subMenu.add(createItem("Take half","half","grab_item",id,index,item));
                subMenu.add(createItem("Take X","x","grab_item",id,index,item));
            }
        }else
        {
            subMenu.add(createItem("Take "+item.getInGameName(),item.getTrueName(),"grab_item",id,index,item));
        }
        return subMenu;
    }
    
    public JMenu createGrabItemSubMenu(Item item,int id,int index)
    {
        JMenu subMenu = new JMenu("Take "+item.getStack()+" x "+item.getInGameName());
        subMenu.setOpaque(true);
        subMenu.setBackground(java.awt.Color.decode("#82ccdd"));
        subMenu.setFont(new Font("TimesRoman",Font.PLAIN,16));
        if(item.isStackable())
        {
            subMenu.add(createItem("Take",item.getTrueName(),"grab_item",id,index,item));
            if(item.getStack()>1)
            {
                subMenu.add(createItem("Take all","all","grab_item",id,index,item));
                subMenu.add(createItem("Take half","half","grab_item",id,index,item));
                subMenu.add(createItem("Take X","x","grab_item",id,index,item));
            }
        }else
        {
            subMenu.add(createItem("Take "+item.getInGameName(),item.getTrueName(),"grab_item",id,index,item));
        }
        return subMenu;
    }
    
    public JMenu createDropItemSubMenu(Item item,int id,int index)
    {
        
        JMenu subMenu = new JMenu("Drop "+item.getStack()+" x "+item.getInGameName());
        subMenu.setOpaque(true);
        subMenu.setBackground(java.awt.Color.decode("#82ccdd"));
        subMenu.setFont(new Font("TimesRoman",Font.PLAIN,16));
        if(item.isStackable())
        {
            subMenu.add(createItem("Drop",item.getTrueName(),"drop_item",id,index,item));
            if(item.getStack()>1)
            {
                subMenu.add(createItem("Drop all","all","drop_item",id,index,item));
                subMenu.add(createItem("Drop half","half","drop_item",id,index,item));
                subMenu.add(createItem("Drop X","x","drop_item",id,index,item));
            }
        }else
        {
            subMenu.add(createItem("Drop "+item.getInGameName(),item.getTrueName(),"drop_item",id,index,item));
        }
        return subMenu;
    }
    
    public JMenu createPlantSubMenu(Tile tile,Plant plant)
    {
        JMenu subMenu = new JMenu(plant.getCurrentName());
        subMenu.setOpaque(true);
        subMenu.setBackground(java.awt.Color.decode("#82ccdd"));
        subMenu.setFont(new Font("TimesRoman",Font.PLAIN,16));
        for(int i=0;i<plant.getCurrentHarvest().getTool().length;i++)
        {
            int tool = plant.getCurrentHarvest().getTool()[i];
            switch(tool)
            {
                case 100:
                    subMenu.add(createItem("Harvest with hands",plant.getCurrentName(),"harvest_plant",plant.getId(),100,tile));
                    break;
                case 101:
                    subMenu.add(createItem("Harvest with axe",plant.getCurrentName(),"harvest_plant",plant.getId(),101,tile));
                    break;
                case 102:
                    subMenu.add(createItem("Harvest with pickaxe",plant.getCurrentName(),"harvest_plant",plant.getId(),102,tile));
                    break;
                case 103:
                    subMenu.add(createItem("Harvest with hoe",plant.getCurrentName(),"harvest_plant",plant.getId(),103,tile));
                    break;
                case 104:
                    subMenu.add(createItem("Harvest with hammer",plant.getCurrentName(),"harvest_plant",plant.getId(),104,tile));
                    break;
            }
        }
        return subMenu;
    }
    
    public JPopupMenu createTilePopupMenu(Tile t)
    {
        JPopupMenu menu = new JPopupMenu("menu_option");
        ArrayList<Pawn> pawns = wm.getCurrentLocalMap().getPawnsAt(t.getX(),t.getY());
        ItemPile ip = wm.getCurrentLocalMap().getItemPileAt(t.getX(), t.getY());
        Furniture f = wm.getCurrentLocalMap().furnitureAt(t.getX(), t.getY());
        
        if(!t.isSolid())
        {
            menu.add(createItem("Walk to","none","walk_to",0,0,t));
        }
        
        if(t.isVisit())
        {
            menu.add(createItem("Look","none","look",0,0,t));
            if(t.getPlant()!=null)
            {
                menu.add(createPlantSubMenu(t,t.getPlant()));
            }

            if(!pawns.isEmpty())
            {
                for(int i=0;i<pawns.size();i++)
                {
                    if(!pawns.get(i).isControl())
                    {
                        menu.add(createPawnSubMenu(pawns.get(i)));
                    }
                }
            }
            if(ip!=null)
            {
                for(int i=0;i<ip.getItems().size();i++)
                {
                    if(ip.getItems().get(i).isStackable()&&ip.getItems().get(i).getStack()>1)
                    {
                        menu.add(createItemSubMenu(ip.getItems().get(i),ip.getId(),i));
                    }else
                    {
                        menu.add(createItem("Take " + ip.getItems().get(i).getInGameName(), "1", "grab_item", ip.getId(), i, ip.getItems().get(i)));
                    }
                }
            }
        }
        
        
        
        if(f!=null)
        {
            
        }
        
        
        
        return menu;
        
        
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

    public CanvasGame getGame()
    {
        return game;
    }

    public void setGame(CanvasGame game)
    {
        this.game = game;
    }

    public JPopupMenu getPopupMenu()
    {
        return popupMenu;
    }

    public void setPopupMenu(JPopupMenu popupMenu)
    {
        this.popupMenu = popupMenu;
    }

    public boolean isPopupDisplay()
    {
        return popupDisplay;
    }

    public void setPopupDisplay(boolean popupDisplay)
    {
        this.popupDisplay = popupDisplay;
    }

    public boolean isZoneDisplay()
    {
        return zoneDisplay;
    }

    public void setZoneDisplay(boolean zoneDisplay)
    {
        this.zoneDisplay = zoneDisplay;
    }

    public boolean isNameDisplay()
    {
        return nameDisplay;
    }

    public void setNameDisplay(boolean nameDisplay)
    {
        this.nameDisplay = nameDisplay;
    }

    public boolean isAutoMove()
    {
        return autoMove;
    }

    public void setAutoMove(boolean autoMove)
    {
        this.autoMove = autoMove;
    }

    public long getLastMove()
    {
        return lastMove;
    }

    public void setLastMove(long lastMove)
    {
        this.lastMove = lastMove;
    }

    public long getCurrentMove()
    {
        return currentMove;
    }

    public void setCurrentMove(long currentMove)
    {
        this.currentMove = currentMove;
    }

    public long getMoveSpeed()
    {
        return moveSpeed;
    }

    public void setMoveSpeed(long moveSpeed)
    {
        this.moveSpeed = moveSpeed;
    }

    public TradingWindow getTradingWindow()
    {
        return tradingWindow;
    }

    public void setTradingWindow(TradingWindow tradingWindow)
    {
        this.tradingWindow = tradingWindow;
    }
    
    
    
    
}
