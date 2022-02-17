import java.util.*;

public class Callience extends Persons{
    public static ArrayList<String[]> calliArmy = new ArrayList<>();
    public static ArrayList<String> calliArmyList = new ArrayList<>();
    public static boolean canContinue = true;
    public static boolean canPrint = true;
    public static String[] friends = {"H","E","D"};
    public static String[] enemies = {"O","T","G"};
    public static int moveCounter = 0;

    public Callience(String name, int x, int y, int HP, int AP, int maxMove, int maxHP) {
        super(name, x, y, HP, AP, maxMove, maxHP);
    }

    public static void move(Callience callience, int x, int y, ArrayList<ArrayList<String>> oldBoard){
        canContinue = true;
        canPrint = true;
        int oldX = callience.getX();
        int oldY = callience.getY();
        int newX = oldX + x;
        int newY = oldY + y;

        // If its destination is on the map
        if ((newX >= 0 && newX <= Commands.board.size()) && (newY >= 0 && newY <= Commands.board.size())){
            moveCounter++;

            // If the destination is empty
            // Go - attack enemies if attackStep is eligible
            if (Commands.board.get(newY).get(newX).equals("  ")){
                callience.setX(newX);
                callience.setY(newY);
                Commands.board.get(newY).set(newX, callience.getName());
                Commands.board.get(oldY).set(oldX,"  ");
                if (callience.getClass().getName().equals("Human")){
                    if (moveCounter == 3){
                        attack(callience);
                        moveCounter = 0;
                    }
                }
                if (callience.getClass().getName().equals("Elf")){
                    if (moveCounter < 4){
                        attack(callience);
                    }
                    if (moveCounter == 4){
                        Elf.rangedAttack((Elf) callience);
                    }
                }
            }

            // If the destination is ally
            else if (Commands.board.get(newY).get(newX).startsWith("H") || Commands.board.get(newY).get(newX).startsWith("E") || Commands.board.get(newY).get(newX).startsWith("D")){
                canContinue = false;
                System.out.println(Commands.boardPrinter(Commands.board));
            }

            // If the destination is enemy - fight to death with the enemy
            else if (Commands.board.get(newY).get(newX).startsWith("O") || Commands.board.get(newY).get(newX).startsWith("T") || Commands.board.get(newY).get(newX).startsWith("G")){
                canContinue = false;

                // If the enemy's health drops to 0 or below
                if (((Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP())-callience.getAP()) <= 0){
                    Zorde.zordeArmyList.remove(Commands.board.get(newY).get(newX));
                    callience.setX(newX);
                    callience.setY(newY);
                    Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).setHP(Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP() - callience.getAP());
                    Commands.board.get(newY).set(newX, callience.getName());
                    Commands.board.get(oldY).set(oldX,"  ");
                    System.out.println(Commands.boardPrinter(Commands.board));
                }

                // If the enemy's health is greater than 0
                else{
                    Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).setHP(((Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP())-callience.getAP()));

                    // If the attacker has more health than the defender
                    if (Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).getHP() > Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP()){
                        int attackerLastHP = (Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).getHP() - Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP());
                        int defenderLastHP = Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP() - Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).getHP();
                        Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).setHP(defenderLastHP);
                        Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).setHP(attackerLastHP);
                        System.out.println(Commands.board.get(newY).get(newX));
                        Zorde.zordeArmyList.remove(Commands.board.get(newY).get(newX));
                        callience.setX(newX);
                        callience.setY(newY);
                        Commands.board.get(newY).set(newX, callience.getName());
                        Commands.board.get(oldY).set(oldX,"  ");
                    }

                    // If the attacker is equal to the defender
                    else if(Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).getHP() == Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP()){
                        Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).setHP(0);
                        Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).setHP(0);
                        Zorde.zordeArmyList.remove(Commands.board.get(newY).get(newX));
                        Callience.calliArmyList.remove(Commands.board.get(oldY).get(oldX));
                        Commands.board.get(oldY).set(oldX,"  ");
                        Commands.board.get(newY).set(newX,"  ");
                    }

                    //If the attacker has less health than the defender
                    else{
                        int attackerLastHP = Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).getHP() - Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP();
                        int defenderLastHP = Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP() - Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).getHP();
                        Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).setHP(attackerLastHP);
                        Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).setHP(defenderLastHP);
                        Callience.calliArmyList.remove(Commands.board.get(oldY).get(oldX));
                        Commands.board.get(oldY).set(oldX,"  ");
                    }
                }
            }
        }

        // If his destination is off the map
        else {
            canPrint = false;
            canContinue = false;
            if (!oldBoard.equals(Commands.board)){
                Commands.outputString.append(Commands.boardPrinter(Commands.board));
            }
            Commands.outputString.append("Error : Game board boundaries are exceeded. Input line ignored.\n").append("\n");
        }
    }

    public static void attack(Callience callience){
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((callience.getX() + (j) >= 0 && callience.getX() + (j) <= Commands.board.size()) &&(callience.getY() + (i) >= 0 && callience.getY() + (i) <= Commands.board.size())){
                    if (Commands.board.get(callience.getY() + (i)).get(callience.getX() + (j)).startsWith("O") || Commands.board.get(callience.getY() + (i)).get(callience.getX() + (j)).startsWith("T") || Commands.board.get(callience.getY() + (i)).get(callience.getX() + (j)).startsWith("G")){

                        // If the enemy dies
                        if (((Persons.personsHashMap.get(Commands.board.get(callience.getY() + (i)).get(callience.getX() + (j))).getHP())-callience.getAP()) <= 0){
                            Zorde.zordeArmyList.remove(Commands.board.get(callience.getY() + (i)).get(callience.getX() + (j)));
                            Persons.personsHashMap.get(Commands.board.get(callience.getY() + (i)).get(callience.getX() + (j)));
                            Persons.personsHashMap.get(Commands.board.get(callience.getY() + (i)).get(callience.getX() + (j))).setHP(((Persons.personsHashMap.get(Commands.board.get(callience.getY() + (i)).get(callience.getX() + (j))).getHP())-callience.getAP()));
                            Commands.board.get(callience.getY() + (i)).set(callience.getX() + (j),"  ");
                        }

                        // If the enemy does not die
                        else{
                            Persons.personsHashMap.get(Commands.board.get(callience.getY() + (i)).get(callience.getX() + (j))).setHP(((Persons.personsHashMap.get(Commands.board.get(callience.getY() + (i)).get(callience.getX() + (j))).getHP())-callience.getAP()));
                        }
                    }
                }
            }
        }
    }
}