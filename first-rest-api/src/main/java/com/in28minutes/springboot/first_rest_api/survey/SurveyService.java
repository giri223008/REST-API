package com.in28minutes.springboot.first_rest_api.survey;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class SurveyService {

	private static List<Survey> surveys = new ArrayList<>();

	static {
		Question question1 = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		Question question2 = new Question("Question2", "Fastest Growing Cloud Platform",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		Question question3 = new Question("Question3", "Most Popular DevOps Tool",
				Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

		List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3));

		Survey survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);

		surveys.add(survey);
	}

	public List<Survey> getAllSurveys() {
		// TODO Auto-generated method stub
		return surveys;
	}

	public Survey getSurveyById(String surveyId) {
		// TODO Auto-generated method stub
		Optional<Survey> optionalSurvey = surveys.stream().filter(survey -> survey.getId().equalsIgnoreCase(surveyId))
				.findFirst();
		if (optionalSurvey.isEmpty()) {
			return null;
		}
		return optionalSurvey.get();
	}

	public List<Question> retrieveAllSurveyQuestions(String surveyId) {
		// TODO Auto-generated method stub
		Survey survey = getSurveyById(surveyId);
		if (survey == null) {
			return null;
		}
		return survey.getQuestions();
	}

	public Question retrieveSpecificSurveyQuestion(String surveyId, String questionId) {

		List<Question> surveyQuestions = retrieveAllSurveyQuestions(surveyId);

		if (surveyQuestions == null)
			return null;

		Optional<Question> optionalQuestion = surveyQuestions.stream()
				.filter(q -> q.getId().equalsIgnoreCase(questionId)).findFirst();

		if (optionalQuestion.isEmpty())
			return null;

		return optionalQuestion.get();
	}

	public String addNewSurveyQuestion(String surveyId, Question question) {
		// TODO Auto-generated method stub
		List<Question> surveyQuestions = retrieveAllSurveyQuestions(surveyId);
		question.setId(generateRandomId());
		surveyQuestions.add(question);
		
		return question.getId();
	}

	private String generateRandomId() {
		// TODO Auto-generated method stub
		SecureRandom secureRandom = new SecureRandom();
		String randomId = new BigInteger(32, secureRandom).toString();
		return randomId;
	}

	public String deleteSurveyQuestion(String surveyId, String questionId) {
		// TODO Auto-generated method stub
		List<Question> surveyQuestions = retrieveAllSurveyQuestions(surveyId);

		if (surveyQuestions == null)
			return null;

		boolean removed = surveyQuestions.removeIf(q -> q.getId().equalsIgnoreCase(questionId));
		
		if(!removed) return null;

		return questionId;
		
	}

	public void updateSurveyQuestion(String surveyId, String questionId, Question question) {
		// TODO Auto-generated method stub
		List<Question> surveyQuestions = retrieveAllSurveyQuestions(surveyId);

		surveyQuestions.removeIf(q -> q.getId().equalsIgnoreCase(questionId));
		
		surveyQuestions.add(question);
	}
}
