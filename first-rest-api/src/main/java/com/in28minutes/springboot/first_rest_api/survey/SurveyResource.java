package com.in28minutes.springboot.first_rest_api.survey;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class SurveyResource {

	private SurveyService surveyService;

	public SurveyResource(SurveyService surveyService) {
		super();
		this.surveyService = surveyService;
	}

	@RequestMapping("/surveys")
	public List<Survey> retrieveAllSurveys() {
		return surveyService.getAllSurveys();

	}

	@RequestMapping("/surveys/{surveyId}")
	public Survey retrieveSurveyById(@PathVariable String surveyId) {

		Survey survey = surveyService.getSurveyById(surveyId);

		if (survey == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return survey;
	}

	@RequestMapping(value="/surveys/{surveyId}/questions", method=RequestMethod.GET)
	public List<Question> retrieveQuestions(@PathVariable String surveyId) {
		List<Question> questions = surveyService.retrieveAllSurveyQuestions(surveyId);

		if (questions == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return questions;
	}

	@RequestMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveSpecificSurveyQuestion(@PathVariable String surveyId,
													@PathVariable String questionId){
		Question question = surveyService.retrieveSpecificSurveyQuestion
										(surveyId, questionId);
		
		if(question==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		return question;
	}
	
	@RequestMapping(value="/surveys/{surveyId}/questions", method = RequestMethod.POST)
	public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, 
										  @RequestBody Question question) {
		String questionId = surveyService.addNewSurveyQuestion(surveyId, question);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{questionId}").buildAndExpand(questionId).toUri();
		return ResponseEntity.created(location ).build();
	}
	
	@RequestMapping(value = "/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteSpecificSurveyQuestion(@PathVariable String surveyId,
													@PathVariable String questionId){
		String question = surveyService.deleteSurveyQuestion(surveyId, questionId);
		
		if(question==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found for the given surveyId and questionId");
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateSpecificSurveyQuestion(@PathVariable String surveyId,
																@PathVariable String questionId, @RequestBody Question question){
		surveyService.updateSurveyQuestion(surveyId, questionId, question);
		
		return ResponseEntity.noContent().build();
	}
}
