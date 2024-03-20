package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {
    JButton entrepreneurButton = new JButton("ИП");
    JButton companyButton = new JButton("ЧТУП");

    public Application() throws HeadlessException {
        super();
        this.setSize(300, 300);
        this.setLocation(500, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 2, 2, 2));
        entrepreneurButton.addActionListener(new EntrepreneurButtonEventListener());
        companyButton.addActionListener(new CompanyButtonEventListener());
        container.add(entrepreneurButton);
        container.add(companyButton);
    }
}