package WebSocket.HP657.HP657.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }

    @GetMapping("/chatroom/{roomId}")
    public String getChatRoomPage(@PathVariable Long roomId, Model model) {
        model.addAttribute("roomId", roomId);
        return "chatroom";
    }
}