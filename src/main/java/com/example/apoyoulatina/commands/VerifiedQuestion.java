package com.example.apoyoulatina.commands;

import com.example.apoyoulatina.models.dao.QuestionByUserDAO;
import com.example.apoyoulatina.models.dao.QuestionDAO;
import com.example.apoyoulatina.models.dao.UserDAO;
import com.example.apoyoulatina.models.dao.UserTelegramDAO;
import com.example.apoyoulatina.models.entities.Question;
import com.example.apoyoulatina.models.entities.QuestionByUser;
import com.example.apoyoulatina.models.entities.User;
import com.example.apoyoulatina.models.entities.UserTelegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * VerifiedQuestion
 */
@Component
public class VerifiedQuestion {

    @Autowired
    private UlatinaBot ulatinaBot;

    @Autowired
    private QuestionByUserDAO questionByUserDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserTelegramDAO userTelegramDAO;

    @Autowired
    private QuestionDAO questionDAO;

    public QuestionByUser active(long chatId) {
        return questionByUserDAO.findByUserTelegramIdTelegramAndActive(chatId, true);
    }

    public void response (long chatId, String messageText) throws TelegramApiException {
        QuestionByUser questionByUser = active(chatId);

        if (questionByUser.getQuestion().getId() == 1) {
            User user = userDAO.findByIdentification(messageText);

            SendMessage sendMessage = new SendMessage()
                .setChatId(chatId);
            if (user != null) {
                
                user.setUserTelegram(userTelegramDAO.findByIdTelegram(chatId));
                userDAO.save(user);

                sendMessage
                    .setText("Hola, "+user.getName()+ " "+ user.getLastName()+ ", en que puedo ayudarle." 
                        + " puede usar estos atajos para ayudarte más fácilmente\n\n"
                        + " /matricular\n"
                        + " /cursos\n"
                        + " /otra_consulta\n"
                        + " /cerrar_sesion");
                questionByUser.setActive(false);
                questionByUserDAO.save(questionByUser);
                
            }else{
                sendMessage
                    .setText(
                        "La identificacion *"+messageText+"*, no fue encontrado, intentelo nuevamente")
                    .setParseMode(ParseMode.MARKDOWN);
            }
            ulatinaBot.execute(sendMessage);
        }else if (questionByUser.getQuestion().getId() == 2) {

            SendMessage sendMessage = new SendMessage()
                .setChatId(chatId);

            String arrName [] = messageText.split(" ");

            String name = arrName[0].substring(0, 1).toUpperCase() + arrName[0].substring(1);
            String lastName = arrName[1].substring(0, 1).toUpperCase() + arrName[1].substring(1);
            
            /* sendMessage
                .setText("Nombre: "+name +
                    "\nApellido: "+ lastName +
                    "\nSe guardo su nombre y apellidos");
 */
            

            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setUserTelegram(userTelegramDAO.findByIdTelegram(chatId));
            userDAO.save(user);
            questionByUser.setActive(false);
            questionByUserDAO.save(questionByUser);
            sendMessage
                .setText("¿En que podemos ayudarle, "+name+"?");
            ulatinaBot.execute(sendMessage);
        }
    }

    public void ActiveQuestion(int idQuestion, long chatId) {
        Question question = questionDAO.findById(idQuestion).orElse(null);

        UserTelegram userTelegram = userTelegramDAO.findByIdTelegram(chatId);

        questionByUserDAO.save(new QuestionByUser(0, true, question, userTelegram));
    }
    
}