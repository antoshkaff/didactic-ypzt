package com.devrezaur.main;

import com.devrezaur.main.controller.MainController;
import com.devrezaur.main.dto.CategoryPerformance;
import com.devrezaur.main.dto.SubjectPerformance;
import com.devrezaur.main.dto.TopicPerformance;
import com.devrezaur.main.model.Question;
import com.devrezaur.main.model.QuestionForm;
import com.devrezaur.main.model.QuizResultSummary;
import com.devrezaur.main.model.ResultPerQuestion;
import com.devrezaur.main.model.Topic;
import com.devrezaur.main.model.User;
import com.devrezaur.main.service.QuestionService;
import com.devrezaur.main.service.QuizResultSummaryService;
import com.devrezaur.main.service.ResultPerQuestionService;
import com.devrezaur.main.service.TopicService;
import com.devrezaur.main.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MainControllerTest {

    @InjectMocks
    private MainController mainController;
    @Mock
    private QuizResultSummaryService quizResultSummaryService;
    @Mock
    private UserService userService;
    @Mock
    private QuestionService questionService;
    @Mock
    private ResultPerQuestionService resultPerQuestionService;
    @Mock
    private TopicService topicService;
    @Mock
    private SessionStatus sessionStatus;

    private User testUser;
    private Model model;
    private List<Question> quizQuestionsSession;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setIdUser(1L);
        testUser.setName("testUser");
        testUser.setPassword("password");
        testUser.setPreparationLevel(User.PreparationLevel.BEGINNER);
        testUser.setRegistrationDate(LocalDateTime.now());

        model = new ConcurrentModel();
        quizQuestionsSession = new ArrayList<>();
    }
    @Test
    void testLoginUser_Failure_WrongPassword() {
        when(userService.findUserByName(anyString())).thenReturn(testUser);

        User userForm = new User();
        userForm.setName("testUser");
        userForm.setPassword("wrongpassword");

        String viewName = mainController.loginUser(userForm, model, new User());

        assertEquals("login", viewName);
        assertNotNull(model.asMap().get("error"));
        assertEquals("Невірний логін або пароль.", model.asMap().get("error"));
        verify(userService, times(1)).findUserByName("testUser");
    }

    @Test
    void testLoginUser_Failure_UserNotFound() {
        when(userService.findUserByName(anyString())).thenReturn(null);

        User userForm = new User();
        userForm.setName("nonExistentUser");
        userForm.setPassword("password");

        String viewName = mainController.loginUser(userForm, model, new User());

        assertEquals("login", viewName);
        assertNotNull(model.asMap().get("error"));
        assertEquals("Невірний логін або пароль.", model.asMap().get("error"));
        verify(userService, times(1)).findUserByName("nonExistentUser");
    }

    @Test
    void testRegisterUser_Success() {
        when(userService.findUserByName(anyString())).thenReturn(null);
        doNothing().when(userService).addNewUser(any(User.class));

        User newUser = new User();
        newUser.setName("newUser");
        newUser.setPassword("newPass");
        newUser.setPreparationLevel(User.PreparationLevel.INTERMEDIATE);

        String viewName = mainController.registerUser(newUser, model);
        assertEquals("redirect:/login.html?registrationSuccess=true", viewName);
        verify(userService, times(1)).findUserByName("newUser");
        verify(userService, times(1)).addNewUser(newUser);
    }

    @Test
    void testRegisterUser_Failure_UserExists() {
        when(userService.findUserByName(anyString())).thenReturn(testUser);

        User existingUser = new User();
        existingUser.setName("testUser");
        existingUser.setPassword("anyPass");

        String viewName = mainController.registerUser(existingUser, model);

        assertEquals("registration", viewName);
        assertNotNull(model.asMap().get("error"));
        assertEquals("Користувач з таким логіном вже існує.", model.asMap().get("error"));
        assertNotNull(model.asMap().get("preparationLevels"));
        verify(userService, times(1)).findUserByName("testUser");
        verify(userService, never()).addNewUser(any(User.class));
    }

    @Test
    void testStartQuiz_UserNotLoggedIn() {
        User anonymousUser = new User();
        String viewName = mainController.startQuiz(null, model, anonymousUser, quizQuestionsSession);
        assertEquals("redirect:/login.html", viewName);
    }

    @Test
    void testStartQuiz_EnoughQuestionsFromDifferentTopics() {
        Topic topic1 = new Topic(1L, "Topic A", "Category X");
        Topic topic2 = new Topic(2L, "Topic B", "Category Y");
        Topic topic3 = new Topic(3L, "Topic C", "Category Z");
        Topic topic4 = new Topic(4L, "Topic D", "Category W");
        Topic topic5 = new Topic(5L, "Topic E", "Category V");

        Question q1 = new Question(1L, "Q1", "A", "B", "C", "D", "E", "A", topic1, "Exp1");
        Question q2 = new Question(2L, "Q2", "A", "B", "C", "D", "E", "B", topic2, "Exp2");
        Question q3 = new Question(3L, "Q3", "A", "B", "C", "D", "E", "C", topic3, "Exp3");
        Question q4 = new Question(4L, "Q4", "A", "B", "C", "D", "E", "D", topic4, "Exp4");
        Question q5 = new Question(5L, "Q5", "A", "B", "C", "D", "E", "E", topic5, "Exp5");
        Question q6 = new Question(6L, "Q6", "A", "B", "C", "D", "E", "A", topic1, "Exp6");

        List<Topic> allTopics = Arrays.asList(topic1, topic2, topic3, topic4, topic5);
        when(topicService.getAllTopics()).thenReturn(allTopics);
        when(questionService.getQuestionsByTopic(topic1.getTopicName())).thenReturn(Arrays.asList(q1, q6));
        when(questionService.getQuestionsByTopic(topic2.getTopicName())).thenReturn(Collections.singletonList(q2));
        when(questionService.getQuestionsByTopic(topic3.getTopicName())).thenReturn(Collections.singletonList(q3));
        when(questionService.getQuestionsByTopic(topic4.getTopicName())).thenReturn(Collections.singletonList(q4));
        when(questionService.getQuestionsByTopic(topic5.getTopicName())).thenReturn(Collections.singletonList(q5));

        String viewName = mainController.startQuiz(null, model, testUser, quizQuestionsSession);

        assertEquals("quiz", viewName);
        assertNotNull(model.asMap().get("questionForm"));
        QuestionForm form = (QuestionForm) model.asMap().get("questionForm");
        assertFalse(form.getQuestions().isEmpty());
        assertEquals(5, form.getQuestions().size());
        assertEquals(5, quizQuestionsSession.size());
        assertEquals(5, form.getQuestions().stream().map(q -> q.getTopic().getTopicName()).distinct().count());
        verify(topicService, times(1)).getAllTopics();
        verify(questionService, atLeast(5)).getQuestionsByTopic(anyString());
    }

    @Test
    void testStartQuiz_NoQuestionsAvailable() {
        when(topicService.getAllTopics()).thenReturn(Arrays.asList(new Topic(1L, "T1", "C1")));
        when(questionService.getQuestionsByTopic(anyString())).thenReturn(new ArrayList<>());
        when(questionService.getAllQuestions()).thenReturn(new ArrayList<>());

        String viewName = mainController.startQuiz(null, model, testUser, quizQuestionsSession);

        assertEquals("quiz", viewName);
        assertNotNull(model.asMap().get("message"));
        assertEquals("На жаль, немає питань для тесту.", model.asMap().get("message"));
        assertTrue(quizQuestionsSession.isEmpty());
    }

    @Test
    void testQuizSubmit_NoQuestionsSubmitted() {
        QuestionForm form = new QuestionForm();
        form.setQuestions(new ArrayList<>());

        String viewName = mainController.quizSubmit(form, model, testUser, quizQuestionsSession);

        assertEquals("redirect:/quiz.html", viewName);
        assertNotNull(model.asMap().get("error"));
        verifyNoInteractions(resultPerQuestionService);
        verifyNoInteractions(quizResultSummaryService);
    }


    @Test
    void testPlanPage_UserNotLoggedIn() {
        User anonymousUser = new User();
        String viewName = mainController.planPage(anonymousUser, model);
        assertEquals("redirect:/login.html", viewName);
    }

    @Test
    void testPlanPage_WithResults() {
        Topic topicA = new Topic(1L, "Topic A", "Category 1");
        Topic topicB = new Topic(2L, "Topic B", "Category 1");
        Topic topicC = new Topic(3L, "Topic C", "Category 2");

        Question q1 = new Question(10L, "Q1", "a", "b", "c", "d", "e", "a", topicA, "");
        Question q2 = new Question(20L, "Q2", "a", "b", "c", "d", "e", "b", topicB, "");
        Question q3 = new Question(30L, "Q3", "a", "b", "c", "d", "e", "c", topicC, "");
        Question q4 = new Question(40L, "Q4", "a", "b", "c", "d", "e", "d", topicA, "");

        ResultPerQuestion res1_q1 = new ResultPerQuestion(testUser, q1, true);
        res1_q1.setAnswerTime(LocalDateTime.now().minusMinutes(5));
        ResultPerQuestion res2_q2 = new ResultPerQuestion(testUser, q2, false);
        res2_q2.setAnswerTime(LocalDateTime.now().minusMinutes(4));
        ResultPerQuestion res3_q3 = new ResultPerQuestion(testUser, q3, true);
        res3_q3.setAnswerTime(LocalDateTime.now().minusMinutes(3));
        ResultPerQuestion res4_q1_later = new ResultPerQuestion(testUser, q1, false);
        res4_q1_later.setAnswerTime(LocalDateTime.now().minusMinutes(1));
        ResultPerQuestion res5_q4 = new ResultPerQuestion(testUser, q4, true);
        res5_q4.setAnswerTime(LocalDateTime.now().minusMinutes(2));

        List<ResultPerQuestion> userResults = Arrays.asList(res1_q1, res2_q2, res3_q3, res4_q1_later, res5_q4);

        when(resultPerQuestionService.getResultsByUser(testUser)).thenReturn(userResults);
        when(topicService.getAllTopics()).thenReturn(Arrays.asList(topicA, topicB, topicC));

        String viewName = mainController.planPage(testUser, model);

        assertEquals("individual-plan", viewName);
        assertNotNull(model.asMap().get("overallSubjectPerformance"));

        SubjectPerformance overallPerf = (SubjectPerformance) model.asMap().get("overallSubjectPerformance");
        assertNotNull(overallPerf);
        assertFalse(overallPerf.getCategories().isEmpty());

        assertEquals("Overall Progress", overallPerf.getSubjectName());
        assertEquals(62.5, overallPerf.getAveragePercentage(), 0.01);

        assertEquals(2, overallPerf.getCategories().size());

        CategoryPerformance cat1 = overallPerf.getCategories().stream().filter(c -> c.getCategoryName().equals("Category 1")).findFirst().orElse(null);
        assertNotNull(cat1);
        assertEquals(25.0, cat1.getAveragePercentage(), 0.01);
        assertEquals(2, cat1.getTopics().size());

        TopicPerformance topicAPerf = cat1.getTopics().stream().filter(tp -> tp.getTopicName().equals("Topic A")).findFirst().orElse(null);
        assertNotNull(topicAPerf);
        assertEquals(50.0, topicAPerf.getPercentage(), 0.01);

        TopicPerformance topicBPerf = cat1.getTopics().stream().filter(tp -> tp.getTopicName().equals("Topic B")).findFirst().orElse(null);
        assertNotNull(topicBPerf);
        assertEquals(0.0, topicBPerf.getPercentage(), 0.01);

        CategoryPerformance cat2 = overallPerf.getCategories().stream().filter(c -> c.getCategoryName().equals("Category 2")).findFirst().orElse(null);
        assertNotNull(cat2);
        assertEquals(100.0, cat2.getAveragePercentage(), 0.01);
        assertEquals(1, cat2.getTopics().size());

        TopicPerformance topicCPerf = cat2.getTopics().stream().filter(tp -> tp.getTopicName().equals("Topic C")).findFirst().orElse(null);
        assertNotNull(topicCPerf);
        assertEquals(100.0, topicCPerf.getPercentage(), 0.01);

        verify(resultPerQuestionService, times(1)).getResultsByUser(testUser);
        verify(topicService, times(1)).getAllTopics();
    }

    @Test
    void testLogout() {
        doNothing().when(sessionStatus).setComplete();

        String viewName = mainController.logout(sessionStatus);

        assertEquals("redirect:/login.html", viewName);
        verify(sessionStatus, times(1)).setComplete();
    }

    @Test
    void testIndex_LoggedIn() {
        String viewName = mainController.index(model, testUser);

        assertEquals("index", viewName);
        assertEquals(testUser.getName(), model.asMap().get("userName"));
    }

    @Test
    void testIndex_NotLoggedIn() {
        User anonymousUser = new User();
        String viewName = mainController.index(model, anonymousUser);
        assertEquals("redirect:/login.html", viewName);
        assertNull(model.asMap().get("userName"));
    }

    @Test
    void testAI() {
        assertEquals("AI", mainController.AI());
    }

    @Test
    void testLogin_NoRegistrationSuccess() {
        String viewName = mainController.login(null, model);
        assertEquals("login", viewName);
        assertNotNull(model.asMap().get("user"));
        assertNull(model.asMap().get("message"));
    }

    @Test
    void testLogin_WithRegistrationSuccess() {
        String viewName = mainController.login("true", model);
        assertEquals("login", viewName);
        assertNotNull(model.asMap().get("user"));
        assertNotNull(model.asMap().get("message"));
        assertEquals("Реєстрація успішна! Тепер ви можете увійти.", model.asMap().get("message"));
    }

    @Test
    void testRegistration() {
        String viewName = mainController.registration(model);
        assertEquals("registration", viewName);
        assertNotNull(model.asMap().get("user"));
        assertNotNull(model.asMap().get("preparationLevels"));
    }

    @Test
    void testGetUserForAI_LoggedIn() {
        ResponseEntity<User> response = mainController.getUserForAI(testUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser.getName(), response.getBody().getName());
    }

    @Test
    void testGetUserForAI_NotLoggedIn() {
        User anonymousUser = new User();
        ResponseEntity<User> response = mainController.getUserForAI(anonymousUser);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }
}