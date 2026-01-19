package ui;

import tool.*;

import javax.swing.*;
import java.awt.*;

public class Start extends JFrame
{

    public Start()
    {
        ComponentStyle.setMyFrameStyle(this, "Wordle", 889, 500);

        JPanel panel ;
        panel = ComponentStyle.setMyBackground("wordle/src/resource/background/start.jpg");

        Font font = MyFont.setMyFont("wordle/src/resource/font/PerfectoCalligraphy.ttf", 65, 2);

        JButton register = ComponentStyle.setMyButtonStyle(new JButton(), "Register", font, Color.WHITE, 50, 50, 210, 100);
        register.addActionListener(e ->
        {
           this.dispose();
           new Register().setVisible(true);
        });
        panel.add(register);

        JButton logIn = ComponentStyle.setMyButtonStyle(new JButton(), "Log In", font, Color.WHITE, 625, 50, 210, 100);
        logIn.setLocation(625, 50);
        logIn.addActionListener(e ->
        {
            this.dispose();
            new LogIn().setVisible(true);
        });
        panel.add(logIn);

        this.add(panel);
    }
}
