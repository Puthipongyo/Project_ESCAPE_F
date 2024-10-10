package Project3_6513172;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class frame_credit extends JFrame {
    private JPanel contentpane_credit;
    private JFrame backframe_3,frame;
    private String path = "src/main/Java/Project3_6513172/resources//";
    private String[] background = {"background_credit.gif"};
    private String[] buttonImages = {"button_back.png"};
    public frame_credit(JFrame mainframe){

        backframe_3 = mainframe;
        setTitle("ESCAPE-F");
        setSize(1365, 768);
        setResizable(false);
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

        contentpane_credit = new JPanel(null);

        ImageIcon backgroundImage = new ImageIcon(path + background[0]);
        JLabel bg_credit_Label = new JLabel(backgroundImage);
        bg_credit_Label.setBounds(0, 0, 1366, 768);
        contentpane_credit.add(bg_credit_Label);


        JPanel buttonpanel = new JPanel(null);
        buttonpanel.setBounds(0, 0, 1200,1200);
        MyButton[] buttons = new MyButton[buttonImages.length];


        // button back
        ImageIcon button_back = new ImageIcon(path + buttonImages[0]);
        buttons[0] =new MyButton(button_back);
        buttons[0].setBounds(150, 620, 166, 140);
        buttons[0].addActionListener(e -> handleClick(0));
        bg_credit_Label.add(buttons[0]);        // Add JButton in JPanel


        buttonpanel.setOpaque(true);
        contentpane_credit.add(buttonpanel);
        setContentPane(contentpane_credit);
        this.setVisible(true);
        this.repaint();

    }
    public void handleClick(int buttonindex){
        switch (buttonindex) {
            case 0:
                backframe_3.setVisible(true);
                dispose();  // delete play_frame
                break;
        }
    }
}