package com.henry.springevent.config

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig : ApplicationContextAware {
    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

    companion object {
        @JvmStatic
        private lateinit var context: ApplicationContext

        fun <T> getBean(beanClass: Class<T>): T {
            return context.getBean(beanClass)
        }
    }
}