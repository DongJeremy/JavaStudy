package org.cloud.ssm.system.mapper;

import org.cloud.ssm.common.base.BaseMapper;
import org.cloud.ssm.system.model.SysLog;

public interface SysLogMapper extends BaseMapper<SysLog> {

    void clearLogs();

}
