package com.stayscape.backend.logging

import com.stayscape.backend.domain.util.SecurityUtils
import org.apache.commons.lang3.StringUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Aspect
@Component
class LoggedMethodPointcut(
    private val securityUtils: SecurityUtils
) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)
    private val mask = "*****"

    @Around("@annotation(com.stayscape.backend.logging.LoggedMethod)")
    @Throws(Throwable::class)
    fun around(jp: ProceedingJoinPoint): Any? {
        val start = System.currentTimeMillis()
        return try {
            val userId = securityUtils.getCurrentUserId()

            MDC.put("cid", UUID.randomUUID().toString())
            MDC.put("userId", userId.getOrNull().toString())
            jp.proceed()
        } finally {
            if (log.isInfoEnabled) {
                val duration = System.currentTimeMillis() - start
                logSignature(jp, duration)
            }
            MDC.clear()
        }
    }

    private fun logSignature(jp: ProceedingJoinPoint, duration: Long) {
        val signature = jp.signature
        val className = signature.declaringType.name
        val methodName = signature.name
        val args = jp.args

        val methodSignature = jp.staticPart.signature as MethodSignature
        val method = methodSignature.method
        val parameterTypes = method.parameterTypes
        val parameters = method.parameters
        val logParams: MutableList<String?> = ArrayList(args.size)
        val loggedMethodAnnotation = method.getDeclaredAnnotation(LoggedMethod::class.java)
        val maskSet = loggedMethodAnnotation.mask.toSet()
        if (args.size == parameterTypes.size && parameterTypes.size == parameters.size) {
            for (i in args.indices) {
                val paramType = parameterTypes[i]?.simpleName
                val paramName = parameters[i]?.name
                val paramValue = if (maskSet.contains(paramName)) {
                    mask
                } else {
                    args[i]?.toString()
                }
                logParams.add("$paramType:$paramName=$paramValue")
            }
        }
        val logParamsJoined = StringUtils.join(logParams, ",")
        log.info("{}::{}({}) executed in {}ms", className, methodName, logParamsJoined, duration)
    }

}
