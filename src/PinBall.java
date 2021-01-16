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
    private final List<Ball> ballList, particleList;
    private final Timer timer;
    private int pressedPosX, pressedPosY, currentPosX, currentPosY;

    private boolean isLeftPressed = false;
    private int leftPressCount = 0;

    // 再描画タイミング
    private static final int INTERVAL = 16;

    public Panel4GameBoard() {
        timer = new Timer(INTERVAL, this);
        ballList = new ArrayList<Ball>();
        particleList = new ArrayList<Ball>();
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
        for (Ball ball: particleList) {
            ball.draw(graphics);
        }

        // 発射しようとしているボールと線分の描画
        if (ball != null) { // 発射準備中のボールがあれば
            ball.draw(graphics);
            graphics.drawLine(pressedPosX, pressedPosY, currentPosX, currentPosY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        for (Ball ball: ballList) // 各ボールをの next メソッドを呼ぶ
            ball.next();
        for (Ball ball: particleList)
            ball.next();

        // ボールの削除
        for (int i = 0; i < ballList.size(); i++) { // 削除フラグチェック、削除
            if (ballList.get(i).getEnd()) {
                randomBalls(particleList, ballList.get(i).getX(), ballList.get(i).getY(), 5, 7);
                ballList.remove(i);
                i--;
            }
        }
        for (int i = 0; i < particleList.size(); i++) {
            if (particleList.get(i).getEnd()) {
                particleList.remove(i);
                i--;
            }
        }

        // 左クリック長押しでボールを出し続ける
        if (isLeftPressed) {
            if (leftPressCount % 7 == 0)
                randomBalls(ballList, currentPosX, currentPosY, 10, 1);
            leftPressCount++;
        }

        // 発射準備中かつ、マウスがその場で長押しされているとき、ボールを大きくする
        if (ball != null) {
            if (pressedPosX == currentPosX && pressedPosY == currentPosY) ball.setR(ball.getR() * 1.02);
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        pressedPosX = e.getX();
        pressedPosY = e.getY();

        // 左クリックでランダムに n 個のボールを飛ばす
        if (e.getButton() == MouseEvent.BUTTON1) {
            isLeftPressed = true;
            randomBalls(ballList, pressedPosX, pressedPosY, 10, 10);
        }
        // 右クリックで好きな方向、速度でボールを撃ち出せる
        else if (e.getButton() == MouseEvent.BUTTON3) {
            Random random = new Random();
            ball = new Ball(10, e.getX(), e.getY(), 0, 0, // ball を null でなくすることで発射準備開始
                    new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)), this);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            isLeftPressed = false;
        }
        // 右クリックを離したとき、ボールを発射
        else if (e.getButton() == MouseEvent.BUTTON3) {
            ball.setVxVy(0.2 * (pressedPosX - e.getX()), 0.2 * (pressedPosY - e.getY()));
            ballList.add(ball);
        }
        ball = null; // 発射準備状態を終わる
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
        currentPosX = e.getX();
        currentPosY = e.getY();
    }

    // ランダムに n 個のボールを作る
    public void randomBalls(List<Ball> ballList, double posX, double posY, double r, int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            ballList.add(new Ball(r, posX, posY,
                    random.nextInt(30) - 15, random.nextInt(15) - 15,
                    new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)), this));
        }
    }
}
