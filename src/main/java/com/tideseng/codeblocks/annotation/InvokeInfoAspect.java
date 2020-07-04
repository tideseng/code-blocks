package com.tideseng.codeblocks.annotation;

import com.tideseng.codeblocks.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class InvokeInfoAspect {

    @Pointcut("@annotation(invokeInfo)")
    public void service(InvokeInfo invokeInfo) {

    }

    @Around("service(invokeInfo)")
    public Object doAround(JoinPoint pjp, InvokeInfo invokeInfo) throws Throwable {
        long begin = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = pjp.getSignature();

        log.info("==================== 请求开始 ====================");
        log.info("请求链接：{}", request.getRequestURL().toString());
        log.info("请求类型：{}", request.getMethod());
        log.info("请求IP：{}", request.getRemoteAddr());
        log.info("方法名称：{}.{}", signature.getDeclaringTypeName(), signature.getName());
        log.info("方法描述：{}", invokeInfo.desc().equals("") ? invokeInfo.value() : invokeInfo.desc());
        log.info("方法入参：{}", JsonUtil.serialize(pjp.getArgs()));

        Object result = ((ProceedingJoinPoint) pjp).proceed();
        long end = System.currentTimeMillis();

        log.info("请求耗时：{}", end - begin);
        log.info("请求返回：{} ms", JsonUtil.serialize(result));

        log.info("==================== 请求结束 ====================");

        return result;
    }
}
