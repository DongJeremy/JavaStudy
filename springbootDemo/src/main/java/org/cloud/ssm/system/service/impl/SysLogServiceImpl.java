package org.cloud.ssm.system.service.impl;

import javax.annotation.Resource;

import org.cloud.ssm.common.base.BaseServiceImpl;
import org.cloud.ssm.system.mapper.SysLogMapper;
import org.cloud.ssm.system.model.SysLog;
import org.cloud.ssm.system.service.ISysLogService;
import org.springframework.stereotype.Service;

@Service("sysLogService")
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public void clearLogs() {
        sysLogMapper.clearLogs();
    }

}
