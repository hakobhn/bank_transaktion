package am.neovision.admin.toolkit.service;

import java.util.List;

public interface ProfileService {

    List<String> locationList();

    List<String> positionList();

    List<String> professionalSkillList();

    List<String> softSkillList();

    List<String> languagesList();
}
