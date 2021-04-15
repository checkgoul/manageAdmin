package com.nynu.goule.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerifyCode {

    public static  String drawRandomText(int width, int height, BufferedImage verifyImg) {
        Graphics2D graphics = (Graphics2D)verifyImg.getGraphics();
        graphics.setColor(Color.WHITE);//设置画笔颜色-验证码背景色
        graphics.fillRect(0, 0, width, height);//填充背景
        graphics.setFont(new Font("微软雅黑", Font.BOLD, 40));
        //数字和字母的组合
        String baseNumLetter = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
        StringBuffer sBuffer = new StringBuffer();
        int x = 10;  //旋转原点的 x 坐标
        String ch = "";
        Random random = new Random();
        for(int i = 0;i < 4;i++){
            graphics.setColor(getRandomColor());
            //设置字体旋转角度
            int degree = random.nextInt() % 30;  //角度小于30度
            int dot = random.nextInt(baseNumLetter.length());
            ch = baseNumLetter.charAt(dot) + "";
            sBuffer.append(ch);
            //正向旋转
            graphics.rotate(degree * Math.PI / 180, x, 45);
            graphics.drawString(ch, x, 45);
            //反向旋转
            graphics.rotate(-degree * Math.PI / 180, x, 45);
            x += 48;
        }
        //画干扰线
        for (int i = 0; i <6; i++) {
            // 设置随机颜色
            graphics.setColor(getRandomColor());
            // 随机画线
            graphics.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height));
        }
        //添加噪点
        for(int i=0;i<30;i++){
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.setColor(getRandomColor());
            graphics.fillRect(x1, y1, 2,2);
        }
        return sBuffer.toString();
    }

    /**
     * 随机取色
     */
    private static Color getRandomColor() {
        Random ran = new Random();
        Color color = new Color(ran.nextInt(256),
                ran.nextInt(256), ran.nextInt(256));
        return color;
    }

    /**
     * RGB转16进制 颜色
     * @param r
     * @param g
     * @param b
     * @return
     */
    private static String convertRGBToHex(int r, int g, int b) {
        String rFString, rSString, gFString, gSString,
                bFString, bSString, result;
        int red, green, blue;
        int rred, rgreen, rblue;
        red = r / 16;
        rred = r % 16;
        if (red == 10) {rFString = "A";}
        else if (red == 11) {rFString = "B";}
        else if (red == 12) {rFString = "C";}
        else if (red == 13) {rFString = "D";}
        else if (red == 14) {rFString = "E";}
        else if (red == 15) {rFString = "F";}
        else {rFString = String.valueOf(red);}

        if (rred == 10) {rSString = "A";}
        else if (rred == 11) {rSString = "B";}
        else if (rred == 12) {rSString = "C";}
        else if (rred == 13) {rSString = "D";}
        else if (rred == 14) {rSString = "E";}
        else if (rred == 15) {rSString = "F";}
        else {rSString = String.valueOf(rred);}

        rFString = rFString + rSString;

        green = g / 16;
        rgreen = g % 16;

        if (green == 10) {gFString = "A";}
        else if (green == 11) {gFString = "B";}
        else if (green == 12) {gFString = "C";}
        else if (green == 13) {gFString = "D";}
        else if (green == 14) {gFString = "E";}
        else if (green == 15) {gFString = "F";}
        else {gFString = String.valueOf(green);}

        if (rgreen == 10) {gSString = "A";}
        else if (rgreen == 11) {gSString = "B";}
        else if (rgreen == 12) {gSString = "C";}
        else if (rgreen == 13) {gSString = "D";}
        else if (rgreen == 14) {gSString = "E";}
        else if (rgreen == 15) {gSString = "F";}
        else {gSString = String.valueOf(rgreen);}

        gFString = gFString + gSString;

        blue = b / 16;
        rblue = b % 16;

        if (blue == 10) {bFString = "A";}
        else if (blue == 11) {bFString = "B";}
        else if (blue == 12) {bFString = "C";}
        else if (blue == 13) {bFString = "D";}
        else if (blue == 14) {bFString = "E";}
        else if (blue == 15) {bFString = "F";}
        else {bFString = String.valueOf(blue);}

        if (rblue == 10) {bSString = "A";}
        else if (rblue == 11) {bSString = "B";}
        else if (rblue == 12) {bSString = "C";}
        else if (rblue == 13) {bSString = "D";}
        else if (rblue == 14) {bSString = "E";}
        else if (rblue == 15) {bSString = "F";}
        else {bSString = String.valueOf(rblue);}
        bFString = bFString + bSString;
        result = "#" + rFString + gFString + bFString;
        return result;
    }


        public static void main(String[] args) {
        Color c = getRandomColor();
        System.out.println(c);
        String s = convertRGBToHex(c.getRed(),c.getGreen(),c.getBlue());
        System.out.println(s);
    }
}
