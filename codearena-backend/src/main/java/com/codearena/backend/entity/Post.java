package com.codearena.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    @Column(columnDefinition="TEXT")
    private String content;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
    }

    public Long getId(){ return id; }
    public Long getUserId(){ return userId; }
    public void setUserId(Long userId){ this.userId=userId; }

    public String getTitle(){ return title; }
    public void setTitle(String title){ this.title=title; }

    public String getContent(){ return content; }
    public void setContent(String content){ this.content=content; }

    public LocalDateTime getCreatedAt(){ return createdAt; }
}