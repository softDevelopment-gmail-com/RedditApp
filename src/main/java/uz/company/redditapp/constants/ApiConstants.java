package uz.company.redditapp.constants;

public interface ApiConstants {

    String api = "/api";
    String version = "/v1";
    String rootApi = api + version;

    String auth = rootApi + "/auth";
    String signUp = "/sign-up";
    String verify = "/verify";
    String login = "/login";
    String subreddit = "/subreddit";
}
