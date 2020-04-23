package com.example.apoyoulatina.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * MainCommand
 */
@Component
public class UlatinaBot extends TelegramLongPollingBot {
    
    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    @Autowired
    private FilterCommand filterCommand;
    
    @Override
    public void onUpdateReceived(Update update) {
        try {
            filterCommand.filter(update);
        } catch (TelegramApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        // TODO Auto-generated method stub
        return username;
    }

    @Override
    public String getBotToken() {
        // TODO Auto-generated method stub
        return token;
    }

    public void send (SendMessage sendMessage) { 
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}