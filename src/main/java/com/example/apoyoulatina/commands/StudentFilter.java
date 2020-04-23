package com.example.apoyoulatina.commands;

import java.util.ArrayList;
import java.util.List;

import com.example.apoyoulatina.models.dao.CourseByStudentDAO;
import com.example.apoyoulatina.models.entities.CourseByStudent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * StudentRegular
 */
@Component
public class StudentFilter {

    @Autowired
    private UlatinaBot ulatinaBot;

    @Autowired
    private VerifiedQuestion verifiedQuestion;

    @Autowired
    private CourseByStudentDAO courseByStudentDAO;

    
    public void regular (Long chatId, Integer messageId, String callBackId) {
        try {
            
            responseCallBack(callBackId);
            ulatinaBot.execute(new DeleteMessage().setChatId(chatId).setMessageId(messageId));
            
            SendMessage response = new SendMessage()
                .setChatId(chatId)
                .setText("Por favor envieme su numero de identificación");
            
            verifiedQuestion.ActiveQuestion(1, chatId);

            ulatinaBot.send(response);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void notRegular (Long chatId, Integer messageId, String callBackId) {
        try {
            responseCallBack(callBackId);
            ulatinaBot.execute(new DeleteMessage().setChatId(chatId).setMessageId(messageId));
            SendMessage response = new SendMessage()
                .setChatId(chatId)
                .setText("¿Me envía su nombre completo?\nEjemplo: Carlos Perez");

            verifiedQuestion.ActiveQuestion(2, chatId);

            ulatinaBot.send(response);
        } catch (TelegramApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void responseCallBack(String callBackId) throws TelegramApiException {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callBackId);
        answerCallbackQuery.setText("Gracias por la respuesta");
        
        ulatinaBot.execute(answerCallbackQuery);
    }

    
    public void courses (Long chatId) throws TelegramApiException {
        System.out.println("CHATIID:::: "+chatId);
        List<CourseByStudent> courses = courseByStudentDAO.findByUserUserTelegramIdTelegram(chatId);

        
        String texto = "Te envio la lista de cursos\n\n✅: Cursos aprobados\n❇️: Cursos matriculados\n❎: Cursos pendientes\n\n";

        int approved = 0;
        int active = 0;
        int pending = 0;

        for (CourseByStudent course : courses) {
            texto += "\n";
            
            texto += course.getCourse().getCourse();
            byte status = course.getStatus();
            if (status == 0) { //Pendientes
                texto += "❎";
                pending ++;
            }else if (status == 1){ //Actuales
                texto += "❇️";
                active ++;
            }else if (status == 2){ //Aprobado
                texto += "✅";
                approved ++;
            }
            
        }

        texto += "\n\nTotal de cursos: "+ courses.size() + " \nAprobados: " + approved + "\nActivos: " + active + "\nPendientes: " + pending;
        texto += "\n\n Volver: /inicio";
        SendMessage sendMessage = new SendMessage()
            .setChatId(chatId)
            .setText(texto);
        ulatinaBot.execute(sendMessage);
    }

    public void matricular(long chatId) throws TelegramApiException {
        byte pendientes = 0;
        List<CourseByStudent> courses = courseByStudentDAO.findByUserUserTelegramIdTelegramAndStatus(chatId, pendientes);

        if (courses.size() == 0) {
            SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("No tiene cursos para matricular");
            ulatinaBot.execute(sendMessage);
        }else {      
                  
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            for (CourseByStudent course : courses) {
                List<InlineKeyboardButton> row = new ArrayList<>();

                String idCourse = Integer.toString(course.getCourse().getId());
                row.add(new InlineKeyboardButton().setText(course.getCourse().getCourse()).setCallbackData(idCourse));
                keyboard.add(row);
            }

            InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
            markupKeyboard.setKeyboard(keyboard);
            
            SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Cursos que puede matricular,\n seleccione para matricular curso")
                .setReplyMarkup(markupKeyboard);

            ulatinaBot.execute(sendMessage);

        }
        

    }

    public void findCourseAndMatricular (long chatId, int courseId) throws TelegramApiException {
        byte p = 0;
        CourseByStudent courseByStudent = courseByStudentDAO.findByUserUserTelegramIdTelegramAndStatusAndId(chatId, p, courseId);
        
        if (courseByStudent != null) {
            byte matriculado = 1;
            courseByStudent.setStatus(matriculado);
            courseByStudentDAO.save(courseByStudent);

            SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Curso "+courseByStudent.getCourse().getCourse() + " , fue matriculado exitosamente");
            
            ulatinaBot.execute(sendMessage);
            matricular(chatId);
            
        }else {
            SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Ocurrió un error, no se encontro el curso");
            ulatinaBot.execute(sendMessage);
            matricular(chatId);
        }
    }

    public void checkout(long chatId) {
        /* Iterable userquestionByUserDAO.findAll();
        UserTelegram user = userTelegramDAO.findByIdTelegram(chatId);

        userTelegramDAO.delete(user); */
    }
}