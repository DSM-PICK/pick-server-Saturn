package com.dsm.pick.configuration

import com.dsm.pick.domain.converter.*
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ConverterConfiguration : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(StateConverter())
        registry.addConverter(ScheduleConverter())
        registry.addConverter(FloorConverter())
        registry.addConverter(PeriodConverter())
        registry.addConverter(GradeConverter())
    }
}