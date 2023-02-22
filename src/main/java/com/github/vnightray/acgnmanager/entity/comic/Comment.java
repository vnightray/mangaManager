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
 * 
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "COMMENT_ID", type = IdType.AUTO)
    private Long commentId;

    private Long userId;

    private Long bookId;

    private String content;

    private LocalDateTime createTime;

    private LocalDateTime modifiedTime;


}
