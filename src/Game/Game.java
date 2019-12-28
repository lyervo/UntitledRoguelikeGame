
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;


import GameStates.InGame;
import Res.Res;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Timot
 */
public class Game extends StateBasedGame
{
    public static void main(String[] args) throws IOException, SlickException
    {
       
//        int screenHeight = 0;
//        int screenWidth = 0;
//        boolean fullScreen = false;
//        int lineCount = 1;
//        
//        try
//        {
//            File config = new File("config.cfg");
//            Scanner configReader = new Scanner(config);
//            while(configReader.hasNext())
//            {
//                String configLine = configReader.nextLine();
//                
//                String[] separator = configLine.split("=");
//                switch(separator[0])
//                {
//                    case "ScreenHeight":
//                        screenHeight = Integer.parseInt(separator[1]);
//                        break;
//                    case "ScreenWidth":
//                        screenWidth = Integer.parseInt(separator[1]);
//                        break;
//                    case "FullScreen":
//                        if(separator[1].equals("0"))
//                        {
//                            fullScreen = false;
//                        }else
//                        {
//                            fullScreen = true;
//                        }
//                        break;
//                        
//                    default:
//                        System.out.println("UNKNOWN CONFIG LINE IN config.cfg LINE "+lineCount);
//                        break;
//                }
//                
//                lineCount++;
//                
//            }
            
            //automatically gets screen dimension
            
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            
            AppGameContainer app = new AppGameContainer(new Game("Game"));
            app.setDisplayMode((int)screenSize.getWidth(), (int)screenSize.getHeight(), false);
//            app.setDisplayMode(1280, 720, false);
            app.setFullscreen(true);   
            app.start();
            
            
//        }catch(IOException e)
//        {
//            
//        }
        

    }
    
    
    public Res res;
    public int fps;
    public long rate;
    public GameState state;
    
    public Game(String title) throws IOException, SlickException
    {
        super(title);
   
    }
    

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        container.setTargetFrameRate(60);
        state = new InGame();
        this.addState(state);
    }
    
    
    
    
    
    
}
