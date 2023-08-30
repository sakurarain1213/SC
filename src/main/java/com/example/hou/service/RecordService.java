package com.example.hou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hou.entity.CountNumber;
import com.example.hou.entity.Record;
import com.example.hou.entity.Text;
import com.example.hou.entity.UserInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hsin
 * @since 2023-04-08
 */
public interface RecordService /*extends IService<Record> */{
/*
录音表要实现的service主要有以下几个：
列表查询：根据一定的条件（例如时间范围、录音人等）查询录音列表。
录音上传：将录音文件上传到指定的存储设备中，并将上传信息记录到录音表中。
录音编辑：对录音文件进行编辑、剪辑、拆分等操作。 现在只有文本编辑处理
           录音删除：根据录音ID删除录音记录及相关信息。
           录音播放：根据录音ID播放录音文件。
           录音详情查询：根据录音ID查询录音文件的详细信息。
           录音转换：将录音文件转换成指定格式或采样率。
           录音下载：将录音文件下载到本地或指定存储设备。
录音转写：将录音文件转写成文字文件或文本信息。这个在前端完成

        录音备份：将录音文件备份到指定的存储设备中，确保数据的安全性和可靠性。
 */

    public String recordAddService(Record record) throws Exception;

    public List<Record> recordGetService(Record record);

    public CountNumber numberGetService(Record record);

    public List<Text> textGetService(Record record);
}
