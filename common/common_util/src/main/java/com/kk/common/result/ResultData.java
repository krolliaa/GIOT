package com.kk.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@ApiModel(value = "全局统一返回的结果格式")
public class ResultData {
    @ApiModelProperty(value = "请求处理是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回状态码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private HashMap<String, Object> data = new HashMap<>();

    /**
     * 请求处理成功返回的结果
     *
     * @return
     */
    public static ResultData ok() {
        ResultData resultData = new ResultData();
        resultData.setSuccess(ResultEnum.SUCCESS.getSuccess());
        resultData.setCode(ResultEnum.SUCCESS.getCode());
        resultData.setMessage(ResultEnum.SUCCESS.getMessage());
        return resultData;
    }

    /**
     * 请求处理失败返回的结果
     *
     * @return
     */
    public static ResultData error() {
        ResultData resultData = new ResultData();
        resultData.setSuccess(ResultEnum.UNKNOWN_REASON.getSuccess());
        resultData.setCode(ResultEnum.UNKNOWN_REASON.getCode());
        resultData.setMessage(ResultEnum.UNKNOWN_REASON.getMessage());
        return resultData;
    }

    /**
     * 创建方法：直接传递键值对即可给 data 赋值
     *
     * @param key
     * @param value
     * @return
     */
    public ResultData data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 修改 SetData
     *
     * @param data
     * @return
     */
    public ResultData data(HashMap<String, Object> data) {
        this.setData(data);
        return this;
    }

    /**
     * 修改 SetSuccess
     *
     * @param success
     * @return
     */
    public ResultData success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    /**
     * 修改 SetCode
     *
     * @param code
     * @return
     */
    public ResultData code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 修改 SetMessage
     *
     * @param message
     * @return
     */
    public ResultData message(String message) {
        this.setMessage(message);
        return this;
    }
}
