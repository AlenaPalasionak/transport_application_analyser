package org.example.gui;

import org.example.core.AbstractDataTransferor;
import org.example.core.CompanyDataTransferor;
import org.example.core.EntrepreneurDataTransferor;
import org.example.util.logger.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppWindow extends JFrame {

    private final JRadioButton entrepreneurButton = new JRadioButton("ИП");
    private final JRadioButton companyButton = new JRadioButton("ЧТУП", true);
    private final String[] monthsNames = {"январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
    private final JComboBox<String> comboBox = new JComboBox<>(monthsNames);
    private AbstractDataTransferor dataTransferor;

    public AppWindow() throws HeadlessException {
        super("\"Автоматизация записи перевозок\"");
        this.setSize(300, 300);
        this.setLocation(900, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4, 2, 2, 2));
        JPanel panel1 = new JPanel();
        container.add(panel1);
        JPanel panel2 = new JPanel();
        container.add(panel2);
        JPanel panel3 = new JPanel();
        container.add(panel3);
        panel1.add(entrepreneurButton);
        panel1.add(companyButton);
        ButtonGroup group = new ButtonGroup();
        group.add(entrepreneurButton);
        group.add(companyButton);
        panel2.add(comboBox);
        JButton sendButton = new JButton("OK");
        panel3.add(sendButton);
        ButtonEventManager eventListener = new ButtonEventManager("Запущен процесс создание списка.");
        sendButton.addActionListener(eventListener);
        Log.info("(AppWindow) 1. AppWindow is created");
    }

    class ButtonEventManager implements ActionListener {
        protected String message;

        public ButtonEventManager(String message) {
            this.message = message;
        }

        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, message
                    , "Output", JOptionPane.PLAIN_MESSAGE);
            if (entrepreneurButton.isSelected()) {
                dataTransferor = new EntrepreneurDataTransferor();
                Log.info("(AppWindow) 2. Entrepreneur button is pressed");
            }
            if (companyButton.isSelected()) {
                dataTransferor = new CompanyDataTransferor();
                Log.info("(AppWindow) 3. Company button is pressed");
            }
            String sheetName = (String) comboBox.getSelectedItem();
            Log.info("(AppWindow) 4. Method addValueToSpreadSheets(" + sheetName + ") is gonna be invoked on the object of the class " + dataTransferor.getClass());
            dataTransferor.addValueToSpreadSheets(sheetName);
        }
    }
}

