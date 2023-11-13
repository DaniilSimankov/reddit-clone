package ru.simankovd.springredditclone.service.impl;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.simankovd.springredditclone.service.MailContentBuilder;

@Service
@RequiredArgsConstructor
public class MailContentBuilderImpl implements MailContentBuilder {

    private final TemplateEngine templateEngine; // todo для чего?

    @Override
    public String build(String message) {

        Context context = new Context();
        context.setVariable("message", message);

        return templateEngine.process("mailTemplate", context);
    }
}
