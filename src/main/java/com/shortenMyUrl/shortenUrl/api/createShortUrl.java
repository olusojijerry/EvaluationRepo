package com.shortenMyUrl.shortenUrl.api;

import com.google.common.hash.Hashing;
import com.shortenMyUrl.shortenUrl.api.dao.UrlRespository;
import com.shortenMyUrl.shortenUrl.api.exception.shortenerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class createShortUrl {

    private final UrlRespository urlRespository;

    @Autowired
    public createShortUrl(UrlRespository urlRespository) {
        this.urlRespository = urlRespository;
    }

    public String shortenURL(String longUrl) {

        Long id = urlRespository.incrementID();
        String uniqueID = Hashing.murmur3_32().hashString(longUrl, StandardCharsets.UTF_8).toString();
        urlRespository.saveUrl("url:"+id, longUrl);
        //String baseString = formatLocalURLFromShortener(localURL);
        String shortenedURL = uniqueID;
        return shortenedURL;
    }

    public String getLongURLFromID(String uniqueID) throws Exception {
        String longUrl = urlRespository.getUrl(uniqueID);

        return longUrl;
    }

    private String formatLocalURLFromShortener(String localURL) {
        String[] addressComponents = localURL.split("/");
        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < addressComponents.length - 1; ++i) {
//            sb.append(addressComponents[i]);
//        }
        //sb.append('');
        return sb.toString();
    }
}
