/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryUI;

import Entity.Furniture;
import Res.Res;
import World.World;
import java.awt.Point;
import java.awt.Rectangle;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class StationUI extends DescBox
{
    private String name, desc;
    private int index;
    
    private Image texture;
    
    private Rectangle bounds;
    
    public StationUI(Furniture furniture,int index,Res res)
    {
        super(furniture.getName(),furniture.getDesc(),res.disposableDroidBB);
        this.name = furniture.getName();
        this.desc = furniture.getDesc();
        this.texture = furniture.getTexture();
        this.index = index;
        this.bounds = new Rectangle(16+(35*index),29,32,32);
    }
    
    public void render(Graphics g,Input input)
    {
        texture.draw(16+(35*index),29);
    }
    
    public void tick(boolean[] k,boolean[] m,Input input,World world)
    {
        tickDesc(bounds.contains(new Point(input.getMouseX(),input.getMouseY())));
    }
}
