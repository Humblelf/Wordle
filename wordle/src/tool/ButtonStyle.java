package tool;

import javax.swing.*;
import java.awt.*;

public class ButtonStyle
{
    private ButtonStyle()
    {}

    public static JButton setButtonStyle(JButton setWhichButton, String text, Font font, Color color, int width, int height)
    {
        setWhichButton.setText(text);
        setWhichButton.setForeground(color);
        setWhichButton.setFont(font);
        setWhichButton.setPreferredSize(new Dimension(width, height));
        setWhichButton.setBorderPainted(false);
        setWhichButton.setFocusPainted(false);
        setWhichButton.setBorder(BorderFactory.createEmptyBorder());

        return setWhichButton;
    }


}
