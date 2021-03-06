package com.lfish.lotteryssc.net.dao;

public class Result<T> {
    private  int showapi_res_code;
    private String showapi_res_error;
    private T showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public T getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(T showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + showapi_res_code +
                ", msg='" + showapi_res_error + '\'' +
                ", data=" + showapi_res_body +
                '}';
    }
}

