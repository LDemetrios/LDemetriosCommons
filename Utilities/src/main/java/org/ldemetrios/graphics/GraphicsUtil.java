package org.ldemetrios.graphics;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("unused")
public class GraphicsUtil {
    public static void drawBezier(Graphics g, double[] xs, double[] ys, int steps) {
        if (xs.length != ys.length) {
            throw new IllegalArgumentException("xs and ys are supposed to be of equal length");
        }
        final int n = xs.length - 1;
        final int[] cn = new int[n + 1];
        cn[0] = 1;
        for (int k = 1; k <= n; k++) {
            cn[k] = cn[k - 1] * (1 - k + n) / k;
        }

        double step = 1.0 / steps;
        int[] resXs = new int[steps + 1];
        int[] resYs = new int[steps + 1];
        for (int i = 0; i < steps; i++) {
            resXs[i] = (int) count(xs, n, cn, step * i);
            resYs[i] = (int) count(ys, n, cn, step * i);
        }
        resXs[steps] = (int) xs[n];
        resYs[steps] = (int) ys[n];
        g.drawPolyline(resXs, resYs, steps + 1);
    }

    private static double count(double[] coords, int n, int[] cn, double t) {
        double sum = 0.0;
        for (int i = 0; i <= n; i++) {
            sum += cn[i] * Math.pow(t, i) * Math.pow(1 - t, n - i) * coords[i];
        }
        return sum;
    }

    public static JFrame createCanvasWindow(Component canvas, String title) {
        JFrame frame = new JFrame(title);
        frame.setLayout(new GridLayout(1, 1));
        frame.add(canvas);
        frame.pack();
        return frame;
    }

    public static JFrame createTableWindow(String[] columns, Object[][] data, String title) {
        JFrame frame = new JFrame(title);
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        frame.add(scrollPane);
        frame.pack();
        return frame;
    }
}
