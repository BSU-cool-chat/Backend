package project.utils;

import project.service.Chat.ChatServiceImpl;
import project.service.Message.MessageServiceImpl;
import project.service.User.UserServiceImpl;

public class Services {
    public MessageServiceImpl messageService;
    public UserServiceImpl userService;
    public ChatServiceImpl chatService;

    public Services(MessageServiceImpl service0, UserServiceImpl service1, ChatServiceImpl service2) {
        messageService = service0;
        userService = service1;
        chatService = service2;
    }
}
