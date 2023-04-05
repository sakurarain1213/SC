package com.example.hou.result;

/**
 * @program: testhou
 * @description:
 * @author: 作者
 * @create: 2023-04-04 22:49
 */
public class ResultUtil {
    /**
     * 成功且带数据
     **/
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    /**
     * 成功但不带数据
     **/
    public static Result success() {

        return success(null);
    }

    /**
     * 失败
     **/
    public static Result error(Object object) {
        Result result = new Result();
        result.setCode(ResultCode.ERROR.getCode());
        result.setMsg(ResultCode.ERROR.getMsg());
        result.setData(object);
        return result;
    }
}
