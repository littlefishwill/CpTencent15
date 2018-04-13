package com.lfish.lotteryssc.wififound.wifishare;

import android.os.Environment;
import android.util.Log;

import java.util.Iterator;
import java.util.Map;
import fi.iki.elonen.NanoHTTPD;

/**
 * Created by shenmegui on 2017/5/10.
 */
public class WiFiShareServer extends NanoHTTPD {
    private static  String Tag = "WiFiShareServer";
    public WiFiShareServer(int port) {
        super(port);
    }

    public WiFiShareServer(String hostname, int port) {
        super(hostname, port);
    }


    @Override
    public Response serve(IHTTPSession session) {
        Map<String, String> parms = session.getParms();

        Iterator<Map.Entry<String, String>> iterator = parms.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            Log.i(Tag,next.getKey());
            switch (next.getKey()){
                case WiFiShareServerConfig.HOST_PARAM_FILE_LIST:
                    return getFileListResponse();
                case WiFiShareServerConfig.HOST_PARAM_FILE_FILEPATH:
                    return SearchFileUtils.getInstance().getFileWithParamss(next.getValue());
                case WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE:
                    return SearchFileUtils.getInstance().findFileWithType(next.getValue());
                case WiFiShareServerConfig.HOST_PARAM_FILE_FILENAME:
                    return SearchFileUtils.getInstance().findFileWithName(next.getValue());
                case WiFiShareServerConfig.HOST_PARAM_FILE_DELECT:
                    return SearchFileUtils.getInstance().delectFile(next.getValue());
            }
        }
        return newFixedLengthResponse(Response.Status.BAD_REQUEST, "text/html", "error: params request ");
    }

    public Response getFileListResponse(){
        return SearchFileUtils.getInstance().getFileWithParamss(Environment.getExternalStorageDirectory().getAbsolutePath());
    }






}
