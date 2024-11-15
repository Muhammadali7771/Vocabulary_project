package org.example.vocabulary;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Vocabulary {
   private Long id;
   private String word;
   private List<String> translations;
   private List<String> synonyms;
   private List<String> examples;
   private Long userID;
   private LocalDate createdDate;
}
