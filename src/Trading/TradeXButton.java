/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trading;

import UI.Button;
import World.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Timot
 */
public class TradeXButton extends Button
{
    
    private TradeItem ti;

    private TradingWindow tradingWindow;
   
    public TradeXButton(int x, int y, int width, int height, String text, Color border, Color fill, Color hoverFill, TrueTypeFont font,TradeItem ti,TradingWindow tradingWindow)
    {
        super(x, y, width, height, text, border, fill, hoverFill, font);
        this.ti = ti;
        this.tradingWindow = tradingWindow;
    }

    @Override
    public void onClick(boolean[] m, World world)
    {
        if(tradingWindow.getTradeXTextField()==null)
        {
            ti.initTradeXTextField(world);
        }else if(tradingWindow.getTradeXTextField().getIndex()!=ti.getIndex())
        {
           ti.initTradeXTextField(world);
        }
    }
    
}
