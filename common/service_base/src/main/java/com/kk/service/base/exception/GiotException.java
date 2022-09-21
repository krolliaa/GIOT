package com.kk.service.base.exception;

import com.kk.common.result.ResultEnum;
import lombok.Data;

@Data
public class GiotException extends RuntimeException {
    private Integer code;

    public GiotException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public GiotException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
