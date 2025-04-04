package HiobsClient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Den 31.10.2024
 */

@Controller
public class ErrorsController {

    @GetMapping(value = {"/{errLink}", "/index/{errLink}", "/login/{errLink}"})
    public ModelAndView errors(@PathVariable("errLink") String errLink, Model model) {

        /**
         * URL auslesen...(nur anzeige in errors.html)
         * path: http://localhost:8090/log
         */
        StringBuffer path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURL();
        model.addAttribute("linkName", path);

        return new ModelAndView("errors");
    }
}
