package com.shortenMyUrl.shortenUrl.api.dao;

import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.io.Serializable;

@Repository
public class UrlRespository {
    private final Jedis jedis;
    private final String idKey;
    private final String urlKey;

    public UrlRespository() {
        this.jedis = new Jedis();
        this.idKey = "id";
        this.urlKey = "url:";
    }

    public UrlRespository(Jedis jedis, String id, String urlKey) {
        this.jedis = new Jedis();
        this.idKey = "id";
        this.urlKey = "url:";
    }

    public Long incrementID() {
        Long id = jedis.incr(idKey);
        return id - 1;
    }

    public void saveUrl(String key, String longUrl) {
        jedis.hset(urlKey, key, longUrl);
    }

    public String getUrl(Long id) throws Exception {
        String url = jedis.hget(urlKey, "url:"+id);
        if (url == null) {
            throw new Exception("URL at key" + id + " does not exist");
        }
        return jedis.hget(urlKey, "url:"+id);
    }
}
