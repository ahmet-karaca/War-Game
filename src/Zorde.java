import java.util.*;

public class Zorde extends Persons{
    public static ArrayList<String[]> zordeArmy = new ArrayList<>();
    public static ArrayList<String> zordeArmyList = new ArrayList<>();
    public static boolean canContinue = true;
    public static String[] friends = {"O","T","G"};
    public static String[] enemies = {"H","E","D"};
    public Zorde(String name, int x, int y, int HP, int AP, int maxMove, int maxHP) {
        super(name, x, y, HP, AP, maxMove, maxHP);
    }
}