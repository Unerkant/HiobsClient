package HiobsClient.controller.other;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Den 19.03.2025
 */

@Controller
public class BlockedUsersController {

    @PostMapping(path = "/blockedUsers")
    public String blockedUser(HttpServletRequest request, Model model) {

        /* Senden an index.html */
        model.addAttribute("otherFragmentName", "blockedUsers");
        /* senden an blockedUsers.html, als Test */
        model.addAttribute("blockedUrl", request.getRequestURI());

        System.out.println("Blocked User: " + request.getRequestURI());
        return "index :: #OTHERINSERT";
    }
}
