package innchkeckerpack.controller;

import innchkeckerpack.domain.Suggestions;
import innchkeckerpack.service.OrganizationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class InnCheckController {

    @GetMapping("/checking")
    public String checking(@RequestParam(name="inn", required=false, defaultValue="Результаты отсутствуют") String inn, Map<String, Object> model) {
        OrganizationService service = new OrganizationService();
        String upDate = service.gettingUpDate();
        model.put("upd", upDate);
        return "checking";
    }

    @GetMapping("/")
    public String main(Map<String, Object> model)
    {
        OrganizationService service = new OrganizationService();
        String upDate = service.gettingUpDate();
        model.put("upd", upDate);

        return "checking";
    }

    @PostMapping
    public String find(@RequestParam String inn, Map<String, Object> model)
    {
        Suggestions suggestions = new Suggestions();
        OrganizationService service = new OrganizationService();
        suggestions = service.jsonParsing(service.sendingPost(inn.trim().replaceAll(" ", "").replaceAll("_", "").replaceAll("[a-zA-Zа-яА-Я]*", "")));
        model.put("organizations", suggestions.getSuggestions());
        String upDate = service.gettingUpDate();
        model.put("upd", upDate);
        //model.put("upd", service.gettingUpDate());

        return "checking";
    }
}
