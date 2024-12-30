package com.example.A1.services;





import com.example.A1.entities.Post;
import com.example.A1.entities.User;
import com.example.A1.repository.PostRepository;
import com.example.A1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String IMAGE_UPLOAD_DIR = "uploaded_images/";

    public Post createPost(Long userId, MultipartFile image) throws IOException {
        // Ensure user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Save image
        Path uploadPath = Paths.get(IMAGE_UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Save post in DB
        Post post = new Post();
        post.setImagePath(fileName);
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        return postRepository.save(post);
    }

    public List<Post> getAllPosts(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size)).getContent();
    }
    
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    

	public byte[] getImage(String fileName) 
		// TODO Auto-generated method stub
		// Retrieve image as byte array
		 throws IOException {
			File file = new File(IMAGE_UPLOAD_DIR + fileName);
			if (!file.exists()) {
				throw new IOException("File not found: " + fileName);
			}

			return java.nio.file.Files.readAllBytes(file.toPath());
		}
	}


//package com.example.A1.services;
//
//import com.example.A1.entities.Post;
//import com.example.A1.entities.User;
//import com.example.A1.repository.PostRepository;
//import com.example.A1.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.*;
//import java.time.LocalDateTime;
//import java.util.Base64;
//import java.util.List;
//
//@Service
//public class PostService {
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private static final String IMAGE_UPLOAD_DIR = "uploaded_images/";
//
//    public Post createPost(Long userId, MultipartFile image) throws IOException {
//        // Ensure user exists
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Save image
//        Path uploadPath = Paths.get(IMAGE_UPLOAD_DIR);
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
//        Path filePath = uploadPath.resolve(fileName);
//        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        // Convert image to Base64
//        String base64Image = convertImageToBase64(filePath);
//
//        // Save post in DB
//        Post post = new Post();
//        post.setImagePath(base64Image); // Store Base64 string in DB
//        post.setCreatedAt(LocalDateTime.now());
//        post.setUser(user);
//
//        return postRepository.save(post);
//    }
//
//    public List<Post> getAllPosts(int page, int size) {
//        return postRepository.findAll(PageRequest.of(page, size)).getContent();
//    }
//
//    private String convertImageToBase64(Path filePath) throws IOException {
//        byte[] fileBytes = Files.readAllBytes(filePath);
//        return Base64.getEncoder().encodeToString(fileBytes);
//    }
//}
//
//
//
