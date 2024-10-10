package Project3_6513172;
//Puthipong Yomabut 6513134
//Phattaradanai Sornsawang 6513172
//Patiharn Kamenkit 6513170
//Praphasiri wannawong 6513116
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class frame_howtoplay extends JFrame{
    private JPanel contentpane_howtoplay;
    private JLabel bg_howtoplay_Label;
    private JFrame backframe_3,frame;
    private String path = "src/main/Java/Project3_6513172/resources/";
    private String[] background = {"background_thai.gif","background_eng.gif"};
    private String[] buttonImages = {"button_back.png","button_th.png","button_eng.png"};
    public frame_howtoplay(JFrame mainframe){

        backframe_3 = mainframe;
        setTitle("ESCAPE-F");
        setSize(1365, 768);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        contentpane_howtoplay = new JPanel(null);

        ImageIcon backgroundImage = new ImageIcon(path + background[0]);
        bg_howtoplay_Label = new JLabel(backgroundImage);
        bg_howtoplay_Label.setBounds(0, 0, 1366, 768);
        contentpane_howtoplay.add(bg_howtoplay_Label);


        JPanel buttonpanel = new JPanel(null);
        buttonpanel.setBounds(0, 0, 1200,1200);
        MyButton[] buttons = new MyButton[buttonImages.length];


        // button back
        ImageIcon button_back = new ImageIcon(path + buttonImages[0]);
        buttons[0] =new MyButton(button_back);
        buttons[0].setBounds(150, 600, 166, 140);
        buttons[0].addActionListener(e -> handleClick(0));
        bg_howtoplay_Label.add(buttons[0]);        // Add JButton in

        ImageIcon button_thai = new ImageIcon(path + buttonImages[1]);
        buttons[1] =new MyButton(button_thai);
        buttons[1].setBounds(940, 550, 140, 150);
        buttons[1].addActionListener(e -> handleClick(1));
        bg_howtoplay_Label.add(buttons[1]);

        ImageIcon button_english = new ImageIcon(path + buttonImages[2]);
        buttons[2] =new MyButton(button_english);
        buttons[2].setBounds(1100, 550, 140, 150);
        buttons[2].addActionListener(e -> handleClick(2));
        bg_howtoplay_Label.add(buttons[2]);


        buttonpanel.setOpaque(true);
        contentpane_howtoplay.add(buttonpanel);
        setContentPane(contentpane_howtoplay);
        this.setVisible(true);
        this.repaint();

    }
    public void handleClick(int buttonindex){
        switch (buttonindex) {
            case 0:
                backframe_3.setVisible(true);
                dispose();  // delete play_frame
                break;
            case 1:
                updateBackground(1);
                break;
            case 2:
                updateBackground(2);
                break;
        }
    }
    public void updateBackground(int selectedLevel) {
        // You can customize this method to set the background image based on the selected level
        // For example, use a switch statement or if-else conditions to determine the image file path
        String backgroundImagePath = determineBackgroundImagePath(selectedLevel);

        // Set the new background image
        ImageIcon newBackgroundImage = new ImageIcon(path + backgroundImagePath);
        bg_howtoplay_Label.setIcon(newBackgroundImage);
    }
    public String determineBackgroundImagePath(int selectedLevel) {
        // Customize this method to map selectedLevel to the appropriate background image path
        // For example:
        switch (selectedLevel) {
            case 1:
                return "background_thai.gif";
            case 2:
                return "background_eng.gif";
            // Default background image
            default:
                return "background_thai.gif";
        }

    }
}

