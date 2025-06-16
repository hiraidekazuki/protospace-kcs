# --- 1段目: Gradle ビルド用ステージ ---
FROM gradle:8.8-jdk21 AS build
WORKDIR /app
COPY . .  
RUN gradle clean build -x test

# --- 2段目: 実行用の軽量イメージ ---
FROM eclipse-temurin:21-alpine
COPY --from=build /app/build/libs/protospace-kcs-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod", "--debug"]