import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static void main(String[] args){
        ArrayList<Character> map = new ArrayList<Character>(10);
        for (int i = 0; i < 10; i++){
            map.add(null);
        }
        ArrayList<Character> PlayerChar = new ArrayList<Character>();
        ArrayList<Character> EnemyChar = new ArrayList<Character>();
        PlayerChar player1 = new PlayerChar("Player 1");
        PlayerChar player2 = new PlayerChar("Player 2");
        EnemyChar enemy1 = new EnemyChar("Enemy 1");
        PlayerChar.add(player1);
        PlayerChar.add(player2);
        EnemyChar.add(enemy1);
        map.set(4, player1);
        map.set(5, enemy1);
        map.set(6, player2);
        Scanner sc = new Scanner(System.in);
        System.out.println(map.size());

        while(map.contains(player1) && map.contains(enemy1)) {
            for (Character currChar : PlayerChar) {
                System.out.println("player character " + currChar.getName() +
                        " at position " + map.indexOf(currChar) + ", health " + currChar.getCurrHealth() + "/" + currChar.getMaxHealth());
            }
            for (Character currChar : EnemyChar) {
                System.out.println("enemy character " + currChar.getName() +
                        " at position " + map.indexOf(currChar) + ", health " + currChar.getCurrHealth() + "/" + currChar.getMaxHealth());
            }
            System.out.println("Player Turn");
            // loop runs while at least one player character has action available
            while (checkActions(PlayerChar)) {
                System.out.println("enter position character to perform action");
                int inputInt = sc.nextInt();
                Character user = map.get(inputInt);
                // checks that there is a player character at the map index of the input
                if (PlayerChar.contains(user)){
                    // checks whether the player character has used their action
                    if (user.isActionUsed()){
                        System.out.println("Character has already used action");
                    }
                    else {
                        System.out.println("enter position of target");
                        int targetInt = sc.nextInt();
                        Character target = map.get(targetInt);
                        // checks if there is an enemy at the map index of the input
                        if (EnemyChar.contains(target)) {
                            Action.attack(user, target);
                            if (target.getCurrHealth() <= 0) {
                                System.out.println(target.getName() + " perished");
                                map.remove(targetInt);
                            }
                        }
                        else{
                            System.out.println("Not a valid target");
                        }
                    }
                }
                else{
                    System.out.println("invaid selection");
                }


            }
            // refreshes all actions
            setActions(PlayerChar);
            System.out.println("Enemy Turn");

        }
    if (map.contains(player1)){
        System.out.println("Victory for the Righteous");
    }
    else{
        System.out.println("Democracy dies in Darkness");
    }

    }

    public static boolean checkActions(ArrayList<Character> list){
        for (Character character : list){
            if (!(character.isActionUsed())){
                return true;
            }

        }
        return false;
    }
    public static void setActions(ArrayList<Character> list){
        for (Character character: list){
            character.restoreAction();
        }
    }
}
