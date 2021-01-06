import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Ball {
    // ボールの半径
    private double r = 10.0;

    // ボールの中心座標
    private double x = 0.0;
    private double y = 0.0;

    // ボールの速度（1フレーム当たりの移動距離）
    private double vx =  15.0;
    private double vy = -15.0;

    // ボールの色
    private Color color = Color.BLACK;

    // y軸 正の方向の加速度（1フレーム当たりの上記「速度」の変化量）
    private double g = 0.5;

    // 反発係数
    private double e = 0.8;

    private int endCount = 0;
    private boolean end = false;

    // ボールを描画するパネル
    private final JPanel panel;

    public Ball(JPanel panel) {
        this.panel = panel;
    }

    public Ball(double r, double x, double y, double vx, double vy, Color color, JPanel panel) {
        this.r = r;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.color = color;
        this.panel = panel;
    }

    /*--- アクセサメソッド ---*/
    // getter
    public double getR() { return r; }    

    public double getX() { return x; }
    public double getY() { return y; }

    public double getVx() { return vx; }
    public double getVy() { return vy; }

    public boolean getEnd() {
        return end;
    }

    // setter
    public void setR(double r) { this.r = r; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setXY(double x, double y) { this.x = x; this.y = y; }

    public void setVx(double vx) { this.vx = vx; }
    public void setVy(double vy) { this.vy = vy; }
    public void setVxVy(double vx, double vy) { this.vx = vx; this.vy = vy; }

    public void setG(double g) { this.g = g; }
    public void setE(double e) { this.e = e; }
    public void setColor(Color color) { this.color = color; }

    // ボールを描画
    public void draw(Graphics graphics) {
        Color prevColor = graphics.getColor();
        graphics.setColor(color);
        graphics.fillOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
        graphics.setColor(prevColor);
    }

    // ボールを左下隅へ移動
    public void goHome() {
        x = r;
        y = panel.getHeight() - r;
    }

    // 座標、速度更新
    public void next() {
        // 画面の幅と高さ取得
        int width  = panel.getWidth();
        int height = panel.getHeight();

        x = x + vx;
        y = y + vy;

        /*--- 衝突判定 ---*/
        if (x < r) {
            x = r;
            vx = -vx * e;
        }

        if (x + r > width) {
            x = width - r;
            vx = -vx * e;
        }

        if (y < r) {
            y = r;
            vy = -vy * e;
        }

        if (y + r > height) {
            y = height - r;
            vy = -vy * e;
        }

        // 画面下向きの加速度
        vy = vy + g;

        if (y == height - r) { //床についている状態で200 回更新したら
            endCount++;
            if (endCount > 200) {
                end = true;
            }
        } else endCount = 0; //床から離れればリセット
    }    
}
