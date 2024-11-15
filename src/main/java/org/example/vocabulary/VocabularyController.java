package org.example.vocabulary;

import org.example.config.SessionUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class VocabularyController {
    private final SessionUser sessionUser;
    private final VocabularyDao vocabularyDao;

    public VocabularyController(SessionUser sessionUser, VocabularyDao vocabularyDao) {
        this.sessionUser = sessionUser;
        this.vocabularyDao = vocabularyDao;
    }

    @GetMapping("/home")
    public String homePage(Model model, @RequestParam(required = false, defaultValue = "4", name = "limit") int limit){
        List<Vocabulary> vocabularies = vocabularyDao.findByUserID(sessionUser.getId(), limit);
        model.addAttribute("vocabularies", vocabularies);
        model.addAttribute("lim", limit);
        return "home";
    }

    @GetMapping("/vocabulary/add")
    public String createVocabularyPage(){
        return "vocabulary_create";
    }

    @GetMapping("/vocabulary/all")
    public String allVocabularyPage(Model model){
        List<Vocabulary> vocabularyList = vocabularyDao.findByUserID(sessionUser.getId());
        Map<LocalDate, List<Vocabulary>> vocabularies = vocabularyList.stream().collect(Collectors.groupingBy(vocabulary -> vocabulary.getCreatedDate()));

        model.addAttribute("vocabularies", vocabularies);
        return "vocabulary_list";
    }

    @PostMapping("/vocabulary/add")
    public String createVocabulary(@ModelAttribute VocabularyCreateDTO dto){
        vocabularyDao.save(dto, sessionUser.getId());
        return "redirect:/home";
    }
}
