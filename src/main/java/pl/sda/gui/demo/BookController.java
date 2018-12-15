package pl.sda.gui.demo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
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

        model.addAttribute("booksDtos", bookDtos);
        return "books";
    }

    @RequestMapping("/addBook")
    public String addBookForm(Model model) {
        model.addAttribute("book", new BookDto());
        return "addBook";
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public String addBook(@ModelAttribute BookDto bookDto, Model model) {
        Book book = new Book(null, bookDto.getTitle(), bookDto.getAuthor(), bookDto.getIsbn());

        bookRepository.save(book);

        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = books.stream()
                .map(x -> new BookDto(x.getTitle(), x.getAuthor(), x.getIsbn()))
                .collect(Collectors.toList());

        model.addAttribute("booksDtos", bookDtos);

        return "/books";
    }

}
