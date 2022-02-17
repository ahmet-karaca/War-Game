import java.io.IOException;
import java.util.*;

public class Goblin extends Zorde{
    public static HashMap<String, Goblin> goblinHash = new HashMap<>();

    public Goblin(String name, int x, int y, int HP, int AP, int maxMove, int maxHP){
        super(name, x, y, HP, AP, maxMove, maxHP);
    }

    public static void moveCaller(Goblin goblin, List<String> list) throws IOException {
        ArrayList<ArrayList<String>> oldBoard = Commands.oldBoardMaker();
        Persons.canContinue = true;
        for (int i = 0; i < list.size(); i++) {
            if(Zorde.canContinue){
                if (i%2 == 1){
                    move(goblin,Integer.parseInt(list.get(i-1)), Integer.parseInt(list.get(i)),friends,enemies,oldBoard);
                    Commands.finisherCheck();
                }
            }
        }
        if (Persons.canPrint){
            Commands.outputString.append(Commands.boardPrinter(Commands.board));
        }
    }
}