package com.PrintLab.utils;

import com.PrintLab.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class EmailUtils {
    private final JavaMailSender javaMailSender;

    public EmailUtils(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Value("${spring.mail.username}")
    private String sender;
    @Async
    public Boolean sendRegistrationEmail(String userEmail, String password) {

            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom(sender);
                helper.setTo(userEmail);
                helper.setSubject("Welcome to Your PrintLab!");
                helper.setText("Thank you for registering with PrintLab!\n\n"
                        + "Your login credentials:\n"
                        + "Email: " + userEmail + "\n"
                        + "Password: " + password + "\n\n"
                        + "Please keep your credentials safe and do not share them with anyone.");

                javaMailSender.send(message);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    @Async
    public Boolean sendOrderAssignedEmail(String userEmail, Order order) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(sender);
            helper.setTo(userEmail);
            helper.setSubject("Order Assigned Notification");

            String emailContent = "Your order has been assigned!\n\n"
                    + "Order details:\n"
                    + "Order ID: " + order.getId() + "\n"
                    + "We appreciate your business. If you have any questions, feel free to contact us.";

            helper.setText(emailContent);

            javaMailSender.send(message);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
