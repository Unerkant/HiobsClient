package HiobsClient.controller.other;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Den 10.03.2025
 */

@Controller
public class UserInfoController {

    @PostMapping(path = "/userInfo")
    public String userInfo(HttpServletRequest request, Model model) {

        System.out.println("User Info: " + request.getRequestURI());
        model.addAttribute("otherFragmentName", "userInfo");
        model.addAttribute("username", request.getParameter("param"));
        model.addAttribute("request", request.getRequestURI());
        System.out.println("Request: " + request.getParameter("param"));

        return "index :: #OTHERINSERT";
    }
}
