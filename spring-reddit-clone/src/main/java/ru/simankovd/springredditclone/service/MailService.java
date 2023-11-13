package ru.simankovd.springredditclone.service;

import ru.simankovd.springredditclone.model.NotificationEmail;

public interface MailService {

    public void sendMail(NotificationEmail notificationEmail);
}
