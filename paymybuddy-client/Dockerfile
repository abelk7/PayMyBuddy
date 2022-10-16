FROM node:16-slim
WORKDIR /app
COPY package.json ./
RUN npm install -g @angular/cli@14.2.4
RUN npm install
EXPOSE 4200
CMD ["ng", "serve", "--host", "0.0.0.0", "--poll", "1"]

# sudo chown -R $USER:$(id -gn $USER) ./*
