FROM node:20-bullseye-slim AS build
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod

### STAGE 2: Run ###
FROM nginx:alpine
COPY --from=build /app/dist/front-proyecto /usr/share/nginx/html


