package com.synapseassignment.fnol.service;

import org.springframework.web.multipart.MultipartFile;

public interface PdfExtractor {
    String extractText(MultipartFile file);
}
