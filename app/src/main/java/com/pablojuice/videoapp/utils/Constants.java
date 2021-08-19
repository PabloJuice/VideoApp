package com.pablojuice.videoapp.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Constants {
    public enum MainVideoTabs{
        SCENES, MYVIDEOS
    }
    public static final Charset STANDARD_CHARSET = StandardCharsets.UTF_8;
    public static final String ITEM_KEY = "videoItem";
    public static final String DATABASE_NAME = "video-items";
}
