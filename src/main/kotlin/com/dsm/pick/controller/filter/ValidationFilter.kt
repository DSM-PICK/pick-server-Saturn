package com.dsm.pick.controller.filter

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class ValidationFilter : Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpServletRequest = request as HttpServletRequest

        val authorization = httpServletRequest.getHeader("Authorization")
        println("authorization: $authorization")
        chain?.doFilter(request, response)
    }
}