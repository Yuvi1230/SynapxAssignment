# FNOL Processor (Java 21 + Spring Boot)

## Approach

1. Accept FNOL PDF via `POST /claims/process` as multipart form data.
2. Extract text using Apache PDFBox.
3. Extract fields by label matching for all required sections.
4. Validate that all mandatory fields are present.
5. Route the claim based on rules (missing fields → Manual Review; fraud keyword → Investigation; low damage → Fast-track).
6. Return structured JSON with fields, missing fields, route, and explanation.

## Run

```powershell
mvn spring-boot:run
```

## API

`POST /claims/process` (multipart form)

- form field: `file`
- content type: `multipart/form-data`

Example:

```powershell
curl -X POST http://localhost:8080/claims/process -F "file=@sample-fnol.pdf"
```

## Output

Sample (from `sample-fnol.pdf`):

```json
{
  "fields": {
    "policyNumber": "ABC123456",
    "policyholderName": "Alex Carter",
    "effectiveDates": "2025-01-01 to 2025-12-31",
    "incidentDate": "2025-01-15",
    "incidentTime": "14:30",
    "location": "123 Main St, Austin, TX",
    "description": "Rear-end collision, no injuries.",
    "claimant": "Alex Carter",
    "thirdParties": "Driver of blue sedan",
    "contactDetails": "555-123-4567, alex@example.com, 123 Main St, Austin, TX",
    "assetType": "Vehicle",
    "assetId": "VIN 1HGCM82633A004352",
    "estimatedDamage": "20000",
    "claimType": "Auto",
    "attachments": "Photos, Repair Estimate",
    "initialEstimate": "18000"
  },
  "missingFields": [],
  "route": "Fast-track",
  "explanation": "Damage below 25,000 and all required fields present"
}
```

Sample (from `sample-fnol-2.pdf`):

```json
{
  "fields": {
    "attachments": "Photos, Police Report",
    "policyNumber": "QZ555888",
    "description": "Minor collision at traffic light, no injuries.",
    "policyholderName": "Priya Sharma",
    "contactDetails": "555-333-7788, priya@example.com, 220 Market St, San Jose, CA",
    "assetType": "Vehicle",
    "claimType": "Auto",
    "thirdParties": "Driver of white SUV",
    "assetId": "VIN 3HGCM82633A009999",
    "estimatedDamage": "15000",
    "location": "220 Market St, San Jose, CA",
    "incidentTime": "11:45",
    "incidentDate": "2026-02-05",
    "claimant": "Priya Sharma",
    "effectiveDates": "2026-01-01 to 2026-12-31",
    "initialEstimate": "14500"
  },
  "missingFields": [],
  "route": "Fast-track",
  "explanation": "Damage below 25,000 and all required fields present"
}
```
