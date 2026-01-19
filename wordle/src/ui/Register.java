package ui;

import data.MembersData;
import tool.*;

import javax.swing.*;
import java.awt.*;

public class Register extends JFrame
{
     private final Font font = MyFont.setMyFont("wordle/src/resource/font/JagerMoon-JagerMoon.ttf", 32, 2);
     private final Font font2 = MyFont.setMyFont("wordle/src/resource/font/LXGWWenKai-Regular.ttf",32,2);
     private final Font tipFont = new Font("Arial", Font.PLAIN, 20);


     private JPanel panel = ComponentStyle.setMyBackground("wordle/src/resource/background/register.png");

     private final JLabel nameLabel = ComponentStyle.setMyLabelStyle(new JLabel(), Color.WHITE, "Enter your username :", font, 200,150, 350,50);
     private final JTextField nameTextField = ComponentStyle.setTextFieldStyle(new JTextField(),  Color.WHITE, font2, 10, 520, 150, 250,50);

     private final JLabel passwordLabel = ComponentStyle.setMyLabelStyle(new JLabel(),  Color.WHITE, "Enter your password :", font, 200,250, 350,50);
     private final JPasswordField passwordField = ComponentStyle.setMyPasswordField(new JPasswordField(),  Color.WHITE, font2, 20, 520, 250, 350,50);
     private  JButton change1 = ComponentStyle.setMyButtonStyle(new JButton(), "wordle/src/resource/other/close.png", 900, 225,80,80);

     private final JLabel confirmLabel = ComponentStyle.setMyLabelStyle(new JLabel(),  Color.WHITE, "Confirm your password :", font, 200,350, 350,50);
     private final JPasswordField confirmField = ComponentStyle.setMyPasswordField(new JPasswordField(), Color.WHITE,  font2, 20, 550, 350, 350,50);
     private  JButton change2 = ComponentStyle.setMyButtonStyle(new JButton(), "wordle/src/resource/other/close.png", 900, 340,80,80);


     private final JLabel securityLabel = ComponentStyle.setMyLabelStyle(new JLabel(),  Color.WHITE, "Set securityQuestion :", font, 200, 450, 350,50);
     private final JTextField securityQuestion = ComponentStyle.setTextFieldStyle(new JTextField(),  Color.WHITE, font2, 10, 520, 450, 350,50);
     private final JLabel answerLabel = ComponentStyle.setMyLabelStyle(new JLabel(),  Color.WHITE, "Enter your answer :", font, 200, 550, 350,50);
     private final JTextField answer = ComponentStyle.setTextFieldStyle(new JTextField(),  Color.WHITE, font2, 10, 520, 550, 350,50);

     private final JButton register = ComponentStyle.setMyButtonStyle(new JButton(), "Register", font, Color.YELLOW, 1050, 300, 130, 50 );
     private final JButton cancel = ComponentStyle.setMyButtonStyle(new JButton(), "cancel", font, Color.YELLOW, 1050, 400, 130, 50 );

     private final JLabel empty = ComponentStyle.setMyLabelStyle(new JLabel(), Color.RED, "Has Empty Content!", tipFont,200, 600, 300,30);
     private final JLabel unequal = ComponentStyle.setMyLabelStyle(new JLabel(), Color.RED, "The two passwords are unequal!", tipFont,200, 400, 300,30);
     private final JLabel lengthWrong = ComponentStyle.setMyLabelStyle(new JLabel(), Color.RED, "The password must longer than 6 and consist of upper, lower, digit, special letters!", tipFont,200, 315, 1000,30);
     private final JLabel sameName = ComponentStyle.setMyLabelStyle(new JLabel(), Color.RED, "The name has been registered!", tipFont,200, 200, 300,30);




