package top.zuishare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * @author niange
 * @ClassName: ControllerConfig
 * @desp:
 * @date: 2018/7/11 下午10:29
 * @since JDK 1.7
 */

@ControllerAdvice
public class ControllerConfig {

    @Autowired
    ResourceUrlProvider resourceUrlProvider;

    @ModelAttribute("urls")
    public ResourceUrlProvider urls() {
        return this.resourceUrlProvider;
    }

}
