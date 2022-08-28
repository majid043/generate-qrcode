
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class GenerateQRCodeUsingZXing {

	public static void main(String[] args) {

		 String myCodeText = "427000006329";
	       
	        int size = 512;
	        String fileType = "png";
	        File fileObj = new File("image.png");
	        try {
	 
	            Map<EncodeHintType, Object> hintType = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
	            hintType.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	 
	            // Now with version 3.4.1 you could change margin (white border size)
	            hintType.put(EncodeHintType.MARGIN, 1); /* default = 4 */
	            Object put = hintType.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
	 
	            QRCodeWriter qrCodeWriter = new QRCodeWriter(); // throws com.google.zxing.WriterException
	            BitMatrix bitMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintType);
	            int width = bitMatrix.getWidth();
	 
	            // The BufferedImage subclass describes an Image with an accessible buffer of crunchifyImage data.
	            BufferedImage buffImage = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
	 
	            // Creates a Graphics2D, which can be used to draw into this BufferedImage.
	            buffImage.createGraphics();
	 
	            // This Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
	            // This is the fundamental class for rendering 2-dimensional shapes, text and images on the Java(tm) platform.
	            Graphics2D graphicsObj = (Graphics2D) buffImage.getGraphics();
	 
	            // setColor() sets this graphics context's current color to the specified color.
	            // All subsequent graphics operations using this graphics context use this specified color.
	            graphicsObj.setColor(Color.white);
	 
	            // fillRect() fills the specified rectangle. The left and right edges of the rectangle are at x and x + width - 1.
	            graphicsObj.fillRect(0, 0, width, width);
	 
	            // TODO: Please change this color as per your need
	            graphicsObj.setColor(Color.BLACK);
	 
	            for (int i = 0; i < width; i++) {
	                for (int j = 0; j < width; j++) {
	                    if (bitMatrix.get(i, j)) {
	                        graphicsObj.fillRect(i, j, 1, 1);
	                    }
	                }
	            }
	 
	            byte[] bytes = toByteArray(buffImage, fileType);

	            ImageIO.write(buffImage, fileType, fileObj);

	            String base64bytes = Base64.getEncoder().encodeToString(bytes);
	            System.out.println(base64bytes);
	            
	            byte[] decByte = Base64.getDecoder().decode(base64bytes);
	            BufferedImage img = ImageIO.read(new ByteArrayInputStream(decByte));
	            
	            File fileObjdec = new File("image_decode.png");
	            
	            // A class containing static convenience methods for locating
	            // ImageReaders and ImageWriters, and performing simple encoding and decoding.
	            ImageIO.write(img, fileType, fileObjdec);
	 
	            System.out.println("\nCongratulation.. You have successfully created QR Code.. \n");
	        } catch (WriterException e) {
	            System.out.println("\nSorry.. Something went wrong...\n");
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 

	}
	
	private static byte[] toByteArray(BufferedImage img, String imageFileType) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, imageFileType, baos);
            return baos.toByteArray();
        } catch (Throwable e) {
            throw new RuntimeException();
        }
    }

}
