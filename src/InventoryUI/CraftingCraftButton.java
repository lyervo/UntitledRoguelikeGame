/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import Item.Crafting;
import UI.Button;
import World.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

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
        crafting.craft();
        inventoryUI.refreshInventoryUI(world.getWm().getCurrentLocalMap());
    }

}
