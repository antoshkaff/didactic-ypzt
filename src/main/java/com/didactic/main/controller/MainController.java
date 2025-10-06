package com.devrezaur.main.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

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

@Controller
@SessionAttributes({"currentUser", "quizQuestions"})
public class MainController {

    private static final int QUIZ_SIZE = 5;

    @Autowired
    private QuizResultSummaryService quizResultSummaryService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ResultPerQuestionService resultPerQuestionService;
    @Autowired
    private TopicService topicService;

    @ModelAttribute("currentUser")
    public User setUpUser() {
        return new User();
    }

    @ModelAttribute("quizQuestions")
    public List<Question> setUpQuizQuestions() {
        return new ArrayList<>();
    }

    @GetMapping("/AI.html")
    public String AI() {
        return "AI";
    }

    @GetMapping("/index.html")
    public String index(Model model, @ModelAttribute("currentUser") User currentUser) {
        if (!isLoggedIn(currentUser)) {
            return "redirect:/login.html";
        }
        model.addAttribute("userName", currentUser.getName());
        return "index";
    }

    @GetMapping("/login.html")
    public String login(@RequestParam(value = "registrationSuccess", required = false) String registrationSuccess, Model model) {
        model.addAttribute("user", new User());
        if (registrationSuccess != null) {
            model.addAttribute("message", "Реєстрація успішна! Тепер ви можете увійти.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User userForm,
                            Model model, @ModelAttribute("currentUser") User sessionUser) {
        if (authenticateAndFillSession(userForm, sessionUser)) {
            return "redirect:/index.html";
        }
        model.addAttribute("error", "Невірний логін або пароль.");
        model.addAttribute("user", userForm);
        return "login";
    }

    @GetMapping("/registration.html")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("preparationLevels", User.PreparationLevel.values());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User userForm, Model model) {
        if (userService.findUserByName(userForm.getName()) != null) {
            model.addAttribute("error", "Користувач з таким логіном вже існує.");
            model.addAttribute("user", userForm);
            model.addAttribute("preparationLevels", User.PreparationLevel.values());
            return "registration";
        }
        userService.addNewUser(userForm);
        return "redirect:/login.html?registrationSuccess=true";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/login.html";
    }

    @GetMapping("/quiz.html")
    public String startQuiz(@RequestParam(value = "topic", required = false) String topicName, Model model,
                            @ModelAttribute("currentUser") User currentUser,
                            @ModelAttribute("quizQuestions") List<Question> quizQuestionsSession) {

        if (!isLoggedIn(currentUser)) {
            return "redirect:/login.html";
        }

        List<Topic> allTopics = topicService.getAllTopics();
        Collections.shuffle(allTopics);

        // Используем новый метод сервиса
        List<Question> randomQuestions = questionService.selectRandomQuestions(allTopics, QUIZ_SIZE);

        if (randomQuestions.isEmpty()) {
            model.addAttribute("message", "На жаль, немає питань для тесту.");
            model.addAttribute("topics", allTopics);
            return "quiz";
        }

        quizQuestionsSession.clear();
        quizQuestionsSession.addAll(randomQuestions);

        QuestionForm questionForm = new QuestionForm();
        questionForm.setQuestions(randomQuestions);

        model.addAttribute("questionForm", questionForm);
        model.addAttribute("topics", allTopics);

        return "quiz";
    }

    @PostMapping("/submitQuestions")
    public String quizSubmit(@ModelAttribute("questionForm") QuestionForm form, Model model,
                             @ModelAttribute("currentUser") User currentUser,
                             @ModelAttribute("quizQuestions") List<Question> quizQuestionsSession) {
        if (!isLoggedIn(currentUser)) {
            return "redirect:/login.html";
        }

        int totalCorrectQuestions = 0;
        List<ResultPerQuestion> individualResults = new ArrayList<>();

        if (form.getQuestions() == null || form.getQuestions().isEmpty() || quizQuestionsSession.isEmpty()) {
            model.addAttribute("error", "Сталася помилка при обробці тесту. Будь ласка, спробуйте знову.");
            return "redirect:/quiz.html";
        }

        Map<String, int[]> topicResults = new HashMap<>();

        for (Question submittedQuestion : form.getQuestions()) {
            Optional<Question> originalQuestionOpt = quizQuestionsSession.stream()
                .filter(q -> q.getQuestionId() != null && q.getQuestionId().equals(submittedQuestion.getQuestionId()))
                .findFirst();

            if (originalQuestionOpt.isPresent()) {
                Question originalQuestion = originalQuestionOpt.get();
                boolean isAnswered = submittedQuestion.getUserChoice() != null && !submittedQuestion.getUserChoice().trim().isEmpty();
                boolean isCorrect = isAnswered && submittedQuestion.getUserChoice().equals(originalQuestion.getCorrectAnswer());

                if (isCorrect) {
                    totalCorrectQuestions++;
                }

                Question questionForResult = new Question();
                questionForResult.setQuestionId(originalQuestion.getQuestionId());
                questionForResult.setQuestionText(originalQuestion.getQuestionText());
                questionForResult.setOptionA(originalQuestion.getOptionA());
                questionForResult.setOptionB(originalQuestion.getOptionB());
                questionForResult.setOptionC(originalQuestion.getOptionC());
                questionForResult.setOptionD(originalQuestion.getOptionD());
                questionForResult.setOptionE(originalQuestion.getOptionE());
                questionForResult.setCorrectAnswer(originalQuestion.getCorrectAnswer());
                questionForResult.setUserChoice(submittedQuestion.getUserChoice());
                questionForResult.setTopic(originalQuestion.getTopic());
                questionForResult.setExplanation(originalQuestion.getExplanation());

                ResultPerQuestion result = new ResultPerQuestion(currentUser, questionForResult, isCorrect);
                individualResults.add(result);

                String tn = originalQuestion.getTopic() != null ? originalQuestion.getTopic().getTopicName() : "Unknown Topic";
                updateTopicStats(topicResults, tn, isCorrect);
            }
        }

        resultPerQuestionService.saveAll(individualResults);

        QuizResultSummary quizSummary = new QuizResultSummary(totalCorrectQuestions, currentUser);
        quizResultSummaryService.saveQuizResultSummary(quizSummary);

        model.addAttribute("procent", (quizQuestionsSession.isEmpty() ? 0.0 : (100.0 / quizQuestionsSession.size()) * totalCorrectQuestions));
        model.addAttribute("totalQuestions", quizQuestionsSession.size());
        model.addAttribute("result", totalCorrectQuestions);
        model.addAttribute("questions", individualResults);

        List<TopicPerformance> topicPerformances = buildTopicPerformances(topicResults);
        model.addAttribute("topicPerformances", topicPerformances);

        return "result";
    }

    @GetMapping("/score")
    public String score(Model model) {
        List<QuizResultSummary> allResults = quizResultSummaryService.getScoreboard();

        Map<User, Optional<QuizResultSummary>> userBestScores = allResults.stream()
            .collect(Collectors.groupingBy(
                QuizResultSummary::getUser,
                Collectors.maxBy(Comparator.comparingInt(QuizResultSummary::getTotalCorrect))
            ));

        List<QuizResultSummary> uniqueUserResults = userBestScores.values().stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .sorted(Comparator.comparingInt(QuizResultSummary::getTotalCorrect).reversed())
            .collect(Collectors.toList());

        model.addAttribute("results", uniqueUserResults);
        return "scoreboard";
    }

    @GetMapping("/individual-plan")
    public String planPage(@ModelAttribute("currentUser") User currentUser, Model model) {
        if (!isLoggedIn(currentUser)) {
            return "redirect:/login.html";
        }

        List<ResultPerQuestion> userResults = resultPerQuestionService.getResultsByUser(currentUser);

        Map<Long, ResultPerQuestion> latestResultsByQuestion = new HashMap<>();
        for (ResultPerQuestion result : userResults) {
            Long questionId = result.getQuestion() != null ? result.getQuestion().getQuestionId() : null;
            if (questionId == null) continue;

            if (!latestResultsByQuestion.containsKey(questionId)) {
                latestResultsByQuestion.put(questionId, result);
            } else {
                ResultPerQuestion existing = latestResultsByQuestion.get(questionId);
                if (result.getAnswerTime() != null && existing.getAnswerTime() != null
                    && result.getAnswerTime().isAfter(existing.getAnswerTime())) {
                    latestResultsByQuestion.put(questionId, result);
                }
            }
        }

        List<ResultPerQuestion> latestResults = new ArrayList<>(latestResultsByQuestion.values());

        Map<String, long[]> topicStats = latestResults.stream()
            .filter(result -> result.getQuestion() != null &&
                             result.getQuestion().getTopic() != null &&
                             result.getQuestion().getTopic().getTopicName() != null)
            .collect(Collectors.groupingBy(
                result -> result.getQuestion().getTopic().getTopicName(),
                Collectors.mapping(
                    result -> new long[]{result.getResultCorrect() ? 1 : 0, 1},
                    Collectors.reducing(new long[]{0, 0}, (a, b) -> new long[]{a[0] + b[0], a[1] + b[1]})
                )
            ));

        List<TopicPerformance> topicPerformances = topicStats.entrySet().stream()
            .map(entry -> {
                String topicName = entry.getKey();
                long correct = entry.getValue()[0];
                long total = entry.getValue()[1];
                double percentage = (total > 0) ? ((double) correct / total) * 100 : 0.0;
                return new TopicPerformance(topicName, percentage);
            })
            .sorted(Comparator.comparingDouble(tp -> tp.getPercentage() >= 100 ? 1_000.0 : tp.getPercentage()))
            .collect(Collectors.toList());

        List<Topic> allTopics = topicService.getAllTopics();
        Map<String, List<Topic>> topicsByCategory = allTopics.stream()
            .collect(Collectors.groupingBy(
                topic -> topic.getCategoryName() != null ? topic.getCategoryName() : "No Category Assigned",
                LinkedHashMap::new,
                Collectors.toList()
            ));

        Map<String, TopicPerformance> topicPerformanceMap = topicPerformances.stream()
                .collect(Collectors.toMap(TopicPerformance::getTopicName, tp -> tp));

        List<CategoryPerformance> categoryPerformances = new ArrayList<>();

        for (Map.Entry<String, List<Topic>> categoryEntry : topicsByCategory.entrySet()) {
            String categoryName = categoryEntry.getKey();
            List<Topic> topicsInCategory = categoryEntry.getValue();

            List<TopicPerformance> topicPerformancesForCategory = topicsInCategory.stream()
                .filter(topic -> topic.getTopicName() != null)
                .map(topic -> topicPerformanceMap.getOrDefault(topic.getTopicName(), new TopicPerformance(topic.getTopicName(), 0.0)))
                .sorted(Comparator.comparingDouble(tp -> tp.getPercentage() >= 100 ? 1_000.0 : tp.getPercentage()))
                .collect(Collectors.toList());

            double categoryAvg = topicPerformancesForCategory.stream()
                .mapToDouble(TopicPerformance::getPercentage)
                .average()
                .orElse(0.0);

            categoryPerformances.add(new CategoryPerformance(categoryName, categoryAvg, topicPerformancesForCategory));
        }

        categoryPerformances.sort(Comparator.comparingDouble(CategoryPerformance::getAveragePercentage));

        double overallAverage = categoryPerformances.stream()
            .mapToDouble(CategoryPerformance::getAveragePercentage)
            .average()
            .orElse(0.0);

        SubjectPerformance overallPerformance = new SubjectPerformance("Overall Progress", overallAverage, categoryPerformances);

        model.addAttribute("overallSubjectPerformance", overallPerformance);

        return "individual-plan";
    }

    @GetMapping("/users")
    public ResponseEntity<User> getUserForAI(@ModelAttribute("currentUser") User currentUser) {
        if (!isLoggedIn(currentUser)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(currentUser);
    }

    private boolean isLoggedIn(User user) {
        return user != null && user.getIdUser() != null;
    }

    private boolean authenticateAndFillSession(User form, User sessionUser) {
        User user = userService.findUserByName(form.getName());
        if (user != null && user.getPassword().equals(form.getPassword())) {
            copyUser(user, sessionUser);
            return true;
        }
        return false;
    }

    private void copyUser(User src, User dst) {
        dst.setIdUser(src.getIdUser());
        dst.setName(src.getName());
        dst.setPassword(src.getPassword());
        dst.setLastTopic(src.getLastTopic());
        dst.setPreparationLevel(src.getPreparationLevel());
        dst.setRegistrationDate(src.getRegistrationDate());
    }

    private void updateTopicStats(Map<String, int[]> topicResults, String topicName, boolean isCorrect) {
        topicResults.putIfAbsent(topicName, new int[]{0, 0});
        int[] arr = topicResults.get(topicName);
        if (isCorrect) arr[0]++;
        arr[1]++;
    }

    private List<TopicPerformance> buildTopicPerformances(Map<String, int[]> topicResults) {
        List<TopicPerformance> topicPerformances = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");

        for (Map.Entry<String, int[]> entry : topicResults.entrySet()) {
            String topicName = entry.getKey();
            int correct = entry.getValue()[0];
            int total = entry.getValue()[1];
            double percentage = (total > 0) ? ((double) correct / total) * 100 : 0.0;
            topicPerformances.add(new TopicPerformance(topicName, Double.parseDouble(df.format(percentage))));
        }
        topicPerformances.sort(Comparator.comparing(TopicPerformance::getTopicName));
        return topicPerformances;
    }
}
