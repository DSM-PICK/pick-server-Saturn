package com.dsm.pick.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.Formatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Configuration
class DateConfiguration {

    @Bean
    fun localDateFormatter() =
        object : Formatter<LocalDate?> {
            override fun parse(text: String, locale: Locale) =
                LocalDate.parse(text, DateTimeFormatter.ISO_DATE)

            override fun print(date: LocalDate, locale: Locale) =
                DateTimeFormatter.ISO_DATE.format(date)
        }
}