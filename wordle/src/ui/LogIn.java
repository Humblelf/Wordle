package ui;

import data.MembersData;
import tool.*;

import javax.swing.*;
import java.awt.*;

public class LogIn extends JFrame
{
    private final Font font1 = MyFont.setMyFont("wordle/src/resource/font/JagerMoon-JagerMoon.ttf",32,2);
    private final Font font2 = MyFont.setMyFont("wordle/src/resource/font/LXGWWenKai-Regular.ttf",32,2);
    private final Font font3 = MyFont.setMyFont("wordle/src/resource/font/JagerMoon-JagerMoon.ttf",54,2);
    private final Font tipFont = new Font("Arial", Font.PLAIN, 20);

    private final JPanel panel = ComponentStyle.setMyBackground("wordle/src/resource/background/logIn.jpg");

    private final JLabel member = ComponentStyle.setMyLabelStyle(new JLabel(), new Color(204, 153, 0), "Enter your Name or Id :", font1, 310,250,400,30);
    private final JTextField memberField = ComponentStyle.setTextFieldStyle(new JTextField(), new Color(204, 204, 204), font2, 20, 640, 245, 400, 50 );

    private final JLabel password = ComponentStyle.setMyLabelStyle(new JLabel(), new Color(204, 153, 0), "Enter your Password :", font1, 310,350,400,30);
    private final JPasswordField passwordField = ComponentStyle.setMyPasswordField(new JPasswordField(), new Color(204, 204, 204), font2, 20, 640, 345, 300, 50 );
    private  JButton change = ComponentStyle.setMyButtonStyle(new JButton(), "wordle/src/resource/other/close.png",1000 , 330,80,80);

    private  final JButton login = ComponentStyle.setMyButtonStyle(new JButton(), "Log In", font3, new Color(204, 153, 0), 400, 545, 200, 50);
    private  final JButton cancel = ComponentStyle.setMyButtonStyle(new JButton(), "Cancel", font3, new Color(204, 153, 0), 800, 545, 200, 50);

    JLabel tipWrongCode = ComponentStyle.setMyLabelStyle(new JLabel(),new Color(220, 0, 0), "Code is wrong", tipFont, 310, 390, 200,30);
    JLabel tipWrongMember = ComponentStyle.setMyLabelStyle(new JLabel(),new Color(220, 0, 0), "The Name or ID is wrong", tipFont, 310, 290, 400,30);
    JLabel tipEmpty = ComponentStyle.setMyLabelStyle(new JLabel(),new Color(220, 0, 0), "Has Empty Content", tipFont, 310, 410, 200,30);




    public LogIn()
    {
        ComponentStyle.setMyFrameStyle(this, "Log In", 1359, 800 );

        panel.add(member);
        panel.add(memberField);
        
        panel.add(password);
        panel.add(passwordField);
        change.addActionListener(e ->
        {
            if (passwordField.getEchoChar() == '*')
            {
                change = ComponentStyle.setMyButtonStyle(change, "wordle/src/resource/other/appear.png", 1000 , 330,80,80);
                passwordField.setEchoChar((char) 0);
            }
            else
            {
                change = ComponentStyle.setMyButtonStyle(change, "wordle/src/resource/other/close.png", 1000 , 330,80,80);
                passwordField.setEchoChar('*');
            }
        });
        panel.add(change);

        login.addActionListener(e ->
        {
           if (success())
           {
               new GameFrame(5);

           }
        });
        panel.add(login);

        cancel.addActionListener(e ->
        {
            this.dispose();
           new Start().setVisible(true);
        });
        panel.add(cancel);

        add(panel);
    }

    private boolean success()
    {
        boolean result = false;

        boolean codeRight = false;
        boolean nameValid = false;
        boolean IDValid = false;
        boolean emptyContent = false;

        String nameOrID = memberField.getText();
        String password = new String(passwordField.getPassword());

        //判断用户名是否存在
        if (MembersData.getDataByName(nameOrID, "Name") != null)
        {
            nameValid = true;
        }
        else if (MembersData.getDataByID(nameOrID, "ID") != null)
        {
            IDValid = true;
        }
        //判断密码是否正确
        if (nameValid || IDValid)
        {
            if (password.equals(MembersData.getDataByName(nameOrID, "Password")) || password.equals(MembersData.getDataByID(nameOrID, "Password")))
            {
                codeRight = true;
                panel.remove(tipWrongCode);
            }
            else
            {
                panel.add(tipWrongCode);
                repaint();
            }
            panel.remove(tipWrongMember);
        }
        else
        {
            panel.add(tipWrongMember);
            repaint();
        }


        //判断输入框是否为空
        if (nameOrID.isEmpty() || password.isEmpty())
        {
            emptyContent = true;
            panel.add(tipEmpty);
            repaint();

        }
        else
        {
            panel.remove(tipEmpty);
        }

        //密码与用户名匹配则成功登录
        if (codeRight  && !emptyContent)
        {
            result = true;
        }
        return result;
    }

}
