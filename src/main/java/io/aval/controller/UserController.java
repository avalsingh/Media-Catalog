package io.aval.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.aval.exception.UserAlreadyExistsException;
import io.aval.exception.UserNotFoundException;
import io.aval.model.Media;
import io.aval.model.User;
import io.aval.service.IMediaService;
import io.aval.service.IUserService;

@RestController
@RequestMapping(value="/users")
public class UserController {

	@Autowired
	private IUserService service;
	
	@Autowired
	private IMediaService mService;
	
	@Autowired
	ServletContext servletContext;
	
	@RequestMapping(method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<User> findAll () {
		return service.findAll();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User findOne(@PathVariable("id") String userId) throws UserNotFoundException {
		return service.findOne(userId);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User create (@RequestBody User user) throws UserAlreadyExistsException {
		return service.create(user);
	}
	
	@RequestMapping(value = "/signup.htm", method = RequestMethod.GET)
	public ModelAndView signuphome(Locale locale, Model model) throws ParseException {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		ModelAndView mav = new ModelAndView("signup");
		
		return mav;
	}
	
	
	@RequestMapping(value="/signedup.htm", method=RequestMethod.POST)
	public ModelAndView signup (HttpServletRequest request, HttpServletResponse response) throws UserAlreadyExistsException {
		User user = new User();
		user.setFirstName(request.getParameter("input_firstName"));
		user.setLastName(request.getParameter("input_lastName"));
		user.setEmail(request.getParameter("input_email"));
		user.setPassword(request.getParameter("input_password"));
		
		service.create(user);
		ModelAndView mav = new ModelAndView("loginform");
		
		return mav;
	}
	
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public User update (@PathVariable("id") String id, @RequestBody User user) throws UserNotFoundException {
		return service.update(id, user);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void delete (@PathVariable("id") String id) throws UserNotFoundException {
		service.delete(id);
	}
	
	@RequestMapping(value="/loginMe.htm",method = RequestMethod.POST)
	public ModelAndView authenticate(Map<String, List<Media>> model, HttpServletRequest request, HttpServletResponse response) throws IOException{
		ModelAndView mav = new ModelAndView();
		String email = request.getParameter("input_email");
		String password = request.getParameter("input_password");
		
		User loggedOnUser = service.authenticate(email, password);
		List<Media> mediaFiles;
		
		
		HttpSession session = request.getSession();
		if(null != loggedOnUser){
			session.setAttribute("user", loggedOnUser);
			
			if(servletContext!=null){
				ArrayList<String> onlineUsers = (ArrayList<String>) servletContext.getAttribute("loggedInUsersList");
				if(onlineUsers==null)
					onlineUsers = new ArrayList<String>();
				onlineUsers.add(loggedOnUser.getEmail());
				servletContext.setAttribute("loggedInUsersList", onlineUsers);
			}
			mediaFiles = mService.findMyMedia(loggedOnUser);
			model.put("mediaList", mediaFiles);
			mav.setViewName("index");
			return mav;
		}
		else{
			session.setAttribute("loginError", "Invalid Credentails");
			mav.setViewName("loginform");
			return mav;
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale, Model model) throws ParseException {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		ModelAndView mav = new ModelAndView("loginform");
		
		return mav;
	}
	
	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	public ModelAndView logoutAction(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		User loggedInUser = (User) session.getAttribute("user");
		ArrayList<String> onlineUsers = (ArrayList<String>) servletContext.getAttribute("loggedInUsersList");
		onlineUsers.remove(loggedInUser.getEmail());
		session.invalidate();
		ModelAndView mav = new ModelAndView("loginform");
		return mav;	}
	
}
