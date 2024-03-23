import javax.swing.*;
import java.awt.*;
public class Joculet {
    public static void main(String[] args) throws Exception {
        int boardWidth=600;
        int boardHeight=600;
        JFrame frame=new JFrame("Snake");

        ImageIcon icon = new ImageIcon(Joculet.class.getResource("/imagini_snake/snake.png"));
        frame.setIconImage(icon.getImage());
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); // la centru
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

        SnakeGame snakeGame=new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack();
         snakeGame.requestFocus();
    }
}