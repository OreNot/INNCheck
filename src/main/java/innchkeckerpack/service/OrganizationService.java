package innchkeckerpack.service;

import com.google.gson.Gson;
import innchkeckerpack.domain.Organization;
import innchkeckerpack.domain.Suggestions;
import innchkeckerpack.repos.OrganizationRepo;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class OrganizationService implements OrganizationRepo {

    private final String USER_AGENT = "Mozilla/5.0";
    private final String URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/party";
    private String postResultString;

    @Override
    public String sendingPost(String inn) {


        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);

        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");
        post.setHeader("Authorization", "Token 3312ffbd375be3421d41e78a7a8e414f2bce1d6c");
        HttpResponse response = null;
        try {
            //StringEntity requestEntity = new StringEntity("{ \"query\": \"сбербанк\" }", ContentType.APPLICATION_JSON);post.setEntity(requestEntity); // По имени организации
            post.setEntity(new StringEntity("{ \"query\": \"" + inn + "\" }", ContentType.create("application/json")));
            response = client.execute(post);
            System.out.println("\nSending 'POST' request to URL : " + URL);
            System.out.println("Post parameters : " + post.getEntity());
            System.out.println("Response Code : " +
                    response.getStatusLine().getStatusCode());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), "UTF8"));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            postResultString = result.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return postResultString;
    }

    @Override
    public Suggestions jsonParsing(String postResultString) {
        Gson g = new Gson();
        Suggestions suggestions = g.fromJson(postResultString.toString(), Suggestions.class);

        for (Organization organization : suggestions.getSuggestions())
        {
            organization.setValue(organization.getValue() != null ? organization.getValue() : "Не указано");
            organization.setUnrestricted_value(organization.getUnrestricted_value() != null ? organization.getUnrestricted_value() : "Не указано");
            //organization.getData().setCapital(organization.getData().getCapital() != null ? toUTF(organization.getData().getCapital()) : "Не указано");
           // organization.getData().setFounders(organization.getData().getFounders() != null ? toUTF(organization.getData().getFounders()) : "Не указано");
            organization.getData().setInn(organization.getData().getInn() != null ? organization.getData().getInn() : "Не указано");
            organization.getData().setKpp(organization.getData().getKpp() != null ? organization.getData().getKpp() : "Не указано");
            organization.getData().setOgrn(organization.getData().getOgrn() != null ? organization.getData().getOgrn() : "Не указано");
            organization.getData().setOkpo(organization.getData().getOkpo() != null ? organization.getData().getOkpo() : "Не указано");
            organization.getData().setOkved(organization.getData().getOkved() != null ? organization.getData().getOkved() : "Не указано");
           // organization.getData().setManagers(organization.getData().getManagers() != null ? toUTF(organization.getData().getManagers()) : "Не указано");
            //organization.getData().setPhones(organization.getData().getPhones() != null ? toUTF(organization.getData().getPhones()) : "Не указано");
            //organization.getData().setEmails(organization.getData().getEmails() != null ? toUTF(organization.getData().getEmails()) : "Не указано");
            //organization.getData().getManagement().setName(organization.getData().getManagement().getName() != null ? toUTF(organization.getData().getManagement().getName()) : "Не указано");
           // organization.getData().getManagement().setPost(organization.getData().getManagement().getPost() != null ? toUTF(organization.getData().getManagement().getPost()) : "Не указано");
            organization.getData().getOpf().setType(organization.getData().getOpf().getType() != null ? organization.getData().getOpf().getType() : "Не указано");
            organization.getData().getOpf().setFull(organization.getData().getOpf().getFull() != null ? organization.getData().getOpf().getFull() : "Не указано");
            organization.getData().getName().setFull(organization.getData().getName().getFull() != null ? organization.getData().getName().getFull() : "Не указано");
            organization.getData().getName().setFull_with_opf(organization.getData().getName().getFull_with_opf() != null ? organization.getData().getName().getFull_with_opf() : "Не указано");
            organization.getData().getName().setShort_with_opf(organization.getData().getName().getShort_with_opf() != null ? organization.getData().getName().getShort_with_opf() : "Не указано");
            organization.getData().getAddress().setValue(organization.getData().getAddress().getValue() != null ? organization.getData().getAddress().getValue() : "Не указано");

        }

        return suggestions;
    }

    @Override
    public String gettingUpDate() {
        Document html = null;


        try {
            html = Jsoup.connect("https://dadata.ru/clean/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

             String[] servNames = html.body().getElementsByClass("col-xs-7").text().split(" ");
             String[] servUpdates = html.body().getElementsByClass("col-xs-5").text().split(" ");
             Map<String, String> dictionares = new HashMap<>();

        System.out.println(servUpdates[3]);
            return servUpdates[3];
        }



}
