package com.dsm.pick.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabaseConfiguration(
    @Value("\${DATABASE_DRIVER:com.mysql.cj.jdbc.Driver}")
    val driverClassName: String,
    @Value("\${DATABASE_URL:jdbc:mysql://localhost:3306/pick?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC}")
    val url: String,
    @Value("\${DATABASE_USERNAME:root}")
    val username: String,
    @Value("\${DATABASE_PASSWORD:1111}")
    val password: String,
) {

    @Bean
    fun dataSource(): DataSource =
        DataSourceBuilder.create()
            .driverClassName(driverClassName)
            .url(url)
            .username(username)
            .password(password)
            .build()
}