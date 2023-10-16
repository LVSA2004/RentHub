package br.com.fiap.alugueis.propriedades;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/propriedade")
public class PropriedadeController {

    @Autowired
    PropriedadeService service;
    
    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("propriedades", service.findAll());
        return "propriedade/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        if (service.delete(id)){
            redirect.addFlashAttribute("success", "Propriedade deletado com sucesso");
        }else{
            redirect.addFlashAttribute("error", "Propriedade não encontrado");
        }
        return "redirect:/propriedade";
    }

    @GetMapping("new")
    public String form(Propriedade propriedade){
        return "propriedade/form";
    }

    @PostMapping
    public String create(@Valid Propriedade propriedade, BindingResult binding, RedirectAttributes redirect){
        if (binding.hasErrors()) return "/propriedade/form";

        service.save(propriedade);
        redirect.addFlashAttribute("success", "Propriedade cadastrado com sucesso");
        return "redirect:/propriedade";
    }
    
}
