package com.webforum.repositories;

import com.webforum.bo.entity.BlogPost;
import com.webforum.bo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * @author Sebastian Löfstrand (selo@kth.se), Jonas Lundvall (jonlundv@kth.se)
 *
 * Blog post repository auto implemented by Spring into a Bean called blogPostRepository
 * CRUD refers Create, Read, Update, Delete
 */
public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {
    Collection<BlogPost> findByOwner(User owner);
    Collection<BlogPost> findAllByOwner(User owner);

    @Query("SELECT bp FROM BlogPost bp WHERE bp.owner.id = ?1")
    Collection<BlogPost> findByOwnerId(Long id);
}