import java.util.*;

public class Persons {
    private String name;
    private int x,y;
    private int HP,AP,maxMove,maxHP;
    public static HashMap<String, Persons> personsHashMap = new HashMap<>();
    public static boolean canContinue = true;
    public static boolean canPrint = true;

    public Persons(String name, int x, int y, int HP, int AP, int maxMove, int maxHP){
        this.name = name;
        this.x = x;
        this.y = y;
        this.HP = HP;
        this.AP = AP;
        this.maxMove = maxMove;
        this.maxHP = maxHP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getAP() {
        return AP;
    }

    public void setAP(int AP) {
        this.AP = AP;
    }

    public int getMaxMove() {
        return maxMove;
    }

    public void setMaxMove(int maxMove) {
        this.maxMove = maxMove;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public static void move(Persons person, int x, int y, String[] friends, String[] enemies, ArrayList<ArrayList<String>> oldBoard){
        canContinue = true;
        canPrint = true;
        int oldX = person.getX();
        int oldY = person.getY();
        int newX = oldX + x;
        int newY = oldY + y;

        // If its destination is on the map
        try {
            if ((newX >= 0 && newX <= Commands.board.size()) && (newY >= 0 && newY <= Commands.board.size())){
                if (person.getClass().getName().equals("Ork")){
                    Ork.healing((Ork) person);
                }

                // If the destination is empty
                // Go - attack enemies if attackStep is eligible
                if (Commands.board.get(newY).get(newX).equals("  ")){
                    person.setX(newX);
                    person.setY(newY);
                    Commands.board.get(newY).set(newX, person.getName());
                    Commands.board.get(oldY).set(oldX,"  ");
                    attack(person, enemies);
                }

                // If the destination is ally
                else if (Commands.board.get(newY).get(newX).startsWith(friends[0]) || Commands.board.get(newY).get(newX).startsWith(friends[1]) || Commands.board.get(newY).get(newX).startsWith(friends[2])){
                    canContinue = false;
                }

                // If the destination is enemy - fight to death with the enemy
                else if (Commands.board.get(newY).get(newX).startsWith(enemies[0]) || Commands.board.get(newY).get(newX).startsWith(enemies[1]) || Commands.board.get(newY).get(newX).startsWith(enemies[2])){
                    canContinue = false;

                    // If the enemy's health drops to 0 or below
                    if (((Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP())-person.getAP()) <= 0){
                        if (Commands.board.get(newY).get(newX).startsWith("O") || Commands.board.get(newY).get(newX).startsWith("G") || Commands.board.get(newY).get(newX).startsWith("T")){
                            Zorde.zordeArmyList.remove(Commands.board.get(newY).get(newX));
                        }
                        if (Commands.board.get(newY).get(newX).startsWith("E") || Commands.board.get(newY).get(newX).startsWith("D") || Commands.board.get(newY).get(newX).startsWith("H")){
                            Callience.calliArmyList.remove(Commands.board.get(newY).get(newX));
                        }

                        person.setX(newX);
                        person.setY(newY);
                        Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).setHP(Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP() - person.getAP());
                        Commands.board.get(newY).set(newX, person.getName());
                        Commands.board.get(oldY).set(oldX,"  ");
                    }

                    // If the enemy's health is greater than 0
                    else{
                        Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).setHP(((Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP())-person.getAP()));

                        // If the attacker has more health than the defender
                        if (Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).getHP() > Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP()){
                            int attackerLastHP = (Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).getHP() - Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP());
                            int defenderLastHP = Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP() - Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).getHP();
                            Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).setHP(defenderLastHP);
                            Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).setHP(attackerLastHP);

                            if (Commands.board.get(newY).get(newX).startsWith("O") || Commands.board.get(newY).get(newX).startsWith("G") || Commands.board.get(newY).get(newX).startsWith("T")){
                                Zorde.zordeArmyList.remove(Commands.board.get(newY).get(newX));
                            }
                            if (Commands.board.get(newY).get(newX).startsWith("E") || Commands.board.get(newY).get(newX).startsWith("D") || Commands.board.get(newY).get(newX).startsWith("H")){
                                Callience.calliArmyList.remove(Commands.board.get(newY).get(newX));
                            }
                            person.setX(newX);
                            person.setY(newY);
                            Commands.board.get(newY).set(newX, person.getName());
                            Commands.board.get(oldY).set(oldX,"  ");
                        }

