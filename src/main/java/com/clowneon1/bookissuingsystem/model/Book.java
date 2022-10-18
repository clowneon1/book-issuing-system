package com.clowneon1.bookissuingsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "book_title")
    private String title;

    @Column(name = "book_description")
    private String description;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "issued_on")
    private LocalDate issueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action =  OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    @OnDelete(action =  OnDeleteAction.CASCADE)
    @JsonIgnore
    private Section section;

    //many to many
    @ManyToMany
    @JoinTable(name = "book_category",
    joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private Set<Category> categories = new HashSet<>();

}
