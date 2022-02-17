import java.io.IOException;
import java.util.*;

public class Elf extends Callience{
    private final int rangedAP;
    public static HashMap<String, Elf> elfHash = new HashMap<>();

    public Elf(String name, int x, int y, int HP, int AP, int maxMove, int maxHP, int rangedAP) {
        super(name, x, y, HP, AP, maxMove, maxHP);
        this.rangedAP = rangedAP;
    }

    public static void moveCaller(Elf elf, List<String> list) throws IOException {
        ArrayList<ArrayList<String>> oldBoard = Commands.oldBoardMaker();
        Callience.canContinue = true;
        Callience.moveCounter = 0;
        for (int i = 0; i < list.size(); i++) {
            if(Callience.canContinue){
                if (i%2 == 1){
                    move(elf, Integer.parseInt(list.get(i-1)),Integer.parseInt(list.get(i)),oldBoard);
                    Commands.finisherCheck();
                }
            }
        }
        if (Callience.canPrint){
            Commands.outputString.append(Commands.boardPrinter(Commands.board));
        }
    }

    public static void rangedAttack(Elf elf){
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                if ((elf.getX() + (j) >= 0 && elf.getX() + (j) < Commands.board.size()-1) &&(elf.getY() + (i) >= 0 && elf.getY() + (i) < Commands.board.size()-1)){
                    if (Commands.board.get(elf.getY() + (i)).get(elf.getX() + (j)).startsWith("O") || Commands.board.get(elf.getY() + (i)).get(elf.getX() + (j)).startsWith("T") || Commands.board.get(elf.getY() + (i)).get(elf.getX() + (j)).startsWith("G")){

                        // If the enemy dies
                        if (((Persons.personsHashMap.get(Commands.board.get(elf.getY() + (i)).get(elf.getX() + (j))).getHP())-elf.rangedAP) <= 0){
                            Persons.personsHashMap.get(Commands.board.get(elf.getY() + (i)).get(elf.getX() + (j)));
                            Persons.personsHashMap.get(Commands.board.get(elf.getY() + (i)).get(elf.getX() + (j))).setHP(((Persons.personsHashMap.get(Commands.board.get(elf.getY() + (i)).get(elf.getX() + (j))).getHP())-elf.rangedAP));
                            Commands.board.get(elf.getY() + (i)).set(elf.getX() + (j),"  ");
                        }

                        // If the enemy does not die
                        else{
                            Persons.personsHashMap.get(Commands.board.get(elf.getY() + (i)).get(elf.getX() + (j))).setHP(((Persons.personsHashMap.get(Commands.board.get(elf.getY() + (i)).get(elf.getX() + (j))).getHP())-elf.rangedAP));
                        }
                    }
                }
            }
        }
    }
}