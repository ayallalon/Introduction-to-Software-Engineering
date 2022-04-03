package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

class ImageWriterTest {

    @Test
    void testWriteToImage() {
        int nX = 800;
        int nY = 500;

        ImageWriter imageWriter = new ImageWriter("yellowred", nX, nY);
        Color yellowColor = new Color(255, 255, 0);
        Color redColor = new Color(255, 0, 0);
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, redColor);
                } else {
                    imageWriter.writePixel(i, j, yellowColor);
                }
            }

            imageWriter.writeToImage();
        }
    }
}