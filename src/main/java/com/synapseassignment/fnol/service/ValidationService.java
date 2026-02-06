package com.synapseassignment.fnol.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ValidationService {
    private static final List<String> REQUIRED_FIELDS = List.of(
            "policyNumber",
            "policyholderName",
            "effectiveDates",
            "incidentDate",
            "incidentTime",
            "location",
            "description",
            "claimant",
            "thirdParties",
            "contactDetails",
            "assetType",
            "assetId",
            "claimType",
            "attachments",
            "initialEstimate",
            "estimatedDamage"
    );

    public List<String> missingFields(Map<String, String> fields) {
        return REQUIRED_FIELDS.stream()
                .filter(f -> !fields.containsKey(f) || fields.get(f) == null || fields.get(f).isBlank())
                .toList();
    }
}
