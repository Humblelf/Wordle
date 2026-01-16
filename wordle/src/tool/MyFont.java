package tool;

import java.awt.*;
import java.io.File;

public class MyFont
{
    private MyFont(){}

    public static Font setMyFont(Font setWhichFont, String fontPath, int fontSize,int style )  // style:"0"代表“普通”，"1"代表“加粗”，"2"代表斜体
    {
        if (style == 0 || style == 1 || style ==2)
        {
            try
            {
                setWhichFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
                setWhichFont = setWhichFont.deriveFont(style, fontSize);
                return setWhichFont;
            }
            catch (Exception e)
            {
                setWhichFont = new Font("微软雅黑 Bold", style, fontSize);
                return setWhichFont;
            }
        }
        else
        {
            return null;
        }
    }
}
