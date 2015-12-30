FROM node

WORKDIR /app
ADD webapp_src/ /app

RUN npm install -g bower grunt
RUN echo '{ "allow_root": true }' > /root/.bowerrc
RUN npm install /app

EXPOSE 9002 9018

#/usr/local/bin/bower -> /usr/local/lib/node_modules/bower/bin/bower

#RUN find / -type f -name grunt 2>/dev/null
ENTRYPOINT ["/app/node_modules/grunt-cli/bin/grunt", "--gruntfile=/app/Gruntfile.js", "serve"]