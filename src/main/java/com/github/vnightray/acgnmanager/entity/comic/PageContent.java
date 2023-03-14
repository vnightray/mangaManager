package com.github.vnightray.acgnmanager.entity.comic;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageContent {
    private int height;
    private int width;
    private String extension;
    private List<Byte> content;
}
