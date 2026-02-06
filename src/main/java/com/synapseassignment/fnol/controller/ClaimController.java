package com.synapseassignment.fnol.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.synapseassignment.fnol.model.ClaimResult;
import com.synapseassignment.fnol.service.FnolProcessorService;

@RestController
@RequestMapping("/claims")
public class ClaimController {
    private final FnolProcessorService processorService;

    public ClaimController(FnolProcessorService processorService) {
        this.processorService = processorService;
    }

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ClaimResult> process(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(processorService.process(file));
    }
}
// sample fnol
// {
//     "fields": {
//         "dateOfLoss": "2025-01-15",
//         "estimatedDamage": "20000",
//         "policyNumber": "ABC123456",
//         "description": "Rear-end collision, no injuries."
//     },
//     "missingFields": [],
//     "route": "Fast-track",
//     "explanation": "Damage below 25,000 and all required fields present"
// }

// sample fnol fraud
// {
//     "fields": {
//         "dateOfLoss": "2025-02-03",
//         "estimatedDamage": "48000",
//         "policyNumber": "FRD123999",
//         "description": "Possible fraud flagged by adjuster."
//     },
//     "missingFields": [],
//     "route": "Investigation",
//     "explanation": "Potential fraud keyword detected in description"
// }
// sample fnol missing 
// {
//     "fields": {
//         "dateOfLoss": "2025-02-01",
//         "estimatedDamage": "",
//         "policyNumber": "ZX987654",
//         "description": "Water damage in basement."
//     },
//     "missingFields": [
//         "estimatedDamage"
//     ],
//     "route": "Manual Review",
//     "explanation": "Missing required fields: estimatedDamage"
// }
// sample fnol 2

// {
//     "fields": {
//         "attachments": "Photos, Police Report",
//         "policyNumber": "QZ555888",
//         "description": "Minor collision at traffic light, no injuries.",
//         "policyholderName": "Priya Sharma",
//         "contactDetails": "555-333-7788, priya@example.com, 220 Market St, San Jose, CA",
//         "assetType": "Vehicle",
//         "claimType": "Auto",
//         "thirdParties": "Driver of white SUV",
//         "assetId": "VIN 3HGCM82633A009999",
//         "estimatedDamage": "15000",
//         "location": "220 Market St, San Jose, CA",
//         "incidentTime": "11:45",
//         "incidentDate": "2026-02-05",
//         "claimant": "Priya Sharma",
//         "effectiveDates": "2026-01-01 to 2026-12-31",
//         "initialEstimate": "14500"
//     },
//     "missingFields": [],
//     "route": "Fast-track",
//     "explanation": "Damage below 25,000 and all required fields present"
// }