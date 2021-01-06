import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

class Panel4GameBoard extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    private Ball ball;
    private final List<Ball> ballList;
    private final Timer timer;
    private int pressedPosX, pressedPosY, currentPosX = -1, currentPosY = -1;

    // 再描画タイミング
    private static final int INTERVAL = 16;

    public Panel4GameBoard() {
        timer = new Timer(INTERVAL, this);
        ballList = new ArrayList<Ball>();
        ballList.add(new Ball(this));
        addMouseListener(this);
        addMouseMotionListener(this);
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
        if (ball != null)
            ball.draw(graphics);
        if (currentPosX != -1 && currentPosY != -1)
            graphics.drawLine(pressedPosX, pressedPosY, currentPosX, currentPosY);

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

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            pressedPosX = e.getX();
            pressedPosY = e.getY();
            Random random = new Random();
            int n = 10;
            for (int i = 0; i < n; i++) {
                ballList.add(new Ball(10, pressedPosX, pressedPosY, random.nextInt(30) - 15, -10,
                        new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)), this));
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            pressedPosX = e.getX();
            pressedPosY = e.getY();
            Random random = new Random();
            ball = new Ball(10, e.getX(), e.getY(), 0, 0,
                    new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)), this);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            ball.setVxVy(0.1 * (pressedPosX - e.getX()), 0.1 * (pressedPosY - e.getY()));
            ballList.add(ball);
        }
        ball = null;
        currentPosX = currentPosY = -1;
    }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentPosX = e.getX();
        currentPosY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
