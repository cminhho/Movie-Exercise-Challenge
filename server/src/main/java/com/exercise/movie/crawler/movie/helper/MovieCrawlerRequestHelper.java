package com.exercise.movie.crawler.movie.helper;

import com.exercise.movie.crawler.movie.vo.MovieCrawler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class MovieCrawlerRequestHelper {

  public List<MovieCrawler> getMovieCrawlerFromStringResponse(String resultKey, String apiOutput) throws IOException {
    final JSONObject obj = new JSONObject(apiOutput);
    final JSONArray results = obj.getJSONArray(resultKey);

    ObjectMapper mapper = new ObjectMapper();
    List<MovieCrawler> movieCrawlerList = Arrays.asList(mapper.readValue(results.toString(),
        MovieCrawler[].class));
    return movieCrawlerList;
  }

  public String getStringResponse(String url) throws IOException {
    DefaultHttpClient httpClient = new DefaultHttpClient();
    HttpGet getRequest = new HttpGet(url);
    getRequest.addHeader("accept", "application/json");
    HttpResponse response = httpClient.execute(getRequest);
    int statusCode = response.getStatusLine().getStatusCode();
    if (statusCode != 200) {
      throw new RuntimeException("Failed with HTTP error code : " + statusCode);
    }
    HttpEntity httpEntity = response.getEntity();
    return EntityUtils.toString(httpEntity);
  }
}
