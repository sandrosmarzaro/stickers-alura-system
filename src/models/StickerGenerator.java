package models;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class StickerGenerator {

    public static void generate(InputStream inputStream, String fileName) throws IOException {

        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(inputStream);
        }
        catch (Exception e) {
            System.out.println("Error convert image");
            return;
        }
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int newHeight = height + 200;
        BufferedImage newBufferedImage = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);

        Graphics2D graphics = newBufferedImage.createGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.setFont(new Font("Impact", Font.PLAIN, 96));
        graphics.setColor(Color.YELLOW);
        String rating = "Test";
        int ratingWidth = graphics.getFontMetrics().stringWidth(rating);
        int centerWidth = (width - ratingWidth) / 2;
        graphics.drawString(rating, centerWidth, newHeight - 100);

        ImageIO.write(newBufferedImage, "png", new File("src/assets/" + fileName));
    }
}
