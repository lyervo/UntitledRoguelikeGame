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
public class CraftingFilterButton extends Button
{

    private Image texture1,texture2;
    private CraftingUI craftingUI;
    
    
    public CraftingFilterButton(int x, int y, Image texture,Image texture2,CraftingUI craftingUI)
    {
        super(x, y, texture);
        this.texture1 = texture;
        this.texture2 = texture2;
        this.craftingUI = craftingUI;
        
    }

    @Override
    public void onClick(boolean[] m, World world)
    {
        if(craftingUI.getFilter()==1)
        {
            craftingUI.setFilter(2);
            setTexture(texture2);
        }else if(craftingUI.getFilter()==2)
        {
            craftingUI.setFilter(1);
            setTexture(texture1);
        }
        craftingUI.refreshUI(world.getWm().getCurrentLocalMap());
    }
    
}
