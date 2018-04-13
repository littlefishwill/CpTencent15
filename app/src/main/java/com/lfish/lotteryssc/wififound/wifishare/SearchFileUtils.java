package com.lfish.lotteryssc.wififound.wifishare;

import android.util.Log;

import com.google.gson.Gson;
import com.lfish.lotteryssc.wififound.dao.FileWifiWebDes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import fi.iki.elonen.NanoHTTPD;

/**
 * Created by shenmegui on 2017/6/22.
 */
public class SearchFileUtils {

    private static SearchFileUtils searchFileUtils;
    public static SearchFileUtils getInstance(){
        if(searchFileUtils==null){
            searchFileUtils = new SearchFileUtils();
        }
        return searchFileUtils;
    }

    private SearchFileUtils() {
    }

    private FileWifiWebDes fileWifiWebDes;
    private List<FileWifiWebDes.FileWifiWeb> fileWifiWebs;

    /**
     * 根据类型搜索
     * @param value
     * @return
     */
    public NanoHTTPD.Response findFileWithType(String value){

        String[] types = new String[0];
        File file ;
        if(value.endsWith(WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_IMAGE)){
            types = WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_IMAGE_ARRAY;
            file = new File(value.replace(WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_IMAGE,""));
        }else if(value.endsWith(WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_VIDEO)){
            types = WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_VIDEO_ARRAY;
            file = new File(value.replace(WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_VIDEO,""));
        }else if(value.endsWith(WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_MUSIC)){
            types = WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_MUSIC_ARRAY;
            file = new File(value.replace(WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_MUSIC,""));
        }else if(value.endsWith(WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_APK)){
            types = WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_APK_ARRAY;
            file = new File(value.replace(WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_APK,""));
        }else{
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "text/html", "error: params request ");
        }

        fileWifiWebDes = new FileWifiWebDes();
        fileWifiWebDes.setPath(file.getAbsolutePath());
        fileWifiWebs = new ArrayList<>();
        findFileWithType(file,types);
        fileWifiWebDes.setWifiWebList(fileWifiWebs);
        return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json", new Gson().toJson(fileWifiWebDes));
    }

    /**
     * 字符匹配
     * @param value
     * @return
     */
    public NanoHTTPD.Response findFileWithName(String value){

        String[] params = value.split(WiFiShareServerConfig.HOST_PARAM_FILE_SEARCH_SPEC);
        if(params.length!=2){
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "text/html", "error: params request ");
        }
        File file  = new File(params[0]);

        fileWifiWebDes = new FileWifiWebDes();
        fileWifiWebDes.setPath(file.getAbsolutePath());
        fileWifiWebs = new ArrayList<>();
        findFileWithRex(file,params[1]);
        fileWifiWebDes.setWifiWebList(fileWifiWebs);
        return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json", new Gson().toJson(fileWifiWebDes));
    }

    /**
     * 递归按照类型寻找文件 后缀
     * @param dir
     * @param type
     */
    private void findFileWithType(File dir, final String[] type) //定义一个返回目录中所有文件的方法showdir
    {

        if(dir.isDirectory())
        {
            File[] files = dir.listFiles(new FilenameFilter()   //定义过滤器，过滤文件类型为.java的文件
            {
                public boolean accept(File dir,String name)
                {
                    for(String endName:type){
                        if(name.endsWith(endName)){
                            return true;
                        }
                    }
                    return false;
                }
            });
            for(int x=0;x<files.length;x++)        //打印以.java结尾的文件名
            {
                File f = files[x];
                FileWifiWebDes.FileWifiWeb fileWifiWeb = fileWifiWebDes.new FileWifiWeb();
                fileWifiWeb.setFile(f.isFile());
                fileWifiWeb.setName(f.getName());
                fileWifiWeb.setLength(f.length());
                fileWifiWeb.setPath(f.getAbsolutePath());
                fileWifiWeb.setLastModifyTime(f.lastModified());
                fileWifiWebs.add(fileWifiWeb);
                Log.i("Search",f.getAbsolutePath());
            }
            File[] files1=dir.listFiles();   //定义没有过滤器的文件列表
            for(int x=0;x<files1.length;x++)
            {
                if(files1[x].isDirectory())   //如果是目录则递归调用showdir方法,不是目录则忽略
                    findFileWithType(files1[x],type);
            }
        }
    }

    /**
     * 递归按照类型寻找文件 包含
     * @param dir 检索的文件夹及其子文件
     * @param rex 条件
     */
    private void findFileWithRex(File dir, final String rex) //定义一个返回目录中所有文件的方法showdir
    {

        if(dir.isDirectory())
        {
            File[] files = dir.listFiles(new FilenameFilter()   //定义过滤器，过滤文件类型为.java的文件
            {
                public boolean accept(File dir,String name)
                {

                    if(name.toLowerCase().contains(rex.toLowerCase())){
                        return true;
                    }
                    return false;
                }
            });
            for(int x=0;x<files.length;x++)        //打印以.java结尾的文件名
            {
                File f = files[x];
                FileWifiWebDes.FileWifiWeb fileWifiWeb = fileWifiWebDes.new FileWifiWeb();
                fileWifiWeb.setFile(f.isFile());
                fileWifiWeb.setName(f.getName());
                fileWifiWeb.setLength(f.length());
                fileWifiWeb.setPath(f.getAbsolutePath());
                fileWifiWeb.setLastModifyTime(f.lastModified());
                fileWifiWebs.add(fileWifiWeb);
                Log.i("Search",f.getAbsolutePath());
            }
            File[] files1=dir.listFiles();   //定义没有过滤器的文件列表
            for(int x=0;x<files1.length;x++)
            {
                if(files1[x].isDirectory())   //如果是目录则递归调用showdir方法,不是目录则忽略
                    findFileWithRex(files1[x],rex);
            }
        }
    }

    public NanoHTTPD.Response getFileWithParamss(String params){
        File file = new File(params);
        if(file.isFile()) {
            FileInputStream fileLocalInput = null;

            try {
                fileLocalInput = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            NanoHTTPD.Response response = WiFiShareServer.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/octet-stream", fileLocalInput, file.length());
            response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());
            return response;
        }else if(file.isDirectory()){
            FileWifiWebDes fileWifiWebDes = new FileWifiWebDes();
            fileWifiWebDes.setPath(file.getAbsolutePath());
            List<FileWifiWebDes.FileWifiWeb> fileWifiWebs = new ArrayList<>();
            String[] files = file.list();
            for(File f:file.listFiles()){
                FileWifiWebDes.FileWifiWeb fileWifiWeb = fileWifiWebDes.new FileWifiWeb();
                fileWifiWeb.setFile(f.isFile());
                fileWifiWeb.setName(f.getName());
                fileWifiWeb.setLength(f.length());
                fileWifiWeb.setPath(f.getAbsolutePath());
                fileWifiWeb.setLastModifyTime(f.lastModified());
                fileWifiWebs.add(fileWifiWeb);
            }
            fileWifiWebDes.setWifiWebList(fileWifiWebs);
            return WiFiShareServer.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/json", new Gson().toJson(fileWifiWebDes));
        }
        return null;

    }

    public NanoHTTPD.Response delectFile(String file){
        File fileDelect = new File(file);
        if(fileDelect.exists() && fileDelect.isFile()) {
            File parentFile = fileDelect.getParentFile();
            fileDelect.delete();
            return getFileWithParamss(parentFile.getAbsolutePath());
        }else{
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST, "text/html", "error: params request filepath ");
        }

    }
}
