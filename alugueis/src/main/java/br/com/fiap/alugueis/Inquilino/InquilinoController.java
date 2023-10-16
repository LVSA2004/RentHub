package br.com.fiap.alugueis.Inquilino;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Controller
@RequestMapping("/inquilino")
public class InquilinoController {
    @Autowired
    InquilinoService service;
    
    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("alugueis", service.findAll());
        return "inquilino/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        if (service.delete(id)){
            redirect.addFlashAttribute("success", "Inquilino deletado com sucesso");
        }else{
            redirect.addFlashAttribute("error", "Inquilino não encontrado");
        }
        return "redirect:/inquilino";
    }

    @GetMapping("new")
    public String form(Inquilino inquilino){
        return "inquilino/form";
    }

    @PostMapping
    public String create(@Valid Inquilino inquilino, BindingResult binding, RedirectAttributes redirect){
        if (binding.hasErrors()) return "/inquilino/form";

        service.save(inquilino);
        redirect.addFlashAttribute("success", "Inquilino cadastrado com sucesso");
        return "redirect:/inquilino";
    }
    
}
