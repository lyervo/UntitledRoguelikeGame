/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CraftingUI;

import Entity.Task;
import InventoryUI.InventoryUI;
import Item.Crafting;
import UI.Button;
import World.World;

import org.newdawn.slick.Image;


/**
 *
 * @author Timot
 */
public class CraftingCraftButton extends Button
{

    private InventoryUI inventoryUI;
    private Crafting crafting;
    
    public CraftingCraftButton(int x, int y,Image texture,Crafting crafting,InventoryUI inventoryUI) {
        super(x, y, texture);
        this.crafting = crafting;
        this.inventoryUI = inventoryUI;
    }

    @Override
    public void onClick(boolean[] m, World world) 
    {
        if(crafting.getSelectIndex()!=-1)
        {
            crafting.clearCraftingTarget();
            world.getWm().getPlayer().addTask(new Task(0,0,-1,-1,"craft"));
            crafting.setCraftingTarget();
            world.getCrafting_ui().refreshUI(world.getWm().getCurrentLocalMap());
            world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
        }
        
    }

}
