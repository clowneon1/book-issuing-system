package com.clowneon1.bookissuingsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSection {
    private Long bookId;
    private Long userId;
    private Long sectionId;
}
