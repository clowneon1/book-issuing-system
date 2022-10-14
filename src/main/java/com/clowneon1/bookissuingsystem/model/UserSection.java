package com.clowneon1.bookissuingsystem.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSection {
    private Long bookId;
    private Long userId;
    private Long sectionId;
    private Date issueDate;
}
