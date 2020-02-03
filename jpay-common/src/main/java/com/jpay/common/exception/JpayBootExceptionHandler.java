package com.jpay.common.exception;

import com.jpay.common.pojo.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 异常处理器
 * 
 * @Author scott
 * @Date 2019
 */
@RestControllerAdvice
@Slf4j
public class JpayBootExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(JpayBootException.class)
	public Result<?> handleRRException(JpayBootException e){
		System.out.println("handleRRException JpayBootException");//debug
		log.error(e.getMessage(), e);
		return Result.error(e.getMessage());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public Result<?> handleDuplicateKeyException(DuplicateKeyException e){
		log.error(e.getMessage(), e);
		return Result.error("数据库中已存在该记录");
	}

	@ExceptionHandler(Exception.class)
	public Result<?> handleException(Exception e){
		log.error(e.getMessage(), e);
		return Result.error("操作失败，"+e.getMessage());
	}

    @ExceptionHandler(PoolException.class)
    public Result<?> handlePoolException(PoolException e) {
    	log.error(e.getMessage(), e);
        return Result.error("Redis 连接异常!");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
	public Result<?> handleDatabaseException(DataIntegrityViolationException e) {
		log.error(e.getMessage(), e);
		return Result.error("数据库操作异常!");
	}

}
