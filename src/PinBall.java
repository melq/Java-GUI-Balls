import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

class Panel4GameBoard extends JPanel implements ActionListener, MouseListener {
    private final List<Ball> ballList;
    private final Timer timer;

    // 再描画タイミング
    private static final int INTERVAL = 50;

    public Panel4GameBoard() {
        timer = new Timer(INTERVAL, this);
        ballList = new ArrayList<Ball>();
        ballList.add(new Ball(this));
        addMouseListener(this);
    }

    public void animationStart() {
        ballList.get(0).goHome();
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        for (Ball ball: ballList) {
            ball.draw(graphics);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        for (Ball ball: ballList) {
            ball.next();
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            Random random = new Random();
            ballList.add(new Ball(10, e.getX(), e.getY(), 30, 20,
                    new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)), this));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
