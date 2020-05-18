package org.cloud.ssm.service;

import org.cloud.ssm.common.base.BaseService;
import org.cloud.ssm.entity.SysLog;

public interface ISysLogService extends BaseService<SysLog> {

    void clearLogs();

}
