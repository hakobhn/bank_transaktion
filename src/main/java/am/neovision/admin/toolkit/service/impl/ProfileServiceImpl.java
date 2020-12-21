package am.neovision.admin.toolkit.service.impl;

import am.neovision.admin.toolkit.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hakob.hakobyan created on 11/20/2020
 */
@Slf4j
@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private List<String> profileLocations;

    @Autowired
    private List<String> profilePositions;

    @Autowired
    private List<String> profileProfessionalSkills;

    @Autowired
    private List<String> profileSoftSkills;

    @Autowired
    private List<String> profileLanguages;

    @Override
    public List<String> locationList() {
        return profileLocations.stream().sorted(String::compareTo).collect(Collectors.toList());
    }

    @Override
    public List<String> positionList() {
        return profilePositions.stream().sorted(String::compareTo).collect(Collectors.toList());
    }

    @Override
    public List<String> professionalSkillList() {
        return profileProfessionalSkills.stream().sorted(String::compareTo).collect(Collectors.toList());
    }

    @Override
    public List<String> softSkillList() {
        return profileSoftSkills.stream().sorted(String::compareTo).collect(Collectors.toList());
    }

    @Override
    public List<String> languagesList() {
        return profileLanguages.stream().sorted(String::compareTo).collect(Collectors.toList());
    }
}
