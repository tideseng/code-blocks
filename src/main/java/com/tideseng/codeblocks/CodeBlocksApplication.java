package com.tideseng.codeblocks;

import com.tideseng.codeblocks.annotation.InvokeInfoAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * 这里使用@Import注解导入切面类，是为了强调在实际开发项目中，切面类通常在common基础包下，其它项目引用时很可能扫描不到
 * 除了使用@Import注解导入外，还可以使用包扫描注解（此时切面类得加上@Component注解）
 */
@Import({InvokeInfoAspect.class})
@SpringBootApplication
public class CodeBlocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeBlocksApplication.class, args);
	}

}
