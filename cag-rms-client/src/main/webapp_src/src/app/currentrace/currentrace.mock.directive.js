'use strict';
(function () {
  angular.module('cag-rms-client').directive('currentRaceMock', ['$http', factory]);

  function factory($http) {
    return {
      scope: true,
      restrict: 'E',
      templateUrl: 'currentrace/currentrace.mock.tpl.html',
      //require: '^currentRace',
      replace: 'true',
      controller: Ctrl,
      controllerAs: 'currentmock',
      link: function(scope, elem, attrs) {
      }
    };
  }

  function Ctrl($http) {
      var mock = this;
      mock.start = start;
      mock.firstSensor = firstSensor;
      mock.secondSensor = secondSensor;
      mock.thirdSensor = thirdSensor;
      mock.cancel = cancel;


      function start() {
        $http({
            method: 'POST',
            url: 'http://localhost:10080/startRace',
            params: {callbackUrl: 'http://localhost:10080/onracestatusupdate'}
        }).then(function successCallback(response) {
            console.debug(response.data);
        }, function errorCallback(response) {
        });
        console.debug('START RACE');
      }

      function firstSensor() {
        $http({
            method: 'POST',
            url: 'http://localhost:10080/passageDetected',
            params: {sensorID: 'START', timestamp: new Date().getTime()}
        }).then(function successCallback(response) {
            console.debug(response.data);
        }, function errorCallback(response) {
        });
        console.debug('FIRST SENSOR');
      }

      function secondSensor() {
        $http({
            method: 'POST',
            url: 'http://localhost:10080/passageDetected',
            params: {sensorID: 'SPLIT', timestamp: new Date().getTime()}
        }).then(function successCallback(response) {
            console.debug(response.data);
        }, function errorCallback(response) {
        });

        console.debug('SECOND SENSOR');
      }

      function thirdSensor() {
        $http({
            method: 'POST',
            url: 'http://localhost:10080/passageDetected',
            params: {sensorID: 'FINISH', timestamp: new Date().getTime()}
        }).then(function successCallback(response) {
            console.debug(response.data);
        }, function errorCallback(response) {
        });

        console.debug('THIRD SENSOR');
      }

      function cancel() {
        $http({
            method: 'POST',
            url: 'http://localhost:10080/cancelRace'
        }).then(function successCallback(response) {
            console.debug(response.data);
        }, function errorCallback(response) {
        });
        console.debug('CANCEL RACE');
      }
    }
}());
