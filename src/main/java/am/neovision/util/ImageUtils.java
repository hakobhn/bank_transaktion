package am.neovision.util;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author hakob.hakobyan created on 11/9/2020
 */
public class ImageUtils {

    public static BufferedImage generateImageFromText(String text) {

        // default values
        Color background = Color.decode("#005cbf");
        Color foreground = Color.decode("#ffffff");

        int size = 28;
        int padding = 10;
        int complexity = 5;

        // buffered image setup
        BufferedImage buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = buffer.createGraphics();
        Font font;
        try {
            font = new JLabel().getFont();
            font = font.deriveFont(size * 2f);
        } catch (Exception e) {
            font = new Font("Arial", Font.BOLD, size);
        }
        Rectangle2D r = font.getStringBounds(text, g2d.getFontRenderContext());
        buffer = new BufferedImage(
                (int) r.getWidth() + padding * 2,
                (int) r.getHeight() + padding * 2,
                BufferedImage.TYPE_INT_RGB);
        g2d = (Graphics2D) buffer.getGraphics();
        g2d.setFont(font);

        // fill background
        g2d.setColor(background);
        g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());

        // text
        g2d.setTransform(AffineTransform.getRotateInstance(0d));
        g2d.setColor(foreground);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        g2d.drawString(text, padding / 1, buffer.getHeight() - padding * size / 15);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
        g2d.drawString(text, padding / 2, buffer.getHeight() - (padding * size / 14));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.drawString(text, (int) (padding / 0.8), buffer.getHeight() - (padding * size / 18));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        g2d.drawString(text, (int) (padding / 1.9), buffer.getHeight() - (padding * size / 17));

        // copy to buffer2 and use cos/sin to distort
        BufferedImage buffer2 = new BufferedImage(
                buffer.getWidth(),
                buffer.getHeight(),
                buffer.getType());
        Graphics2D g2d2 = (Graphics2D) buffer2.getGraphics();
        g2d2.drawImage(buffer, 0, 0, null);

        Random rand = new Random();
        double seed = rand.nextDouble() * 3d + 5d;
        for (int x = 0; x < buffer.getWidth(); x++) {
            for (int y = 0; y < buffer.getHeight(); y++) {
                int xx = x + (int) (Math.cos((double) y / seed) * ((double) complexity / 2d));
                int yy = y + (int) (Math.sin((double) x / (seed + 1)) * ((double) complexity / 2d));
                xx = Math.abs(xx % buffer.getWidth());
                yy = Math.abs(yy % buffer.getHeight());
                buffer.setRGB(x, y, buffer2.getRGB(xx, yy));
            }
        }

        // draw lines
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2d.setTransform(AffineTransform.getRotateInstance(rand.nextDouble() * 0.3d - 0.15d));
        g2d.setColor(new Color(128, 128, 128, 128));
        for (int x = -100; x < buffer.getWidth() + 100; x = x + (rand.nextInt(9) + 6)) {
            g2d.setStroke(new BasicStroke(
                    1,
                    BasicStroke.CAP_SQUARE,
                    BasicStroke.CAP_SQUARE,
                    10, new float[]{rand.nextInt(10) + 2, rand.nextInt(4) + 2},
                    0));
            g2d.drawLine(x, -100, x, buffer.getHeight() + 100);
        }
        g2d.setColor(new Color(188, 188, 128, 64));
        for (int y = -100; y < buffer.getHeight() + 100; y = y + (rand.nextInt(8) + 7)) {
            g2d.setStroke(new BasicStroke(
                    1,
                    BasicStroke.CAP_SQUARE,
                    BasicStroke.CAP_SQUARE,
                    10, new float[]{rand.nextInt(10) + 2, rand.nextInt(3) + 2},
                    0));
            g2d.drawLine(-100, y, buffer.getWidth() + 100, y);
        }

        // free graphic resources
        g2d.dispose();
        return buffer;
    }

}
