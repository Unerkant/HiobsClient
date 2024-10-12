package HiobsClient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Den 30.09.2024
 */

@Controller
public class HomeController {

    @GetMapping(value = {"/","/home","/home{path}" })
    public String getHome(@RequestParam(required = false) String path){

        //System.out.println("Home, path: " + path);
        return "home";
    }
}
