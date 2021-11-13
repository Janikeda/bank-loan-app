package ru.bank.application.mail;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
}
