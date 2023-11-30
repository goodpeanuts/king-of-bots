package com.kingofboss.backend.service.user.feedback;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface AddFeedbackService {
    Map<String, String> add(MultipartFile file, Map<String, String> data);

}
