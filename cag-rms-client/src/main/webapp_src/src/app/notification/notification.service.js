(function () {
  "use strict";

  var messages = [];

  angular.module('cag-rms-client')
    .directive('notificationTray', DirectiveFactory)
    .service('notificationService', function ($timeout) {
      cleanMessages();
      return new Service($timeout);

      function cleanMessages() {
        var now = new Date().getTime();
        _.remove(messages, function(msg){
          return now - msg.time > 10000;
        });
        $timeout(cleanMessages, 10000);
      }
    });

  function Service($timeout) {
    this.showErrorMessage = function (message) {
      addMessage('error', message);
    };
    this.showInfoMessage = function (message) {
      addMessage('info', message);
    };

    function addMessage(type, m) {
      // Async
      $timeout(function(){
        messages.unshift({type: type, msg: m, time: new Date().getTime()});
        if (messages.length > 4) {
          messages.pop();
        }
      },0);
    }
  }

  function DirectiveFactory() {
    return {
      scope: {},
      restrict: 'E',
      link: function (scope, elems, attrs) {
        scope.alerts = messages;
        scope.close = function (index) {
          messages.splice(index, 1);
        };
      },
      templateUrl: 'notification/notification.tpl.html'
    };
  }
}());