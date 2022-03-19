package com.example.demo.temp;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Zoom {

    public static int[] getCutPoint(int maxWidth, int maxHeight, Rectangle rectangle) {
        // a b c d
        // a x轴左移，y轴上移
        // b x轴右移，y轴上移
        // c x轴右移，y轴下移
        // d x轴左移，y轴下移
        int ax = rectangle.getA().getX();
        int ay = rectangle.getA().getY();
        int bx = rectangle.getB().getX();
        int by = rectangle.getB().getY();
        int cx = rectangle.getC().getX();
        int cy = rectangle.getC().getY();
        int dx = rectangle.getD().getX();
        int dy = rectangle.getD().getY();
        int i = 0;
        List<Point> rtn = new ArrayList<>();
        while (true) {
            i++;
            if (i > 10000) {
                break;
            }
            // 裁剪图形到达最高 break
            if (bx - ax == maxWidth) {
                break;
            }
            boolean isMaxHeight = false;
            // 裁剪图形到达最高，Y轴不在移动
            if (cy - ay == maxHeight) {
                isMaxHeight = true;
            }
            // 移动ad X
            if (ax > 0) {
                ax--;
                dx--;
            }
            // 移动bc X
            if (bx < maxWidth) {
                bx++;
                cx++;
            }
            // 裁剪图形到达最高
            if (isMaxHeight) {
                continue;
            }
            // 移动ab Y
            if (ay > 0) {
                ay--;
                by--;
            }
            // 移动cd Y
            if (cy < maxHeight) {
                cy++;
                dy++;
            }
        }
        return new int[]{ax, ay, bx, by, cx, cy, dx, dy};
    }

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle();
        rectangle.setA(new Point(1, 180));
        rectangle.setB(new Point(11, 180));
        rectangle.setC(new Point(11, 190));
        rectangle.setD(new Point(1, 190));
        System.out.println(Arrays.toString(getCutPoint(100, 200, rectangle)));
    }
}
