package org.example.gui;

import org.example.core.AbstractDataTransferor;
import org.example.util.FileHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseEventListener implements ActionListener {
    protected String message;
    protected AbstractDataTransferor dataTransferor;

    public BaseEventListener(String message, AbstractDataTransferor dataTransferor) {
        this.message = message;
        this.dataTransferor = dataTransferor;
    }

    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, message
                , "Output", JOptionPane.PLAIN_MESSAGE);
        dataTransferor.addValueToSheet();
    }
}
