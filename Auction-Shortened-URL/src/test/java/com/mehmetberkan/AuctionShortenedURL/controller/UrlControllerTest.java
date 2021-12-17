package com.mehmetberkan.AuctionShortenedURL.controller;

import com.mehmetberkan.AuctionShortenedURL.model.Url;
import com.mehmetberkan.AuctionShortenedURL.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UrlControllerTest {

    @Mock
    UrlService urlService;

    UrlController urlController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        urlController = new UrlController(urlService);
    }

    @Test
    void createUrl() {
        int userId = 1;
        String url = "Test";

        when(urlService.createUrl(userId,url)).thenReturn(new Url(1,"Test","Test",1));

        Url result = urlService.createUrl(1,"Test");
        assertEquals("Test",result.getUrl());
    }

    @Test
    void getAllUrls() {
        Url url = new Url("Test","Test1",1);
        Url url1 = new Url("Test","Test1",2);
        List<Url> urlList = new ArrayList<>();
        urlList.add(url);
        urlList.add(url1);

        when(urlService.getAllUrls()).thenReturn(urlList);

        List<Url> result = urlController.getAllUrls();
        assertEquals(2, result.size());
    }

    @Test
    void getByUrlId() {
        int urlId = 1;

        when(urlService.getByUrlId(urlId)).thenReturn(new Url(1,"Test","Test",1));

        Url url = urlController.getByUrlId(1);
        assertEquals("Test",url.getUrl());
    }

    @Test
    void getAllByUser() {
        int userId = 1;
        Url url = new Url(1,"Test","Test",1);
        Url url1 = new Url(1,"Test","Test",1);
        List<Url> urlList = new ArrayList<>();
        urlList.add(url);
        urlList.add(url1);

        when(urlService.getAllByUserId(userId)).thenReturn(urlList);

        List<Url> result = urlController.getAllByUser(1);
        assertEquals(2, result.size());
    }

    @Test
    void getShortedenUrl() {
        String url = "Test";
        int userId = 1;

        when(urlService.getShortenedUrl(userId,url)).thenReturn("TestShortened");

        String result = urlController.getShortedenUrl(1,"Test");
        assertEquals("TestShortened",result);
    }

    @Test
    void redirect() {
        String shortenedUrl = "Test";
        String originalUrl = "OTest";

        when(urlService.getOriginalUrl(shortenedUrl)).thenReturn(originalUrl);

        ResponseEntity<Void> result = urlController.redirect("Test");
        HttpStatus status = HttpStatus.MOVED_PERMANENTLY;
        assertEquals(status,result.getStatusCode());
    }

    @Test
    void deleteUrl() {
        int urlId = 1;

        when(urlService.deleteUrl(urlId)).thenReturn("Successfully");

        String result = urlController.deleteUrl(1);
        assertEquals("Successfully", result);
    }
}