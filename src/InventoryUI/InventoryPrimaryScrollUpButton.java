/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import UI.Button;
import World.World;
import org.newdawn.slick.Image;

/**
 *
 * @author Timot
 */
public class InventoryPrimaryScrollUpButton extends Button
{

    private InventoryUI inventoryUI;
    
    public InventoryPrimaryScrollUpButton(int x, int y, Image texture,InventoryUI inventoryUI)
    {
        super(x, y, texture);
        this.inventoryUI = inventoryUI;
    }

    @Override
    public void onClick(boolean[] m, World world)
    {
        inventoryUI.primaryScrollUp();
    }
    
}