                        // If the attacker is equal to the defender
                        else if(Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).getHP() == Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP()){
                            Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).setHP(0);
                            Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).setHP(0);

                            if (Commands.board.get(newY).get(newX).startsWith("O") || Commands.board.get(newY).get(newX).startsWith("G") || Commands.board.get(newY).get(newX).startsWith("T")){
                                Zorde.zordeArmyList.remove(Commands.board.get(newY).get(newX));
                            }
                            if (Commands.board.get(newY).get(newX).startsWith("E") || Commands.board.get(newY).get(newX).startsWith("D") || Commands.board.get(newY).get(newX).startsWith("H")){
                                Callience.calliArmyList.remove(Commands.board.get(newY).get(newX));
                            }
                            if (Commands.board.get(oldY).get(oldX).startsWith("O") || Commands.board.get(oldY).get(oldX).startsWith("G") || Commands.board.get(oldY).get(oldX).startsWith("T")){
                                Zorde.zordeArmyList.remove(Commands.board.get(oldY).get(oldX));
                            }
                            if (Commands.board.get(oldY).get(oldX).startsWith("E") || Commands.board.get(oldY).get(oldX).startsWith("D") || Commands.board.get(oldY).get(oldX).startsWith("H")){
                                Callience.calliArmyList.remove(Commands.board.get(oldY).get(oldX));
                            }
                            Commands.board.get(oldY).set(oldX,"  ");
                            Commands.board.get(newY).set(newX,"  ");
                        }

                        //If the attacker has less health than the defender
                        else{
                            int attackerLastHP = Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).getHP() - Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP();
                            int defenderLastHP = Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).getHP() - Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).getHP();
                            Persons.personsHashMap.get(Commands.board.get(oldY).get(oldX)).setHP(attackerLastHP);
                            Persons.personsHashMap.get(Commands.board.get(newY).get(newX)).setHP(defenderLastHP);
                            if (Commands.board.get(oldY).get(oldX).startsWith("O") || Commands.board.get(oldY).get(oldX).startsWith("G") || Commands.board.get(oldY).get(oldX).startsWith("T")){
                                Zorde.zordeArmyList.remove(Commands.board.get(oldY).get(oldX));
                            }
                            if (Commands.board.get(oldY).get(oldX).startsWith("E") || Commands.board.get(oldY).get(oldX).startsWith("D") || Commands.board.get(oldY).get(oldX).startsWith("H")){
                                Callience.calliArmyList.remove(Commands.board.get(oldY).get(oldX));
                            }
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
                throw new BoundaryException();
                //Commands.outputString.append("Error : Game board boundaries are exceeded. Input line ignored.\n").append("\n");

            }
        } catch (BoundaryException e) {
            Commands.outputString.append("Error : Game board boundaries are exceeded. Input line ignored.\n").append("\n");
        }
    }

    public static void attack(Persons person, String[] enemies){
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((person.getX() + (j) >= 0 && person.getX() + (j) < Commands.board.size()) &&(person.getY() + (i) >= 0 && person.getY() + (i) < Commands.board.size())){
                    if (Commands.board.get(person.getY() + (i)).get(person.getX() + (j)).startsWith(enemies[0]) || Commands.board.get(person.getY() + (i)).get(person.getX() + (j)).startsWith(enemies[1]) || Commands.board.get(person.getY() + (i)).get(person.getX() + (j)).startsWith(enemies[2])){

                        // If the enemy dies
                        if (((Persons.personsHashMap.get(Commands.board.get(person.getY() + (i)).get(person.getX() + (j))).getHP())-person.getAP()) <= 0){
                            if (Commands.board.get(person.getY() + (i)).get(person.getX() + (j)).startsWith("O") || Commands.board.get(person.getY() + (i)).get(person.getX() + (j)).startsWith("G") || Commands.board.get(person.getY() + (i)).get(person.getX() + (j)).startsWith("T")){
                                Zorde.zordeArmyList.remove(Commands.board.get(person.getY() + (i)).get(person.getX() + (j)));
                            }
                            if (Commands.board.get(person.getY() + (i)).get(person.getX() + (j)).startsWith("E") || Commands.board.get(person.getY() + (i)).get(person.getX() + (j)).startsWith("D") || Commands.board.get(person.getY() + (i)).get(person.getX() + (j)).startsWith("H")){
                                Callience.calliArmyList.remove(Commands.board.get(person.getY() + (i)).get(person.getX() + (j)));
                            }
                            Persons.personsHashMap.get(Commands.board.get(person.getY() + (i)).get(person.getX() + (j)));
                            Persons.personsHashMap.get(Commands.board.get(person.getY() + (i)).get(person.getX() + (j))).setHP(((Persons.personsHashMap.get(Commands.board.get(person.getY() + (i)).get(person.getX() + (j))).getHP())-person.getAP()));
                            Commands.board.get(person.getY() + (i)).set(person.getX() + (j),"  ");
                        }

                        // If the enemy does not die
                        else{
                            Persons.personsHashMap.get(Commands.board.get(person.getY() + (i)).get(person.getX() + (j))).setHP(((Persons.personsHashMap.get(Commands.board.get(person.getY() + (i)).get(person.getX() + (j))).getHP())-person.getAP()));
                        }
                    }
                }
            }
        }
    }
}