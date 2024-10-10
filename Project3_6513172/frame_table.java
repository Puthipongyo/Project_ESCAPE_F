package Project3_6513172;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class frame_table extends JFrame{
    private JTable table;
    public frame_table(String user_name, String level,int score){
        setTitle("Scoreboard");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450,300,400,200);

        // Create a JTable with a DefaultTableModel
        DefaultTableModel tableModel ;

        if (user_name != null && level != null && !user_name.isEmpty() && !level.isEmpty() ) {
            tableModel = new DefaultTableModel(new Object[][]{{user_name, level,score}}, new Object[]{"Username", "Level","Score"});
        } else {
            tableModel = new DefaultTableModel(new Object[][]{}, new Object[]{"Username", "Level","Score"});
        }

        // Create the table with the model
        table = new JTable(tableModel);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        JButton backButton = new JButton("Back to first frame");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose(); // Close the current frame
            }
        });

        // Add the button to the frame
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }
    public frame_table(String user_name, String level,String score){
        setTitle("Information Display");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450,300,400,200);

        // Create a JTable with a DefaultTableModel
        DefaultTableModel tableModel = null ;


        if (user_name == null && level == null &&  score == null ) {
            tableModel = new DefaultTableModel(new Object[][]{},
                    new Object[]{"Username", "Level","Score"});
        }

        // Create the table with the model
        table = new JTable(tableModel);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        JButton backButton = new JButton("Back to first frame");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose(); // Close the current frame
            }
        });

        // Add the button to the frame
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}