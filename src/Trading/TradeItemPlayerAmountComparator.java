package Trading;


import Trading.TradeItem;
import java.util.Comparator;

public class TradeItemPlayerAmountComparator implements Comparator
{

    @Override
    public int compare(Object o1, Object o2)
    {
        double difference = ((((TradeItem)o2).getPlayerAmount()+((TradeItem)o2).getTradeAmount())-(((TradeItem)o1).getPlayerAmount()+((TradeItem)o1).getTradeAmount()));
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
