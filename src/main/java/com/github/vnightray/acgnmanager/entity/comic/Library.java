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
 * the root source of books and series
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Library extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "LIBRARY_ID", type = IdType.AUTO)
    private Long libraryId;

    private String sourceLocation;

    private Boolean isPrivate;

    private Boolean isDeleted = false;

}
