package io.github.kpharish06.whatsappapi.dto;

import io.github.kpharish06.whatsappapi.entity.AttachmentType;

public class AttachmentResponse {

    private String path;
    private AttachmentType type;

    public AttachmentResponse(String path, AttachmentType type) {
        this.path = path;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public AttachmentType getType() {
        return type;
    }
}
