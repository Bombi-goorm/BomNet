FROM node:18-slim AS builder
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install
COPY . .

RUN npm run build

FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html
COPY app/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 8185
ENTRYPOINT ["nginx", "-g", "daemon off;"]
