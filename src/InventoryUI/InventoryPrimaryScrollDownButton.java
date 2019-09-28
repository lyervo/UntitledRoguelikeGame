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
public class InventoryPrimaryScrollDownButton extends Button
{

    private InventoryUI inventoryUI;
    
    public InventoryPrimaryScrollDownButton(int x, int y,int xofs,int yofs, Image texture,InventoryUI inventoryUI)
    {
        super(x, y, xofs,yofs,texture);
        this.inventoryUI = inventoryUI;
    }

    @Override
    public void onClick(boolean[] m, World world)
    {
        inventoryUI.primaryScrollDown();
    }
    
}
