package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

import java.util.List;

@Component
public class EmailValidator implements Validator {

    final
    UserService userService;

    public EmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserTo userTo = (UserTo) target;
        List<User> userList = userService.getAll();
        for (User us : userList) {
            if (userTo.isNew() && us.getEmail().equals(userTo.getEmail())) {
                errors.rejectValue("email", "user.emailAlreadyExists");
            } else if (us.getEmail().equals(userTo.getEmail())) {
                User user = userService.get(userTo.getId());
                userList.remove(user);
                for (User u: userList) {
                    if(u.getEmail().equals(userTo.getEmail())){
                        errors.rejectValue("email", "user.emailAlreadyExists");
                    }
                }
            }
        }
    }
}
