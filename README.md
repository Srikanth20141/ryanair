# ✈️ Ryanair API Challenge

This project is designed to test the Ryanair demo API using automated tests. Below are the instructions to set up and run the project.

---

## ✅ Pre-requisites

Before starting, ensure you have the following installed:

- [Docker](https://www.docker.com/products/docker-desktop/)
- [Maven](https://maven.apache.org/download.cgi)
- Git (to clone the project)

---

## 🐳 Step 1: Load and Run the Demo App in Docker

1. Import the demo app Docker image:

   ```bash
   docker load -i api_testing_service_latest.tar.xz
  ```bash
   docker run -d -p 8900:8900 --name apiservice api_testing_service

2. Clone the project
git clone <repository_url>
cd <project_folder>


3: Run the Tests via cmd
"mvn test"


4: View the Test Report inside 
"/reports/report.html"
