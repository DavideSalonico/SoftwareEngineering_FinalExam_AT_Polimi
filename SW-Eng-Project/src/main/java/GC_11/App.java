package GC_11;

public class App 
{
    public static void main( String[] args )
    {
        PersonalGoalCard card = new PersonalGoalCard();
        for (Triplet t : card.getGoalList()){
            System.out.println("R " + t.getRow() + " C " + t.getCol() + " COLOR " + t.getColor());
        }
    }
}
