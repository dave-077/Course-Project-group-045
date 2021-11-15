import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Game extends JFrame implements MouseListener{
    static int state = -1;
    static int X = -1;
    static int Y = -1;
    static int Combat = 0;
    static int Animation = 0;
    static int Map = 1;
    static int End = 0;
    static ArrayList <Character> playerChar = new ArrayList<>();
    static ArrayList <Character> enemyChar = new ArrayList<>();
    static Character selectedChar;
    static Character selectedEnemy;
    static boolean itemSelect = false;
    static ArrayList<Item> itemList = new ArrayList<>();
    static Map currMap;

    public void mousePressed(MouseEvent e) {
        if (Game.Animation == 0){
            X = (int) (Math.ceil((e.getX() - 205) / 75));
            Y = (int) (Math.ceil((e.getY() + 45) / 75));
            if (0 > X | X > 6 | 0 > Y | Y > 8){
                selectedChar = null;
            }
        }else {
            X = -1;
            Y = -1;
        }
        //currtile is the map character associated with the tile clicked
        Character currTile = currMap.getCharByPos(X, Y);
        //if a player character is already selected
        if (selectedChar != null) {
            //if the clicked tile is an enemy, perform the attack then deselect all characters
            if (enemyChar.contains(currTile) && Action.attackable(selectedChar, currTile)) {
                Action.attack(selectedChar, currTile);
                Combat = 1;
                selectedEnemy = currTile;
                if (currTile.getCurrHealth() <= 0){
                    System.out.println(currTile.getName() + " has perished");
                    currMap.removeChar(currTile);
                }
                if(!currMap.contains(enemyChar)){
                    End = 1;
                }

            /*if an empty tile is also selected and is able to be moved into, move the character to the position
             then deselect all characters  */
            }else if (currTile == null && Action.moveable(selectedChar, X, Y)){
                Action.move(selectedChar, X, Y);
                selectedChar = null;
            } else if (!Action.moveable(selectedChar, X, Y)){
                selectedChar = null;
            }
            //presumeably a item is selected through the popup menu
            if (selectedChar != null && itemSelect ){
                //itemList.get().use_item(selectedChar);
            }
            if (X == 1 && Y == 9){
                itemSelect = true;
                //presumeably this triggers some popup in the UI listing all available items
            }
        }
        //if no character is currently selected and tile has a player character on it, select the player character
        if (playerChar.contains(currTile)){
            selectedChar = currTile;
        }
        //if the click uses the last playable character action
        if (!checkActions(playerChar)) {
            System.out.print("End of Player Turn");
            //this should ultimately be replaced with some method call that conducts the Enemy AI's turn
            setActions(playerChar);
        }
    }


    public void mouseReleased(MouseEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }

    public Game() {
        initUI();
    }

    private void initUI() {
        add(new UI());
        setResizable(false);
        pack();
        setTitle("Course Project");
        setLocation(150,20);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addMouseListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Map map1 = new Map(6, 8);
        currMap = map1;
        PlayerChar player1 = new PlayerChar("Marth", 75, 40, 2);
        PlayerChar player2 = new PlayerChar("Hector", 75, 60, 3);
        EnemyChar enemy1 = new EnemyChar("Sword", 100, 10, 2);
        EnemyChar enemy2 = new EnemyChar("Axe", 100, 10, 2);
        playerChar.add(player1);
        playerChar.add(player2);
        enemyChar.add(enemy1);
        enemyChar.add(enemy2);
        map1.addChar(enemy1, 3,1);
        map1.addChar(enemy2, 4,1);
        map1.addChar(player1, 3,8);
        map1.addChar(player2, 4,8);
        EventQueue.invokeLater(() -> {
            JFrame ex = new Game();
            ex.setVisible(true);
        });
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


    public static Character getCharacterByName(String name, ArrayList<Character> list){
        for (Character character: list){
            if (name.equals(character.getName())){
                return character;
            }
        }
        return null;
    }
}
