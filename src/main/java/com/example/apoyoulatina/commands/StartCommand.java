package com.example.apoyoulatina.commands;

import java.util.ArrayList;
import java.util.List;

import com.example.apoyoulatina.models.dao.UserDAO;
import com.example.apoyoulatina.models.dao.UserTelegramDAO;
import com.example.apoyoulatina.models.entities.User;
import com.example.apoyoulatina.models.entities.UserTelegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
/**
 * StartCommand
 */
@Component
public class StartCommand {

    @Autowired
    private UserTelegramDAO userTelegramDAO;

    @Autowired
    private UserDAO userDAO;

    public SendMessage start(Long chatId, String clientName) {

        String responseString = "Hola "+
            clientName+
            ", bienvenido al bot de apoyo de la Universidad Latina\n"+
            "En que puedo ayudarle\n\n"+
            "Seleccione /inicio para iniciar"+
            " o seleccione /ayuda para enviarle la guia de ayuda"+
            "[.](https://www.ulatina.ac.cr/hubfs/website/logoOscuro.png)";
        
        UserTelegram userTelegram = userTelegramDAO.findByIdTelegram(chatId);

        if (userTelegram == null) {
            userTelegramDAO.save(new UserTelegram(0, chatId, 0));
        }

        SendMessage response = new SendMessage()
            .setChatId(chatId)
            .setText(responseString);

        return response.setParseMode(ParseMode.MARKDOWN);
    }

    public SendMessage inicio(Long chatId) {
        
        User user = userDAO.findByUserTelegramIdTelegram(chatId);

        SendMessage response = new SendMessage()
            .setChatId(chatId);

        if (user != null) {
            String responseText = "Hola "+user.getName() + " " +user.getLastName() + " en que puedo ayudarle\n"
                + " puede usar estos atajos para ayudarte más fácilmente\n\n"
                + " /matricular\n"
                + " /cursos\n"
                + " /otra_consulta\n"
                + " /cerrar_sesion";
            
            response.setText(responseText);
        }else {

            response.setText("¿Eres estudiante regular?");
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            List<InlineKeyboardButton> firstRow = new ArrayList<>();

            firstRow.add(new InlineKeyboardButton().setText("✅Si soy").setCallbackData("regular"));
            firstRow.add(new InlineKeyboardButton().setText("😌Todavía no").setCallbackData("no regular"));
            
            keyboard.add(firstRow);

            /* List<InlineKeyboardButton> secondRow = new ArrayList<>();
            secondRow.add(new InlineKeyboardButton().setText("🕵🏽‍♂️Continuar como anonimo").setCallbackData("anonimo"));

            keyboard.add(secondRow); */

            InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
            markupKeyboard.setKeyboard(keyboard);

            
            response.setReplyMarkup(markupKeyboard);
        }
        
        return response;
    }
    
}