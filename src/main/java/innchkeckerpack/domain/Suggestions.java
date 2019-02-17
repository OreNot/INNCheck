package innchkeckerpack.domain;

import java.util.ArrayList;
import java.util.List;

public class Suggestions {
    List<Organization> suggestions = new ArrayList<>();

    public List<Organization> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Organization> suggestions) {
        this.suggestions = suggestions;
    }
}
