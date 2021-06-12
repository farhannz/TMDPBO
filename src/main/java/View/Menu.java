/*
 * Created by JFormDesigner on Wed Jun 09 09:20:30 ICT 2021
 */

package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ViewModel.VMHighScore;

/**
 * @author Farhan
 */
public class Menu extends JFrame {
    public Menu() {
        initComponents();
    }

    private void button2ActionPerformed(ActionEvent e) {
        // TODO add your code here
        if(!textField1.getText().isEmpty()){
            GameLoop game = new Game(1280,720,"TMD PBO Farhan C2", textField1.getText());
            Thread t = new Thread(game::run);
            t.start();
            this.setVisible(false);
            this.dispose();
        }
    }

    private void button3ActionPerformed(ActionEvent e) {
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        textField1 = new JTextField();
        label1 = new JLabel();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        button2 = new JButton();
        button3 = new JButton();

        //======== this ========
        var contentPane = getContentPane();

        //---- textField1 ----
        textField1.setColumns(1);

        //---- label1 ----
        label1.setText("Username :");

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(table2);
        }

        //---- button2 ----
        button2.setText("Mulai");
        button2.addActionListener(e -> button2ActionPerformed(e));

        //---- button3 ----
        button3.setText("Keluar");
        button3.addActionListener(e -> button3ActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 586, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(178, 178, 178)
                    .addComponent(label1)
                    .addGap(18, 18, 18)
                    .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(145, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap(218, Short.MAX_VALUE)
                    .addComponent(button2)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(button3)
                    .addGap(263, 263, 263))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1)
                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(button2)
                        .addComponent(button3))
                    .addContainerGap(22, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

        this.setTitle("TMD PBO Farhan C2");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        VMHighScore hs = new VMHighScore();
        DefaultTableModel tabel = hs.getTabel();
        table2.setModel(tabel);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JTextField textField1;
    private JLabel label1;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JButton button2;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
