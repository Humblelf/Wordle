package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameFrame extends JPanel implements KeyListener {
    private int letterNumber;//总共几个字母
    private ArrayList<String> wordList;//所有读取的合格的单词
    private JLabel[][] letterLabel;//填字母的地方
    private final int RECTANGLE_SPACE = 10;
    private final int LABEL_SIZE = 60;
    private int currentRow = 0;           // 当前正在输入的行
    private int currentCol = 0;
    private boolean gameEnd;
    private String answerWord;
    private String guessWord;
    private int index = 0;
    private Timer messageTimer;
    protected int[] guessTime;
    private boolean isAnimating = false;

    public GameFrame(int letterNumber){
        this.letterNumber = letterNumber;
        this.wordList = new ArrayList<>();
        guessTime = new int[7];
        loadWordList();
        answerWord = wordList.get(index);
        System.out.println(answerWord);
        index++;
        gameEnd = false;

        messageTimer = new Timer(1000, e -> {
            repaint();
            messageTimer.stop();
        });
        messageTimer.setRepeats(false);

        setOpaque(true);
        setBackground(Color.WHITE);

        setupUI();
        repaint();

        // 设置焦点，以便接收键盘事件
        setFocusable(true);
        requestFocusInWindow();

        // 添加键盘监听器
        addKeyListener(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int x = (getWidth() - 200) / 2;
                if (e.getX() >= x && e.getX() <= x + 200 && e.getY() >= 500 && e.getY() <= 550)
                    startNewGame();
            }
        });
    }

    private void loadWordList(){
        try {
            File file = new File("wordle/src/resource/other/totalWordList.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String word;

            while ((word = reader.readLine()) != null){
                if (word.length() == letterNumber)
                    wordList.add(word);
            }
            Collections.shuffle(wordList);
        }catch (Exception e){
            System.out.println("Fail");
        }
    }

    private void setupUI(){
        setLayout(new BorderLayout());

        JPanel wordlePanel = new JPanel();
        wordlePanel.setLayout(new BoxLayout(wordlePanel, BoxLayout.Y_AXIS));
        wordlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        letterLabel = new JLabel[6][letterNumber];
        for (int i = 0; i < 6; i++){
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(null);
            int rowWidth = letterNumber * LABEL_SIZE + (letterNumber - 1) * RECTANGLE_SPACE;
            rowPanel.setPreferredSize(new Dimension(rowWidth, LABEL_SIZE));
            rowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            int x = 0;
            for (int j = 0; j < letterNumber; j++){
                AnimatedLabel l = (AnimatedLabel) createLetterLabel("");
                letterLabel[i][j] = l;
                l.setLocation(x, 0);
                l.setSize(LABEL_SIZE, LABEL_SIZE);
                rowPanel.add(l);
                x += LABEL_SIZE + RECTANGLE_SPACE;
            }
            wordlePanel.add(rowPanel);
            wordlePanel.add(Box.createRigidArea(new Dimension(0, RECTANGLE_SPACE)));
        }

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(wordlePanel);

        add(wrapper, BorderLayout.NORTH);
    }

    private JLabel createLetterLabel(String text){
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 32));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);

        // 设置格子为正方形
        label.setPreferredSize(new Dimension(LABEL_SIZE, LABEL_SIZE));
        label.setMinimumSize(new Dimension(LABEL_SIZE, LABEL_SIZE));
        label.setMaximumSize(new Dimension(LABEL_SIZE, LABEL_SIZE));

        // 添加边框
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        return new AnimatedLabel(text);
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

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        drawButton((Graphics2D) g);
    }

    @Override
    public Dimension getPreferredSize() {
        int wordWidth = letterNumber * LABEL_SIZE + (letterNumber - 1) * RECTANGLE_SPACE + 40;
        int height = 600;

        return new Dimension(wordWidth, height);
    }

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e){
        if (isAnimating || gameEnd)
            return;

        int keyCode = e.getKeyCode();
        char keyChar = e.getKeyChar();

        // 处理字母输入（A-Z）
        boolean atLast;
        atLast = !letterLabel[currentRow][letterNumber - 1].getText().isEmpty();

        if (keyChar >= 'a' && keyChar <= 'z' || keyChar >= 'A' && keyChar <= 'Z') {
            if (currentRow < 6 && currentCol < letterNumber && !atLast) {
                String letter = String.valueOf(keyChar).toUpperCase();
                JLabel l = letterLabel[currentRow][currentCol];
                l.setText(letter);//更新字母
                l.setForeground(Color.BLACK);
                ((AnimatedLabel) l).bounce();
                if (currentCol < letterNumber - 1) {
                    currentCol++; // 移动到同一行的下一列
                }
            }
        }
        // 退格键删除
        else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            if (currentCol > 0 && !atLast){
                currentCol--;
                letterLabel[currentRow][currentCol].setText("");
            }else if (atLast)
                letterLabel[currentRow][currentCol].setText("");
        }
        // 回车键提交
        else if (keyCode == KeyEvent.VK_ENTER && atLast) {
            StringBuilder guess = new StringBuilder();
            for (int i = 0; i < letterNumber; i++)
                guess.append(letterLabel[currentRow][i].getText().toLowerCase());
            guessWord = guess.toString();
            if (!wordList.contains(guessWord))
                showNotFound();
            else {
                changeColor(currentRow, 0);
                currentRow++;
                currentCol = 0;
            }
        }
    }

    private int[] judge(){
        int[] m = new int[letterNumber];//-1是没出现，0是不在正确位置，1是在正确位置
        Arrays.fill(m, -1);

        int[] letterCount = new int[26];
        for (int i = 0; i < letterNumber; i++){
            char c = answerWord.charAt(i);
            letterCount[c - 'a']++;
        }

        for (int i = 0; i < letterNumber; i++)
            if (answerWord.charAt(i) == guessWord.charAt(i)){
                m[i] = 1;
                letterCount[answerWord.charAt(i) - 'a']--;
            }

        for (int i = 0; i < letterNumber; i++){
            if (m[i] != 1){
                char c = guessWord.charAt(i);
                if (letterCount[c - 'a'] > 0){
                    m[i] = 0;
                    letterCount[c - 'a']--;
                }
            }
        }

        return m;
    }

    private void changeColor(int row, int index){
        isAnimating = true;

        if (index < letterNumber){
            int[] array = judge();
            if (array[index] == -1)
                turnGrey(row, index);
            else if (array[index] == 0)
                turnYellow(row, index);
            else
                turnGreen(row, index);

            letterLabel[row][index].setForeground(Color.WHITE);

            ((AnimatedLabel) letterLabel[row][index]).bounce();

            new Timer(300, e -> {
                changeColor( row, index + 1);
                ((Timer) e.getSource()).stop();
            }).start();
        }else{
            boolean win = false;

            if (guessWord != null && guessWord.equals(answerWord)) {
                gameEnd = true;
                win = true;
                guessTime[row]++;
            }else if (row >= 5) {
                gameEnd = true;
                guessTime[6]++;
            }

            if (gameEnd){
                new GameEndFrame(this, win);
            }
            isAnimating = false;
        }
    }

    private void turnGreen(int row, int col){
        letterLabel[row][col].setBackground(new Color(86, 171, 89));
    }

    private void turnYellow(int row, int col){
        letterLabel[row][col].setBackground(new Color(201, 180, 88));
    }

    private void turnGrey(int row, int col){
        letterLabel[row][col].setBackground(new Color(120, 124, 126));
    }

    private void showNotFound(){
        JLabel msg = new JLabel("Word not found", SwingConstants.CENTER);
        msg.setFont(new Font("Arial", Font.BOLD, 18));
        msg.setForeground(Color.BLACK);

        /* 1. 用 JPanel 当底板，负责画圆角背景 */
        JPanel panel = new JPanel() {
            private final int R = 18;          // 圆角半径
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(240, 240, 240, 240));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), R, R);
                g2.setColor(Color.GRAY);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, R, R);
                g2.dispose();
                super.paintComponent(g);   // 让子组件（label）继续画
            }
        };
        panel.setLayout(new BorderLayout());
        panel.add(msg, BorderLayout.CENTER);
        panel.setOpaque(false);   // 关键：自己画背景，不让系统画

        /* 2. 建一个无边框、背景透明的窗口 */
        JWindow tip = new JWindow(SwingUtilities.getWindowAncestor(this));
        tip.setBackground(new Color(0, 0, 0, 0)); // 全透明
        tip.setContentPane(panel);
        tip.setSize(200, 150);
        tip.setLocationRelativeTo(this);

        /* 3. 把窗口形状裁成圆角（可选，仅支持部分平台，写不写都行） */
        try {
            int R = 18;
            RoundRectangle2D shape =
                    new RoundRectangle2D.Float(0, 0, tip.getWidth(), tip.getHeight(), R, R);
            tip.setShape(shape);          // JDK 6u10+ 支持
        } catch (Exception ignore) { /* 老 JDK 或平台不支持就放弃 */ }

        tip.setVisible(true);

        /* 1 秒后自动消失 */
        new Timer(2000, e -> {
            tip.dispose();
            ((Timer) e.getSource()).stop();
        }).start();
    }

    protected void startNewGame(){
        if (index >= wordList.size()) {
            index = 0;
            Collections.shuffle(wordList);
        }

        answerWord = wordList.get(index);
        index++;
        gameEnd = false;
        isAnimating = false;
        currentRow = 0;
        currentCol = 0;
        guessWord = null;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < letterNumber; j++) {
                JLabel label = letterLabel[i][j];
                label.setText("");
                label.setBackground(null);  // 恢复默认背景（透明）
                label.setOpaque(true);      // 确保不透明设置正确
                label.setForeground(Color.WHITE);  // 恢复默认文字颜色
                label.setBorder(BorderFactory.createLineBorder(new Color(86, 87, 88), 2));
            }
        }

        System.out.println(answerWord);

        // 重新获取焦点，确保键盘输入继续有效
        requestFocusInWindow();
        repaint();
    }

    /* 带放大/回弹动画的 JLabel */
    private static class AnimatedLabel extends JLabel {
        private static final int BASE   = 60;          // 正常尺寸
        private static final float SCALE = 1.15f;      // 最大放大比例
        private static final int STEP   = 3;           // 每步像素变化
        private final Timer timer;
        private int current = BASE;
        private int target  = BASE;

        AnimatedLabel(String text) {
            super(text, SwingConstants.CENTER);
            setFont(new Font("Arial", Font.BOLD, 32));
            setForeground(Color.WHITE);
            setOpaque(true);
            setPreferredSize(new Dimension(BASE, BASE));
            setMinimumSize (null);
            setMaximumSize (null);
            setBorder(BorderFactory.createLineBorder(new Color(86, 87, 88), 2));

            /* 10ms 刷新一次 */
            timer = new Timer(10, e -> tick());
            timer.setRepeats(true);
        }

        /* 外部调用：触发一次放大-回弹 */
        void bounce() {
            setMaximumSize(null);   // 临时放开上限
            target = Math.round(BASE * SCALE);   // 先放大
            if (!timer.isRunning()) timer.start();
        }

        private void tick() {
            if (current < target) {
                current = Math.min(current + STEP, target);
            } else if (current > target) {
                current = Math.max(current - STEP, target);
            }

            /* 中心定位：先拿到当前中心，再反推左上角 */
            int centerX = getX() + getWidth() / 2;
            int centerY = getY() + getHeight() / 2;
            int newLeft = centerX - current / 2;
            int newTop  = centerY - current / 2;

            setLocation(newLeft, newTop);
            setSize(current, current);

            if (current == target) {
                if (target > BASE) {
                    target = BASE;
                } else {
                    /* 动画结束，重新锁死 60×60 并回到初始左上角 */
                    setLocation(centerX - BASE / 2, centerY - BASE / 2);
                    setSize(BASE, BASE);
                    timer.stop();
                }
            }
        }
    }
}
