package project.service.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.Exceptions.UserNotFoundException;
import project.dao.Chat.ChatDAO;
import project.models.Chat;
import project.models.ChatInfo;
import project.service.Message.MessageService;
import project.service.User.UserService;

import java.util.List;

@Component
public class ChatServiceImpl implements ChatService {
    ChatDAO chatDAO;
    MessageService messageService;
    UserService userService;

    private void FillChatWithMessagesAndMembers(Chat chat) {
        chat.setMessages(messageService.getAllMessages(chat.getId()));
        chat.setMembers(userService.getAllChatMembers(chat.getId()));
    }

    @Autowired
    ChatServiceImpl(ChatDAO chatDAO, MessageService messageService, UserService userService) {
        this.chatDAO = chatDAO;
        this.messageService = messageService;
        this.userService = userService;
    }

    @Override
    public List<Chat> getAllUsersChats(int user_id) {
        return chatDAO.getAllUsersChats(user_id).stream().peek(this::FillChatWithMessagesAndMembers).toList();
    }

    @Override
    public List<ChatInfo> getAllUsersChatsInfo(int user_id) {
        return getAllUsersChats(user_id).stream()
                .filter(chat -> !chat.getMessages().isEmpty())
                .map(Chat::getChatInfo)
                .toList();
    }

    @Override
    public Chat getChat(int chat_id) {
        var chat = chatDAO.getChat(chat_id);
        FillChatWithMessagesAndMembers(chat);
        return chat;
    }

    @Override
    public Chat getOrCreateStandardChat(int user1_id, int user2_id) {
        try {
            return chatDAO.getOrCreateStandardChat(userService.getUser(user1_id), userService.getUser(user2_id));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
