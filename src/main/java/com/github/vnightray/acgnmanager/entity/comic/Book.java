package com.github.vnightray.acgnmanager.entity.comic;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.vnightray.acgnmanager.util.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * book entity
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "BOOK", excludeProperty = {"tagsMap", "mediaType"})
public class Book extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @TableId(value = "BOOK_ID", type = IdType.AUTO)
    private Long bookId;

    private Long galleryId;

    private Long seriesId;

    private Long libraryId;

    private Integer ownerId;

    private String bookName;

    private String bookProfile;

    private String bookHash;

    private String tags;

    private HashMap<String, List<String>> tagsMap;

    private String location;

    private Double fileSize;

    private String extension;

    private SupportMediaType mediaType;

    private Integer pageCount;

    private Boolean isPrivate;

    private Boolean isDeleted = false;

    public static Book createBookByLocationAndIds(String location, Long seriesId, Long libraryId){
        Book book = new Book().setBookId(null).setSeriesId(seriesId).setLibraryId(libraryId)
                .setLocation(location).setExtension(Constants.distinguishExtension(location))
                .setMediaType(SupportMediaType.valueOf(Constants.distinguishExtension(location)));
        book.setCreateTime(LocalDateTime.now());
        book.setModifiedTime(LocalDateTime.now());
        return book;
    }

}
