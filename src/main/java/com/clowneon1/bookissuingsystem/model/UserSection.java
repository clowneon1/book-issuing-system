package com.clowneon1.bookissuingsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserSection {
    private Long bookId;
    private Long userId;
    private Long sectionId;
    private Date issueDate;
}
