package ui;

import javax.swing.*;
import java.awt.*;

public class GameEndFrame extends JFrame {
    private GameFrame gameFrame;
    private boolean win;

    public GameEndFrame(GameFrame gameFrame, boolean win){
        this.gameFrame = gameFrame;
        this.win = win;
        initializeWindow();
        setUpUI();
    }

    private void initializeWindow(){
        setSize(400, 600);  // 设置窗口大小
        setLocationRelativeTo(null);  // 窗口居中
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 点击X只关闭这个窗口
        setVisible(true);
    }

    private void setUpUI(){
        final String text = win ? "You win!" : "You lose!";

        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);          // 先清背景
                g.setColor(Color.BLACK);          // 字色
                g.setFont(new Font("Arial", Font.BOLD, 50));
                FontMetrics fm = g.getFontMetrics();          // 1. 取尺子
                int stringWidth = fm.stringWidth(text);       // 2. 量宽度
                int x = (getWidth() - stringWidth) / 2;       // 3. 水平居中
                g.drawString(text, x, 100);     // 从(150,200)开始画
            }
        };

        canvas.setPreferredSize(new Dimension(400, 250));
        canvas.setOpaque(true);   // 如果要背景色就设 setBackground(...)
        setContentPane(canvas);   // 直接当成主面板
    }
}
