package com.shortenMyUrl.shortenUrl.api.controller;

import com.shortenMyUrl.shortenUrl.api.exception.shortenerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.shortenMyUrl.shortenUrl.api.model.*;
import com.shortenMyUrl.shortenUrl.api.createShortUrl;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.*;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class controller {
    private final createShortUrl CreateShortUrl;

    @Autowired
    StringRedisTemplate shortStrMem;
    String shortenedUrl = "";


    public controller(createShortUrl createShortUrl) {
        CreateShortUrl = createShortUrl;
    }

    @RequestMapping(value = "", method=RequestMethod.POST, consumes = {"application/json"})
    public response createShortenUrl(@Validated @RequestBody urlModel urlRequest, HttpServletRequest request){
        response Response = null;
        String longUrl = urlRequest.getUrl();
        if(shortenerException.INSTANCE.validateURL(longUrl)) {
            String shortenedUrl = CreateShortUrl.shortenURL(urlRequest.getUrl());
            String localUrl = request.getRequestURL().toString();
            shortStrMem.opsForValue().set(shortenedUrl, localUrl);
            Response = new response(urlRequest.getUrl(), "Successful", null, localUrl+"/"+shortenedUrl);
        }
        else
            Response = new response(urlRequest.getUrl(), "UnSuccessful", null, "Bad Url");

        //System.out.println(createShortUrl.getShortCodeFromURL("https://viralpatel.net/blogs/create-url-shortner-in-java-struts2-hibernate/"));

        return Response;
    }
    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public String redirectUrl(@PathVariable String id, HttpServletResponse response) throws IOException, URISyntaxException, Exception {
        String redirectUrlString = shortStrMem.opsForValue().get(id);
        //String rds = CreateShortUrl.getLongURLFromID(id);
        //System.out.println(rds);
        response.sendRedirect(redirectUrlString);
        return redirectUrlString;
    }


//    @RequestMapping(value = "/{id}", method=RequestMethod.GET)
//    public RedirectView redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {
//        String redirectUrlString = CreateShortUrl.getLongURLFromID(id);
//        RedirectView redirectView = new RedirectView();
//        redirectView.setUrl(redirectUrlString);
//        return redirectView;
//    }
}
