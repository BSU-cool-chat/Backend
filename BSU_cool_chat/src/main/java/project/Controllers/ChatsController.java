package project.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.service.Chat.ChatService;
import project.service.Message.MessageService;
import project.service.User.UserService;
import project.models.Chat;
import project.models.Message;

@Controller
public class ChatsController {
    private final ChatService chatService;
    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public ChatsController(ChatService chatService, UserService userService, MessageService messageService) {
        this.chatService = chatService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/chats/{user_id}")
    public String displayAllUsersChats(@PathVariable("user_id") int user_id, Model model) {
        model.addAttribute("chats_info", chatService.getAllUsersChatsInfo(user_id));
        model.addAttribute("id", user_id);
        return "users/chats";
    }

    @GetMapping("/chats/{user_id}/chat/{chat_id}")
    public String displayUsersChat(@PathVariable("chat_id") int chat_id,
                                   @PathVariable("user_id") int user_id,
                                   Model model) {
        model.addAttribute("chat", chatService.getChat(chat_id));
        model.addAttribute("user_id", user_id);
        model.addAttribute("chat_id", chat_id);
        model.addAttribute("message", new Message());
        return "users/chat";
    }

    @PostMapping("/chats/{user_id}/new_standard_chat/{another_user_id}")
    public String createUsersChat(@PathVariable("user_id") int user_id,
                                   @PathVariable("another_user_id") int another_user_id,
                                   Model model) {
        Chat chat = chatService.getOrCreateStandardChat(user_id, another_user_id);
        return "redirect:/chats/" + user_id + "/chat/" + chat.getId();
    }

    @PostMapping("/chats/{user_id}/chat/{chat_id}/new_message")
    public String sendMessage(@PathVariable("user_id") int user_id,
                              @PathVariable("chat_id") int chat_id,
                              @ModelAttribute("message") Message message) {
        System.out.println("New message from user " + user_id + " in chat " + chat_id + "\n Message: " + message.getText());
        message.setSender(userService.getUser(user_id));
        message.setChatId(chat_id);
        messageService.createMessage(message);
        return "redirect:/chats/" + user_id + "/chat/" + chat_id;
    }
}
