package Project3_6513172;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class board extends JPanel implements ActionListener {
    private Newmain mainFrame;
    private MySoundEffect gameOversound;
    private Dimension d; // height and width of the board
    private final Font font = new Font("Helvetica", Font.BOLD, 15);
    private boolean inGame = false; // is the game in progress?
    private boolean dying = false; // is alive?
    private final int Block_Size = 24; // size of each block
    private final int N_block = 15; // number of blocks (15wx15h)
    private final int Screen_Size = N_block * Block_Size; // size of the screen
    private final int MAX_GHOSTS = 18; // maximum number of ghosts
    private final int Speed = 6; // speed of player
    private int currentLevel = 0; // current level
    private int maxLevel = 10; // maximum level
    private int N_Ghosts = 2; // number of ghosts
    private int lives, score; // lives and score
    private int[] dx, dy; // used to move player/ ghost
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy; // ghost position and it differential location
    private  int ghostSpeed = 3; // ghost speed
    private Image heart, ghost; // heart and ghost image
    private Image Player; // image of player
    private int player_x, player_y, player_dx, player_dy; // player position and it differential location
    private int req_dx, req_dy; // requested direction (control key)
    private short[] screenData; // screen data to redraw the screen
    private Timer timer; // timer to delay animation
    private final short levelData[] = {
            19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
            17, 16, 16, 16, 16, 24, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            25, 24, 24, 24, 28, 0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
            0,  0,  0,  0,  0,  0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
            19, 18, 18, 18, 18, 18, 16, 16, 16, 16, 24, 24, 24, 24, 20,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
            17, 16, 16, 16, 24, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
            17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 18, 18, 18, 18, 20,
            17, 24, 24, 28, 0, 25, 24, 24, 16, 16, 16, 16, 16, 16, 20,
            21, 0,  0,  0,  0,  0,  0,   0, 17, 16, 16, 16, 16, 16, 20,
            17, 18, 18, 22, 0, 19, 18, 18, 16, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            25, 24, 24, 24, 26, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
    }; // board data

    public int get_score(){return score;}
    public board(Newmain mainFrame) {
        this.mainFrame = mainFrame;
        loadImages();
        setVariables();
        addKeyListener(new cursorControl());
        setFocusable(true);
        startGame();

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        painting(g);
    }

    private void loadImages() {
        String path = "src/main/Java/Project3_6513172/resources/";
        Player = new ImageIcon(path + "sillyman_ingame.png").getImage();
        ghost = new ImageIcon(path + "f.png").getImage();
        heart = new ImageIcon(path + "a.png").getImage();
    }
    public void setMode(String mode){
        switch (mode){
            case "Newbie":
                timer.setDelay(1000);
                ghostSpeed=2;
                N_Ghosts=2;
                break;
            case "Easy":
                timer.setDelay(200);
                ghostSpeed=4;
                N_Ghosts=3;
                break;
            case "Normal":
                timer.setDelay(100);
                ghostSpeed=4;
                N_Ghosts=4;
                break;
            case "Hard":
                timer.setDelay(50);
                ghostSpeed=6;
                N_Ghosts=6;
                break;
            case "Nightmare":
                timer.setDelay(20);
                ghostSpeed=8;
                N_Ghosts=8;
                break;
        }
    }
    public void setChar(String character) {
        String path = "src/main/Java/Project3_6513172/resources/";
        switch (character) {
            case "SillyMan":
                Player = new ImageIcon(path + "sillyman_ingame.png").getImage();
                break;
            case "Copter":
                Player = new ImageIcon(path + "ter_ingame.png").getImage();
                break;
            case "Pluem":
                Player = new ImageIcon(path + "pluem_ingame.png").getImage();
                break;
            case "Phat":
                Player = new ImageIcon(path + "jumm_ingame.png").getImage();
                break;
            case "Grace":
                Player = new ImageIcon(path + "grace_ingame.png").getImage();
                break;
        }
    }

    public void Intro(Graphics2D g2d) {
        String start = "Press SPACE to start or press M to Exit";
        g2d.setColor(Color.yellow);
        g2d.drawString(start, (Screen_Size)/4-50, 150);
    }

    public void drawScore(Graphics2D g2d) {
        g2d.setFont(font);
        g2d.setColor(new Color(96, 128, 255));
        String s = "Score: " + score;
        g2d.drawString(s, Screen_Size / 2 + 96, Screen_Size + 16);

        // Add these lines to display the current level
        String level = "Level: " + currentLevel;
        g2d.drawString(level, Screen_Size / 2 + 96, Screen_Size + 32);

        for (int i = 0; i < lives; i++) {
            g2d.drawImage(heart, i * 28 + 8, Screen_Size + 1, this);
        }
    }

    private void setVariables() {
        screenData = new short [N_block * N_block];
        d = new Dimension(400, 400);
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        //ghostSpeed = new int[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];
        timer = new Timer(1000, this); //redraw every 40ms it is speed of game more time game will more slow
        timer.start();
    }


    private void startGame() {
        lives = 3;
        score = 0;
        increaseLevel();
        N_Ghosts = 2;
    }

    public void increaseLevel() {
        for (int i = 0; i < N_block * N_block; i++) {
            screenData[i] = levelData[i]; //draw board
        }
        if (currentLevel==maxLevel) {
            JOptionPane.showMessageDialog(null, "Congratulations!! Level cleared \n Your score is: " + score, "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.setVisible(true);
            Window parentWindow = SwingUtilities.getWindowAncestor(this);
            parentWindow.dispose();
        } else {
            setGame();
        }
        currentLevel++; // Increase the level each time a level is completed
    }

    private void playGame(Graphics2D g2d) {
        if (dying) {
            death();
        } else {
            movePlayer();
            drawPlayer(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }

    public void movePlayer() {
        int pos;
        short ch;
        if (player_x % Block_Size == 0 && player_y % Block_Size == 0) {
            pos = player_x / Block_Size + N_block * (int) (player_y / Block_Size);
            ch = screenData[pos]; //get the value of the block that player is standing
            if ((ch & 16) != 0) { //check if there is a point
                screenData[pos] = (short) (ch & 15); //remove the point
                score++;
            }
            if (req_dx != 0 || req_dy != 0) { //check if player is moving
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0) || //check if there is a wall
                        (req_dx == 1 && req_dy == 0 && (ch & 4) != 0) ||
                        (req_dx == 0 && req_dy == -1 && (ch & 2) != 0) ||
                        (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    player_dx = req_dx; //if no wall move
                    player_dy = req_dy;
                }
            }
            // Check for standstill
            if ((player_dx == -1 && player_dy == 0 && (ch & 1) != 0) ||
                    (player_dx == 1 && player_dy == 0 && (ch & 4) != 0) ||
                    (player_dx == 0 && player_dy == -1 && (ch & 2) != 0) ||
                    (player_dx == 0 && player_dy == 1 && (ch & 8) != 0)) {
                player_dx = 0;
                player_dy = 0;
            }
        }
        player_x = player_x + Speed * player_dx; //move player
        player_y = player_y + Speed * player_dy;
    }

    public void drawPlayer(Graphics2D g2d) {
        if (req_dx == -1) {
            g2d.drawImage(Player, player_x + 1,  player_y + 1, this);
        } else if (req_dx == 1) {
            g2d.drawImage(Player, player_x + 1,  player_y + 1, this);
        } else if (req_dy == -1) {
            g2d.drawImage(Player, player_x + 1,  player_y + 1, this);
        } else {
            g2d.drawImage(Player, player_x+ 1,  player_y + 1, this);
        }
    }

    public void moveGhosts(Graphics2D g2d) { // move ghost automatically
        int pos;
        int count;
        for (int i = 0; i < N_Ghosts; i++) {
            if (ghost_x[i] % Block_Size == 0 && ghost_y[i] % Block_Size == 0) { //check if ghost is standing on a block
                pos = ghost_x[i] / Block_Size + N_block * (int) (ghost_y[i] / Block_Size); //get the value of the block that ghost is standing
                count = 0;
                // 1 is left 2 is top 4 is right 8 is bottom
                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) { //check if there is a wall on the right and the ghost not move to the right it will move left
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }
                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) { //check if there is a wall on the top and the ghost not move to the top it will move down
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }
                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) { //check if there is a wall on the left and the ghost not move to the left it will move right
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }
                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) { //check if there is a wall on the bottom and the ghost not move to the top it will move up
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }
                if (count == 0) {
                    if ((screenData[pos] & 15) == 15) { //check if there is a point
                        ghost_dx[i] = 0; //ghost will stop
                        ghost_dy[i] = 0;
                    } else {
                        ghost_dx[i] = -ghost_dx[i]; //ghost will move to the opposite direction
                        ghost_dy[i] = -ghost_dy[i];
                    }
                } else {
                    count = (int) (Math.random() * count);
                    if (count > 3) {
                        count = 3; //random the direction of ghost
                    }
                    ghost_dx[i] = dx[count];
                    ghost_dy[i] = dy[count];
                }
            }
            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed);
            drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

            if (player_x > (ghost_x[i] - 12) && player_x < (ghost_x[i] + 12)
                    && player_y > (ghost_y[i] - 12) && player_y < (ghost_y[i] + 12)
                    && inGame) {
                dying = true;
            }
        }
    }

    public void drawGhost(Graphics2D g2d, int x, int y) {
        g2d.drawImage(ghost, x, y, this);
    }

    private void checkMaze() {

        short i = 0;
        boolean finished = true;

        while (i < N_block * N_block && finished) {

            if ((screenData[i] & 48) != 0) { //check if there is a point remain
                finished = false;
            }

            i++;
        }

        if (finished) {

            score += 100;
            if (currentLevel < maxLevel) {
                N_Ghosts++;
            }
            inGame = false;
            JOptionPane.showMessageDialog(null, "Congratulations!! go to next level.", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            lives=3;
            inGame =  true;
            increaseLevel();
        }
    }
    public void playmusic(){
        gameOversound = new MySoundEffect("src/main/Java/Project3_6513172/resources/over.wav");
        gameOversound.playOnce();
        gameOversound.setVolume(0.2f);
    }

    public void death() {
        lives--;
        if (lives == 0) {
            inGame = false;
            playmusic();
            JOptionPane.showMessageDialog(null, "Game Over!!. Your score is: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            lives = 3;
            for (int i = 0; i < N_block * N_block; i++) {
                screenData[i] = levelData[i]; //draw board
            }
        }
        setGame();
    }

    public void drawMaze(Graphics2D g2d) {
        int i = 0;
        int x, y;
        for (y = 0; y < Screen_Size; y += Block_Size) {
            for (x = 0; x < Screen_Size; x += Block_Size) {
                g2d.setColor(new Color(0, 72, 251)); //set color of the wall
                g2d.setStroke(new BasicStroke(3)); //set the width of the wall
                if ((levelData[i] == 0)) { //check if there is a wall
                    g2d.fillRect(x, y, Block_Size, Block_Size); //draw the block
                }
                if ((screenData[i] & 1) != 0) { //check if there is a wall on the right
                    g2d.drawLine(x, y, x, y + Block_Size - 1);
                }
                if ((screenData[i] & 2) != 0) { //check if there is a wall on the top
                    g2d.drawLine(x, y, x + Block_Size - 1, y); //draw the wall
                }
                if ((screenData[i] & 4) != 0) { //check if there is a wall on the left
                    g2d.drawLine(x + Block_Size - 1, y, x + Block_Size - 1,
                            y + Block_Size - 1);
                }
                if ((screenData[i] & 8) != 0) { //check if there is a wall on the bottom
                    g2d.drawLine(x, y + Block_Size - 1, x + Block_Size - 1,
                            y + Block_Size - 1);
                }
                if ((screenData[i] & 16) != 0) { //check if there is a point
                    g2d.setColor(new Color(255, 255, 255)); //set color of the point
                    g2d.fillRect(x + 11, y + 11, 2, 2); //draw the point
                }
                i++;
            }
        }
    }

    private void setGame() {
        short i;
        int dx = 1;
        //int random;
        int position = 0;
        for (i = 0; i < N_Ghosts; i++) {
            ghost_y[i] = 4 * Block_Size; //ghost position when start
            ghost_x[i] = 4 * Block_Size;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;

        }
        player_x = 7 * Block_Size; //player position when start
        player_y = 11 * Block_Size;
        player_dx = 0;
        player_dy = 0;
        req_dx = 0;
        req_dy = 0;
        dying = false;
    }

    public void painting(Graphics g) {
        // super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g; //convert to 2d object
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height); // draw rectangle
        drawMaze(g2d);
        drawScore(g2d);
        if (inGame) {
            playGame(g2d);
        } else {
            Intro(g2d);
        }
        Toolkit.getDefaultToolkit().sync(); //sync the graphics state
        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    class cursorControl extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (inGame) {
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                }
            } else {
                if (key == KeyEvent.VK_SPACE) {
                    inGame = true;
                } else if (key == KeyEvent.VK_M) {
                    int response = JOptionPane.showConfirmDialog(null, "Do you want to go back to the menu?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        validate();
                        mainFrame.setVisible(true);
                        Window parentWindow = SwingUtilities.getWindowAncestor(board.this);
                        parentWindow.dispose();
                    }
                }
            }
        }

    }

}



