package ucm.form;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {
	@GetMapping("/")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Usuario());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@Valid @ModelAttribute Usuario greeting, BindingResult errores) {
        return "result";
    }
}