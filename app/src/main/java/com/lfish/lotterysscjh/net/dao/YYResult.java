package com.lfish.lotterysscjh.net.dao;

import java.util.ArrayList;

/**
 * Created by shenmegui on 2018/2/27.
 */
public class YYResult {
    private int ret_code;
    private ArrayList<YYRESULT_CHILD> result = new ArrayList<YYRESULT_CHILD>();


    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public ArrayList<YYRESULT_CHILD> getResult() {
        return result;
    }

    public void setResult(ArrayList<YYRESULT_CHILD> result) {
        this.result = result;
    }

    public class YYRESULT_CHILD{
        private String code;
        private String expect;
        private String openCode;
        private String time;
        private String name;
        private long timestamp;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getExpect() {
            return expect;
        }

        public void setExpect(String expect) {
            this.expect = expect;
        }

        public String getOpenCode() {
            return openCode;
        }

        public void setOpenCode(String openCode) {
            this.openCode = openCode;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
