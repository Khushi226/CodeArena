
package com.codearena.backend.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

@RestController
@RequestMapping("/api/learn")
public class LearnController {

    

//     @GetMapping("/problems")
// public List<Map<String,String>> getProblems() {

//     List<Map<String,String>> problems = new ArrayList<>();

//     try {

//         ClassPathResource resource = new ClassPathResource("articles");
//         File folder = resource.getFile();

//         File[] topicFolders = folder.listFiles();

//         if(topicFolders == null) return problems;

//         for(File topicFolder : topicFolders){

//             if(topicFolder.isDirectory()){

//                 String topic = topicFolder.getName();

//                 File[] files = topicFolder.listFiles();

//                 if(files == null) continue;

//                 for(File file : files){

//                     if(file.getName().endsWith(".md")){

//                         String slug = file.getName().replace(".md","");

//                         Map<String,String> problem = new HashMap<>();
//                         problem.put("title", slug.replace("-", " "));
//                         problem.put("slug", slug);
//                         problem.put("topic", topic);

//                         problems.add(problem);
//                     }
//                 }
//             }
//         }

//     } catch(Exception e){
//         e.printStackTrace();
//     }

//     return problems;
// }
@GetMapping("/problems")
public List<Map<String, String>> getProblems() {

    List<Map<String, String>> problems = new ArrayList<>();

    try {

        ClassPathResource resource = new ClassPathResource("articles");
        File folder = resource.getFile();

        for (File file : Objects.requireNonNull(folder.listFiles())) {

            if (file.getName().endsWith(".md")) {

                String slug = file.getName().replace(".md", "");

                Map<String, String> problem = new HashMap<>();
                problem.put("title", slug.replace("-", " "));
                problem.put("slug", slug);
                problem.put("topic", "General");

                problems.add(problem);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return problems;
}

 @GetMapping("/{slug}")
public ResponseEntity<String> getArticle(@PathVariable String slug) {

    try {

        File file = new File("codearena-backend/src/main/resources/articles/" + slug + ".md");

        System.out.println("Looking for file at: " + file.getAbsolutePath());

        if(!file.exists()) {
            return ResponseEntity.status(404).body("Article not found");
        }

        String content = Files.readString(file.toPath());

        return ResponseEntity.ok(content);

    } catch(Exception e) {
        return ResponseEntity.status(500).body("Error loading article");
    }
}
}