package com.jmlearning.randomthings;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SimulatedAnnealing extends JFrame {

    Random random = new Random(1);
    int n = random.nextInt(300) + 250;

    int[] bestState;
    double[] x = new double[n];
    double[] y = new double[n];

    {
        for(int i = 0; i < n; i++) {

            x[i] = random.nextDouble();
            y[i] = random.nextDouble();
        }
    }

    public void anneal() {

        int[] currentState = new int[n];

        for(int i = 0; i < n; i++)

            currentState[i] = i;

        double currentEnergy = eval(currentState);
        bestState = currentState.clone();
        double bestEnergy = currentEnergy;

        for(double temperature = 0.1, coolingFactor = 0.999999; temperature > 1e-4; temperature *= coolingFactor) {

            int i = random.nextInt(n);
            int j = (i + 1 + random.nextInt(n - 2)) % n;
            int i1 = (i - 1 + n) % n;
            int j1 = (j + 1) % n;

            double delta =

                    dist(x[currentState[i1]], y[currentState[i1]], x[currentState[j]], y[currentState[j]])
                    + dist(x[currentState[i]], y[currentState[i]], x[currentState[j1]], y[currentState[j1]])
                    - dist(x[currentState[i1]], y[currentState[i1]], x[currentState[i]], y[currentState[i]])
                    - dist(x[currentState[j]], y[currentState[j]], x[currentState[j1]], y[currentState[j1]]);

            if(delta < 0 || Math.exp(-delta / temperature) > random.nextDouble()) {

                reverse(currentState, i, j);
                currentEnergy += delta;

                if(bestEnergy > currentEnergy) {

                    bestEnergy = currentEnergy;
                    System.arraycopy(currentState, 0, bestState, 0, n);
                    repaint();
                }
            }
        }
    }

    static void reverse(int[] p, int i, int j) {

        int n = p.length;

        while(i != j) {

            int t = p[j];
            p[j] = p[i];
            p[i] = t;
            i = (i + 1) % n;

            if(i == j)

                break;

            j = (j - 1 + n) % n;
        }
    }

    double eval(int[] state) {

        double res = 0;

        for(int i = 0, j = state.length - 1; i < state.length; j = i++)

            res += dist(x[state[i]], y[state[i]], x[state[j]], y[state[j]]);

        return res;
    }

    static double dist(double x1, double y1, double x2, double y2) {

        double dx = x1 - x2;
        double dy = y1 - y2;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public SimulatedAnnealing() {

        setContentPane(new JPanel() {

            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                ((Graphics2D) g).setStroke(new BasicStroke(3));

                g.setColor(Color.BLUE);

                int w = getWidth() - 5;
                int h = getHeight() - 30;

                for(int i = 0, j = n - 1; i < n; j= i++)

                    g.drawLine((int) (x[bestState[i]] * w), (int) ((1 - y[bestState[i]]) * h),
                            (int) (x[bestState[j]] * w), (int) ((1 - y[bestState[j]]) * h));

                g.setColor(Color.RED);

                for(int i = 0; i < n; i++)

                    g.drawOval((int) (x[i] * w), (int) ((1 - y[i]) * h) - 1, 3, 3);

                g.setColor(Color.BLACK);
                g.drawString(String.format("length: %.3f", eval(bestState)), 5, h + 20);
            }
        });

        setSize(new Dimension(600, 600));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        new Thread(this::anneal).start();
    }

    public static void main(String[] args) {

        new SimulatedAnnealing();
    }
}