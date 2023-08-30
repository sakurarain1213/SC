package com.example.hou.controller;


import com.example.hou.entity.CountNumber;
import com.example.hou.entity.Record;
import com.example.hou.entity.Text;
import com.example.hou.entity.UserInfo;
import com.example.hou.result.Result;
import com.example.hou.result.ResultUtil;
import com.example.hou.service.RecordService;
import com.example.hou.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hsin
 * @since 2023-04-08
 */
/*
localhost:8080/record/add   添加记录
主要只用这一个实体类的功能
{
"username":"iraina",
"txtFile":"19 28 51 test我想问一下，test是不是傻瓜？st 19 54 03 ，我想问问你？st"
}

现在改成传录音文件了


localhost:8080/record/get   查询全部记录
{
"username":"iraina",
"startTime":"2023-04-13 10:10:51",
"endTime":"2023-09-13 10:10:51"
}

{
    "username":"6",
    "startTime":"2023-04-06 23:59:59",
    "endTime":"2023-04-09 23:59:59"
}




localhost:8080/record/getCount   查询词汇统计数量
{
"username":"iraina",
"startTime":"2023-04-13 10:10:51",
"endTime":"2023-09-13 10:10:51"
}

localhost:8080/record/getText   查询词汇统计数量
{
"username":"iraina",
"startTime":"2023-04-13 10:10:51",
"endTime":"2023-09-13 10:10:51"
}



接口0  头像功能  文件上传思路：判断合法 随机命名放入服务器  得到地址放入数据库
[添加筛选 统计的实现   包括三大词汇按天统计]  调整东八区时间

接口1  每节课（前端发送一个起止40min的时间和用户  查询）  返回三大词汇次数总计OK    1.1-1.3   返回一节课三大词汇具体时间和内容

接口2   按每天返回  用户  查询）  返回三大词汇次数总计OK     2.1-2.3   返回

接口3  教学建议板块  输出多维向量
*/
@RestController
@Slf4j
@RequestMapping("/record")
public class RecordController {

    @Autowired
    RecordService recordService;//不要忘记注入

    @RequestMapping("/add")
    public Result recordAdd(@RequestBody Record record) {
        String msg = recordService.recordAddService(record);
        if (("SUCCESS").equals(msg)) {
            return ResultUtil.success("语音文本上传成功");
        } else {
            return ResultUtil.error(msg);
        }
    }


    @RequestMapping("/get")
    //因为返回的是一个list  所以消息需要根据新的格式自定义
    public Result recordGet(@RequestBody Record record) {
        List<Record> l = recordService.recordGetService(record);
        if (l!=null) {
            //相当于重新打开了ResultUtil的封装  自定义返回消息也在返回类的属性位置编辑
            Result r = new Result();
            r.setCode(200);
            r.setMsg("成功查询到语音记录数量：" + l.size());
            r.setData(l);
            return r;
            //return ResultUtil.success(l);//强大的result类可以自定义返回类型
        }
        else {
            return ResultUtil.error("缺少用户与时间查询条件或查询结果为空");
        }
    }


    @RequestMapping("/getCount")
    //因为返回的是一个list  所以消息需要根据新的格式自定义
    public Result recordGetCount(@RequestBody Record record) {
        CountNumber c = recordService.numberGetService(record);
        if (c!=null) {
            //重新打开ResultUtil的封装
            Result r = new Result();
            r.setCode(200);
            r.setMsg("成功统计词汇");
            r.setData(c);
            return r;
            //return ResultUtil.success(l);//强大的result类可以自定义返回类型
        }
        else {
            return ResultUtil.error("缺少用户与时间查询条件或查询结果为空");
        }
    }

    @RequestMapping("/getText")
    //因为返回的是一个list  所以消息需要根据新的格式自定义
    public Result recordGetText(@RequestBody Record record) {
        List<Text> t = recordService.textGetService(record);
        if (t!=null) {
            //重新打开ResultUtil的封装
            Result r = new Result();
            r.setCode(200);
            r.setMsg("成功统计涉嫌违规句子："+t.size()+"条");
            r.setData(t);
            return r;
            //return ResultUtil.success(l);//强大的result类可以自定义返回类型
        }
        else {
            return ResultUtil.error("缺少用户与时间查询条件或查询结果为空");
        }
    }



}

