package com.dsm.pick.configuration

import com.dsm.pick.controller.filter.LogFilter
import com.dsm.pick.controller.filter.ValidationFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfiguration {

    @Bean
    fun logFilterRegistration(): FilterRegistrationBean<LogFilter> {
        val filter = FilterRegistrationBean(LogFilter())
        filter.addUrlPatterns("/auth/*")
        filter.addUrlPatterns("/attendance/*")
        filter.addUrlPatterns("/main/*")
        return filter
    }
}