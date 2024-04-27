package com.example.todo.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.todo.entity.Project;
import com.example.todo.entity.Todo;
import com.example.todo.repository.ProjectRepository;
import com.example.todo.repository.TodoRepository;
import com.example.todo.entity.GistFile;
import com.example.todo.entity.GistRequest;

@RestController
@RequestMapping("/api/gist")
public class GistController1 {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Value("${github.token}")
    private String githubToken;
    

    @GetMapping("/create/{projectId}")
    public ResponseEntity<String> createGist(@PathVariable Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found.");
        }

        List<Todo> todos = todoRepository.findByProjectId(projectId);

        String gistContent = "# " + project.getTitle() + "\n\n";
        gistContent += "Summary: " + countCompletedTodos(todos) + " / " + todos.size() + " completed.\n\n";

        gistContent += "## Pending Todos\n";
        gistContent += generateTaskLists(todos, false);

        gistContent += "\n## Completed Todos\n";
        //gistContent += generateTaskList(todos, true);
        gistContent += generateCompletedTaskList(todos,true);

        String url = "https://api.github.com/gists";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(githubToken);

        GistFile markdownFile = new GistFile();
        markdownFile.setContent(gistContent);

        
        
        GistRequest gistRequest = new GistRequest();
        gistRequest.setDescription("Project Todo List");
        gistRequest.setPublics(false);
        //gistRequest.setFiles(Map.of("ProjectTitle.md", markdownFile));

        Map<String, GistFile> filesMap = new HashMap<>();
     // Assuming project.title provides the desired file name
        String fileName = project.getTitle()+ ".md";
        //code to write file into local system
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(gistContent);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save gist file.");
        }
        //............................................
        filesMap.put(fileName, markdownFile);

       // filesMap.put("ProjectTitle.md", markdownFile);
        gistRequest.setFiles(filesMap);
        HttpEntity<GistRequest> requestEntity = new HttpEntity<>(gistRequest, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        return responseEntity;
    }

    private int countCompletedTodos(List<Todo> todos) {
        int count = 0;
        for (Todo todo : todos) {
            if (todo.isStatus()) {
                count++;
            }
        }
        return count;
    }

    /*private String generateTaskList(List<Todo> todos, boolean completed) {
        StringBuilder taskList = new StringBuilder();
        for (Todo todo : todos) {
            if (todo.isStatus() == completed) {
                taskList.append("- [").append(todo.isStatus() ? "x" : " ").append("] ").append(todo.getDescription()).append("\n");
            }
        }
        return taskList.toString();
    }*/
    
    private String generateCompletedTaskList(List<Todo> todos, boolean completed) {
        StringBuilder taskList = new StringBuilder();
        for (Todo todo : todos) {
            if (todo.isStatus() == completed) { // Make sure this condition is correct
                taskList.append("- [x] ").append(todo.getDescription()).append("\n");
            }
        }
       
        return taskList.toString();
    }


    private String generateTaskLists(List<Todo> todos, boolean completed) {
        StringBuilder taskList = new StringBuilder();
        for (Todo todo : todos) {
            if (todo.isStatus() == completed) {
                taskList.append("- [").append(todo.isStatus() ? "x" : " ").append("] ").append(todo.getDescription()).append("\n");
            }
        }
        return taskList.toString();
    }
}
