package com.lfish.lotterysscjh.wififound.wifishare;

/**
 * Created by shenmegui on 2017/6/5.
 */
public class WiFiShareServerConfig {
    /**
     * 获取 文件列表
     */
    public static final String HOST_PARAM_FILE_LIST = "filelist";

    /**
     * 获取 指定文件
     */
    public static final String HOST_PARAM_FILE_FILEPATH = "filepath";

    /**
     * 获取 - 搜索指定类型文件
     */
    public static final String HOST_PARAM_FILE_FILETYPE = "filetype";

    /**
     * 获取 - 搜索指定文字的文件
     */
    public static final String HOST_PARAM_FILE_FILENAME = "filename";

    /**
     * 删除 - 指定路径的文件
     */
    public static final String HOST_PARAM_FILE_DELECT = "delectfile";

    /**
     * 获取 - 搜索指定文字的文件
     */
    public static final String HOST_PARAM_FILE_SEARCH_SPEC = "searchpec";

    public static final String[] HOST_PARAM_FILE_FILETYPE_IMAGE_ARRAY = new String[]{".png",".jpg",".jpeg",".gif"};
    public static final String[] HOST_PARAM_FILE_FILETYPE_VIDEO_ARRAY = new String[]{".mp4",".avi",".rmvb",".rm",".flash",".mid",".3gp",".flv",".m4v"};
    public static final String[] HOST_PARAM_FILE_FILETYPE_MUSIC_ARRAY = new String[]{".mp3",".cd",".wma",".rm",".real",".wav"};
    public static final String[] HOST_PARAM_FILE_FILETYPE_APK_ARRAY = new String[]{".apk"};

                /**
                 * 获取 - 搜索指定类型文件 iamge
                 */
                public static final String HOST_PARAM_FILE_FILETYPE_IMAGE = "image";

                /**
                 * 获取 - 搜索指定类型文件 video
                 */
                public static final String HOST_PARAM_FILE_FILETYPE_VIDEO = "video";
                /**
                 * 获取 - 搜索指定类型文件 music
                 */
                public static final String HOST_PARAM_FILE_FILETYPE_MUSIC = "music";
                /**
                 * 获取 - 搜索指定类型文件 apk
                 */
                public static final String HOST_PARAM_FILE_FILETYPE_APK= "apk";



}
