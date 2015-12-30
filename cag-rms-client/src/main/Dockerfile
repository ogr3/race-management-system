FROM node

WORKDIR /app
ADD webapp_src/ /app/webapp_src

RUN npm install -g bower grunt
RUN echo '{ "allow_root": true }' > /root/.bowerrc
RUN npm install /app/webapp_src

EXPOSE 9002

ENTRYPOINT ["/app/webapp_src/node_modules/grunt-cli/bin/grunt", "--gruntfile=/app/webapp_src/Gruntfile.js", "serve"]