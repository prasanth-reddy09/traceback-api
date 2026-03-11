package com.traceback.api.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private final Cloudinary cloudinary;

    public Map<String, Object> generateSignature() {

        long timeStamp = System.currentTimeMillis() / 1000L;
        String uniquePublicId = "item_" + UUID.randomUUID().toString();

        Map<String, Object> params = new HashMap<>();
        params.put("timestamp", timeStamp);
        params.put("folder", "traceback_uploads");
        params.put("public_id", uniquePublicId);

        // Generate the secure signature using your API Secret
        String signature = cloudinary.apiSignRequest(params, cloudinary.config.apiSecret);

        // Return everything the frontend Next.js app needs to make the direct upload
        Map<String, Object> response = new HashMap<>();
        response.put("signature", signature);
        response.put("timestamp", timeStamp);
        response.put("cloud_name", cloudinary.config.cloudName);
        response.put("api_key", cloudinary.config.apiKey);
        response.put("folder", "traceback_uploads");
        response.put("public_id", uniquePublicId);

        return response;
    }
}