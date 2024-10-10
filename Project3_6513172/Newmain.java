package Project3_6513172;
//Puthipong Yomabut 6513134
//Phattaradanai Sornsawang 6513172
//Patiharn Kamenkit 6513170
//Praphasiri wannawong 6513116
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;

class Newmain extends JFrame {
    private JFrame frame;
    private JPanel contentPane;
    private frame_play frame_play;
    private frame_credit frame_credit;
    private frame_setting frame_setting;
    private frame_table frame_table;
    private String name;
    private board b;

    private frame_howtoplay frame_howtoplay;
    private MySoundEffect themeSound;

    private String path = "src/main/Java/Project3_6513172/resources/";
    private String[] background = {"background_menu.png"};
    private String[] buttonImages = {"button_play.png", "button_setting.png", "button_howtoplay.png","button_score.png", "button_exit.png","button_credit.png"};

    private String[] button_new = {path + "button_play2"};

    public Newmain() {

        setTitle("ESCAPE-F");
        setSize(1365, 768);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION
                );

                if (result == JOptionPane.YES_OPTION) {
                    // If user clicks "Yes", exit the application
                    System.exit(0);
                }
                // If user clicks "No" or closes the dialog, do nothing
            }
        });

        playmusic();

        contentPane = new JPanel(null);

        ImageIcon backgroundImage = new ImageIcon(path + background[0]);
        JLabel bgLabel = new JLabel(backgroundImage);
        bgLabel.setBounds(0, 0, 1366, 768);
        contentPane.add(bgLabel);

        JPanel buttonPanel = new JPanel(null);
        buttonPanel.setBounds(0, 0, 2000,2000);

        // Use a loop to simplify button creation
        MyButton[] buttons = new MyButton[buttonImages.length];

        // buuton_play
        ImageIcon button_play = new ImageIcon(path + buttonImages[0]);
        buttons[0] = new MyButton(button_play);
        buttons[0].setBounds(550, 125 , 200, 100);
        buttons[0].addActionListener(e -> handleButtonClick(0));
        bgLabel.add(buttons[0]);

        buttons[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttons[0].setIcon(new ImageIcon(path + "button_play2.png")); // Change the icon when mouse enters
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttons[0].setIcon(button_play); // Change back to the default icon when mouse exits
            }
        });

        //button_setting
        ImageIcon button_setting = new ImageIcon(path + buttonImages[1]);
        buttons[1] = new MyButton(button_setting);
        buttons[1].setBounds(550, 245, 200,100);
        buttons[1].addActionListener(e -> handleButtonClick(1));
        bgLabel.add(buttons[1]);

        //button_howtoplay
        ImageIcon button_howtoplay = new ImageIcon(path + buttonImages[2]);
        buttons[2] = new MyButton(button_howtoplay);
        buttons[2].setBounds(500, 365, 300,100);
        buttons[2].addActionListener(e -> handleButtonClick(2));
        bgLabel.add(buttons[2]);

        //button_score
        ImageIcon button_score = new ImageIcon(path + buttonImages[3]);
        buttons[3] = new MyButton(button_score);
        buttons[3].setBounds(550, 485, 200,100);
        buttons[3].addActionListener(e -> handleButtonClick(3));
        bgLabel.add(buttons[3]);

        //button_exit
        ImageIcon button_exit = new ImageIcon(path + buttonImages[4]);
        buttons[4] = new MyButton(button_exit);
        buttons[4].setBounds(550, 605, 200,100);
        buttons[4].addActionListener(e -> handleButtonClick(4));
        bgLabel.add(buttons[4]);

        ImageIcon button_credit = new ImageIcon(path + buttonImages[5]);
        buttons[5] = new MyButton(button_credit);
        buttons[5].setBounds(980, 605, 200, 100);
        buttons[5].addActionListener(e -> handleButtonClick(5));
        bgLabel.add(buttons[5]);

        //button_score

        contentPane.add(buttonPanel);
        setContentPane(contentPane);
        repaint();

        this.setVisible(true); // Commented out to be placed after pack()
    }

    public void playmusic(){
        themeSound = new MySoundEffect(path + "pacmanbg.wav");
        themeSound.playLoop();
        themeSound.setVolume(0.05f);
    }

    public void playmusic2(){
        if (themeSound != null && !themeSound.clip.isRunning()) {
            System.out.println("Sound play.");
            themeSound = new MySoundEffect(path + "pacmanbg.wav");
            themeSound.playLoop();
            themeSound.setVolume(0.05f);
        }
    }
    public void stopmusic(){
        themeSound.stop();
    }

    private void handleButtonClick(int buttonIndex) {
        switch (buttonIndex) {
            case 0:
                frame_play =new frame_play(this);
                frame_play.setVisible(true);
                this.setVisible(false);
                break;
            case 1:
                frame_setting =new frame_setting(this);
                frame_setting.setVisible(true);
                this.setVisible(false);
                break;
            case 2:
                frame_howtoplay =new frame_howtoplay(this);
                frame_howtoplay.setVisible(true);
                this.setVisible(false);
                break;
            case 3:
                b = new board(this);
                if(this.frame_play == null){
                    frame_table =new frame_table ( null, null, null );
                    frame_table.setVisible(true);
                    System.out.println("null");
                }else{
                    name = frame_play.get_name();
                    frame_table =new frame_table ( name, frame_play.get_level(), frame_play.get_score() );
                    frame_table.setVisible(true);
                }
                break;
            case 4:
                int option = JOptionPane.showConfirmDialog(Newmain.this, "Are you sure you want to quit the game?", "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    stopmusic();
                    System.exit(0); // Quit the game
                }
                break;
            case 5:
                frame_credit =new frame_credit(this);
                frame_credit.setVisible(true);
                this.setVisible(false);
                break;
        }
    }

    public static void main(String[] args) {
        Newmain main = new Newmain();
        main.repaint(); // Sizes the frame based on its content
    }
}
class MyButton extends JButton {

    public MyButton(ImageIcon icon) {
        super(icon);
        setBorderPainted(false); // Removes the button border
        setContentAreaFilled(false); // Makes the button transparent
        setFocusPainted(false); // Removes the focus indication
        setOpaque(false); // Makes the button background transparent
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Changes cursor to hand when over the button


    }


}

class MySoundEffect extends JFrame {
    public Clip clip;
    private FloatControl gainControl;

    public MySoundEffect(String filename) {
        try {
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            e.printStackTrace();
            clip = null; // Ensure clip is null if an error occurs
        }
    }

    public void playOnce() {
        if (clip != null) {
            clip.setMicrosecondPosition(0);
            clip.start();
        }
    }

    public void playLoop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.setMicrosecondPosition(0);
            System.out.println("Sound stopped.");
        }
    }

    public void setVolume(float gain) {
        if (clip != null && gainControl != null) {
            if (gain < 0.0f) gain = 0.0f;
            if (gain > 1.0f) gain = 1.0f;
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }
}







