package com.github.vnightray.acgnmanager.entity.comic;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * series information
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Series extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "SERIES_ID", type = IdType.AUTO)
    private Long seriesId;

    private String seriesName;

    private Long libraryId;

    private String seriesLocation;

    private String tags;

    private Long thumbnailId;

    private Boolean isPrivate;

    private Boolean isDeleted = false;

}
