package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

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
        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                setBackground(Color.WHITE);

                String text = win ? "You win!" : "You lose!";
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 50));
                FontMetrics fm = g.getFontMetrics();
                int stringWidth = fm.stringWidth(text);
                int x = (getWidth() - stringWidth) / 2;
                g.drawString(text, x, 150);

                drawGrade((Graphics2D) g);
                drawButton((Graphics2D) g);
            }
        };

        canvas.setPreferredSize(new Dimension(400, 600));
        canvas.setLayout(null);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getX() >= 100 && e.getX() <= 300 &&
                        e.getY() >= 500 && e.getY() <= 550) {
                    startNewGame();
                }
            }
        });

        setContentPane(canvas);
    }

    private void drawGrade(Graphics2D g){
        int total = 0;
        for (int i: gameFrame.guessTime)
            total += i;
        int correct = total - gameFrame.guessTime[6];

        int[] array = Arrays.copyOfRange(gameFrame.guessTime, 0, 6);
        Arrays.sort(array);
        int max = array[5];
        Font f = new Font("Arial", Font.BOLD, 10);

        for (int i = 0; i < 6; i++){
            int num = gameFrame.guessTime[i];
            int y = 205 + i * 30;
            g.setColor(new Color(86, 171, 89));
            int width = 0;

            if (max != 0) {
                if (num == max) {
                    width = 300;
                    g.fillRect(50, y, 300, 20);
                } else {
                    double ratio = (double) num / max;
                    width = (int) (300 * ratio);
                    g.fillRect(50, y, width, 20);
                }
            }

            String text = String.valueOf(i + 1);
            g.setColor(Color.BLACK);
            g.setFont(f);
            g.drawString(text, 25, y + 10);
            
            String time = String.valueOf(num);
            g.drawString(time, 60 + width, y + 10);
        }

        if (total > 0) {
            int winRate = (int) Math.round((double) correct * 100 / total);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            String text = "Win Rate: " + winRate + "%     " + "Total Game: " + total + "     Total Win: " + correct;
            g.drawString(text, 25, 400);
        }
    }

    private void drawButton(Graphics2D g){
        int width = 200;
        int height = 50;
        int x = (getWidth() - 200) / 2;
        int y = 500;

        g.setColor(new Color(86, 171, 89));
        g.fillRect(x, y, width, height);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String text = "NEW GAME";
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g.drawString(text,
                x + (width - textWidth) / 2,
                y + (height + textHeight) / 2 - 2);
    }

    private void startNewGame(){
        gameFrame.startNewGame();
        dispose();
    }

}
