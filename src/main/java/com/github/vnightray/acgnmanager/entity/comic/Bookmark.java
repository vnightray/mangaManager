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
 * the bookmark of book
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Bookmark extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "BOOKMARK_ID", type = IdType.AUTO)
    private Integer bookmarkId;

    private Long bookId;

    private Integer pageId;

    private Integer ownerId;

}
