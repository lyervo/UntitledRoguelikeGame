/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FurnitureUI;


import InventoryUI.ItemUI;
import Item.Item;
import Res.Res;
import World.World;
import java.awt.Point;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 *
 * @author Timot
 */
public class FurnitureItemUI extends ItemUI
{
    
    private FurnitureUI ui;
    
    public FurnitureItemUI(Item item, int index, Res res,FurnitureUI ui)
    {
        super(item, index, res);
        this.ui = ui;
        
        if (index < 3)
        {
            bounds = new Rectangle(16 + (index * (71)), 32, 64, 64);
        } else
        {
            int colIndex = index % 3;
            int rowIndex = index / 3;
            bounds = new Rectangle((colIndex * 64) + (colIndex * 7) + 16, (rowIndex * 71) + 32, 64, 64);
        }

        
    }
    
    @Override
    public void tick(boolean[] k,boolean[] m,Input input,World world,int x,int y)
    {
        if(!world.isDrag())
        {
            if (bounds.contains(new Point2D(input.getMouseX()-x, input.getMouseY()-y)))
            {
                hover = true;

            } else {
                hover = false;
            }

            if (m[1] && hover)
            {
                world.spawnItemOptionTab(input.getMouseX(), input.getMouseY(), index, 6, item);
            } else if (input.isMouseButtonDown(0) && hover && !ui.isDrag() && !world.isDrag())
            {
                xofs = input.getMouseX() - x - (int)bounds.getX();
                yofs = input.getMouseY() - y - (int)bounds.getY();
                drag = true;
                world.setDrag(true);
                ui.setDrag(true);
            }


//            if(hover&&ui.getItemOptionTab()==null)
//            {
//                tickDesc(true);
//
//            }else
//            {
//                tickDesc(false);
//            }
        }
    }
    
    @Override
    public void render(Graphics g,Input input,int x,int y)
    {
        if(!drag)
        {
            if((index/3)-ui.getScroll()>=0&&(index/3)-ui.getScroll()<=2)
            {
                int colIndex = index % 3;
                int rowIndex = index / 3;

                item.getTexture().draw((colIndex * 64) + (colIndex * 7) + 16+x, ((rowIndex-ui.getScroll()) * 71) + 32+y, 64, 64);

                if (item.isStackable())
                {
                    font.drawString((colIndex * 64) + (colIndex * 7) + 16+x, ((rowIndex-ui.getScroll()) * 71) + 32+y, item.getStack() + "");
                }
            }
        }       
    }
    
    @Override
    public void checkDrop(Input input,World world,int x,int y)
    {
        Rectangle inventoryDropRect = new Rectangle(input.getMouseX()-xofs-world.getInventoryWindow().getX(),input.getMouseY()-yofs-world.getInventoryWindow().getY(),64,64);
        if(inventoryDropRect.intersects(world.getInventory_ui().getPrimaryBounds().getBoundsInParent()))
        {
            world.getInventory_ui().getPlayer_inventory().addItem(item);
            ui.getFurniture().getItems().remove(index);
            ui.refreshUI();
            world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
        }
    }
    
}
