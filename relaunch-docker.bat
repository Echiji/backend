docker-compose down
mvn clean package
IF %ERRORLEVEL% NEQ 0 (
    echo "Erreur lors du build Maven, arrêt du script."
    exit /b %ERRORLEVEL%
)
docker-compose up --build -d
