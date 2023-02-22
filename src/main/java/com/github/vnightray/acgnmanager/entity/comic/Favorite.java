package com.github.vnightray.acgnmanager.entity.comic;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class Favorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "FAVORITE_ID", type = IdType.INPUT)
    private Integer favoriteId;

    private Long bookId;

    private Integer userId;


}
