package com.url.shortner.service;

import com.url.shortner.repository.UrlRepository;
import com.url.shortner.util.IDConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UrlServiceImpl implements UrlService{

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public String shortenUrl(String localUrl, String longUrl) {
        long id=urlRepository.incrementId();
        String uniqueId=IDConverter.instance.createUniqueId(id);// base62 id
        log.info("unique id : "+uniqueId);
        urlRepository.saveUrl("url:"+id,longUrl); //saving long url with key url:{base62Id}
        String baseString=formatLocalUrl(localUrl);
        String shortenedUrl=baseString+uniqueId;
        log.info("shortened url : {} for long url : {} ",shortenedUrl,longUrl);
        return shortenedUrl;
    }

    private String formatLocalUrl(String localUrl) {
        String[] addressComponents=localUrl.split("/");
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<addressComponents.length-1;i++){
            sb.append(addressComponents[i]);
        }
        sb.append("/");
        return sb.toString();
    }

    @Override
    public String getLongUrl(String uniqueId) {
        long id=IDConverter.instance.getDictionaryKeyFromUniqueId(uniqueId);
        String longUrl=urlRepository.getUrl(id);
        log.info("retrieved long url : {}",longUrl);
        return longUrl;
    }
}
