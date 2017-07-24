package ru.jihor.hiatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.jihor.hiatus.annotations.UnitOfWork;

import java.util.concurrent.TimeUnit;

/**
 * @author jihor (jihor@ya.ru)
 *         Created on 2017-07-20
 */
@RestController
public class DemoController {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @UnitOfWork
    public String doWork() throws InterruptedException {
        TimeUnit.SECONDS.sleep(30);
        return "Done";
    }
}
