package org.cloud.ssm.common.aop;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.cloud.ssm.common.annotation.OperationLog;
import org.cloud.ssm.common.utils.IPUtils;
import org.cloud.ssm.entity.SysLog;
import org.cloud.ssm.entity.User;
import org.cloud.ssm.mapper.SysLogMapper;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OperationLogAspect {

    @Resource
    private SysLogMapper sysLogMapper;

    /**
     * 定义切入点，切入点为org.cloud.common.annotation.OperationLog下的所有函数
     */
    @Pointcut("@annotation(org.cloud.ssm.common.annotation.OperationLog)")
    public void pointcut() {
    }

    /**
     * 切面执行了
     * 
     * @param point
     * @return
     * @throws Throwable
     */
    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        Object result;
        long beginTime = System.currentTimeMillis();
        // 执行方法
        result = thisJoinPoint.proceed();
        // 执行时长
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveLog(thisJoinPoint, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();

        // 获取注解上的操作描述
        OperationLog operationLogAnnotation = method.getAnnotation(OperationLog.class);
        if (operationLogAnnotation != null) {
            sysLog.setOperation(operationLogAnnotation.value());
        }

        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        // 请求的方法参数
        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = parameterNameDiscoverer.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                params.append("  ").append(paramNames[i]).append(": ").append(args[i]);
            }
            sysLog.setParams(params.toString());
        }

        sysLog.setIp(IPUtils.getIpAddr());

        // 获取当前登录用户名
        if (SecurityUtils.getSubject().isAuthenticated()) {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            sysLog.setUsername(user.getUsername());
        }

        sysLog.setTime((int) time);
        sysLogMapper.insert(sysLog);
    }
}
