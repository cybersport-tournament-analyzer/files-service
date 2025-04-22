package com.gpoint.files_service.util;

import com.gpoint.files_service.classes.ByteArrayMultipartFile;
import lombok.experimental.UtilityClass;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

@UtilityClass
public class PictureCompressor {

    public ByteArrayMultipartFile compressPic(InputStream inputStream, String filename, int width, int length) {
        return compress(filename, () -> {
            try {
                return getImage(inputStream, width, length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private ByteArrayMultipartFile compress(String filename, Supplier<BufferedImage> supplier) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            BufferedImage scaledImage = supplier.get();
            ImageIO.write(scaledImage, "jpg", outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = outputStream.toByteArray();

        return new ByteArrayMultipartFile(bytes, filename, "image/jpeg");
    }

    private BufferedImage getImage(InputStream inputStream, int width, int length) throws IOException {
        return Thumbnails.of(inputStream).size(width, length).asBufferedImage();
    }
}