package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by LGM on 08/19/2020
 */
@Controller
public class IndexController {

    @RequestMapping({"","/","/index"})
    public String getIndexPage(){
        return "index";
    }
}
