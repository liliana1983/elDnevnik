package com.iktpreobuka.elektronskidnevnik.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskidnevnik.entities.ClassesEntity;
import com.iktpreobuka.elektronskidnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronskidnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskidnevnik.entities.SubjectEntity;
import com.iktpreobuka.elektronskidnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskidnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.GradeDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.ClassesRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.GradeRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.SubjectRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronskidnevnik.services.EmailService;
import com.iktpreobuka.elektronskidnevnik.services.UserService;
import com.iktpreobuka.elektronskidnevnik.util.RestError;

@RestController
@RequestMapping(path = "/grades")
public class GradeController {
	@Autowired
	public JavaMailSender emailSender;
	@Autowired
	EmailService emailService;
	@Autowired
	ClassesRepository classesRepository;
	@Autowired
	TeacherRepository teacherRepository;
	@Autowired
	SubjectRepository subjectRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	GradeRepository gradeRepository;

	@Autowired
	UserService userService;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Secured({ "ROLE_TEACHER", "ROLE_ADMIN", "ROLE_HEADMASTER" })
	@PostMapping(value = "/giveGradeToStudent")
	public ResponseEntity<?> grading(@RequestParam Integer classId, @RequestParam Integer subjectId,
			 @RequestParam Integer studentId, @RequestBody GradeDTO newGrade) throws Exception {
		UserEntity user= userService.userLoggedIn();
		Integer teacherId=user.getId();
		if (classesRepository.existsById(classId)) {
			ClassesEntity classes = classesRepository.findById(classId).get();
			if (subjectRepository.existsById(subjectId)
					&& classes.getListOfSubjects().contains(subjectRepository.findById(subjectId).get())) {
				SubjectEntity subject = subjectRepository.findById(subjectId).get();
				if (teacherRepository.existsById(teacherId)
						&& classes.getTeacher().contains(teacherRepository.findById(teacherId).get())
						&& subject.getTeacher().contains(teacherRepository.findById(teacherId).get())) {
					TeacherEntity teacher = teacherRepository.findById(teacherId).get();
					if (studentRepository.existsById(studentId)
							&& classes.getStudents().contains(studentRepository.findById(studentId).get())) {
						StudentEntity student = studentRepository.findById(studentId).get();
						GradeEntity grade = new GradeEntity();
						grade.setGradeValue(newGrade.getGradeValue());
						grade.setDateWhenGiven(newGrade.getDateWhenGiven());
						grade.setGradeType(newGrade.getGradeType());
						grade.setSubject(subject);
						grade.setStudent(student);
						gradeRepository.save(grade);
						logger.info("student graded");
						SimpleMailMessage message = new SimpleMailMessage();
						message.setTo(student.getGuardian().getEmail());
						message.setSubject("your child just got a new grade");
						message.setText(student.getName()+ " " +student.getLastName() +" "+" just received new grade "+ grade.getGradeValue() + " from subject " + subject.getName()+" given by teacher "+ teacher.getLastName()+ " "+ teacher.getName());
						emailSender.send(message);
						logger.info("email sent to Guardian");
						return new ResponseEntity<>(message, HttpStatus.CREATED);

					}return new ResponseEntity<RestError>(new RestError(7,"student isnt enrolled in this class"), HttpStatus.BAD_REQUEST);

				}return new ResponseEntity<RestError>(new RestError(8,"teacher isnt teaching in this class or he isnt teaching the given subject in that class"),
						HttpStatus.BAD_REQUEST);

			}return new ResponseEntity<RestError>(new RestError(9,"class doesnt exist or the given subject isnt found in the perticular class"),HttpStatus.BAD_REQUEST);
			
		}return new ResponseEntity<RestError>(new RestError(10, "class not found"),HttpStatus.NOT_FOUND);
	}
}
