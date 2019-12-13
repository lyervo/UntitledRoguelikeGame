/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import World.LocalMap;
import World.Tile;
import rlforj.los.ILosBoard;

/**
 *
 * @author Timot
 */
public class FOVBoard implements ILosBoard
{

    private int[][] vision;
    
    public FOVBoard(Tile[][] tiles)
    {
        
        vision = new int[tiles.length][tiles[0].length];
        
        //make a simple copy of the map
        for(int iy=0;iy<tiles.length;iy++)
        {
            for(int ix=0;ix<tiles[0].length;ix++)
            {
                //-1 means it is an obstacle
                //0 means it is not visible to the entity
                //1 means it is visible
                if(tiles[iy][ix].isSolid())
                {
                    vision[iy][ix] = -1;
                }else
                {
                    vision[iy][ix] = 0;
                }
            }
        }
    }
    
    public boolean playerInVision(int x,int y,int range,LocalMap lm)
    {
        for(int iy=y-(range+2);iy<=y+range+2;iy++)
        {
            for(int ix=x-(range+2);ix<=x+range+2;ix++)
            {
                if(contains(ix,iy))
                {
                    if(vision[iy][ix] == 1)
                    {
                        if(((Pawn)lm.entityAt(ix, iy)) == null)
                        {

                        }else if(((Pawn)lm.entityAt(ix, iy)).isControl())
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    
    public void resetVision(int x,int y,int range)
    {
        for(int iy=y-(range+2);iy<=range+2;iy++)
        {
            for(int ix=x-(range+2);ix<=range+2;ix++)
            {
                if(contains(ix,iy))
                {
                    if(vision[iy][ix]==-1)
                    {
                        
                    }else
                    {
                        vision[iy][ix] = 0;
                    }
                }
            }
        }
    }
    
    
    @Override
    public boolean contains(int x, int y)
    {
        if (y < 0 || y >= vision.length || x < 0 || x >= vision[0].length) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isObstacle(int x, int y) {
        return vision[y][x] == -1;
    }

    @Override
    public void visit(int x, int y) 
    {
        if(vision[y][x]!=-1)
        {
            vision[y][x] = 1;
        }
    }

    public int[][] getVision() {
        return vision;
    }

    public void setVision(int[][] vision) {
        this.vision = vision;
    }
    
    
    
    
}
