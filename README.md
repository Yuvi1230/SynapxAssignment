# FNOL Processor (Java 21 + Spring Boot)

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
curl -X POST http://localhost:8080/claims/process -F "file=@sample.pdf"
```

## Output

Returns JSON:

```json
{
  "fields": {
    "policyNumber": "ABC123",
    "dateOfLoss": "2024-12-01",
    "estimatedDamage": "20000",
    "description": "Rear-end collision"
  },
  "missingFields": [],
  "route": "Fast-track",
  "explanation": "Damage below 25,000 and all required fields present"
}
```
