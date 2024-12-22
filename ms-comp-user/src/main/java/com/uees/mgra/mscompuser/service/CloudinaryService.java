package com.uees.mgra.mscompuser.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uees.mgra.mscompuser.models.dto.UpdateClientRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    private final static Map<String, String> configCloudinary = new HashMap<>();

    public CloudinaryService(@Value("${cloudinary.name}") String cloudName,
                             @Value("${cloudinary.apiKey}")
                             String apiKey,
                             @Value("${cloudinary.apiSecret}")
                             String apiSecret) {
        configCloudinary.put("cloud_name", cloudName);
        configCloudinary.put("api_key", apiKey);
        configCloudinary.put("api_secret", apiSecret);
        cloudinary = new Cloudinary(configCloudinary);
    }

    public Map upload(UpdateClientRequest user) throws IOException {
        File file = convert(user);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        file.delete();
        return result;
    }

    public Map delete(String id) throws IOException {
        Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;
    }

    private File convert(UpdateClientRequest user) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(user.image());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        String filename = UUID.randomUUID() + formattedDateTime + ".jpg";
        File file = new File(filename);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(decodedBytes);
        }
        return file;
    }
}
