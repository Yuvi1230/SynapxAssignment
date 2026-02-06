package com.synapseassignment.fnol.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SimpleFieldExtractor implements FieldExtractor {
    @Override
    public Map<String, String> extract(String text) {
        Map<String, String> fields = new HashMap<>();

        fields.put("policyNumber", extractByLabels(text, List.of("POLICY NUMBER", "POLICY NO")));
        fields.put("policyholderName", extractByLabels(text, List.of("POLICYHOLDER NAME", "INSURED NAME", "NAME OF INSURED")));
        fields.put("effectiveDates", extractByLabels(text, List.of("EFFECTIVE DATES", "POLICY PERIOD", "EFFECTIVE DATE")));

        fields.put("incidentDate", extractByLabels(text, List.of("INCIDENT DATE", "DATE OF LOSS", "LOSS DATE")));
        fields.put("incidentTime", extractByLabels(text, List.of("INCIDENT TIME", "TIME OF LOSS", "LOSS TIME")));
        fields.put("location", extractByLabels(text, List.of("LOCATION", "LOSS LOCATION", "ACCIDENT LOCATION", "ADDRESS OF LOSS")));
        fields.put("description", extractByLabels(text, List.of("DESCRIPTION", "LOSS DESCRIPTION", "ACCIDENT DESCRIPTION")));

        fields.put("claimant", extractByLabels(text, List.of("CLAIMANT", "CLAIMANT NAME")));
        fields.put("thirdParties", extractByLabels(text, List.of("THIRD PARTIES", "OTHER PARTIES", "THIRD PARTY")));
        fields.put("contactDetails", extractByLabels(text, List.of("CONTACT DETAILS", "CONTACT INFO", "CONTACT INFORMATION")));

        fields.put("assetType", extractByLabels(text, List.of("ASSET TYPE", "PROPERTY TYPE", "VEHICLE TYPE")));
        fields.put("assetId", extractByLabels(text, List.of("ASSET ID", "ASSET IDENTIFIER", "VIN", "SERIAL NUMBER")));
        fields.put("estimatedDamage", extractByLabels(text, List.of("ESTIMATE AMOUNT", "ESTIMATED DAMAGE", "ESTIMATED LOSS")));

        fields.put("claimType", extractByLabels(text, List.of("CLAIM TYPE", "LOSS TYPE", "TYPE OF CLAIM")));
        fields.put("attachments", extractByLabels(text, List.of("ATTACHMENTS", "SUPPORTING DOCS")));
        fields.put("initialEstimate", extractByLabels(text, List.of("INITIAL ESTIMATE", "INITIAL DAMAGE ESTIMATE")));

        return fields;
    }

    private String extractByLabels(String text, List<String> labels) {
        if (text == null || text.isBlank()) {
            return "";
        }

        String[] lines = text.split("\\R");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            for (String label : labels) {
                if (line.equalsIgnoreCase(label)) {
                    if (i + 1 < lines.length) {
                        return lines[i + 1].trim();
                    }
                }
                if (line.toUpperCase().startsWith(label)) {
                    String remainder = line.substring(label.length()).trim();
                    remainder = stripLeadingPunctuation(remainder);
                    if (!remainder.isBlank()) {
                        return remainder;
                    }
                }
            }
        }

        return "";
    }

    private String stripLeadingPunctuation(String value) {
        int idx = 0;
        while (idx < value.length()) {
            char c = value.charAt(idx);
            if (Character.isLetterOrDigit(c)) {
                break;
            }
            idx++;
        }
        return value.substring(idx).trim();
    }
}
