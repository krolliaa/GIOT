package com.kk.service.base.handler;

import com.kk.common.result.ResultData;
import com.kk.common.result.ResultEnum;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultData error(Exception exception) {
        exception.printStackTrace();
        return ResultData.error().message("全局异常统一处理");
    }

    @ExceptionHandler(value = {BadSqlGrammarException.class})
    @ResponseBody
    public ResultData error(BadSqlGrammarException badSqlGrammarException) {
        badSqlGrammarException.printStackTrace();
        return ResultData.error().message("SQL语法错误");
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseBody
    public ResultData error(HttpMessageNotReadableException httpMessageNotReadableException) {
        httpMessageNotReadableException.printStackTrace();
        return ResultData.setResultData(ResultEnum.JSON_PARSE_ERROR);
    }
}
