package info.spring.maven.Controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import info.spring.maven.Model.User;
import info.spring.maven.Service.CaptchasDotNet;
import info.spring.maven.Service.UserService;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	
	@Autowired
	private UserService userService;
	
	
	// User list method-home.jpg
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getUserLists(Model model){
		
		
		 try {
			
			 model.addAttribute("userlist", userService.listUser());
		model.addAttribute("asd",userService.listUser().size());
		 }catch (Exception ex) {
		      logger.error("--- error: " + ex);
		 }return "home";
	}	
	//Users add method and use captcha
	
	@RequestMapping(value="/", method=RequestMethod.POST)		   
    public String addUser(Model model,HttpServletRequest request){    
		
		
		try {
		
			
			CaptchasDotNet captchas = new CaptchasDotNet(
					  request.getSession(true),     // Ensure session
					  "demo",                       // client
					  "secret"                      // secret
					  );
					// Read the form values
					String message  = request.getParameter("message");
					String password = request.getParameter("captcha");
					
					// Check captcha
					char captchasCheck=captchas.check(password);
					
					if(captchasCheck!='s' && captchasCheck!='m' && captchasCheck!='w' )
					{
						User user = new User(request.getParameter("name"), request.getParameter("surname"), request.getParameter("telephone"));	
						User addedUser = userService.addUser(user);
						 
						
					}
					model.addAttribute("userlist", userService.listUser());
					model.addAttribute("asd",userService.listUser().size());
		
		}catch (Exception ex) {
			 logger.error("--- error: " + ex);
		 }
		return "home";
	}
	//  User delete method
	
	
	/** 
	 * 
	 * 
	 * 				Alttaki metod Ajax mantýðýna uygun bir þekilde yazýlmýþtýr.
	 * 
	 *    Butona týklanýlýnca gerçekleþen ajax isteði   
	 * 
	 * görevini yerine getirmektedir fakat  farklý bir sayfaya yönlendirmektedir.
	 * 
	 *   Anasayfaya tekrar geri gelmek gerekiyor.Bunu engelleyemedim.
	 * 
	 * Bu yüzden addUser metodunu yukarýda görüldüðü þekilde (Ajax mantýðý olmadan) yazdým.
	 * 
	 * View de de form yorum satýrý içindedir.
	 * 
	 * */
	
	
	
	
	
	/** *
	 * 
	 * @RequestMapping(value="/add", method=RequestMethod.GET)		   
    public @ResponseBody String addUser(HttpServletRequest request){    
		System.out.println("adddddddddddddddddd");
		String html=null;
		try {
			System.out.println("asd");
			
			CaptchasDotNet captchas = new CaptchasDotNet(
					  request.getSession(true),     // Ensure session
					  "demo",                       // client
					  "secret"                      // secret
					  );
					// Read the form values
					String message  = request.getParameter("message");
					String password = request.getParameter("captcha");
					System.out.println(password);
					// Check captcha
					String body;
					switch (captchas.check(password)) {
					  case 's':
					    body = "Session seems to be timed out or broken. ";
					    body += "Please try again or report error to administrator.";
					    break;
					  case 'm':
					    body = "Every CAPTCHA can only be used once. ";
					    body += "The current CAPTCHA has already been used. ";
					    body += "Please use back button and reload";
					    break;
					  case 'w':
					    body = "You entered the wrong password. ";
					    body += "Please use back button and try again. ";
					    break;
					  default:
					    body = "Your message was verified to be entered by a human and is \"" + message + "\"";
					    User user = new User(request.getParameter("name"), request.getParameter("surname"), request.getParameter("telephone"));	
						
						User addedUser = userService.addUser(user);
					html = "<tr id = userTable" + addedUser.getId() + ">" +			
							  "<td>" + addedUser.getName()+ "</td>" + 					
							  "<td>" + addedUser.getSurname()+ "</td>" +
							  "<td>" + addedUser.getPhone()+ "</td>" +
							  "<td>" +
							  	"<input type=\"button\" class=\"button\" name=\"deleteButton\" value=\"Delete\" onclick=\"deleteConfirm('" + addedUser.getId() + "');\"/>" +
							  	"<input type=\"button\" class=\"button\" name=\"updateButton\" value=\"Update\" onclick=\"updateConfirm('" + addedUser.getId()+ "');\"/>" +
							  "</td>" +				
						  "</tr>";
					    break;
					}
					System.out.println(body+html);

		
		}catch (Exception ex) {
			 logger.error("--- error: " + ex);
		 }
		return html;
	}
	 */
	
	
	// User delete method
	@RequestMapping(value = "/delete", method = RequestMethod.GET) 
	public @ResponseBody void deleteUser(HttpServletRequest request){
		//User user = userService.findUser(request.getParameter("id"));
		
		try {
			
			
			userService.deleteUser(request.getParameter("id"));
		}catch(Exception ex){
			logger.error("error:"+ex);
		}
	}	
	
	/**
	    * Updates the user with given identity
	    * @param request The request coming from client
	    * @return A string includes html data 
	*/
	//User Update method
	@RequestMapping(value = "/update", method = RequestMethod.GET) 
	public @ResponseBody String updateUser(HttpServletRequest request){
		
		String html=null;
		try{
		String Id = request.getParameter("id");
		String Name = request.getParameter("name");
		String Surname = request.getParameter("surname");
		String Phone = request.getParameter("telephone");
		userService.updateUser(Id, Name, Surname, Phone);
		
		html = "<tr id = userTable" + Id + ">" +			
						  "<td>" + Name+ "</td>" + 					
						  "<td>" + Surname + "</td>" +
						  "<td>" + Phone + "</td>" +
						  "<td>" +
						  	"<input type=\"button\" class=\"button\" name=\"deleteButton\" value=\"Delete\" onclick=\"deleteConfirm('" + Id + "');\"/>" +
						  	"<input type=\"button\" class=\"button\" name=\"updateButton\" value=\"Update\" onclick=\"updateConfirm('" + Id + "');\"/>" +
						  "</td>" +				
					  "</tr>";
		 
	
		}catch(Exception ex){
			System.out.println("hata");
			logger.error("error:"+ex);
		}
		return html;
	}	
}
