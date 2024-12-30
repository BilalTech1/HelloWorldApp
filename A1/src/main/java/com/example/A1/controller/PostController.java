package com.example.A1.controller;
//////
//////
////import com.example.A1.entities.Post;
////import com.example.A1.services.PostService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////import org.springframework.web.multipart.MultipartFile;
////
////import java.io.IOException;
////import java.util.List;
////
////@RestController
////@RequestMapping("/api/posts")
////public class PostController {
////
////    @Autowired
////    private PostService postService;
////
////    @PostMapping("/{userId}")
////    public ResponseEntity<Post> createPost(@PathVariable Long userId,
////                                           @RequestParam("image") MultipartFile image) {
////        try {
////            Post post = postService.createPost(userId, image);
////            return ResponseEntity.ok(post);
////        } catch (IOException e) {
////            return ResponseEntity.badRequest().build();
////        }
////    }
////
////    @GetMapping
////    public List<Post> getAllPosts(@RequestParam(defaultValue = "0") int page,
////                                  @RequestParam(defaultValue = "10") int size) {
////        return postService.getAllPosts(page, size);
////    }
////}
////
//
////import com.example.A1.entities.Post;
////import com.example.A1.services.PostService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////import org.springframework.web.multipart.MultipartFile;
////
////import java.io.IOException;
////import java.util.List;
////
////@RestController
////@RequestMapping("/api/posts")
////public class PostController {
////
////    @Autowired
////    private PostService postService;
////
////    // Create a new post with an image
////    @PostMapping("/{userId}")
////    public ResponseEntity<Post> createPost(
////            @PathVariable Long userId, 
////            @RequestParam("image") MultipartFile image) {
////        try {
////            Post post = postService.createPost(userId, image);
////            return ResponseEntity.ok(post);
////        } catch (IOException e) {
////            return ResponseEntity.badRequest().body(null);
////        } catch (RuntimeException e) {
////            return ResponseEntity.notFound().build();
////        }
////    }
////
////    // Get all posts with pagination
////    @GetMapping
////    public ResponseEntity<List<Post>> getAllPosts(
////            @RequestParam(defaultValue = "0") int page, 
////            @RequestParam(defaultValue = "10") int size) {
////        List<Post> posts = postService.getAllPosts(page, size);
////        return ResponseEntity.ok(posts);
////    }
////}
////
//
//
//import com.example.A1.entities.Post;
//import com.example.A1.services.PostService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/posts")
//public class PostController {
//
//    @Autowired
//    private PostService postService;
//
//    @PostMapping("/{userId}")
//    public ResponseEntity<Post> createPost(@PathVariable Long userId,
//                                           @RequestParam("image") MultipartFile image) {
//        try {
//            Post post = postService.createPost(userId, image);
//            return ResponseEntity.ok(post);
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @GetMapping
//    public List<Post> getAllPosts(@RequestParam(defaultValue = "0") int page,
//                                  @RequestParam(defaultValue = "10") int size) {
//        return postService.getAllPosts(page, size);
//    }
//
////    // Get an image by its file name
////    @GetMapping(value = "/image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
////    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
////        byte[] imageBytes = postService.getImage(fileName);
////		HttpHeaders headers = new HttpHeaders();
////		headers.setContentType(MediaType.IMAGE_JPEG);
////		return ResponseEntity.ok().headers(headers).body(imageBytes);
////    }
//    @GetMapping(value = "/image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
//        try {
//            byte[] imageBytes = postService.getImage(fileName);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG);
//            return ResponseEntity.ok().headers(headers).body(imageBytes);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body(null); // Return an empty response with 500 Internal Server Error
//        }
//    }
//
//    
//    
//}
//

import com.example.A1.entities.Post;
import com.example.A1.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Create a new post with an image
    @PostMapping("/{userId}")
    public ResponseEntity<Post> createPost(@PathVariable Long userId,
                                           @RequestParam("image") MultipartFile image) {
        try {
            Post post = postService.createPost(userId, image);
            return ResponseEntity.ok(post);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get all posts with pagination
    @GetMapping
    public List<Post> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return postService.getAllPosts(page, size);
    }

    // Get an image by its file name
    @GetMapping(value = "/image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        try {
            byte[] imageBytes = postService.getImage(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return ResponseEntity.ok().headers(headers).body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null); // Return an empty response with 500 Internal Server Error
        }
    }
//    
//    @GetMapping(value = "/image/{fileName}")
//    public ResponseEntity<String> getImageAsBase64(@PathVariable String fileName) {
//        try {
//            byte[] imageBytes = postService.getImage(fileName);
//            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//            return ResponseEntity.ok(base64Image);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("Error: Unable to process the image.");
//        }
//    }

    // Get a single post by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        try {
            Post post = postService.getPostById(id);
            return ResponseEntity.ok(post);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


