package com.url.shortner.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Slf4j
@Repository
public class UrlRepository {
    private final Jedis jedis;
    private final String idKey;
    private final String urlKey;

    public UrlRepository(Jedis jedis, String idKey, String urlKey) {
        this.jedis = new Jedis();
        this.idKey = "id";
        this.urlKey = "url:";
    }

    public Long incrementId(){
        long id=jedis.incr(idKey);
        log.info("incrementing id : {}",id-1);
        return id-1;
    }

    public void saveUrl(String key,String longUrl){ //since we convert the incremented id into uniqueID (IDConverter) string and pass here
        log.info("saving url {} at {}",longUrl,key);
        jedis.hset(urlKey,key,longUrl); //this method receives key as url:{id}
    }

    public String getUrl(Long id){//since we convert String of uniqueId back to long id and pass into this method
        String longUrl=jedis.hget(urlKey,"url:"+id);
        if(longUrl==null){
            throw new RuntimeException("URL not found for id : "+id);
        }
        return longUrl;

    }

}
