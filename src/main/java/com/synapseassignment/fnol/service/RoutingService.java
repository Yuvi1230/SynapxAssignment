package com.synapseassignment.fnol.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;

@Component
public class RoutingService {
    public String route(Map<String, String> fields, boolean hasMissing) {
        if (hasMissing) {
            return "Manual Review";
        }

        String description = fields.getOrDefault("description", "");
        if (description.toLowerCase(Locale.ROOT).contains("fraud")) {
            return "Investigation";
        }

        if (isInjuryClaim(fields)) {
            return "Specialist Queue";
        }

        BigDecimal damage = parseDamage(fields.get("estimatedDamage"));
        if (damage != null && damage.compareTo(new BigDecimal("25000")) < 0) {
            return "Fast-track";
        }

        return "Standard Queue";
    }

    private boolean isInjuryClaim(Map<String, String> fields) {
        String description = fields.getOrDefault("description", "");
        String lower = description.toLowerCase(Locale.ROOT);
        return lower.contains("injury") || lower.contains("hospital") || lower.contains("medical");
    }

    private BigDecimal parseDamage(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }

        String cleaned = raw.replaceAll("[^0-9.]", "");
        if (cleaned.isBlank()) {
            return null;
        }

        try {
            return new BigDecimal(cleaned);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
