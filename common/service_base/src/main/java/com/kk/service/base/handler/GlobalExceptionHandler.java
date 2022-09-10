package com.kk.service.base.handler;

import com.kk.common.result.ResultData;
import com.kk.common.result.ResultEnum;
import com.kk.common.util.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultData error(Exception exception) {
        log.error(ExceptionUtils.getMessage(exception));
        return ResultData.error().message("全局异常统一处理");
    }

    @ExceptionHandler(value = {BadSqlGrammarException.class})
    @ResponseBody
    public ResultData error(BadSqlGrammarException badSqlGrammarException) {
        log.error(ExceptionUtils.getMessage(badSqlGrammarException));
        return ResultData.error().message("SQL语法错误");
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseBody
    public ResultData error(HttpMessageNotReadableException httpMessageNotReadableException) {
        log.error(ExceptionUtils.getMessage(httpMessageNotReadableException));
        return ResultData.setResultData(ResultEnum.JSON_PARSE_ERROR);
    }
}
