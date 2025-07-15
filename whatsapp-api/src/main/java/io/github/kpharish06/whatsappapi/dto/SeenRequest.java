package io.github.kpharish06.whatsappapi.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeenRequest {
	
    private UUID messageId;
}
