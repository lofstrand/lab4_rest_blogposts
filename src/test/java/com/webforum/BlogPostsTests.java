package com.webforum;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webforum.bo.dto.BlogPostDto;
import com.webforum.bo.entity.BlogPost;
import com.webforum.bo.entity.User;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.*;

public class BlogPostsTests {

    public static final String REST_USERS_URI = "http://192.168.99.100:8080/api/users";
    public static final String REST_BLOGPOSTS_URI = "http://192.168.99.100:8081/api/blogposts";

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void saveBlogPost() {

        BlogPostDto blogPost = new BlogPostDto(2L, "New content...");

        try {
            URI uri = restTemplate.postForLocation(REST_BLOGPOSTS_URI + "/", blogPost, BlogPostDto.class);
            System.out.println("Location : " + uri.toASCIIString());
        } catch(
            HttpClientErrorException.Conflict e) {
            try {
                CustomErrorType cet = objectMapper.readValue(e.getResponseBodyAsString(), CustomErrorType.class);
                System.out.println(cet.getErrorMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
/*
    @Test
    public void saveGraphBlogPost() {
        BlogPostDto blogPost = new BlogPostDto();
        blogPost.setOwner(2L);
        blogPost.setContent("");
        blogPost.setGraph_url("http://localhost:8090/index.html#/graph/5c0f9b7975204a0500ee33fd");

        try {
            URI uri = restTemplate.postForLocation(REST_BLOGPOSTS_URI + "/", blogPost, BlogPostDto.class);
            System.out.println("Location : " + uri.toASCIIString());
        } catch(
                HttpClientErrorException.Conflict e) {
            try {
                CustomErrorType cet = objectMapper.readValue(e.getResponseBodyAsString(), CustomErrorType.class);
                System.out.println(cet.getErrorMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
*/
    @Test
    @SuppressWarnings("unchecked")
    public void getBlogPostsForUser() {
        ResponseEntity<Collection<BlogPost>> response = restTemplate.exchange(REST_BLOGPOSTS_URI + "/user/2", HttpMethod.GET, null, new ParameterizedTypeReference<Collection<BlogPost>>() { });
        Collection<BlogPost> blogPosts = (Collection<BlogPost>) response.getBody();

        if(blogPosts.isEmpty()) {
            System.out.println("No blogposts");
        } else {
          for(BlogPost bp : blogPosts)
              System.out.println(bp.getOwner() + ", " + bp.getContent() + ", " + bp.getGraph_url() + " ," + bp.getCreationDate());
        }
    }
}
