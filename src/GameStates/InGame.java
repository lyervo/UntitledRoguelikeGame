/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameStates;

import Res.Res;
import World.World;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Timot
 */
public class InGame extends BasicGameState
{
    
    public boolean[] keys;
    public Input input;
    public Res res;
    public GameContainer container;
    private World world;
    public boolean[] m;

    public double scale;
    
    private long scrollLimit;
    

    private long lastAnim,currentAnim;
    private boolean animate;
    
    @Override
    public int getID()
    {
        return 1;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        try {
            res = new Res(container);
        } catch (IOException ex) {
            Logger.getLogger(InGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        container.setTargetFrameRate(60);
        
        input = new Input(container.getHeight());
        
        scrollLimit = System.currentTimeMillis();
        
        
        this.container = container;
        m = new boolean[20];
        keys = new boolean[256];
        
        world = new World(res,container,input);
        
        animate = false;
        
        lastAnim = System.currentTimeMillis();
        currentAnim = 0;
        
    }

    @Override
    public void mouseReleased(int button,int x,int y)
    {
        m[button] = true;
        m[19] = true;
    }
    
    @Override
    public void mousePressed(int button,int x,int y)
    {
       m[button+10] = true;
    }
    
    
    @Override
    public void mouseDragged(int oldx,int oldy,int newx,int newy)
    {
        
    }
    
    @Override 
    public void mouseWheelMoved(int change)
    {
        if(System.currentTimeMillis()-scrollLimit>500)
        {
            if(change>0)
            {
                m[16] = true;
            }else if(change<0)
            {
                m[17] = true;
            }
        }
    }
    
    
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        if(res!=null)
        {
            world.render(g,animate);
        }
        animate = false;
        
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        
        if(currentAnim<=500)
        {
            currentAnim+= System.currentTimeMillis() - lastAnim;
            lastAnim = System.currentTimeMillis();
        }else
        {
            animate = true;
            currentAnim = 0;
            lastAnim = System.currentTimeMillis();
        }
        
        if(res!=null)
        {
            world.tick(keys,m, input);
            Arrays.fill(keys, false);
            Arrays.fill(m, false);
        }
    }
    
    
    
    

    
    @Override
    public void keyReleased(int key,char c)
    {
        
        
    }
    
    @Override
    public void keyPressed(int key,char c)
    {

        keys[key] = true;
        if(key!=Input.KEY_F1)
        {
            keys[255] = true;
        }
        
    }

    
}
