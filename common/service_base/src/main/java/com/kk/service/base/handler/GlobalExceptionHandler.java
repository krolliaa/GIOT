package com.kk.service.base.handler;

import com.kk.common.result.ResultData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultData error(Exception exception) {
        exception.printStackTrace();
        return ResultData.error().message("全局异常统一处理");
    }
}
