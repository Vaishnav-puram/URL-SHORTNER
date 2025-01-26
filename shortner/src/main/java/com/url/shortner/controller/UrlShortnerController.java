package com.url.shortner.controller;

import com.url.shortner.request.ShortenRequest;
import com.url.shortner.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.SocketException;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping("/tinyUrl")
public class UrlShortnerController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shortenUrl")
    public String shortenUrl(@RequestBody ShortenRequest shortenRequest, HttpServletRequest request) throws SocketException {
        String shortneUrl=urlService.shortenUrl(request.getRequestURL().toString(),shortenRequest.getUrl());
        return shortneUrl;
    }

    @GetMapping(value = "/{id}")
    public void redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {
        log.info("Received shortened url to redirect: " + id);
        String redirectUrlString = urlService.getLongUrl(id);
        log.info("Original URL: " + redirectUrlString);

        response.sendRedirect(redirectUrlString);
    }
}
