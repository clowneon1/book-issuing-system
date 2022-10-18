package com.clowneon1.bookissuingsystem.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSection {
    private Long bookId;
    private Long userId;
    private Long sectionId;
    private LocalDate issueDate;
}
