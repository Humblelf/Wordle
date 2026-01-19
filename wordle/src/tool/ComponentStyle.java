package tool;

import javax.swing.*;
import java.awt.*;

public class ComponentStyle
{
    private ComponentStyle(){}

    public static void setMyFrameStyle(JFrame frame, String text, int width, int height)
    {
        frame.setTitle(text);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    public static JPanel setMyBackground(String path)
    {
        JPanel backgroundPanel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                ImageIcon backgroundIcon = new ImageIcon(path);
                Image image = backgroundIcon.getImage();
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        backgroundPanel.setOpaque(true);
        backgroundPanel.setLayout(null);

        return backgroundPanel;
    }

    public static JLabel setMyLabelStyle(JLabel myLabel, Color color, String text, Font font, int x, int y, int width, int height)
    {
        myLabel.setText(text);
        myLabel.setFont(font);
        myLabel.setLocation(x, y);
        myLabel.setOpaque(false);
        myLabel.setSize(width, height);
        myLabel.setForeground(color);
        return myLabel;
    }

    public static JTextField setTextFieldStyle(JTextField myTextField, Color color, Font myFont,int length, int x, int y, int width, int height)
    {
        myTextField.setColumns(length);
        myTextField.setFont(myFont);
        myTextField.setBorder(BorderFactory.createEmptyBorder(2, 10, 9, 10));
        myTextField.setOpaque(false);
        myTextField.setForeground(color);
        myTextField.setLocation(x, y);
        myTextField.setSize(width, height);
        return myTextField;
    }

    public static JPasswordField setMyPasswordField(JPasswordField myPasswordField, Color color, Font myFont,int length, int x, int y, int width, int height)
    {
        myPasswordField.setColumns(length);
        myPasswordField.setFont(myFont);
        myPasswordField.setEchoChar('*');
        myPasswordField.setLocation(x, y);
        myPasswordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        myPasswordField.setOpaque(false);
        myPasswordField.setSize(width, height);
        myPasswordField.setForeground(color);
        return myPasswordField;
    }

    public static JButton setMyButtonStyle(JButton setWhichButton, String text, Font font, Color color, int x, int y, int width, int height)
    {
        setWhichButton.setText(text);
        setWhichButton.setForeground(color);
        setWhichButton.setFont(font);
        setWhichButton.setSize(width, height);
        setWhichButton.setLocation(x, y);
        setWhichButton.setBorderPainted(false);
        setWhichButton.setFocusPainted(false);
        setWhichButton.setContentAreaFilled(false);
        setWhichButton.setMargin(new Insets(2, 5, 2, 5));
        setWhichButton.setBorder(BorderFactory.createEmptyBorder());

        return setWhichButton;
    }

    public static JButton setMyButtonStyle(JButton myButton, String path,int x, int y,int  width,int height)
    {
        ImageIcon icon = new ImageIcon(path);
        ImageIcon picture = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));

        myButton.setText("");
        myButton.setIcon(picture);
        myButton.setSize(width, height);
        myButton.setLocation(x, y);

        myButton.setFocusPainted(false);
        myButton.setBorderPainted(false);
        myButton.setContentAreaFilled(false);
        myButton.setOpaque(false);
        myButton.setRolloverEnabled(true);
        myButton.setRolloverIcon(null);

        return myButton;
    }
}
