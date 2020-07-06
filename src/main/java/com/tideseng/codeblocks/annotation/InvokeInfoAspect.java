package com.tideseng.codeblocks.annotation;

import com.tideseng.codeblocks.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
     * 定义切入点：通过@within拦截所有被该注解修饰的类
     * @param invokeInfo
     */
    @Pointcut("@within(invokeInfo)")
    public void typeService(InvokeInfo invokeInfo) {

    }

    /**
     * 定义切入点：通过@annotation拦截所有被该注解修饰的方法
     * @param invokeInfo
     */
    @Pointcut("@annotation(invokeInfo)")
    public void methodService(InvokeInfo invokeInfo) {

    }

    /**
     * 定义环绕通知方法
     * @param pjp
     * @param invokeInfo
     * @return
     * @throws Throwable
     */
    @Around("typeService(invokeInfo) || methodService(invokeInfo)")
    public Object doAround(ProceedingJoinPoint pjp, InvokeInfo invokeInfo) throws Throwable {
        long begin = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = pjp.getSignature();
        if(invokeInfo == null) invokeInfo = ((MethodSignature) signature).getMethod().getDeclaringClass().getAnnotation(InvokeInfo.class);

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
