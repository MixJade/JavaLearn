package config;

import controller.Interceptor.ProjectInterceptor01;
import controller.Interceptor.ProjectInterceptor02;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SpringMvcSupport extends WebMvcConfigurationSupport {
    //添加拦截器
    private ProjectInterceptor01 projectInterceptor01;
    private ProjectInterceptor02 projectInterceptor02;

    @Autowired
    public void setProjectInterceptor01(ProjectInterceptor01 projectInterceptor01) {
        this.projectInterceptor01 = projectInterceptor01;
    }

    @Autowired
    public void setProjectInterceptor02(ProjectInterceptor02 projectInterceptor02) {
        this.projectInterceptor02 = projectInterceptor02;
    }

    //设置静态资源访问过滤，当前类需要设置为配置类，并被扫描加载
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //当访问/pages/????时候，从/pages目录下查找内容
        registry.addResourceHandler("/page/**").addResourceLocations("/page/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
        registry.addResourceHandler("/element-ui/**").addResourceLocations("/element-ui/");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //拦截器的执行顺序在这里定义
        registry.addInterceptor(projectInterceptor02).addPathPatterns("/students", "/students/*");
        registry.addInterceptor(projectInterceptor01).addPathPatterns("/students", "/students/*");
    }
}
