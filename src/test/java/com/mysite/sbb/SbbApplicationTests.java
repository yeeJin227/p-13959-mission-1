package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	void testJpa1() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	@DisplayName("findAll 조회")
	void testJpa2() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2,all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	@DisplayName("findById 조회")
	void testJpa3() {
		Optional<Question> oq = this.questionRepository.findById(2);
		if(oq.isPresent()) {
			Question q = oq.get();
			assertEquals("스프링부트 모델 질문입니다", q.getSubject());
		}
	}

	@Test
	@DisplayName("수정")
	void testJpa4() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());

		Question q = oq.get();
		q.setSubject("제목 수정했습니다.");

		this.questionRepository.save(q);
	}

	@Test
	@DisplayName("삭제")
	void testJpa5() {
		assertEquals(2, this.questionRepository.count());

		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());

		Question q = oq.get();
		this.questionRepository.delete(q);

		assertEquals(1, this.questionRepository.count());
	}


	@Test
	@DisplayName("질문 id 1에 댓글 생성")
	void answerTest(){
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();


		Answer a1 = new Answer();
		a1.setContent("네 자동으로 생성됩니다.");
		a1.setQuestion(q);
		a1.setCreateDate(LocalDateTime.now());

		this.answerRepository.save(a1);
	}

	@Test
	@DisplayName("댓글 조회")
	void answerTest1(){
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();

		assertEquals(2,a.getQuestion().getId());
	}
}
