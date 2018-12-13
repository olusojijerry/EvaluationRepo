package com.shortenMyUrl.shortenUrl.api.controller;

import com.shortenMyUrl.shortenUrl.api.exception.shortenerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.shortenMyUrl.shortenUrl.api.model.*;
import com.shortenMyUrl.shortenUrl.api.createShortUrl;

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

    @RequestMapping(value = "/shortener", method=RequestMethod.POST, consumes = {"application/json"})
    public response createShortenUrl(@Validated @RequestBody urlModel urlRequest, HttpServletRequest request){
        response Response = null;
        String longUrl = urlRequest.getUrl();
        if(shortenerException.INSTANCE.validateURL(longUrl)) {
            String localURL = request.getRequestURL().toString();
            String shortenedUrl = CreateShortUrl.shortenURL(localURL, urlRequest.getUrl());

            shortStrMem.opsForValue().set(shortenedUrl, urlRequest.getUrl().toString());

            Response = new response(shortenedUrl, "Successful", null, shortStrMem.opsForValue().get(shortenedUrl));
        }

        //Response = new response(urlRequest.getUrl(), "UnSuccessful", null, "");

        //System.out.println(createShortUrl.getShortCodeFromURL("https://viralpatel.net/blogs/create-url-shortner-in-java-struts2-hibernate/"));

        return Response;
    }
    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public String redirectUrl(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {

        String redirectUrlString = shortStrMem.opsForValue().get(id);
        System.out.println(redirectUrlString);
        response.sendRedirect(redirectUrlString);
        return redirectUrlString;
    }
}
