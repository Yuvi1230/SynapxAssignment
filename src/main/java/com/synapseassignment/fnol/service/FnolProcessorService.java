package com.synapseassignment.fnol.service;

import com.synapseassignment.fnol.model.ClaimResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class FnolProcessorService {
    private final PdfExtractor pdfExtractor;
    private final FieldExtractor fieldExtractor;
    private final ValidationService validationService;
    private final RoutingService routingService;
    private final ExplanationService explanationService;

    public FnolProcessorService(
            PdfExtractor pdfExtractor,
            FieldExtractor fieldExtractor,
            ValidationService validationService,
            RoutingService routingService,
            ExplanationService explanationService
    ) {
        this.pdfExtractor = pdfExtractor;
        this.fieldExtractor = fieldExtractor;
        this.validationService = validationService;
        this.routingService = routingService;
        this.explanationService = explanationService;
    }

    public ClaimResult process(MultipartFile file) {
        String text = pdfExtractor.extractText(file);
        Map<String, String> fields = fieldExtractor.extract(text);
        List<String> missing = validationService.missingFields(fields);
        String route = routingService.route(fields, !missing.isEmpty());
        String explanation = explanationService.explain(fields, missing, route);

        return new ClaimResult(fields, missing, route, explanation);
    }
}
