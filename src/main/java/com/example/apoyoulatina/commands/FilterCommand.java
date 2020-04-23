package com.example.apoyoulatina.commands;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * FilterCommand
 */
@Component
public class FilterCommand {

    @Autowired
    private UlatinaBot ulatinaBot;

    @Autowired
    private StartCommand startCommand;

    @Autowired
    private StudentFilter studentFilter;
    
    @Autowired
    private VerifiedQuestion verifiedQuestion;

    public void filter (Update update) throws TelegramApiException {
     
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
        
            String clientName = message.getChat().getFirstName();
            Long chatId = message.getChatId();
            String messageText = message.getText().toLowerCase();

            int messageId = message.getMessageId();
            
            if (verifiedQuestion.active(chatId) != null) {
                
                System.out.println("Tiene preguntas sin contestar");
                verifiedQuestion.response(chatId, messageText);
            }else {
                if (messageText.equals("/start")) {
                    
                    
                    ulatinaBot.execute(new DeleteMessage().setChatId(chatId).setMessageId(messageId));
                    
                    
                    SendMessage sendMessage = startCommand.start(chatId, clientName);
                    ulatinaBot.send(sendMessage);
                    
                }else if (messageText.equals("/inicio")) {
                    try {
                        ulatinaBot.execute(new DeleteMessage().setChatId(chatId).setMessageId(messageId));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
    
                    SendMessage sendMessage = startCommand.inicio(chatId);
                    ulatinaBot.send(sendMessage);
                }else if (messageText.equals("/matricular")) {
                    studentFilter.matricular(chatId);

                }else if (messageText.equals("/otra_consulta")){
                    SendMessage sendMessage = new SendMessage()
                        .setChatId(chatId)
                        .setText("En un momento, te contactaremos para ayudarte. gracias");

                    SendMessage sendMessage2 = new SendMessage()
                        .setChatId("-408102917")
                        .setText("El usuario @"+update.getMessage().getFrom().getUserName()+" necesita ayuda");
                    ulatinaBot.execute(sendMessage);
                    ulatinaBot.execute(sendMessage2);
                }else if (messageText.equals("/cursos") || messageText.equals("mis cursos")) {
                    studentFilter.courses(chatId);
                }else if(messageText.equals("/cerrar_sesion")) {
                    //TODO: Falta cerrar sesión
                }else if(messageText.equals("/encuesta")) {
                    List<String> options = new ArrayList<>();
                    options.add("Excelente");
                    options.add("Bueno");
                    
                    SendPoll sendPoll = new SendPoll(chatId, "¿Que te parecio el bot?", options)
                        .setType("quiz")
                        .setCorrectOptionId(0);
                    ulatinaBot.execute(sendPoll);
                }else {
                    SendMessage sendMessage = new SendMessage()
                        .setChatId(chatId)
                        .setText("Lo siento, no entiendí, click en alguno de los 2 enlaces, para ayudarte\n\n"+
                            "/inicio : (matriculas, inicio sesión, etc.)\n"+ 
                            "/otra_consulta : si desea una consulta detallada click aquí");

                    ulatinaBot.execute(sendMessage);
                }
            }
        }else if(update.hasCallbackQuery()){

            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            int messageId = update.getCallbackQuery().getMessage().getMessageId();
            String data = update.getCallbackQuery().getData();
            String callBackId = update.getCallbackQuery().getId();
            if (data.equals("regular")) { 
                studentFilter.regular(chatId, messageId, callBackId);
               
            }else if (data.equals("no regular")) {
                studentFilter.notRegular(chatId, messageId, callBackId);
            }else if (isNumeric(data)){
                int courseId = Integer.parseInt(data);
                studentFilter.findCourseAndMatricular(chatId, courseId);

            }
        }
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}