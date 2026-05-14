package com.nick.auth.controllers;

import java.io.IOException;
import java.util.List;

import com.nick.auth.entity.LollaUserEntry;
import com.nick.auth.entity.NorthCoastUserEntry;
import com.nick.auth.entity.TahoeLiveUserEntry;
import com.nick.auth.service.LollaService;
import com.nick.auth.service.NorthcoastService;
import com.nick.auth.service.TahoeLiveService;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nick.auth.entity.User;
import com.nick.auth.exception.DuplicateRegistrationException;
import com.nick.auth.repo.UserRepository;
import com.nick.auth.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
public class AppController {
	private final UserService userService;
	private final LollaService lollaService;
	private final NorthcoastService northcoastService;
	private final TahoeLiveService tahoeLiveService;


@GetMapping({"", "/index", "/home", "/login", "/"})
public String viewLoginPage() {
	return "login";
}

	@GetMapping("/lollapolloza.html")
	public String viewLollaPage(Model model) {
		model.addAttribute("entries", lollaService.findAll());
		return "lollapolloza";
	}
	@GetMapping("/tahoelive.html")
	public String viewTahoePage(Model model) {
		model.addAttribute("entries", tahoeLiveService.findAll());
		return "tahoelive";
	}
	@GetMapping({"/northcoast.html"})
	public String viewNorthcoastPage(Model model) {
		model.addAttribute("entries", northcoastService.findAll());
		return "northcoast";
	}


@GetMapping("/register")
public String showRegistrationForm(Model model) {
model.addAttribute("user", new User());





return "signup";
}

@Autowired
private UserRepository userRepo;



@GetMapping("/users")
public String listUsers(Model model) {
	List<User> listUsers  = userRepo.findAll();
	model.addAttribute("listUsers", listUsers);
	return "users";
}


	@PostMapping("/lolla/photo/upload")
	public String uploadLollaPhoto(@RequestParam @NotBlank String title,
		   @RequestParam(required=false) String description,
		   @RequestParam("file") MultipartFile file,
		   RedirectAttributes ra)
	{
		try {
			LollaUserEntry saved = lollaService.uploadPhoto(title, description,
					file);
			ra.addFlashAttribute("success", "Uploaded: " +
					saved.getTitle());
		} catch (IOException e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/lollapolloza.html";
	}

	@PostMapping("/lolla/video/upload")
	public String uploadLollaVideo(@RequestParam @NotBlank String title,
		   @RequestParam(required=false) String description,
		   @RequestParam("videoURL") String videoURL,
		   RedirectAttributes ra)
	{
		try {
			LollaUserEntry saved = lollaService.uploadVideo(title, description,
					videoURL);
			ra.addFlashAttribute("success", "Uploaded: " +
					saved.getTitle());
		} catch (IOException e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/lollapolloza.html";
	}

	@PostMapping("/northcoast/photo/upload")
	public String uploadNorthcoastPhoto(@RequestParam @NotBlank String title,
						 @RequestParam(required=false) String description,
						 @RequestParam("file") MultipartFile file,
						 RedirectAttributes ra)
	{
		try {
			NorthCoastUserEntry saved = northcoastService.uploadPhoto(title, description,
					file);
			ra.addFlashAttribute("success", "Uploaded: " +
					saved.getTitle());
		} catch (IOException e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/northcoast.html";
	}

	@PostMapping("/northcoast/video/upload")
	public String uploadNorthcoastVideo(@RequestParam @NotBlank String title,
						 @RequestParam(required=false) String description,
						 @RequestParam("videoURL") String videoURL,
						 RedirectAttributes ra)
	{
		try {
			NorthCoastUserEntry saved = northcoastService.uploadVideo(title, description,
					videoURL);
			ra.addFlashAttribute("success", "Uploaded: " +
					saved.getTitle());
		} catch (IOException e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/northcoast.html";
	}

	@PostMapping("/tahoelive/photo/upload")
	public String uploadTahoePhoto(@RequestParam @NotBlank String title,
						 @RequestParam(required=false) String description,
						 @RequestParam("file") MultipartFile file,
						 RedirectAttributes ra)
	{
		try {
			TahoeLiveUserEntry saved = tahoeLiveService.uploadPhoto(title, description,
					file);
			ra.addFlashAttribute("success", "Uploaded: " +
					saved.getTitle());
		} catch (IOException e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/tahoelive.html";
	}

	@PostMapping("/tahoelive/video/upload")
	public String uploadTahoeVideo(@RequestParam @NotBlank String title,
						 @RequestParam(required=false) String description,
						 @RequestParam("videoURL") String videoURL,
						 RedirectAttributes ra)
	{
		try {
			TahoeLiveUserEntry saved = tahoeLiveService.uploadVideo(title, description,
					videoURL);
			ra.addFlashAttribute("success", "Uploaded: " +
					saved.getTitle());
		} catch (IOException e) {
			ra.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/tahoelive.html";
	}



@PostMapping("/process_register")
public String processRegister(User user, Model model) {

	try {
		userService.registerUser(user);
	} catch(DuplicateRegistrationException ex) {
		model.addAttribute("errorMessage", ex.getMessage());
		return "signup";
	}
	
	return "register_success";
}

}
