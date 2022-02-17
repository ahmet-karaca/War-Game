import java.io.IOException;
import java.util.*;

public class Troll extends Zorde{
    public static HashMap<String, Troll> trollHash = new HashMap<>();

    public Troll(String name, int x, int y, int HP, int AP, int maxMove, int maxHP){
        super(name, x, y, HP, AP, maxMove, maxHP);
    }

    public static void moveCaller(Troll troll,List<String> list) throws IOException {
        ArrayList<ArrayList<String>> oldBoard = Commands.oldBoardMaker();
        Persons.canContinue = true;
        move(troll,Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)),friends,enemies,oldBoard);
        Commands.finisherCheck();
        if (Persons.canPrint){
            Commands.outputString.append(Commands.boardPrinter(Commands.board));
        }
    }
}