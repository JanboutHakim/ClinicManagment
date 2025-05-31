Doctor Service API Documentation
This RESTful API manages doctor profiles, experiences, services, schedules, and associated data such as specializations, insurance companies, and availability.

🧠 Base URL
bash
Copy
Edit
/api/doctors
📘 Endpoints
🔍 Doctor Retrieval
GET /{id}
Retrieve a doctor by ID.

Response: DoctorDto

GET /all
Retrieve all doctors.

Response: List<DoctorDto>

🏠 Address Management
PUT /{id}/address
Update a doctor's address.

Request Body: AddressDto

Response: DoctorDto

🩺 Specializations
GET /{doctorId}/specializations
Get all specializations for a doctor.

Response: List<SpecializationsDto>

POST /{id}/specialization
Add a specialization.

Request Body: SpecializationsDto

Response: DoctorDto

DELETE /{doctorId}/specializations/delete/{specializationId}
Remove a specialization.

Response: DoctorDto

PUT /{doctorId}/specializations/update
Update a specialization.

Request Body: SpecializationsDto

Response: DoctorDto

📈 Experience
GET /{doctorId}/experiences
Retrieve all experiences for a doctor.

Response: List<ExperienceDto>

POST /{doctorId}/experiences
Add an experience.

Request Body: ExperienceDto

Response: DoctorDto

PUT /{doctorId}/experiences/
Update an experience.

Request Body: ExperienceDto

Response: DoctorDto

DELETE /{doctorId}/experiences/{experienceId}
Remove an experience.

Response: DoctorDto

🎓 Scientific Backgrounds
GET /{doctorId}/scientific-backgrounds
Get scientific backgrounds.

Response: List<ScientificBackgroundDto>

POST /{doctorId}/scientific-backgrounds
Add a scientific background.

Request Body: ScientificBackgroundDto

Response: DoctorDto

DELETE /{doctorId}/scientific-backgrounds/{scientificBackgroundId}
Remove a scientific background.

Response: DoctorDto

PUT /{doctorId}/scientific-backgrounds/update
Update a scientific background.

Request Body: ScientificBackgroundDto

Response: DoctorDto

🧪 Union Membership
PUT /{id}/union-membership-number
Update union membership number.

Request Body: String

Response: DoctorDto

⏳ Experience Years
PUT /{id}/years-of-experience
Update years of experience.

Request Body: ExperienceYearsDto

Response: DoctorDto

💼 Services
GET /{doctorId}/services
Get all services.

Response: List<DoctorServiceDto>

POST /{doctorId}/services
Add a service.

Request Body: DoctorServiceDto

Response: DoctorDto

PUT /{doctorId}/services/
Update a service.

Request Body: DoctorServiceDto

Response: DoctorDto

DELETE /{doctorId}/services/{serviceId}
Remove a service.

Response: DoctorDto

🕐 Schedules
GET /{doctorId}/schedules
Get regular schedules.

Response: List<DoctorScheduleDto>

POST /{doctorId}/schedules
Add a schedule.

Request Body: DoctorScheduleDto

Response: DoctorDto

PUT /{doctorId}/schedules/update
Update a schedule.

Request Body: DoctorScheduleDto

Response: DoctorDto

PUT /{doctorId}/schedules/{doctorScheduleId}
Remove a schedule.

Response: DoctorDto

🎉 Holiday Schedules
GET /{doctorId}/holiday-schedules
Get holiday schedules.

Response: List<DoctorHolidayScheduleDto>

POST /{doctorId}/holiday-schedules
Add a holiday schedule.

Request Body: DoctorHolidayScheduleDto

Response: DoctorDto

PUT /{doctorId}/holiday-schedules/update
Update a holiday schedule.

Request Body: DoctorHolidayScheduleDto

Response: DoctorDto

DELETE /{doctorId}/holiday-schedules/{doctorHolidayScheduleId}
Remove a holiday schedule.

Response: DoctorDto

🛡 Insurance Companies
GET /{doctorId}/insurance-companies
List insurance companies for a doctor.

Response: List<InsuranceCompanyDto>

POST /{doctorId}/insurance-companies
Add an insurance company.

Request Body: InsuranceCompanyDto

Response: DoctorDto

POST /{doctorId}/insurance-companies/{companyId}
Remove an insurance company.

Response: DoctorDto

📅 Appointment Slots
GET /{doctorId}/appointments
Get available appointment slots.

Response: List<LocalDateTime>

📦 DTOs Overview
Each endpoint expects and/or returns Data Transfer Objects (DTOs) such as:

DoctorDto

AddressDto

SpecializationsDto

ExperienceDto

ScientificBackgroundDto

DoctorServiceDto

DoctorScheduleDto

DoctorHolidayScheduleDto

InsuranceCompanyDto

ExperienceYearsDto

Ensure the frontend sends valid JSON objects matching these DTOs.

📬 Notes
All endpoints return a JSON-formatted response.

Input validation is enforced using @Valid where applicable.

Use appropriate HTTP methods: GET, POST, PUT, and DELETE.

🛠 Example Request
http
Copy
Edit
PUT /api/doctors/1/years-of-experience
Content-Type: application/json

{
  "years": 10
}
Response:

json
Copy
Edit
{
  "id": 1,
  "name": "Dr. Jane Doe",
  "yearsOfExperience": 10,
  ...
}
