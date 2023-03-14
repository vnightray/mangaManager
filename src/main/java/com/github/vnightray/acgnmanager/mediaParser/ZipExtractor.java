package com.github.vnightray.acgnmanager.mediaParser;

import cn.hutool.core.io.FileUtil;
import com.github.vnightray.acgnmanager.entity.comic.PageContent;
import com.github.vnightray.acgnmanager.util.DecompressTool;
import net.greypanther.natsort.CaseInsensitiveSimpleNaturalComparator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipExtractor {

    private static void testImageIORatio(){
        String pictDir = "D:\\BaiduNetdiskDownload\\pics\\元气壁纸";
        File pictPath = new File(pictDir);
        Path destPath = new File("D:\\BaiduNetdiskDownload\\pics\\wallpaper").toPath();
        try {
            if (pictPath.isDirectory()){
                File[] files = pictPath.listFiles();
                List<File> todelete = new ArrayList<>();
                for (int i = 0; i < files.length; i++) {
                    ImageInputStream imageInputStream = ImageIO.createImageInputStream(files[i]);
                    Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
                    if (readers.hasNext()){
                        ImageReader reader = readers.next();
                        reader.setInput(imageInputStream);
                        if (reader.getAspectRatio(0) > 1.0f) {
                            todelete.add(files[i]);
//                            Files.copy(files[i].toPath(), destPath.resolve(files[i].getName()), StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                    imageInputStream.close();
                }
                for (File file : todelete) {
                    if (file.exists() && file.isFile()){
                        try {
                            file.deleteOnExit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testEncryptedCompression() {
        String zipFilePath = "D:\\cache\\delete\\[Leonat] 家の彼女ユニちゃん先輩 (プリンセスコネクト!ReADive) [無修正] [中国翻訳].zip";
        try {
            long start = System.currentTimeMillis();
//            HashMap<String, PageContent> fileContent = DecompressTool.decompress7zEncryptionFile(zipFilePath, "123abc...!@#");
            HashMap<String, PageContent> fileContent = DecompressTool.decompressEncryptionFile(zipFilePath, "");
            System.out.println("consume time: " + (System.currentTimeMillis() - start));
            List<String> pageNameList = new ArrayList<>(fileContent.keySet());
            pageNameList.sort(CaseInsensitiveSimpleNaturalComparator.getInstance());
            System.out.println("length: " + fileContent.keySet().size());
            pageNameList.forEach(item -> {
                System.out.println(item + " ---- " + FileUtil.extName(item));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        testEncryptedCompression();
    }

}
