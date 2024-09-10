package columns;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Columns extends JPanel implements Runnable, KeyListener {
    static final int SL = 25, Depth = 15, Width = 7, MaxLevel = 7, TimeShift = 250, FigToDrop = 33, MinTimeShift = 200, LeftBorder = 2, TopBorder = 2;

    Color[] MyStyles = {Color.black, Color.cyan, Color.blue, Color.red, Color.green, Color.yellow, Color.pink, Color.magenta, Color.black};
    int Level, i, j, ii, k, ch;
    long Score, DScore, tc;
    Font fCourier;
    Figure Fig;
    int[][] Fnew, Fold;
    boolean NoChanges = true, KeyPressed = false;
    Graphics2D g2d;
    Thread thr = null;

    public Columns() {
        setPreferredSize(new Dimension(Width * SL + LeftBorder * 2, Depth * SL + TopBorder * 2));
        addKeyListener(this);
        setFocusable(true);
        Fnew = new int[Width + 2][Depth + 2];
        Fold = new int[Width + 2][Depth + 2];
    }

    void CheckNeighbours(int a, int b, int c, int d, int i, int j) {
        if ((Fnew[j][i] == Fnew[a][b]) && (Fnew[j][i] == Fnew[c][d])) {
            Fold[a][b] = 0;
            DrawBox(a, b, 8);
            Fold[j][i] = 0;
            DrawBox(j, i, 8);
            Fold[c][d] = 0;
            DrawBox(c, d, 8);
            NoChanges = false;
            Score += (Level + 1) * 10;
            k++;
        }
    }

    void Delay(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
        }
    }

    void DrawBox(int x, int y, int c) {
        if (c == 0) {
            g2d.setColor(Color.black);
            g2d.fillRect(LeftBorder + x * SL - SL, TopBorder + y * SL - SL, SL, SL);
            g2d.setColor(Color.black);
            g2d.drawRect(LeftBorder + x * SL - SL, TopBorder + y * SL - SL, SL, SL);
        } else if (c == 8) {
            g2d.setColor(Color.white);
            g2d.drawRect(LeftBorder + x * SL - SL + 1, TopBorder + y * SL - SL + 1, SL - 2, SL - 2);
            g2d.drawRect(LeftBorder + x * SL - SL + 2, TopBorder + y * SL - SL + 2, SL - 4, SL - 4);
            g2d.setColor(Color.black);
            g2d.fillRect(LeftBorder + x * SL - SL + 3, TopBorder + y * SL - SL + 3, SL - 6, SL - 6);
        } else {
            g2d.setColor(MyStyles[c]);
            g2d.fillRect(LeftBorder + x * SL - SL, TopBorder + y * SL - SL, SL, SL);
            g2d.setColor(Color.black);
            g2d.drawRect(LeftBorder + x * SL - SL, TopBorder + y * SL - SL, SL, SL);
        }
    }

    void DrawField() {
        for (i = 1; i <= Depth; i++) {
            for (j = 1; j <= Width; j++) {
                DrawBox(j, i, Fnew[j][i]);
            }
        }
    }

    void DrawFigure(Figure f) {
        DrawBox(f.x, f.y, f.c[1]);
        DrawBox(f.x, f.y + 1, f.c[2]);
        DrawBox(f.x, f.y + 2, f.c[3]);
    }

    void DropFigure(Figure f) {
        int zz;
        if (f.y < Depth - 2) {
            zz = Depth;
            while (Fnew[f.x][zz] > 0) zz--;
            DScore = (((Level + 1) * (Depth * 2 - f.y - zz) * 2) % 5) * 5;
            f.y = zz - 2;
        }
    }

    boolean FullField() {
        for (int i = 1; i <= Width; i++) {
            if (Fnew[i][3] > 0)
                return true;
        }
        return false;
    }

    void HideFigure(Figure f) {
        DrawBox(f.x, f.y, 0);
        DrawBox(f.x, f.y + 1, 0);
        DrawBox(f.x, f.y + 2, 0);
    }

    void PackField() {
        int n;
        for (i = 1; i <= Width; i++) {
            n = Depth;
            for (j = Depth; j > 0; j--) {
                if (Fold[i][j] > 0) {
                    Fnew[i][n] = Fold[i][j];
                    n--;
                }
            }
            for (j = n; j > 0; j--) Fnew[i][j] = 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        ShowLevel();
        ShowScore();
        DrawField();
        DrawFigure(Fig);
    }

    void PasteFigure(Figure f) {
        Fnew[f.x][f.y] = f.c[1];
        Fnew[f.x][f.y + 1] = f.c[2];
        Fnew[f.x][f.y + 2] = f.c[3];
    }

    @Override
    public void run() {
        for (i = 0; i < Width + 1; i++) {
            for (j = 0; j < Depth + 1; j++) {
                Fnew[i][j] = 0;
                Fold[i][j] = 0;
            }
        }
        Level = 0;
        Score = 0;
        j = 0;
        k = 0;
        requestFocus();

        do {
            tc = System.currentTimeMillis();
            Fig = new Figure();
//            DrawFigure(Fig);
            repaint();
            while ((Fig.y < Depth - 2) && (Fnew[Fig.x][Fig.y + 3] == 0)) {
                if ((int) (System.currentTimeMillis() - tc) > (MaxLevel - Level) * TimeShift + MinTimeShift) {
                    tc = System.currentTimeMillis();
                    HideFigure(Fig);
                    Fig.y++;
//                    DrawFigure(Fig);
                    repaint();

                }
                DScore = 0;
                do {
                    Delay(50);

                    if (KeyPressed) {

                        KeyPressed = false;
                        switch (ch) {
                            case KeyEvent.VK_LEFT:
                                System.out.println("left");
                                if ((Fig.x > 1) && (Fnew[Fig.x - 1][Fig.y + 2] == 0)) {
                                    HideFigure(Fig);
                                    Fig.x--;
//                                    DrawFigure(Fig);
                                    repaint();

                                }
                                break;
                            case KeyEvent.VK_RIGHT:
                                if ((Fig.x < Width) && (Fnew[Fig.x + 1][Fig.y + 2] == 0)) {
                                    HideFigure(Fig);
                                    Fig.x++;
//                                    DrawFigure(Fig);
                                    repaint();

                                }
                                break;
                            case KeyEvent.VK_UP:
                                i = Fig.c[1];
                                Fig.c[1] = Fig.c[2];
                                Fig.c[2] = Fig.c[3];
                                Fig.c[3] = i;
//                                DrawFigure(Fig);
                                repaint();

                                break;
                            case KeyEvent.VK_DOWN:
                                i = Fig.c[1];
                                Fig.c[1] = Fig.c[3];
                                Fig.c[3] = Fig.c[2];
                                Fig.c[2] = i;
//                                DrawFigure(Fig);
                                repaint();

                                break;
                            case KeyEvent.VK_SPACE:
                                HideFigure(Fig);
                                DropFigure(Fig);
//                                DrawFigure(Fig);
                                repaint();

                                tc = 0;
                                break;
                            case 'P':
                            case 'p':
                                while (!KeyPressed) {
                                    HideFigure(Fig);
                                    Delay(50);
//                                    DrawFigure(Fig);
                                    repaint();

                                    Delay(50);
                                }
                                tc = System.currentTimeMillis();
                                break;
                            case '-':
                                if (Level > 0) Level--;
                                k = 0;
                                ShowLevel();
                                break;
                            case '+':
                                if (Level < MaxLevel) Level++;
                                k = 0;
                                ShowLevel();
                                break;
                        }
                    }
                } while ((int) (System.currentTimeMillis() - tc) <= (MaxLevel - Level) * TimeShift + MinTimeShift);
            }
            PasteFigure(Fig);
            do {
                NoChanges = true;
                TestField();
                if (!NoChanges) {
                    Delay(50);
                    PackField();
                    DrawField();
                    Score += DScore;
                    ShowScore();
                    if (k >= FigToDrop) {
                        k = 0;
                        if (Level < MaxLevel) Level++;
                        ShowLevel();
                    }
                }
            } while (!NoChanges);
        } while (!FullField());
    }

    void ShowHelp() {
        g2d.setColor(Color.black);
        g2d.drawString(" Keys available:", 200 - LeftBorder, 102);
        g2d.drawString("Roll Box Up:     ", 200 - LeftBorder, 118);
        g2d.drawString("Roll Box Down:   ", 200 - LeftBorder, 128);
        g2d.drawString("Figure Left:     ", 200 - LeftBorder, 138);
        g2d.drawString("Figure Right:    ", 200 - LeftBorder, 148);
        g2d.drawString("Level High/Low: +/-", 200 - LeftBorder, 158);
        g2d.drawString("Drop Figure:   space", 200 - LeftBorder, 168);
        g2d.drawString("Pause:           P", 200 - LeftBorder, 180);
        g2d.drawString("Quit:     Esc or Q", 200 - LeftBorder, 190);
    }

    void ShowLevel() {
        g2d.setColor(Color.black);
        g2d.clearRect(LeftBorder + 100, 390, 100, 20);
        g2d.drawString("Level: " + Level, LeftBorder + 100, 400);
    }

    void ShowScore() {
        g2d.setColor(Color.black);
        g2d.clearRect(LeftBorder, 390, 100, 20);
        g2d.drawString("Score: " + Score, LeftBorder, 400);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        KeyPressed = true;
        ch = e.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    void TestField() {
        for (i = 1; i <= Depth; i++) {
            for (j = 1; j <= Width; j++) {
                Fold[j][i] = Fnew[j][i];
            }
        }
        for (i = 1; i <= Depth; i++) {
            for (j = 1; j <= Width; j++) {
                if (Fnew[j][i] > 0) {
                    CheckNeighbours(j, i - 1, j, i + 1, i, j);
                    CheckNeighbours(j - 1, i, j + 1, i, i, j);
                    CheckNeighbours(j - 1, i - 1, j + 1, i + 1, i, j);
                    CheckNeighbours(j + 1, i - 1, j - 1, i + 1, i, j);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Columns Game");
        Columns gamePanel = new Columns();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.pack();
        frame.setVisible(true);
        new Thread(gamePanel).start();
    }
}