import java.io.IOException;
import java.util.*;

public class Ork extends Zorde{
    private final int healPoints;
    public static HashMap<String,Ork> orkHash = new HashMap<>();

    public Ork(String name, int x, int y, int HP, int AP, int maxMove, int maxHP, int healPoints){
        super(name, x, y, HP, AP, maxMove, maxHP);
        this.healPoints = healPoints;
    }

    public static void moveCaller(Ork ork, List<String> list) throws IOException {
        ArrayList<ArrayList<String>> oldBoard = Commands.oldBoardMaker();
        Persons.canContinue = true;
        move(ork,Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)),friends,enemies,oldBoard);
        Commands.finisherCheck();
        if (Persons.canPrint){
            Commands.outputString.append(Commands.boardPrinter(Commands.board));
        }
    }

    public static void healing(Ork ork){
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((ork.getX() + (j) >= 0 && ork.getX() + (j) < Commands.board.size()) && (ork.getY() + (i) >= 0 && ork.getY() + (i) < Commands.board.size())) {

                    // If there is friendly unit nearby
                    if (Commands.board.get(ork.getY() + (i)).get(ork.getX() + (j)).startsWith("O") || Commands.board.get(ork.getY() + (i)).get(ork.getX() + (j)).startsWith("T") || Commands.board.get(ork.getY() + (i)).get(ork.getX() + (j)).startsWith("G")){

                        // If current health is less than MaxHP
                        if (Persons.personsHashMap.get(Commands.board.get(ork.getY() + (i)).get(ork.getX() + (j))).getHP() < Persons.personsHashMap.get(Commands.board.get(ork.getY() + (i)).get(ork.getX() + (j))).getMaxHP()){

                            // Add health if new HP is less than or equal to MaxHP
                            if ((Persons.personsHashMap.get(Commands.board.get(ork.getY() + (i)).get(ork.getX() + (j))).getHP() + ork.healPoints) <= (Persons.personsHashMap.get(Commands.board.get(ork.getY() + (i)).get(ork.getX() + (j))).getMaxHP())){
                                Persons.personsHashMap.get(Commands.board.get(ork.getY() + (i)).get(ork.getX() + (j))).setHP(Persons.personsHashMap.get(Commands.board.get(ork.getY() + (i)).get(ork.getX() + (j))).getHP() + ork.healPoints);
                            }

                            // Equal health to MaxHP if new health is greater than MaxHP
                            else{
                                Persons.personsHashMap.get(Commands.board.get(ork.getY() + (i)).get(ork.getX() + (j))).setHP(Persons.personsHashMap.get(Commands.board.get(ork.getY() + (i)).get(ork.getX() + (j))).getMaxHP());
                            }
                        }
                    }
                }
            }
        }
    }
}