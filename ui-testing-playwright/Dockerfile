FROM mcr.microsoft.com/playwright:v1.50.1-noble
WORKDIR /app
COPY . .
RUN npm install
RUN npx playwright install
CMD [ "npx", "playwright", "test" ]