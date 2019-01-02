package com.webforum.bo.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URI;
import java.util.Date;

/**
 * @author Sebastian Löfstrand (selo@kth.se), Jonas Lundvall (jonlundv@kth.se)
 *
 * Represents a Blog post for Users
 */
@Entity
@Table(name = "blog_post")
public class BlogPost implements Serializable {
    // Constants ----------------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="blog_post_id", updatable=false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blog_post_owner")
    private User owner;

    @Column(name="blog_post_content", nullable = false)
    private String content;

    @Column(name="blog_post_graph")
    private String graph_url;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;

    // Getters/setters ----------------------------------------------------------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getGraph_url() { return graph_url; }
    public void setGraph_url(String graph_url) { this.graph_url = graph_url; }

    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    public Date getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(Date updatedDate) { this.updatedDate = updatedDate; }

    // Constructors -------------------------------------------------------------------------------
    public BlogPost() {
    }

    public BlogPost(User owner, String content) {
        this.owner = owner;
        this.content = content;
    }

    public BlogPost(User owner, String content, String graph_url) {
        this.owner = owner;
        this.content = content;
        this.graph_url = graph_url;
    }

    // Actions ------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "BlogPost{" +
                "id=" + id +
                ", owner=" + owner.getUsername() +
                ", content='" + content + '\'' +
                '}';
    }
}
