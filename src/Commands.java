import java.io.*;
import java.util.*;

public class Commands {
    public static ArrayList<String> initialArray = new ArrayList<>();
    public static ArrayList<String> commandArray = new ArrayList<>();
    public static ArrayList<ArrayList<String>> board = new ArrayList<>();
    public static StringBuilder outputString = new StringBuilder();

    public static void command() throws IOException {
        int boardSize;
        String line;
        FileReader fReader = new FileReader(Main.initialsFile);
        BufferedReader bReader = new BufferedReader(fReader);
        while ((line = bReader.readLine()) != null) {
            initialArray.add(line);
        }
        String line2;
        FileReader fReader2 = new FileReader(Main.commandsFile);
        BufferedReader bReader2 = new BufferedReader(fReader2);
        while ((line2 = bReader2.readLine()) != null){
            commandArray.add(line2);
        }
        bReader.close();
        fReader.close();
        boardSize = Integer.parseInt(initialArray.get(1).split("x")[0]);
        board = emptyBoardMaker(boardSize);
        armies();
        boardSetter(Zorde.zordeArmy,Callience.calliArmy);
        outputString.append(boardPrinter(board));
        actions();
    }

    public static  ArrayList<ArrayList<String>> emptyBoardMaker(int size){
        ArrayList<ArrayList<String>> board = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                temp.add("  ");
            }
            board.add(temp);
        }
        return board;
    }

    public static ArrayList<ArrayList<String>> oldBoardMaker(){
        ArrayList<ArrayList<String>> oldBoard = new ArrayList<>();
        for (int i = 0; i < Commands.board.size(); i++) {
            ArrayList<String> temp = new ArrayList<>();
            oldBoard.add(temp);
            for (int j = 0; j < Commands.board.get(0).size(); j++) {
                oldBoard.get(i).add(Commands.board.get(i).get(j));
            }
        }
        return oldBoard;
    }

    public static String boardPrinter(ArrayList<ArrayList<String>> board){
        StringBuilder toString = new StringBuilder();
        String star = "*";
        int times = (board.size()*2)+2;
        String stars = String.join("",Collections.nCopies(times, star));
        toString.append(stars);
        toString.append("\n");
        for (int i = 0; i < board.size(); i++) {
            toString.append("*");
            for (int j = 0; j < board.size(); j++) {
                toString.append(board.get(i).get(j));
            }
            toString.append("*" + "\n");
        }
        toString.append(stars);
        toString.append("\n" + "\n");
        ArrayList<String>allArmyList = new ArrayList<>();
        allArmyList.addAll(Callience.calliArmyList);
        allArmyList.addAll(Zorde.zordeArmyList);
        Collections.sort(allArmyList);
        for (String str : allArmyList){
            toString.append(str).append("\t").append(Persons.personsHashMap.get(str).getHP()).append("\t(").append(Persons.personsHashMap.get(str).getMaxHP()).append(")\n");
        }
        toString.append("\n");
        return toString.toString();
    }

    public static void armies(){
        for (int i = 0; i < initialArray.size(); i++) {
            if (initialArray.get(i).equals("CALLIANCE")){
                int j = i+1;
                while (j < initialArray.size()){
                    Callience.calliArmy.add(initialArray.get(j).split(" "));
                    if (initialArray.get(j).split(" ")[0].equals("HUMAN")){
                        Persons newHuman = new Human(initialArray.get(j).split(" ")[1],Integer.parseInt(initialArray.get(j).split(" ")[2]),Integer.parseInt(initialArray.get(j).split(" ")[3]),100, Constants.humanAP, Constants.humanMaxMove,100);
                        Human.humanHash.put(initialArray.get(j).split(" ")[1], (Human) newHuman);
                        Persons.personsHashMap.put(initialArray.get(j).split(" ")[1],newHuman);
                        Callience.calliArmyList.add(initialArray.get(j).split(" ")[1]);
                    }
                    if (initialArray.get(j).split(" ")[0].equals("ELF")){
                        Persons newElf = new Elf(initialArray.get(j).split(" ")[1],Integer.parseInt(initialArray.get(j).split(" ")[2]),Integer.parseInt(initialArray.get(j).split(" ")[3]),70, Constants.elfAP, Constants.elfMaxMove,70, Constants.elfRangedAP);
                        Elf.elfHash.put(initialArray.get(j).split(" ")[1], (Elf) newElf);
                        Persons.personsHashMap.put(initialArray.get(j).split(" ")[1],newElf);
                        Callience.calliArmyList.add(initialArray.get(j).split(" ")[1]);
                    }
                    if (initialArray.get(j).split(" ")[0].equals("DWARF")){
                        Persons newDwarf = new Dwarf(initialArray.get(j).split(" ")[1],Integer.parseInt(initialArray.get(j).split(" ")[2]),Integer.parseInt(initialArray.get(j).split(" ")[3]),120,Constants.dwarfAP, Constants.dwarfMaxMove,120);
                        Dwarf.dwarfHash.put(initialArray.get(j).split(" ")[1], (Dwarf) newDwarf);
                        Persons.personsHashMap.put(initialArray.get(j).split(" ")[1],newDwarf);
                        Callience.calliArmyList.add(initialArray.get(j).split(" ")[1]);
                    }
                    j++;
                    if (initialArray.get(j).equals("")){
                        break;
                    }
                }
            }
            if (initialArray.get(i).equals("ZORDE")){
                int j = i+1;
                while (j < initialArray.size()){
                    Zorde.zordeArmy.add(initialArray.get(j).split(" "));
                    if (initialArray.get(j).split(" ")[0].equals("ORK")){
                        Persons newOrk = new Ork(initialArray.get(j).split(" ")[1],Integer.parseInt(initialArray.get(j).split(" ")[2]),Integer.parseInt(initialArray.get(j).split(" ")[3]),200, Constants.orkAP,Constants.orkMaxMove, 200, Constants.orkHealPoints);
                        Ork.orkHash.put(initialArray.get(j).split(" ")[1], (Ork) newOrk);
                        Persons.personsHashMap.put(initialArray.get(j).split(" ")[1],newOrk);
                        Zorde.zordeArmyList.add(initialArray.get(j).split(" ")[1]);
                    }
                    if(initialArray.get(j).split(" ")[0].equals("TROLL")){
                        Persons newTroll = new Troll(initialArray.get(j).split(" ")[1],Integer.parseInt(initialArray.get(j).split(" ")[2]),Integer.parseInt(initialArray.get(j).split(" ")[3]),150, Constants.trollAP,Constants.trollMaxMove, 150);
                        Troll.trollHash.put(initialArray.get(j).split(" ")[1], (Troll) newTroll);
                        Persons.personsHashMap.put(initialArray.get(j).split(" ")[1],newTroll);
                        Zorde.zordeArmyList.add(initialArray.get(j).split(" ")[1]);
                    }
                    if(initialArray.get(j).split(" ")[0].equals("GOBLIN")){
                        Persons newGoblin = new Goblin(initialArray.get(j).split(" ")[1],Integer.parseInt(initialArray.get(j).split(" ")[2]),Integer.parseInt(initialArray.get(j).split(" ")[3]),80, Constants.goblinAP, Constants.goblinMaxMove, 80);
                        Goblin.goblinHash.put(initialArray.get(j).split(" ")[1], (Goblin) newGoblin);
                        Persons.personsHashMap.put(initialArray.get(j).split(" ")[1],newGoblin);
                        Zorde.zordeArmyList.add(initialArray.get(j).split(" ")[1]);
                    }
                    j++;
                }
            }
        }
    }

    public static void boardSetter(ArrayList<String[]> army1, ArrayList<String[]> army2){
        ArrayList<ArrayList<String[]>> armyList = new ArrayList<>();
        armyList.add(army1);
        armyList.add(army2);
        for (ArrayList<String[]> army : armyList){
            for (String[] strarray : army){
                board.get(Integer.parseInt(strarray[3])).set(Integer.parseInt(strarray[2]),strarray[1]);
            }
        }
    }

    public static void actions() throws IOException {
        for (String str : commandArray){
            String character = str.split(" ")[0];
            String[] array = str.split(" ")[1].split(";");
            List<String> list = Arrays.asList(array);
            try {
                if ((list.size()) / 2 == Persons.personsHashMap.get(character).getMaxMove()) {
                    if (character.startsWith("O")) {
                        Ork.moveCaller((Ork) Persons.personsHashMap.get(character), list);
                    }
                    if (character.startsWith("T")) {
                        Troll.moveCaller((Troll) Persons.personsHashMap.get(character), list);
                    }
                    if (character.startsWith("G")) {
                        Goblin.moveCaller((Goblin) Persons.personsHashMap.get(character), list);
                    }
                    if (character.startsWith("H")) {
                        Human.moveCaller((Human) Persons.personsHashMap.get(character), list);
                    }
                    if (character.startsWith("D")) {
                        Dwarf.moveCaller((Dwarf) Persons.personsHashMap.get(character), list);
                    }
                    if (character.startsWith("E")) {
                        Elf.moveCaller((Elf) Persons.personsHashMap.get(character), list);
                    }
                } else {
                    throw new MoveCountException();
                    //outputString.append("Error : Move sequence contains wrong number of move steps. Input line ignored.\n").append("\n");
                }
            }
            catch (MoveCountException e) {
                outputString.append("Error : Move sequence contains wrong number of move steps. Input line ignored.\n").append("\n");
            }
        }
    }

    public static void finisherCheck() throws IOException {
        if (Zorde.zordeArmyList.isEmpty()){
            outputString.append(Commands.boardPrinter(Commands.board));
            outputString.append("\n").append("Game Finished").append("\n").append("Calliance Wins");
            outputCreater();
            System.exit(0);
        }
        else if(Callience.calliArmyList.isEmpty()){
            outputString.append(Commands.boardPrinter(Commands.board));
            outputString.append("\n").append("Game Finished").append("\n").append("Zorde Wins");
            outputCreater();
            System.exit(0);
        }

    }

    public static void outputCreater() throws IOException {
        File outputFile = new File(Main.outputFile);
        FileWriter fWriter = new FileWriter(outputFile,false);
        BufferedWriter bWriter = new BufferedWriter(fWriter);
        bWriter.write(outputString.toString());
        bWriter.close();
    }
}