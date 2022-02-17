import java.io.IOException;
import java.util.*;

public class Dwarf extends Callience{
    public static HashMap<String, Dwarf> dwarfHash = new HashMap<>();

    public Dwarf(String name, int x, int y, int HP, int AP, int maxMove, int maxHP) {
        super(name, x, y, HP, AP, maxMove, maxHP);
    }

    public static void moveCaller(Dwarf dwarf, List<String> list) throws IOException {
        ArrayList<ArrayList<String>> oldBoard = Commands.oldBoardMaker();
        Persons.canContinue = true;
        for (int i = 0; i < list.size(); i++) {
            if(Persons.canContinue){
                if (i%2 == 1){
                    move(dwarf,Integer.parseInt(list.get(i-1)), Integer.parseInt(list.get(i)),friends,enemies,oldBoard);
                    Commands.finisherCheck();
                }
            }
        }
        if (Persons.canPrint){
            Commands.outputString.append(Commands.boardPrinter(Commands.board));
        }
    }
}