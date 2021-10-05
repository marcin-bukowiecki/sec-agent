package io.sec.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcin Bukowiecki
 */
public class CustomPatterns {

    private List<CustomPattern> customPatternList = new ArrayList<>();

    public List<CustomPattern> getCustomPatternList() {
        return customPatternList;
    }

    public void setCustomPatternList(List<CustomPattern> customPatternList) {
        this.customPatternList = customPatternList;
    }
}
