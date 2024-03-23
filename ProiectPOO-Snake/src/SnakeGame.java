import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private class Patrat {
        int x;
        int y;

        Patrat(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int patratSize = 25;
    int viteza=300;
    int scor = 0;

    Patrat snakeCap;
    ArrayList<Patrat> snakeCorp;

    Patrat papa;
    Patrat otravitor;
    Patrat bonus;
    private boolean bonusEaten = false;
    int nrVieti=3;
    Random rand;

    Timer gameLoop;
    int dirX;
    int dirY;
    boolean gameOver=false;
    private Boolean gamestarts;

    ImageIcon papaImage;
    ImageIcon otravitorImage;
    ImageIcon bonusImage;
    ImageIcon[] snakeHeadImage;
    ImageIcon backgroundImage;
    ImageIcon[] viataImage;

    public SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeCap = new Patrat(5, 5);
        snakeHeadImage = new ImageIcon[4];
        snakeHeadImage[0]= new ImageIcon(Joculet.class.getResource("/imagini_snake/snakeHead_sus.png"));
        snakeHeadImage[2]= new ImageIcon(Joculet.class.getResource("/imagini_snake/snakeHead_stanga.png"));
        snakeHeadImage[1]= new ImageIcon(Joculet.class.getResource("/imagini_snake/snakeHead_jos.png"));
        snakeHeadImage[3]= new ImageIcon(Joculet.class.getResource("/imagini_snake/snakeHead_dreapta.png"));

        snakeCorp=new ArrayList<Patrat>();
        gamestarts = false;

        papa = new Patrat(10, 10);
        papaImage = new ImageIcon(Joculet.class.getResource("/imagini_snake/pizza_final.gif"));
        rand=new Random();
        placePizza();

        otravitor = new Patrat(15, 15);
        otravitorImage = new ImageIcon(Joculet.class.getResource("/imagini_snake/ezgif.com-resize.gif"));
        rand=new Random();
        placeOtravitor();

        bonusImage = new ImageIcon(SnakeGame.class.getResource("/imagini_snake/stea.gif"));
        bonus = new Patrat(25, 25);
            placeBonus();

         viataImage=new ImageIcon[3];
         for(int i=0; i<3;i++){
             viataImage[i]=new ImageIcon(Joculet.class.getResource("/imagini_snake/anaconda.png"));
         }
        dirX=0;
        dirY=0;
        gameLoop=new Timer(200, this);
        gameLoop.start();
        addKeyListener(this);
        setFocusable(true);
        backgroundImage = new ImageIcon(Joculet.class.getResource("/imagini_snake/Cartoon_green_texture_grass.jpg"));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(gamestarts) {
            drawBackground(g);
            draw(g);
        }else{
            drawintro(g);
        }
    }
    public void drawintro(Graphics g){
        String mesaj="PRESS K TO START";
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawImage(backgroundImage.getImage(), 0, 0, boardWidth, boardHeight, this);
        g.drawString(mesaj,150,300);
    }
    public void drawBackground(Graphics g) {
        // Desenează imaginea de fundal a panoului de joc
        g.drawImage(backgroundImage.getImage(), 0, 0, boardWidth, boardHeight, this);
    }
    public void draw(Graphics g) {
        // Desenează planșa de joc
        if (!gameOver) {
        // Desenează imaginea pentru pizza
        g.drawImage(papaImage.getImage(), papa.x * patratSize, papa.y * patratSize, patratSize, patratSize, this);
        g.drawImage(otravitorImage.getImage(), otravitor.x * patratSize, otravitor.y * patratSize, patratSize, patratSize, this);
        g.drawImage(bonusImage.getImage(), bonus.x * patratSize, bonus.y * patratSize, patratSize, patratSize, this);
        for(int i=0;i<nrVieti;i++){
            g.drawImage(viataImage[i].getImage(), boardWidth - (i + 1) * patratSize, 0, patratSize, patratSize, this);
        }
        int headIndex = 0;
            if (dirY == -1) {
                headIndex = 0;  // Sus
            } else if (dirY == 1) {
                headIndex = 1;  // Jos
            } else if (dirX == -1) {
                headIndex = 2;  // Stânga
            } else if (dirX == 1) {
                headIndex = 3;  // Dreapta
            }
        // Desenează capul șarpelui
        g.setColor(new java.awt.Color(53, 115, 4));
            g.drawImage(snakeHeadImage[headIndex].getImage(), snakeCap.x * patratSize, snakeCap.y * patratSize, patratSize, patratSize,this);

        //Deseneaza corpul sarpelui
        for(int i=0;i<snakeCorp.size();i++){
            Patrat snakePart=snakeCorp.get(i);
            g.fill3DRect(snakePart.x * patratSize, snakePart.y * patratSize, patratSize, patratSize,true);}

            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.setColor(Color.red);
            g.drawString("Scor: "+String.valueOf(scor),patratSize-16, patratSize );
        }else {
            g.drawImage(backgroundImage.getImage(), 0, 0, boardWidth, boardHeight, this);

            // Desenează textul "GAME OVER" în mijlocul ecranului
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics fontMetrics = g.getFontMetrics();
            int textWidth = fontMetrics.stringWidth("GAME OVER");
            int textHeight = fontMetrics.getHeight();
            int x = (boardWidth - textWidth) / 2;
            int y = (boardHeight - textHeight) / 2;
            g.drawString("GAME OVER", x, y);

            // Desenează scorul
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("Scor final: " + String.valueOf(scor), x + 10, y + textHeight + 20);
        }
    }

    public void placePizza(){
        papa.x=rand.nextInt(boardWidth/patratSize);// aici vine 600/25=24
        papa.y=rand.nextInt(boardHeight/patratSize);

    }
    public void placeOtravitor(){
        do {
            otravitor.x = rand.nextInt(boardWidth / patratSize);
            otravitor.y = rand.nextInt(boardHeight / patratSize);
        } while (coliziune(otravitor, snakeCap) || coliziune(otravitor, papa));
    }
    public void checkBonus() {
        if (coliziune(snakeCap, bonus)) {
            scor += 5;
            bonusEaten = true;
            bonus.x = -1;
            bonus.y = -1;
        }
        if (scor % 7 == 3 && bonusEaten) {
            placeBonus();
            bonusEaten = false;
        }
    }
    private void placeBonus() {
            do {
                bonus.x = rand.nextInt(boardWidth / patratSize);
                bonus.y = rand.nextInt(boardHeight / patratSize);
            } while (coliziune(bonus, snakeCap) || coliziune(otravitor, papa) || coliziune(bonus, papa) || coliziune(bonus, otravitor));
        }

    public boolean coliziune(Patrat patrat1, Patrat patrat2){
        return patrat1.x==patrat2.x && patrat1.y==patrat2.y;
    }

    public void move(){
        //mananca pizza
        if(coliziune(snakeCap, papa)){
            snakeCorp.add(new Patrat(papa.x, papa.y));
            scor += 1;
            viteza -= 10;
            gameLoop.setDelay(viteza);
            placePizza();
        }
        if(coliziune(snakeCap, otravitor)) {
            nrVieti--;
            viteza += 3;
            scor -= 1;
            gameLoop.setDelay(viteza);
           /* if (scor <= 0 || nrVieti<=0) {
                scor=0;
                gameOver = true;}*/
            if(scor<=0){
                scor=0;
                gameOver = true;
            }
            if(nrVieti<=0) {
                gameOver = true;
            }
            if(nrVieti>0 && scor>0){
                placeOtravitor();
            }
        }

        for(int i=snakeCorp.size()-1;i>=0;i--){
            Patrat snakePart=snakeCorp.get(i);
            if(i==0){
                snakePart.x= snakeCap.x;
                snakePart.y= snakeCap.y;
            }
            else {
                Patrat prevSnakePart=snakeCorp.get(i-1);
                snakePart.x= prevSnakePart.x;
                snakePart.y= prevSnakePart.y;
            }
        }
        //pt sarpe
        snakeCap.x+=dirX;
        snakeCap.y+=dirY;

        //final joc
        for(int i=0;i<snakeCorp.size();i++){
            Patrat snakePart=snakeCorp.get(i);
            if(coliziune(snakeCap, snakePart)){
                gameOver=true;
            }
        }
// atinge margine
        if(snakeCap.x*patratSize<0 || snakeCap.x*patratSize>boardWidth||snakeCap.y*patratSize<0 || snakeCap.y*patratSize>boardHeight) {
                  gameOver=true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(gamestarts) {
           move();
           checkBonus();
           repaint();
           if (gameOver) {
               gameLoop.stop();
           }
       }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP && dirY!=1){
            dirX=0;
            dirY=-1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN && dirY!=-1){
            dirX=0;
            dirY=1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT && dirX!=1){
            dirX=-1;
            dirY=0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT&& dirX!=-1){
            dirX=1;
            dirY=0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_K){
            gamestarts = true;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
