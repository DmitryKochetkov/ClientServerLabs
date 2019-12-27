package mirea.dimedrol.lab5;

import mirea.dimedrol.lab5.domain.User;
import mirea.dimedrol.lab5.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<User> users = userRepo.findAll();
        model.put("users", users);
        return "main";
    }

    @PostMapping
    public String reg(@RequestParam String fullname, @RequestParam String login, @RequestParam String passwd, Map<String, Object> model) {
        User user = new User(fullname, login, passwd);
        userRepo.save(user);
        Iterable<User> users = userRepo.findAll();
        model.put("users", users);
        return "main";
    }

    @PostMapping("/del")
    public String del(@RequestParam Integer delete_id, Map<String, Object> model) {
        userRepo.deleteById(delete_id);
        Iterable<User> users = userRepo.findAll();
        model.put("users", users);
        return "main";
    }
}
