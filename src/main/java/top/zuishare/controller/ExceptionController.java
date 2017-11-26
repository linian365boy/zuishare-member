package top.zuishare.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author niange
 * @ClassName: ExceptionController
 * @desp:
 * @date: 2017/11/25 下午4:24
 * @since JDK 1.7
 */
@Controller
public class ExceptionController implements ErrorController {

    private static final String ERROR_PATH = "/errors";

    @RequestMapping("/404")
    public String error404(){
        return "errors/404";
    }

    @RequestMapping("/500")
    public String error500(){
        return "errors/500";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
