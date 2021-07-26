package com.iktpreobuka.elektronskidnevnik.services;

import java.util.List;

import com.iktpreobuka.elektronskidnevnik.entities.GradeEntity;

public interface GradeService {
	public List<GradeEntity> getGradesSubject(Integer studentId, Integer subjectd);
	public List<Integer> gradeValuesOneSubject(Integer studentId, Integer subjectId);
//	public double calculateAverage(List <Integer> gradeValues);
	
	public Double closeGrade(Double average);
	double calculateAverage(Integer subjectId, Integer studentId);
}
