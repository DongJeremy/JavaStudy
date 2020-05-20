package org.cloud.ssm.system.service;

import org.cloud.ssm.common.base.BaseService;
import org.cloud.ssm.system.model.SysLog;

public interface ISysLogService extends BaseService<SysLog> {

    public void clearLogs();

}
