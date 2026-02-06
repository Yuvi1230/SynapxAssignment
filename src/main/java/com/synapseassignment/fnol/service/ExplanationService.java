package com.synapseassignment.fnol.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ExplanationService {
    public String explain(Map<String, String> fields, List<String> missingFields, String route) {
        if (!missingFields.isEmpty()) {
            return "Missing required fields: " + String.join(", ", missingFields);
        }

        String description = fields.getOrDefault("description", "").toLowerCase();
        if (description.contains("fraud")) {
            return "Potential fraud keyword detected in description";
        }

        if (route.equals("Specialist Queue")) {
            return "Injury-related claim routed to specialist";
        }

        if (route.equals("Fast-track")) {
            return "Damage below 25,000 and all required fields present";
        }

        return "All required fields present; routed to standard queue";
    }
}
