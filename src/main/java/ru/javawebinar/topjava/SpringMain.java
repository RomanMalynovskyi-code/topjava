package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDateTime;
import java.time.Month;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
           /* System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));*/
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll().forEach(System.out::println);
           /* mealRestController.getAllBetweenDates(LocalDateTime.of(2020,Month.JANUARY,30,0,0),
                    LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0)).forEach(System.out::println);*/
        }
    }
}
