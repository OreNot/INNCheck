package innchkeckerpack.repos;

import innchkeckerpack.domain.Suggestions;

public interface OrganizationRepo {

     String sendingPost(String inn);
     Suggestions jsonParsing(String postResultString);
     String gettingUpDate();
}
