/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trading;

import java.util.Comparator;

/**
 *
 * @author Timot
 */
public class TradeItemSellValueComparator implements Comparator
{

    @Override
    public int compare(Object o1, Object o2)
    {
        double difference = (((TradeItem)o2).getSellValue()-((TradeItem)o1).getSellValue());
        if(difference == 0)
        {
            return 0;
        }else if(difference <0)
        {
            return -1;
        }else
        {
            return 1;
        }
        
    }
    
}
