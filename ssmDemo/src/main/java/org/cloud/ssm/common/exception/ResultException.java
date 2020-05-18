package org.cloud.ssm.common.exception;

import org.cloud.ssm.common.enums.ResultEnum;

public class ResultException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ResultException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ResultException(ResultInterface resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ResultException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
