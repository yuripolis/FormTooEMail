package com.formandmail.formtomail.controller;

import com.formandmail.formtomail.model.MailModel;
import com.formandmail.formtomail.service.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.validation.Valid;
import java.util.Properties;

@RestController
public class controller extends service{

    @GetMapping("/form")
    public ModelAndView form(){
        ModelAndView mv = new ModelAndView("form");
        return mv;
    }





    @PostMapping("/form")
    public ModelAndView send(@ModelAttribute  MailModel mail) {
        final String fromEmail = "yuripolistchuk@gmail.com";
        final String password = "woffrgzqulhulaib";

        System.out.println(service.valEmail(mail.getEmail()));




             if (valEmail(mail.getEmail()) == false) {
                 System.out.println("invalid emailadress");
                 ModelAndView mv = new ModelAndView("/form");
                 mv.addObject("error", "Email In-valido");
                 return mv;
             } else {

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };
            Session session = Session.getInstance(props, auth);


            service.sendEmail(session, mail.getEmail(), mail.getAssunto(), mail.getmensagem());

            return new ModelAndView("/form") ;
            }

    }
}
