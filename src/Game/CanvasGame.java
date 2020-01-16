/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import GameStates.InGame;
import Res.Res;
import World.World;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


/**
 *
 * @author Timot
 */
public class CanvasGame extends BasicGame 
{
    public static void main(String[] args)
    {
        CanvasGame game = new CanvasGame("Game");
    }

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

    private CanvasGameContainer canvasGameContainer;
    
    private JFrame frame;
    

    
    public CanvasGame(String title)
    {
        super(title);
        try
        {
            canvasGameContainer = new CanvasGameContainer(this);
            canvasGameContainer.setBounds(0, 0, 1920, 1080);
            frame = new JFrame("Game");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.add(canvasGameContainer);
            
            frame.addWindowListener(new WindowAdapter() {
            
            
            @Override
            public void windowClosing(WindowEvent e)
            {
                canvasGameContainer.dispose();
            }
            
            @Override
            public void windowClosed(WindowEvent e)
            {
                System.exit(0);
            }
            
            
            
            });
            
            
            
            
            
            frame.setVisible(true);
            
            canvasGameContainer.start();
            
            
        } catch (SlickException ex)
        {
            Logger.getLogger(CanvasGame.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        UIManager.put("MenuItem.selectionBackground", Color.decode("#60a3bc"));
        UIManager.put("MenuItem.background", Color.decode("#82ccdd"));
        UIManager.put("MenuItem.border", Color.decode("#82ccdd"));
//        UIManager.put("Menu.selectionBackground", Color.decode("#60a3bc"));
//        UIManager.put("Menu.background", Color.decode("#82ccdd"));
        UIManager.put("PopupMenu.border",Color.decode("#82ccdd"));
        UIManager.put("Menu.border",Color.decode("#82ccdd"));
        UIManager.put("Menu.selectionBackground", Color.decode("#60a3bc"));
        
        
        
        UIManager.put("Menu.backgroundColor", Color.red);
        
        
    }
    

    @Override
    public void init(GameContainer container) throws SlickException {
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
        
        world = new World(res,container,this,input);
        
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
       m[19] = true;
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
    public void render(GameContainer container,Graphics g) throws SlickException {
        if(res!=null)
        {
            world.render(g,animate);
        }
        animate = false;
        
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        
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

    public boolean[] getKeys()
    {
        return keys;
    }

    public void setKeys(boolean[] keys)
    {
        this.keys = keys;
    }

    public Input getInput()
    {
        return input;
    }

    public void setInput(Input input)
    {
        this.input = input;
    }

    public Res getRes()
    {
        return res;
    }

    public void setRes(Res res)
    {
        this.res = res;
    }

    public GameContainer getContainer()
    {
        return container;
    }

    public void setContainer(GameContainer container)
    {
        this.container = container;
    }

    public World getWorld()
    {
        return world;
    }

    public void setWorld(World world)
    {
        this.world = world;
    }

    public boolean[] getM()
    {
        return m;
    }

    public void setM(boolean[] m)
    {
        this.m = m;
    }

    public double getScale()
    {
        return scale;
    }

    public void setScale(double scale)
    {
        this.scale = scale;
    }

    public long getScrollLimit()
    {
        return scrollLimit;
    }

    public void setScrollLimit(long scrollLimit)
    {
        this.scrollLimit = scrollLimit;
    }

    public long getLastAnim()
    {
        return lastAnim;
    }

    public void setLastAnim(long lastAnim)
    {
        this.lastAnim = lastAnim;
    }

    public long getCurrentAnim()
    {
        return currentAnim;
    }

    public void setCurrentAnim(long currentAnim)
    {
        this.currentAnim = currentAnim;
    }

    public boolean isAnimate()
    {
        return animate;
    }

    public void setAnimate(boolean animate)
    {
        this.animate = animate;
    }

    public CanvasGameContainer getCanvasGameContainer()
    {
        return canvasGameContainer;
    }

    public void setCanvasGameContainer(CanvasGameContainer canvasGameContainer1)
    {
        this.canvasGameContainer = canvasGameContainer1;
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public void setFrame(JFrame frame)
    {
        this.frame = frame;
    }

    
    
    
    
    
    
}
