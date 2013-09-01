import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;


public class FrameExtractor {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Usage: FrameExtractor filename.gif");
			System.exit(1);
		}
		
		ImageReader imgReader = (ImageReader)ImageIO.getImageReadersByFormatName("gif").next();
        ImageInputStream imgInputStream = ImageIO.createImageInputStream(new File(args[0]));
        imgReader.setInput(imgInputStream, false);   
        int noFrames = imgReader.getNumImages(true);
        System.out.println(String.format("image contains %d frames", noFrames));
        StringBuilder pixelString = new StringBuilder();
        for (int i = 0; i < noFrames; i++) {
        	BufferedImage frame = imgReader.read(i);
			int[] pixels = frame.getRGB(0, 0, frame.getWidth(),
					frame.getHeight(), null, 0, frame.getWidth());
			for (int j = 0; j < pixels.length; j++) {
				int rgbPx = pixels[j];
				Color c = new Color(rgbPx);
				pixelString.append(String.format("(%3d, %3d, %3d) ",
						c.getRed(), c.getGreen(), c.getBlue()));
				if (j % frame.getWidth() == 0)
					pixelString.append("\n");
			}
			
			System.out.println(String.format("Pixmap for frame %d:", i+1));
			System.out.println(pixelString.toString());
			pixelString.delete(0, pixelString.length());
        }
	}

}
