package com.mysite.sbb;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final QuestionRepository questionRepository;

    @Bean
    ApplicationRunner baseInitDataApplicationRunner(){
        return args->{
            work1();
        };
    }

    @Transactional
    void work1(){
        Question q1 = new Question();
        q1.setSubject("제목1입니다.");
        q1.setContent("제목1에 적은 내용1입니다.");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("제목2입니다.");
        q2.setContent("제목2에 적은 내용2입니다.");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2);
    }
}
