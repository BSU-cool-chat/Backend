package project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.dao.Chat.ChatService;

import java.util.Date;

@Controller
public class ChatsController {
    private ChatService chatService;

    @Autowired
    public ChatsController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chats/{receiver_id}")
    public String displayAllUsersMessages(@PathVariable("receiver_id") int user_id, Model model) {
        model.addAttribute("chats", chatService.getAllUsersChatsInfo(user_id));
        model.addAttribute("id", user_id);
        return "users/chats2";
    }
}
