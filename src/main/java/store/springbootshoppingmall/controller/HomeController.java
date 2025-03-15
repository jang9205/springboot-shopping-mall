package store.springbootshoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import store.springbootshoppingmall.domain.Item;
import store.springbootshoppingmall.service.item.ItemService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService;

    @GetMapping("/")
    public String home(Model model) {
        List<Item> latestItems = itemService.findLatestItems();
        model.addAttribute("latestItems", latestItems);
        return "index";
    }
}
