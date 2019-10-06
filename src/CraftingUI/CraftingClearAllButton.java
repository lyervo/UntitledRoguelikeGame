/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CraftingUI;

import InventoryUI.InventoryUI;
import Item.Crafting;
import UI.Button;
import World.World;
import org.newdawn.slick.Image;

/**
 *
 * @author Timot
 */
public class CraftingClearAllButton extends Button
{

    private Crafting crafting;
    private InventoryUI inventoryUI;
    
    public CraftingClearAllButton(int x, int y, Image texture,Crafting crafting,InventoryUI inventoryUI)
    {
        super(x, y, texture);
        this.crafting = crafting;
        this.inventoryUI = inventoryUI;
    }

    @Override
    public void onClick(boolean[] m, World world)
    {
        crafting.clearAllIngridient();
        inventoryUI.refreshInventoryUI(world.getWm().getCurrentLocalMap());
    }
    
}
