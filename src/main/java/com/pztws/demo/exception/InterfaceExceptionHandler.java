package com.pztws.demo.exception;

import com.pztws.demo.utils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;


/**
 * 自定义异常处理器
 * @author ieflex
 */
//@RestControllerAdvice 是组件注解，他使得其实现类能够被classpath扫描自动发现
// 如果应用是通过MVC命令空间或MVC Java编程方式配置，那么该特性默认是自动开启的。
@RestControllerAdvice
@Slf4j
public class InterfaceExceptionHandler {
    Result result = new Result();
    /**
     * 拦截所有运行时的全局异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result runtimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        result.setStatus(false);
        result.setData(e.getMessage());
        return result;
    }

    /**
     * 系统异常捕获处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        log.error(e.getMessage(), e);
        result.setStatus(false);
        result.setData("系统内部问题，请联系管理员");
        return result;
    }
}
