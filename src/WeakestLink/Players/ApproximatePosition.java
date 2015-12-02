package WeakestLink.Players;
import WeakestLink.Game.Vote;

import java.util.*;
import java.awt.Point;

public class ApproximatePosition extends Player
{

    @Override
    public int vote(Set<Integer> currentOpponent)
    {
        List<Integer> present = new ArrayList<>(currentOpponent);
        int[] emptyPosition = new int[9-present.size()-1];
        Collections.sort(present);

        //If it is the first round, vote for the smartest buddy
        if(present.size()==8)
            return present.get(present.size()-1);


        int lastCheck=present.get(0);
        for(int i=1;i<present.size();i++)
        {
            if(present.get(i)-lastCheck>1)
                for (int j=lastCheck;j<=present.get(i);j++)
                    if(j!=getSmartness())
                        emptyPosition[emptyPosition.length-1]=j;
            lastCheck=present.get(i);
        }

        //untill there's at least 3 excluded members, we continue with this behaviour
        if(emptyPosition.length<=2)
            return decide(emptyPosition[0],present.get(present.size()-1));

        Point maxRangeOfBlank=new Point(present.get(present.size()-1),present.get(present.size()-1));
        for (int i=0;i<emptyPosition.length-1;i++)
            if(emptyPosition[i+1]-emptyPosition[i]==1)
            {
                int start= emptyPosition[i];
                int size=0;
                while(i+size+1<emptyPosition.length && emptyPosition[i+size+1]-emptyPosition[i+size]==1)
                    size++;
                if(size>=sizeOfRange(maxRangeOfBlank))
                    maxRangeOfBlank=new Point(emptyPosition[i],emptyPosition[size]);
                i=size;
            }

        return decide(maxRangeOfBlank,present.get(present.size()-1));
    }

    private int decide(int blankSeat, int smartest)
    {
        return decide(new Point(blankSeat,blankSeat),smartest);
    }

    private int decide(Point rangeBlankSeat, int smartest)
    {
        if (rangeBlankSeat.getY()==9||((int)rangeBlankSeat.getY()+1)==getSmartness()){
            if ((rangeBlankSeat.getX()==0||(int)rangeBlankSeat.getX()-1==getSmartness())){
                return smartest; //should not happen
            } else {
                return (int) rangeBlankSeat.getX()-1; //Vote for dumber than the missing
            }
        } else {
            return (int) rangeBlankSeat.getY() +1; //Vote for smarter than the missing, default comportment
        }
    }
    //Return the number of consecutive values between X and Y (included)
    private int sizeOfRange(Point range)
    {
        return (int)(range.getY()-range.getX())+1;
    }
}