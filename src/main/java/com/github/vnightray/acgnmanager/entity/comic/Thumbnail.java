package com.github.vnightray.acgnmanager.entity.comic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * thumbnail information
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Thumbnail {

    private static final long serialVersionUID = 1L;

    @TableId(value = "THUMBNAIL_ID", type = IdType.AUTO)
    private Long thumbnailId;

    private Long bookId;

    private String extension;

    private String thumbnail;

}
