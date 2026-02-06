package com.synapseassignment.fnol.model;

import java.util.List;
import java.util.Map;

public record ClaimResult(
        Map<String, String> fields,
        List<String> missingFields,
        String route,
        String explanation
) {}
