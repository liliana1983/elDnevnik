package com.iktpreobuka.elektronskidnevnik.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskidnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskidnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskidnevnik.entities.dto.UserDTO;
import com.iktpreobuka.elektronskidnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskidnevnik.repositories.UserRepository;
import com.iktpreobuka.elektronskidnevnik.services.UserService;
import com.iktpreobuka.elektronskidnevnik.util.Encryption;
import com.iktpreobuka.elektronskidnevnik.util.RestError;
import com.iktpreobuka.elektronskidnevnik.util.UserCustomValidator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(path = "/")
public class UserController {
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private UserCustomValidator userCustomValidator;
	@Autowired
	private Validator[] usernameValidator;

	@InitBinder("user")
	protected void initUserBinder(WebDataBinder binder) {
		binder.addValidators(userCustomValidator);
	}

	@InitBinder("username")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(usernameValidator);
	}

	@Autowired
	private UserRepository userRepository;
	
	@Value("${spring.security.secret-key}")
	private String secretKey;
	
	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;
	
	@Autowired
	UserService userService;

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	private String getJWTToken(UserEntity userEntity) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(userEntity.getRole().getName());
		String token = Jwts.builder().setId("softtekJWT").setSubject(userEntity.getUsername())
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + this.tokenDuration))
				.signWith(SignatureAlgorithm.HS512, this.secretKey.getBytes()).compact();
		logger.info("token created");
		return "Bearer " + token;
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String pwd) {
		UserEntity userEntity = userRepository.findByUsername(username);
		if (userEntity != null && Encryption.validatePassword(pwd, userEntity.getPassword())) {
			String token = getJWTToken(userEntity);
			UserDTO user = new UserDTO();
			user.setUser(username);
			user.setToken(token);
			logger.info("User logged in");
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public ResponseEntity<?> listUsers() {
		return new ResponseEntity<List<UserEntity>>((List<UserEntity>) userRepository.findAll(), HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
	}

	@Secured("ROLE_ADMIN")
	@PostMapping(value = "/addUser")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO newUser, @RequestParam Integer roleId,
			BindingResult result) {
		if (result.hasErrors())
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		UserEntity user = new UserEntity();
		user.setName(newUser.getName());
		user.setLastName(newUser.getLastName());
		user.setPassword(Encryption.getPassEncoded(newUser.getPassword()));
		user.setPassword(Encryption.getPassEncoded(newUser.getConfirmPassword()));
		user.setUsername(newUser.getUsername());
		RoleEntity rola = roleRepository.findById(roleId).get();
		user.setRole(rola);
		userRepository.save(user);
		logger.info(user.toString() + " : created.");
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
	@PutMapping(value="/addRole")
	public ResponseEntity<?> addRole(@Valid@RequestBody RoleEntity role, @PathVariable Integer userId){
		if(userRepository.existsById(userId)) {
			UserEntity user= userRepository.findById(userId).get();
			user.setRole(role);
			userRepository.save(user);
			logger.info("User connected with role");
			return new ResponseEntity<>(user,HttpStatus.OK);
		}return new ResponseEntity<RestError>(new RestError(2,"user doesnt exists with this ID number"), HttpStatus.NO_CONTENT);
	}
}
