package com.example.servingwebcontent;

import com.example.servingwebcontent.models.Booking;
import com.example.servingwebcontent.repo.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class GreetingController {


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        Iterable<Booking> bookings = bookingRepository.findAll();
        model.addAttribute("books", bookings);
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Страница про нас");
        return "about";
    }

    @GetMapping("/borrow")
    public String borrow(Model model) {
        model.addAttribute("title", "Выбор времени");
        return "borrow";
    }

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/borrow/add")
    public String borrowAdd(@RequestParam String startTime, @RequestParam String endTime, @RequestParam String comment, Model model) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTimeStart = LocalDateTime.parse(startTime, formatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(endTime, formatter);
        Booking booking = Booking.builder()
                .startTime(dateTimeStart)
                .endTime(dateTimeEnd)
                .comment(comment)
                .build();
        bookingRepository.save(booking);
        return "redirect:/";
    }
}