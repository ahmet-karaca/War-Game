import java.io.IOException;
import java.util.*;

public class Human extends Callience{
    public static HashMap<String, Human> humanHash = new HashMap<>();

    public Human(String name, int x, int y, int HP, int AP, int maxMove, int maxHP){
        super(name, x, y, HP, AP, maxMove, maxHP);
    }

    public static void moveCaller(Human human, List<String> list) throws IOException {
        ArrayList<ArrayList<String>> oldBoard = Commands.oldBoardMaker();
        Callience.canContinue = true;
        Callience.moveCounter = 0;
        for (int i = 0; i < list.size(); i++) {
            if(Callience.canContinue){
                if (i%2 == 1){
                    move(human, Integer.parseInt(list.get(i-1)),Integer.parseInt(list.get(i)),oldBoard);
                    Commands.finisherCheck();
                }
            }
        }
        if (Callience.canPrint){
            Commands.outputString.append(Commands.boardPrinter(Commands.board));
        }
    }
}