package org.cloud.ssm.mapper;

import org.cloud.ssm.common.base.BaseMapper;
import org.cloud.ssm.entity.SysLog;

public interface SysLogMapper extends BaseMapper<SysLog> {

    void clearLogs();

}
