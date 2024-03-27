package br.com.satrtup.Startup.service.book;

import br.com.satrtup.Startup.controller.book.BookController;
import br.com.satrtup.Startup.domain.Book;
import br.com.satrtup.Startup.domain.vo.BookVO;
import br.com.satrtup.Startup.exception.RequiredObjectIsNullException;
import br.com.satrtup.Startup.exception.books.BooksExceptionNotFound;
import br.com.satrtup.Startup.mapper.DozerMapper;
import br.com.satrtup.Startup.repository.book.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BooksService {

    private Logger logger = Logger.getLogger(BooksService.class.getName());

    @Autowired
    BooksRepository repository;

    @Autowired
    PagedResourcesAssembler<BookVO> assembler;

    public BookVO create(BookVO book) {

        if (book == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one book!");
        var entity = DozerMapper.parseObject(book, Book.class);
        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {

        var booksPage = repository.findAll(pageable);

        var booksVOs = booksPage.map(p -> DozerMapper.parseObject(p, BookVO.class));
        booksVOs.map(p -> p.add(
                linkTo(methodOn(BookController.class)
                        .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(
                methodOn(BookController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(booksVOs, link);
    }

    public BookVO findById(Long id) {

        var entity = repository.findById(id)
                .orElseThrow(() -> new BooksExceptionNotFound("No records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) {

        if (book == null) throw new RequiredObjectIsNullException();

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new BooksExceptionNotFound("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {

        var entity = repository.findById(id)
                .orElseThrow(() -> new BooksExceptionNotFound("No records found for this ID!"));
        repository.delete(entity);
    }
}
