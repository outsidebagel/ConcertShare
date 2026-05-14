package com.nick.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${gallery.photo.base-path}")
    private String photoBase;
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String base = photoBase.replace("\\", "/");
        if (!base.endsWith("/")) base += "/";
        registry.addResourceHandler("/gallery/**")
                .addResourceLocations("file:" + base + "gallery/");
    }
}
