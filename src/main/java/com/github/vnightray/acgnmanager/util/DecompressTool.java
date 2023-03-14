package com.github.vnightray.acgnmanager.util;

import cn.hutool.core.io.FileUtil;
import com.github.vnightray.acgnmanager.entity.comic.PageContent;
import com.google.common.primitives.Bytes;
import net.greypanther.natsort.CaseInsensitiveSimpleNaturalComparator;
import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class DecompressTool {

    /**
     * 获取压缩文件内文件列表
     * @param compressedFilePath 文件路径
     * @return List
     * @throws Exception 异常
     */
    public static List<String> getCompressedFileContentNameList(String compressedFilePath, String password) throws Exception {
        IInArchive archive;
        RandomAccessFile randomAccessFile = new RandomAccessFile(compressedFilePath, "r");
        archive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile), password);
        ISimpleInArchive simpleInArchive = archive.getSimpleInterface();
        List<String> contentNames = new ArrayList<>();
        Arrays.stream(simpleInArchive.getArchiveItems()).filter(item -> {
            try {
                return !item.isFolder();
            } catch (SevenZipException e) {
                throw new RuntimeException(e);
            }
        }).forEach(item -> {
            try {
                contentNames.add(item.getPath());
            } catch (SevenZipException e) {
                throw new RuntimeException(e);
            }
        });
        if (!contentNames.isEmpty()) {
            contentNames.sort(CaseInsensitiveSimpleNaturalComparator.getInstance());
        }
        return contentNames;
    }

    /**
     * 解压加密文件指定内容
     * @param compressedFilePath 文件路径
     * @param contentName 压缩文件内文件名
     * @param password 文件密码.没有可随便写,或空
     * @return PageContent
     * @throws Exception 异常
     */
    public static PageContent getEncryptionFileContentByName(String compressedFilePath, String contentName,  String password) throws Exception {
        IInArchive archive;
        RandomAccessFile randomAccessFile = new RandomAccessFile(compressedFilePath, "r");
        archive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile), password);
        ISimpleInArchive simpleInArchive = archive.getSimpleInterface();
        PageContent pageContent = new PageContent();
        try {
            Arrays.stream(simpleInArchive.getArchiveItems()).forEach(item -> {
                try {
                    if (!item.isFolder()) {
                        if (item.getPath().equals(contentName)){
                            ExtractOperationResult result;
                            result = item.extractSlow( data -> {
                                // decompress the compressed file
                                try {
                                    List<Byte> bytes = Bytes.asList(data);
                                    pageContent.setContent(bytes);
                                    pageContent.setExtension(FileUtil.extName(contentName));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return data.length; // Return amount of consumed
                            }, password);
                            if (result.equals(ExtractOperationResult.WRONG_PASSWORD)) {
                                throw new Exception("Wrong password");
                            }
                            if (!result.equals(ExtractOperationResult.OK)) {
                                throw new Exception("something went wrong: " + result);
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } finally {
            archive.close();
            randomAccessFile.close();
        }
        return pageContent;
    }

    /**
     * 解压加密文件
     * @param compressedFilePath 文件路径
     * @param password 文件密码.没有可随便写,或空
     * @return HashMap
     * @throws Exception 异常
     */
    public static HashMap<String, PageContent> decompressEncryptionFile(String compressedFilePath, String password) throws Exception {
        IInArchive archive;
        RandomAccessFile randomAccessFile = new RandomAccessFile(compressedFilePath, "r");
        archive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile), password);
        ISimpleInArchive simpleInArchive = archive.getSimpleInterface();
        HashMap<String, PageContent> fileContent = new HashMap<>();
        try {
            Arrays.stream(simpleInArchive.getArchiveItems()).forEach(item -> {
                try {
                    if (!item.isFolder()) {
                        ExtractOperationResult result;
                        result = item.extractSlow( data -> {
                            // decompress the compressed file
                            try {
                                String itemPath = item.getPath();
                                if (!itemPath.contains(File.separator) || (itemPath.lastIndexOf(File.separator) > 0
                                        && itemPath.lastIndexOf(File.separator) < itemPath.length() - 1)) {
                                    List<Byte> content = Bytes.asList(data);
                                    fileContent.putIfAbsent(
                                            itemPath.substring(itemPath.lastIndexOf(File.separator) + 1),
                                            new PageContent().setContent(content));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return data.length; // Return amount of consumed
                        }, password);
                        if (result.equals(ExtractOperationResult.WRONG_PASSWORD)) {
                            throw new Exception("Wrong password");
                        }
                        if (!result.equals(ExtractOperationResult.OK)) {
                            throw new Exception("something went wrong: " + result);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } finally {
            archive.close();
            randomAccessFile.close();
        }
        return fileContent;
    }
}
