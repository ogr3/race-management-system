(function () {
  "use strict";
  angular.module('cag-rms-client')
    .service('clientApiService', function ($rootScope, $resource, $q, $http, $timeout, APP_CONFIG, localStorageService) {
      var service = new Service($rootScope, $resource, $q, $http, $timeout, APP_CONFIG, localStorageService);
      return service;
    });

  function Service($rootScope, $resource, $q, $http, $timeout, APP_CONFIG, localStorageService) {
    var USER_INFO_KEY = 'cagrms.userinfo';
    var TOKEN_KEY = 'cagrms.token';
    var eventBus = new EventBus($rootScope, $timeout, APP_CONFIG);

    this.login = function (userId, password) {
      console.debug('Logging in:', userId);
      return $q.when({displayName: 'Kalle Banan', email: 'kallebanan@bageriet.se'})
        .then(function (userInfo) {
          localStorageService.set(USER_INFO_KEY, userInfo);
          localStorageService.set(TOKEN_KEY, 'abc-123');
          return userInfo;
        });
    };
    this.logout = function () {
      console.debug('Logging out');
      localStorageService.set(USER_INFO_KEY, null);
      localStorageService.set(TOKEN_KEY, null);
      return $q.when({});
    };
    this.getCurrentUser = function () {
      return localStorageService.get(USER_INFO_KEY);
    };
    this.getResults = function () {
      //$http({
      //  method: 'GET',
      //  url: 'http://localhost:10180/results'
      //})
      return $q.when(['dummy1', 'dummy2']);
    };
    this.setConnectionListener = function (connectionListener) {
      eventBus.setConnectionListener(connectionListener);
    };
    this.addEventListener = function (eventListener) {
      eventBus.addListener(eventListener);
    };
    this.removeEventListener = function (eventListener) {
      eventBus.removeListener(eventListener);
    };
  }

  function EventBus($rootScope, $timeout, APP_CONFIG) {
    var state = 'NOT_CONNECTED';
    var listeners = [];
    var connectionListener;
    checkConnection();

    this.setConnectionListener = function (l) {
      connectionListener = l;
    };

    this.addListener = function (listener) {
      listeners.push(listener);
    };

    this.removeListener = function (listener) {
      _.remove(listeners, listener);
    };

    function checkConnection() {
      if (state === 'NOT_CONNECTED') {
        connect();
      }

      $timeout(checkConnection, 3000);
    }

    function connect() {
      state = 'CONNECTING';
      var wsUri = 'http://' + APP_CONFIG.clientApi + '/eventbus';
      notifyConnectionListener();

      var eventBusWS = new SockJS(wsUri);
      eventBusWS.onopen = function () {
        state = 'CONNECTED';
        notifyConnectionListener();
      };
      eventBusWS.onmessage = function (event) {
        var data = event.data;
        state = 'CONNECTED';
        console.debug('Received from WS: ', data, 'state:', state);
        _.forEach(listeners, function (l) {
          l(data);
        });
        notifyConnectionListener();
      };
      eventBusWS.onclose = function () {
        state = 'NOT_CONNECTED';
        console.debug('WS connection closed. State:', state);
        notifyConnectionListener();
      };

      function notifyConnectionListener() {
        console.debug('WS to:', wsUri, 'State:', state);

        if (connectionListener) {
          $rootScope.$apply(function () {
            connectionListener(state);
          });
        }
      }
    }

  }
}());