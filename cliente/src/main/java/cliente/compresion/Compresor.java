package cliente.compresion;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class Compresor {

  public final static String JPG = "jpeg";

  public static BufferedImage compress(BufferedImage image, float quality) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      write(image, quality, out);
      return ImageIO.read(new ByteArrayInputStream(out.toByteArray()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static void write(BufferedImage image, float quality, ByteArrayOutputStream out) throws IOException {
    Iterator writers = ImageIO.getImageWritersBySuffix(JPG);
    if (!writers.hasNext()) {
      throw new IllegalStateException("No writers found");
    }
    ImageWriter writer = (ImageWriter) writers.next();
    ImageOutputStream ios = ImageIO.createImageOutputStream(out);
    writer.setOutput(ios);
    ImageWriteParam param = writer.getDefaultWriteParam();
    if (quality >= 0) {
      param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
      param.setCompressionQuality(quality);
    }
    writer.write(null, new IIOImage(image, null, null), param);
  }

  public static BufferedImage read(byte[] bytes) {
    try {
      return ImageIO.read(new ByteArrayInputStream(bytes));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
