package org.cloud.ssm.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cloud.ssm.common.base.BaseMapper;
import org.cloud.ssm.system.model.SysLog;

@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
    void clearLogs();

}
