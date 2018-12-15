package pl.sda.gui.demo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    @Value("${gui.message}")
    private String guiMessage;

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping("/index")
    public String getIndex(Model model) {
        model.addAttribute("guiMesssage", guiMessage);
        return "index";
    }

    @RequestMapping("/books")
    public String getBooks(Model model) {
        List<Book> books = bookRepository.findAll();

        List<BookDto> bookDtos = books.stream()
                .map(x -> new BookDto(x.getTitle(), x.getAuthor(), x.getIsbn()))
                .collect(Collectors.toList());

        model.addAttribute("books", bookDtos);
        return "books";
    }

}
