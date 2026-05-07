import javax.swing.JFrame;

public class Snake extends JFrame {

    public Snake() {

        add(new Board());

        setTitle("Snake Game");
        setSize(320, 340);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {

        Snake game = new Snake();
        game.setVisible(true);
    }
}