    public Register()
    {
         ComponentStyle.setMyFrameStyle(this, "Register a New Count", 1422, 800);

         panel.add(nameLabel);

         panel.add(nameTextField);

         panel.add(passwordLabel);

         panel.add(passwordField);

         change1.addActionListener(e ->
         {
              if (passwordField.getEchoChar() == '*')
              {
                   change1 = ComponentStyle.setMyButtonStyle(change1, "wordle/src/resource/other/appear.png", 900, 225,80,80);
                   passwordField.setEchoChar((char) 0);
              }
              else
              {
                   change1 = ComponentStyle.setMyButtonStyle(change1, "wordle/src/resource/other/close.png", 900, 225,80,80);
                   passwordField.setEchoChar('*');
              }
         });
         panel.add(change1);

         panel.add(confirmLabel);

         panel.add(confirmField);

         change2.addActionListener(e ->
         {
              if (confirmField.getEchoChar() == '*')
              {
                   ComponentStyle.setMyButtonStyle(change2, "wordle/src/resource/other/appear.png", 900, 340,80,80);
                   confirmField.setEchoChar((char) 0);
              }
              else
              {
                   ComponentStyle.setMyButtonStyle(change2, "wordle/src/resource/other/close.png", 900, 340,80,80);repaint();
                   confirmField.setEchoChar('*');
              }
         });
         panel.add(change2);


         panel.add(securityLabel);

         panel.add(securityQuestion);

         panel.add(answerLabel);

         panel.add(answer);

         panel.add(register);
         register.addActionListener(e ->
         {
              if (whetherValid())
              {
                   this.dispose();
                   new LogIn().setVisible(true);
              }
         });

         panel.add(cancel);
         cancel.addActionListener(e ->
         {
            this.dispose();
            new Start().setVisible(true);
         });

         add(panel);

    }

    private boolean whetherValid()
    {
         boolean valid = false;

         boolean codeEqual = false;
         boolean nameValid = false;
         boolean emptyContent = false;
         boolean enoughLength = false;
         boolean hasUpper = false;
         boolean hasLower = false;
         boolean hasDigit = false;
         boolean hasSpecial = false;



         String name = nameTextField.getText().trim();
         String password = new String(passwordField.getPassword()).trim();
         String passwordRepeat = new String(confirmField.getPassword()).trim();

         //两次输入的密码是否一致
         if (password.equals(passwordRepeat))
         {
              codeEqual = true;
         }

         //用户名是否已存在
         if (MembersData.getDataByName(name,"Name") == null)
         {
              nameValid = true;
         }

         //是否内容为空
         if (name.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty() || securityQuestion.getText().isEmpty() || answer.getText().isEmpty())
         {
              emptyContent = true;
         }

         //是否有特殊字符， 是都大小写结合， 是否含有数字
         char[] passwordArray = password.toCharArray();
        for (char c : passwordArray)
        {
            if ( (c >= 33 && c <= 47) || (c >= 58 && c <= 64) || (c >= 91 && c <= 96) || (c >= 123 && c <= 126) )
            {
                 hasSpecial = true;
            }
            else if (Character.isDigit(c))
            {
                 hasDigit = true;
            }
            else if (Character.isLowerCase(c))
            {
                 hasLower = true;
            }
            else if (Character.isUpperCase(c))
            {
                 hasUpper = true;
            }
        }

         //密码长度是否大于6
         if(passwordField.getPassword().length >= 6)
         {
              enoughLength = true;
         }

         if (codeEqual && nameValid && !emptyContent && enoughLength && hasUpper && hasLower && hasDigit && hasSpecial )
         {
             MembersData.createMembersDataTable();
             MembersData.insertData(name, password, securityQuestion.getText(), answer.getText());
             valid = true;
         }

         if (emptyContent) //有内容为空
         {
              panel.add(empty);
         }
         else
         {
             panel.remove(empty);
         }

         if (!codeEqual)  //两次密码不一致
         {
              panel.add(unequal);
         }
         else
         {
             panel.remove(unequal);
         }

         if (!enoughLength || !hasDigit || !hasLower || !hasUpper || !hasSpecial) //密码长度不足,或者没有特殊字符，大小写，数字
         {
              panel.add(lengthWrong );
         }
         else
         {
             panel.remove(lengthWrong);
         }

         if (!nameValid)//用户名已存在
         {
              panel.add(sameName);
         }
         else
         {
             panel.remove(sameName);
         }

         repaint();;
         return valid;
    }
}
