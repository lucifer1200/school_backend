package com.ltfs.digital_whatsapp_service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 50063554 on 9/9/2024
 * @project Digital_whatsapp_service
 * @autor name: Ambrish Tiwari
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackExecutionTime {
}
