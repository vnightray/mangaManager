package com.github.vnightray.acgnmanager.entity.comic;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
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
@TableName(value = "BOOK", excludeProperty = {"tagsMap"})
public class Book implements Serializable {

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

    private Integer pageCount;

    private LocalDateTime createTime;

    private LocalDateTime modifiedTime;

    private Integer isPrivate;

    private Integer isDeleted;


}
