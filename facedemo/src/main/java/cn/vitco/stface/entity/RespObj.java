package cn.vitco.stface.entity;


/**
* @Description:    返回对象数据
* @Author:         lin.yang
* @CreateDate:     2018/11/17 20:28
* @UpdateUser:     lin.yang
* @UpdateDate:     2018/11/17 20:28
* @UpdateRemark:
* @Version:        1.0
*/
public class RespObj {
    private int code = 0000;

    private String msg = "";

    private Object data = null;

    public RespObj(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RespObj(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
