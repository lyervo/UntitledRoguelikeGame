package Trading;


import Trading.TradeItem;
import java.util.Comparator;

public class TradeItemPawnAmountComparator implements Comparator
{

    @Override
    public int compare(Object o1, Object o2)
    {
        double difference = ((((TradeItem)o2).getTargetAmount()-((TradeItem)o2).getTradeAmount())-(((TradeItem)o1).getTargetAmount()-((TradeItem)o1).getTradeAmount()));
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
