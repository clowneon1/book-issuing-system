package com.clowneon1.bookissuingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSection {
    private Long bookId;
    private Long userId;
    private Long sectionId;
    private Date issueDate;
}
