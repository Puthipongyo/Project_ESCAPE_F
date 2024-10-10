package Project3_6513172;
//Puthipong Yomabut 6513134
//Phattaradanai Sornsawang 6513172
//Patiharn Kamenkit 6513170
//Praphasiri wannawong 6513116
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class frame_play extends JFrame {

    private board b;
    private String name,level_2;
    //    private String  get_name,get_level;
    protected ArrayList<String> get_name,get_level;
    private JPanel contentpane_play;
    private JFrame backframe;
    private JTextField user_name;
    private JComboBox<String> level;
    private JLabel bgLabel,LevelLabel;
    private String path = "src/main/Java/Project3_6513172/resources//";
    private String[] background = {"background_menu.png"};
    private String[] buttonImages = {"button_back.png","button_continue.png"};

    private String[] charactername = {"SillyMan","Copter","Pluem","Phat","Grace"};

    private String[] CharacterImages = {"sillyman.png","ter.png","pluem.png","jumm.png","grace.png"};
    private String[] LevelImages = {"newbie.png"};

    private String [] mode = {"Newbie","Easy","Normal","Hard","Nightmare"};
    public String get_name(){return name;}
    public String get_level(){return level_2;}
    public int get_score(){return b.get_score() ; }
    public frame_play(JFrame mainframe){
        Newmain mainFrame = (Newmain) mainframe;
        b = new board(mainFrame);
        backframe = mainframe;
        setTitle("ESCAPE-F");
        setSize(1365, 768);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentpane_play = new JPanel(null);

        ImageIcon backgroundImage = new ImageIcon(path + background[0]);
        bgLabel = new JLabel(backgroundImage);
        bgLabel.setBounds(0, 0, 1366, 768);
        contentpane_play.add(bgLabel);

        // Register
        // Create and add a JTextField
        JLabel nameLabel = new JLabel("Name : ");
        nameLabel.setBounds(545, 175, 100, 25);
        nameLabel.setForeground(Color.WHITE);
        bgLabel.add(nameLabel);

        // Increase font size
        Font currentFont = nameLabel.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() + 5f); // Change the 5f to the desired font size increase
        nameLabel.setFont(newFont);

        //contentpane_play.add(nameLabel);
        user_name = new JTextField(200);
        user_name.setFont(new Font("Comic Sans MS", Font.BOLD+Font.ITALIC, 20));
        user_name.setBounds(620, 175, 150, 30);
        user_name.setOpaque(true); // Set the text field as non-opaque
        user_name.setBackground(Color.WHITE);
        user_name.setForeground(Color.black);
        bgLabel.add(user_name);


        JPanel buttonpanel = new JPanel(null);
        buttonpanel.setBounds(0, 0, 1200,1200);
        MyButton[] buttons = new MyButton[buttonImages.length];

        ImageIcon characterImage = new ImageIcon(path + CharacterImages[0]);
        JLabel characterLabel = new JLabel(characterImage);
        characterLabel.setBounds(350, 250, 100, 100);
        bgLabel.add(characterLabel);

        ImageIcon characterImage1 = new ImageIcon(path + CharacterImages[1]);
        JLabel characterLabel1 = new JLabel(characterImage1);
        characterLabel1.setBounds(470, 240, 100, 160);
        bgLabel.add(characterLabel1);

        ImageIcon characterImage2 = new ImageIcon(path + CharacterImages[2]);
        JLabel characterLabel2 = new JLabel(characterImage2);
        characterLabel2.setBounds(620, 190, 100, 160);
        bgLabel.add(characterLabel2);

        ImageIcon characterImage3 = new ImageIcon(path + CharacterImages[3]);
        JLabel characterLabel3 = new JLabel(characterImage3);
        characterLabel3.setBounds(770, 230, 100, 140);
        bgLabel.add(characterLabel3);

        ImageIcon characterImage4 = new ImageIcon(path + CharacterImages[4]);
        JLabel characterLabel4 = new JLabel(characterImage4);
        characterLabel4.setBounds(920, 220, 100, 120);
        bgLabel.add(characterLabel4);

        JRadioButton[] tb = new JRadioButton[5];
        ButtonGroup buttonGroup = new ButtonGroup();

        tb[0] = new JRadioButton(charactername[0], true);
        for(int i = 1; i < 5; i++){
            tb[i] = new JRadioButton(charactername[i]);
        }

        for (int i = 0; i < 5; i++) {
            tb[i].setBounds(350 + i * 150, 350, 100, 25);
            tb[i].setOpaque(false);
            tb[i].setForeground(Color.WHITE);
            tb[i].addActionListener(e -> {
                JRadioButton radioButton = (JRadioButton) e.getSource();
                String selectedCharacter = radioButton.getText();
                b.setChar(selectedCharacter);
            });
            bgLabel.add(tb[i]);
            buttonGroup.add(tb[i]);
        }


        level = new JComboBox<>(mode);
        level.setBounds(420, 450, 200, 80);
        level.setOpaque(false);
        level.setForeground(Color.BLACK);
        bgLabel.add(level);

        ImageIcon LevelImage = new ImageIcon(path + LevelImages[0]);
        LevelLabel = new JLabel(LevelImage);
        LevelLabel.setBounds(650, 415, 300, 150);
        bgLabel.add(LevelLabel);

        level.addActionListener(e -> {
            String selectedMode = (String) level.getSelectedItem();// Create an instance of the board class
            b.setMode(selectedMode); // Set the mode based on the selected item
            updateBackground(selectedMode);
        });



        // button back
        ImageIcon button_back = new ImageIcon(path + buttonImages[0]);
        buttons[0] =new MyButton(button_back);
        buttons[0].setBounds(150, 600, 166, 140);
        buttons[0].addActionListener(e -> handleClick(0));
        bgLabel.add(buttons[0]);        // Add JButton in JPanel

        // button continue
        ImageIcon button_continue = new ImageIcon(path + buttonImages[1]);
        buttons[1] =new MyButton(button_continue);
        buttons[1].setBounds(1000, 610, 233, 150);
        buttons[1].addActionListener(e -> handleClick(1 ));
        bgLabel.add(buttons[1]);


        // Add JButton in JPanel

        buttonpanel.setOpaque(true);
        contentpane_play.add(buttonpanel);

        // Set JPanel
        setContentPane(contentpane_play);
        this.setVisible(true);
        this.repaint();
    }
    public void updateBackground(String selectedLevel) {
        // You can customize this method to set the background image based on the selected level
        // For example, use a switch statement or if-else conditions to determine the image file path
        String backgroundImagePath = updateButton(selectedLevel);

        // Set the new background image
        ImageIcon newBackgroundImage = new ImageIcon(path + backgroundImagePath);
        LevelLabel.setIcon(newBackgroundImage);
    }
    public String updateButton(String selectedLevel) {
        // Customize this method to map selectedLevel to the appropriate background image path
        // For example:
        switch (selectedLevel) {
            case "Easy":
                return "easy.png";
            case "Normal":
                return "normal.png";
            case "Hard":
                return "hard.png";
            // Add more cases as needed
            case "Nightmare":
                return "nightmare.png";
            default:
                return "newbie.png"; // Default background image
        }
    }
    public void handleClick(int buttonindex) {
        switch (buttonindex) {
            case 0:
                int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit to menu?", "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    backframe.setVisible(true);
                    dispose();
                }
                break;
            case 1:
                if(user_name.getText().isEmpty() ){
                    JOptionPane.showMessageDialog(null, "Enter name", "Error", JOptionPane.WARNING_MESSAGE);
                } else{
                    name = user_name.getText();
                    level_2 =  level.getSelectedItem().toString();
                    dispose(); // Close the current frame
                    EscapeF p = new EscapeF(b,backframe);
                    p.setVisible(true);
                    p.setTitle("ESCAPE-F");
                    p.setSize(1366,788);
                    p.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    p.setLocationRelativeTo(null);

                    break;
                }

        }

    }

}
