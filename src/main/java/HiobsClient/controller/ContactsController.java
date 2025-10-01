package HiobsClient.controller;

import HiobsClient.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Den 13.02.2024
 */

@Controller
public class ContactsController {

    @Autowired
    private AuthService authService;

    @GetMapping(value = "/contacts")
    public ModelAndView contacts(HttpServletRequest request,
                                 Model model) {

        /* anmeldung prüfen */
        if (authService.authToken() == null) {
            return new ModelAndView("login/maillogin");
        }

        model.addAttribute("leftFragmentName", request.getRequestURI());
        model.addAttribute("contactsRequestUri", request.getRequestURI());

        //System.out.println("Get Mapping: " + request.getRequestURI());
        return new ModelAndView("index");
    }

}
