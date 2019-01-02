package com.webforum.controllers;

import com.webforum.CustomErrorType;
import com.webforum.bo.dto.BlogPostDto;
import com.webforum.bo.entity.BlogPost;
import com.webforum.bo.entity.User;
import com.webforum.repositories.BlogPostRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Sebastian Löfstrand (selo@kth.se), Jonas Lundvall (jonlundv@kth.se)
 *
 * Blog post controller for REST API requests
 */
@Controller    // This means that this class is a Controller
@RequestMapping(path="/api/blogposts") // This means URL's start with /demo (after Application path)
public class BlogPostController {
    // Constants ----------------------------------------------------------------------------------
    public static final Logger logger = LoggerFactory.getLogger(BlogPostController.class);

    // Properties ---------------------------------------------------------------------------------
    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private BlogPostRepository blogPostRepository;


    // Actions ------------------------------------------------------------------------------------

    /**
     * Find single BlogPost by identification
     *
     * @param id the identification
     * @return response entity
     */
    @GetMapping(path="/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        logger.info("Fetching BlogPost with id {}", id);
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);

        if(blogPost.get() == null) {
            logger.error("BlogPost with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Blogpost with id "), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(blogPost.get(), HttpStatus.OK);
    }

    /**
     *  Retrieve all blog posts by user id
     *
     * @param id the user id
     * @return the response entity
     */
    @GetMapping(path="/user/{id}")
    @ResponseBody
    public ResponseEntity<?> findAllByUserId(@PathVariable Long id) {
        User user = new User();
        user.setId(Long.valueOf(id));
        Collection<BlogPost> blogPosts = (Collection<BlogPost>) blogPostRepository.findByOwner(user);
        if (blogPosts.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Collection<BlogPost>>(blogPosts, HttpStatus.OK);
    }


    /**
     * Create a blog post
     *
     * @param blogPostDto the blog post to create in database
     * @param ucBuilder
     * @return the response entity
     */
    @PostMapping(path="/")
    public ResponseEntity<?> create(@RequestBody BlogPostDto blogPostDto, UriComponentsBuilder ucBuilder) {
        BlogPost blogPost = convertToEntity(blogPostDto);

        if(blogPost == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        logger.info("Creating BlogPost : {}", blogPost);

        blogPostRepository.save(blogPost);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/blogposts/{id}").buildAndExpand(blogPost.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    /**
     * Converter from blog post to blog post dto
     *
     * @param blogPost the blog post
     * @return the blog post dto
     */
    private BlogPostDto convertToDto(BlogPost blogPost) {
        BlogPostDto postDto = modelMapper.map(blogPost, BlogPostDto.class);
        postDto.setId(blogPost.getId());

        return postDto;
    }

    /**
     * Converter from blog post dto to blog post
     *
     * @param blogPostDto the blog post dto
     * @return the blog post
     * @throws ParseException
     */
    private BlogPost convertToEntity(BlogPostDto blogPostDto) throws ParseException {
        BlogPost blogPost = modelMapper.map(blogPostDto, BlogPost.class);
        blogPost.setOwner( new User( blogPostDto.getOwner() ) );
        return blogPost;
    }

}
