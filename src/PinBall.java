import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class PinBall {
    public static void main(String[] args) {
        JFrame window = new JFrame("PinBall");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(650, 400);
        
        Panel4GameBoard panel = new Panel4GameBoard();

        window.add(panel);
        window.setVisible(true);

        panel.animationStart();
    }
}

class Panel4GameBoard extends JPanel implements ActionListener {
    private final Ball ball;
    private final Timer timer;

    // 再描画タイミング
    private static final int INTERVAL = 50;

    public Panel4GameBoard() {
        timer = new Timer(INTERVAL, this);
        ball = new Ball(this);
    }

    public void animationStart() {
        ball.goHome();
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        ball.draw(graphics);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        ball.next();
        repaint();
    }
}
