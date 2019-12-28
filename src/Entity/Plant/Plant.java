/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Plant;

import Camera.Camera;
import Entity.Entity;
import World.LocalMap;
import World.World;
import java.util.ArrayList;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Timot
 */
public class Plant extends Entity
{
    
    private PlantTemplate plantTemplate;
    
    private int stage,currentGrowth;
    
    private boolean disturbed;
    
    private ArrayList<Harvest> harvests;
    
    public Plant(int id, int x, int y, PlantTemplate template)
    {
        super(id, x, y, template.getSprites(), false);
        this.plantTemplate = template;
        stage = 0;
        currentGrowth = 0;
        disturbed = false;
        this.harvests = new ArrayList<Harvest>(template.getHarvest());
    }

    @Override
    public void tick(boolean[] k, boolean[] m, Input input, World world)
    {
        if(world.isMoved())
        {
            if(!disturbed)
            {
                if(currentGrowth<plantTemplate.getMaxGrowth())
                {
                    currentGrowth++;
                
                    for(int i=1;i<plantTemplate.getStageGrowths().length;i++)
                    {
                        if(currentGrowth>=plantTemplate.getStageGrowths()[i])
                        {
                            stage = i;

                        }else
                        {
                            break;
                        }
                    }
                }
                for(Harvest h:harvests)
                {
                    h.depleteCurrent();
                }
            }else
            {
                
            }
        }
    }
    
    @Override
    public void render(Camera cam,LocalMap map,boolean animate)
    {
        if(map.getTiles()[y][x].isVisit())
        {
            if(sprites == null)
            {
                texture.draw(x*cam.getTile_size()+cam.getXofs(),y*cam.getTile_size()+cam.getYofs());
            }else
            {

                sprites.getSprite(stage,0).draw(x*cam.getTile_size()+cam.getXofs(),y*cam.getTile_size()+cam.getYofs());



            }
        }
    }

    @Override
    public boolean hasItem(String name)
    {
        return false;
    }
    
}
