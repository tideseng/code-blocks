package com.tideseng.codeblocks.annotation;

import com.tideseng.codeblocks.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 切面类
 */
@Slf4j
@Aspect
public class InvokeInfoAspect {

    /**
     * 定义切入点：用到InvokeInfo注解的都是切入点，与包路径无关
     * @param invokeInfo
     */
    @Pointcut("@annotation(invokeInfo)")
    public void service(InvokeInfo invokeInfo) {

    }

    /**
     * 定义环绕通知方法
     * @param pjp
     * @param invokeInfo
     * @return
     * @throws Throwable
     */
    @Around("service(invokeInfo)")
    public Object doAround(ProceedingJoinPoint pjp, InvokeInfo invokeInfo) throws Throwable {
        long begin = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = pjp.getSignature();

        log.info("==================== 请求开始 ====================");
        log.info("请求链接：{}", request.getRequestURL().toString());
        log.info("请求类型：{}", request.getMethod());
        log.info("请求IP：{}", request.getRemoteAddr()); // 具体的IP获取方式根据服务器而定
        log.info("方法名称：{}.{}", signature.getDeclaringTypeName(), signature.getName());
        log.info("方法描述：{}", invokeInfo.desc().equals("") ? invokeInfo.value() : invokeInfo.desc());
        log.info("方法入参：{}", JsonUtil.serialize(pjp.getArgs()));

        Object result = null;
        try{
            result = pjp.proceed();
        } catch(Exception e){
            log.error("请求异常：{}", e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            long end = System.currentTimeMillis();

            log.info("请求耗时：{} ms", end - begin);
            log.info("请求返回：{}", JsonUtil.serialize(result));

            log.info("==================== 请求结束 ====================");
        }

        return result;
    }
}
