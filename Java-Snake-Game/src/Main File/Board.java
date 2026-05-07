import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    final int DOT_SIZE = 10;
    final int WIDTH = 300;
    final int HEIGHT = 300;

    int[] x = new int[900];
    int[] y = new int[900];

    int dots = 3;

    int appleX;
    int appleY;

    boolean left = false;
    boolean right = true;
    boolean up = false;
    boolean down = false;

    boolean inGame = true;

    Timer timer;

    public Board() {

        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT && !right) {
                    left = true;
                    right = false;
                    up = false;
                    down = false;
                }

                if (key == KeyEvent.VK_RIGHT && !left) {
                    right = true;
                    left = false;
                    up = false;
                    down = false;
                }

                if (key == KeyEvent.VK_UP && !down) {
                    up = true;
                    left = false;
                    right = false;
                    down = false;
                }

                if (key == KeyEvent.VK_DOWN && !up) {
                    down = true;
                    left = false;
                    right = false;
                    up = false;
                }
            }
        });

        initGame();
    }

    void initGame() {

        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i * DOT_SIZE;
            y[i] = 50;
        }

        locateApple();

        timer = new Timer(100, this);
        timer.start();
    }

    void locateApple() {

        appleX = ((int)(Math.random() * 29)) * DOT_SIZE;
        appleY = ((int)(Math.random() * 29)) * DOT_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (inGame) {

            // Apple
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, DOT_SIZE, DOT_SIZE);

            // Snake
            for (int i = 0; i < dots; i++) {

                if (i == 0) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(x[i], y[i], DOT_SIZE, DOT_SIZE);
            }

        } else {

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Game Over", 90, 150);
        }
    }

    void move() {

        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (left) x[0] -= DOT_SIZE;
        if (right) x[0] += DOT_SIZE;
        if (up) y[0] -= DOT_SIZE;
        if (down) y[0] += DOT_SIZE;
    }

    void checkApple() {

        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            locateApple();
        }
    }

    void checkCollision() {

        for (int i = dots; i > 0; i--) {

            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }

        if (x[0] < 0 || x[0] >= WIDTH ||
            y[0] < 0 || y[0] >= HEIGHT) {

            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            move();
            checkApple();
            checkCollision();
        }

        repaint();
    }

    // MAIN METHOD
    public static void main(String[] args) {

        JFrame frame = new JFrame("Snake Game");

        Board game = new Board();

        frame.add(game);
        frame.setSize(320, 340);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}