package org.cloud.ssm.common.exception;

public interface ResultInterface {
    /**
     * 获取状态编码
     * 
     * @return 编码
     */
    Integer getCode();

    /**
     * 获取提示信息
     * 
     * @return 提示信息
     */
    String getMessage();
}
