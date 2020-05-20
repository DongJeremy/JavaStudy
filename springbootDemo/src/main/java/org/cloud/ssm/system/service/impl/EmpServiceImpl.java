package org.cloud.ssm.system.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.cloud.ssm.common.base.BaseServiceImpl;
import org.cloud.ssm.system.mapper.EmpMapper;
import org.cloud.ssm.system.model.Emp;
import org.cloud.ssm.system.service.IEmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("empService")
public class EmpServiceImpl extends BaseServiceImpl<EmpMapper, Emp> implements IEmpService {

    private final Logger logger = LoggerFactory.getLogger(EmpServiceImpl.class);

    @Resource
    private EmpMapper empMapper;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public Long saveBatch(List<Emp> list) {
        SqlSession batchSqlSession = null;
        try {
            long beginTime = System.currentTimeMillis();
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            int batchCount = 500;
            for (int index = 0; index < list.size(); index++) {
                Emp emp = list.get(index);
                emp.setUserId(UUID.randomUUID().toString());
                emp.setPassword("password");
                batchSqlSession.getMapper(EmpMapper.class).insert(emp);
                if (index != 0 && index % batchCount == 0) {
                    batchSqlSession.commit();
                }
            }
            batchSqlSession.commit();
            long endTime = System.currentTimeMillis();
            logger.info("插入完成， 耗时 " + (endTime - beginTime) + " 毫秒！");
        } finally {
            if (batchSqlSession != null) {
                batchSqlSession.close();
            }
        }
        return 1L;
    }

    @Override
    public Long updateBatch(List<Emp> list) {
        SqlSession batchSqlSession = null;
        try {
            long beginTime = System.currentTimeMillis();
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            int batchCount = 500;
            for (int index = 0; index < list.size(); index++) {
                Emp workCalendar = list.get(index);
                batchSqlSession.getMapper(EmpMapper.class).updateByPrimaryKey(workCalendar);
                if (index != 0 && index % batchCount == 0) {
                    batchSqlSession.commit();
                }
            }
            batchSqlSession.commit();
            long endTime = System.currentTimeMillis();
            logger.info("更新完成， 耗时 " + (endTime - beginTime) + " 毫秒！");
        } finally {
            if (batchSqlSession != null) {
                batchSqlSession.close();
            }
        }
        return 1L;
    }

    @Override
    public boolean disableUserByID(Long id) {
        return empMapper.updateStatusByPrimaryKey(id, 0) == 1;
    }

    @Override
    public boolean enableUserByID(Long id) {
        return empMapper.updateStatusByPrimaryKey(id, 1) == 1;
    }

    @Override
    public List<Emp> getAllByCondition(String username, String deptName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return empMapper.selectAllByCondition(username, deptName);
    }

    @Override
    public Long getCountByCondition(String username, String deptName) {
        return empMapper.selectCountByCondition(username, deptName);
    }

}
