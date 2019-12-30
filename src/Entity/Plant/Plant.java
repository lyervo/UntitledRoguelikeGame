/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Plant;

import Camera.Camera;
import Entity.Entity;
import Entity.Pawn;
import Item.Item;
import World.LocalMap;
import World.Tile;
import World.World;
import java.util.ArrayList;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;
import java.util.Random;

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
    
    private Tile tile;
    
    public Plant(int id, int x, int y, PlantTemplate template,Tile tile)
    {
        super(id, x, y, template.getSprites(), false);
        this.plantTemplate = template;
        stage = 0;
        currentGrowth = 0;
        disturbed = false;
        this.harvests = new ArrayList<Harvest>(template.getHarvest());
        this.tile = tile;
    }
    
    
    public void harvest(Item tool,Pawn user,World world)
    {
        for(Integer i:harvests.get(stage).getTool())
        {
            if(tool.getProperties().contains(i))
            {
                harvests.get(stage).harvest((int)tool.getEffectByName("tool_efficiency").getValue());
                if(harvests.get(stage).finishedHarvest())
                {
                    
                    for(HarvestResult r :harvests.get(stage).getHarvestResult())
                    {
                        Item produce = world.getItemLibrary().getItemByName(r.getItemName());
                        Random rand = new Random();
                        int resultAmount = 0;
                        if(r.getMaxAmount() == r.getMinAmount())
                        {
                            resultAmount = r.getMinAmount();
                        }else
                        {
                            resultAmount = r.getMinAmount() + rand.nextInt(r.getMaxAmount()-r.getMinAmount()+1);
                        }
                        if(produce.isStackable())
                        {
                            produce.setStack(resultAmount);
                            if(user.isControl())
                            {
                                world.getWm().getPlayerInventory().addItem(world.getItemLibrary().getItemByName(r.getItemName()));
                            }else
                            {
                                user.getInventory().addItem(world.getItemLibrary().getItemByName(r.getItemName()));
                            }
                        }else
                        {
                            for(int a=0;a<resultAmount;a++)
                            {
                                if(user.isControl())
                                {
                                    world.getWm().getPlayerInventory().addItem(world.getItemLibrary().getItemByName(r.getItemName()));
                                }else
                                {
                                    user.getInventory().addItem(world.getItemLibrary().getItemByName(r.getItemName()));
                                }
                            }
                        }
                        
                    }
                    
                    if(user.isControl())
                    {
                        world.getInventory_ui().refreshInventoryUI(world.getWm().getCurrentLocalMap());
                    }
                    
                    resetHarvest();
                    
                    if(harvests.get(stage).isStageRevert())
                    {
                        stage--;
                    }else if(harvests.get(stage).isKill())
                    {
                        tile.clearPlant();
                    }
                }else
                {
                    disturbed = true;
                }
            }
        }
    }
    

    public int[] getCurrentTools()
    {
        return harvests.get(stage).getTool();
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
            disturbed = false;
        }
    }
    
    public void resetHarvest()
    {
        for(Harvest h:harvests)
        {
            h.setCurrent(0);
        }
    }
    
    public String getCurrentName()
    {
        return plantTemplate.getNAMES().get(stage);
    }
    
    @Override
    public void render(Camera cam,LocalMap map,boolean animate)
    {
        if(tile.isVisit())
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
    
    public boolean getCurrentSolid()
    {
        return plantTemplate.getSOLIDS()[stage];
    }

    @Override
    public boolean hasItem(String name)
    {
        return false;
    }
    
    public boolean isSolid()
    {
        return plantTemplate.getSOLIDS()[stage];
    }

    public PlantTemplate getPlantTemplate()
    {
        return plantTemplate;
    }

    public void setPlantTemplate(PlantTemplate plantTemplate)
    {
        this.plantTemplate = plantTemplate;
    }

    public int getStage()
    {
        return stage;
    }

    public void setStage(int stage)
    {
        this.stage = stage;
    }

    public int getCurrentGrowth()
    {
        return currentGrowth;
    }

    public void setCurrentGrowth(int currentGrowth)
    {
        this.currentGrowth = currentGrowth;
    }

    public boolean isDisturbed()
    {
        return disturbed;
    }

    public void setDisturbed(boolean disturbed)
    {
        this.disturbed = disturbed;
    }

    public ArrayList<Harvest> getHarvests()
    {
        return harvests;
    }

    public void setHarvests(ArrayList<Harvest> harvests)
    {
        this.harvests = harvests;
    }

    public Tile getTile()
    {
        return tile;
    }

    public void setTile(Tile tile)
    {
        this.tile = tile;
    }
    
    
    
    
}
